package org.uma.cloud.stream.service;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.uma.cloud.common.configuration.JvLinkDeserializer;
import org.uma.cloud.common.model.RacingDetails;
import org.uma.cloud.common.model.odds.Exacta;
import org.uma.cloud.common.model.odds.Quinella;
import org.uma.cloud.common.model.odds.QuinellaPlace;
import org.uma.cloud.common.model.odds.Trifecta;
import org.uma.cloud.common.model.odds.Trio;
import org.uma.cloud.common.model.odds.WinsPlaceBracketQuinella;
import org.uma.cloud.common.utils.lang.DateUtil;
import org.uma.cloud.stream.configuration.WebClientConfiguration.JvLinkWebClientException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class JvLinkWebService {

    @Autowired
    private WebClient webClient;

    @Autowired
    private JvLinkDeserializer jvLinkDeserializer;

    // レースID
    private static final String RACE_ID = "raceId";

    // 今週のレース情報
    // TODO: 今週金曜日時点のデータを取得
    private static final String racingDetailsWithFriday = "/racingDetails/{epochMilli}";

    // 単勝・複勝・枠連
    private static final String winsPlaceBracketQuinella = "/winsPlaceBracketQuinella";

    // 馬連
    private static final String quinella = "/quinella";

    // ワイド
    private static final String quinellaPlace = "/quinellaPlace";

    // 馬単
    private static final String exacta = "/exacta";

    // 三連複
    private static final String trio = "/trio";

    // 三連単
    private static final String trifecta = "/trifecta";

    // 馬連時系列
    private static final String timeseriesWinsPlaceBracketQuinella = "/timeseries/winsPlaceBracketQuinella";

    // 馬連時系列
    private static final String timeseriesQuinella = "/timeseries/quinella";


    private void errorHandle(Throwable throwable) {
        if (throwable instanceof JvLinkWebClientException) {
            if (HttpStatus.NOT_FOUND == ((JvLinkWebClientException) throwable).getHttpStatus()) {
                log.warn(throwable.getMessage());
            } else {
                // リクエスト形式に誤りあり。
                log.error("クライアントのリクエストに問題あり : ", throwable);
            }
        } else {
            // サーバ側に問題あり。
            log.error("JvLinkWebServiceでエラー発生: ", throwable);
        }
    }


    private Mono<String> findOneByRaceId(String path, String raceId) {
        return webClient.get().uri(uriBuilder -> uriBuilder
                .path(path)
                .queryParam(RACE_ID, raceId)
                .build())
                .retrieve()
                .bodyToMono(ExternalResponse.class)
                .map(ExternalResponse::getData)
                .doOnError(this::errorHandle);
    }

    private Flux<String> findAllByRaceId(String path, String raceId) {
        return webClient.get().uri(uriBuilder -> uriBuilder
                .path(path)
                .queryParam(RACE_ID, raceId)
                .build())
                .retrieve()
                .bodyToFlux(ExternalResponse.class)
                .map(ExternalResponse::getData)
                .doOnError(this::errorHandle);
    }

    /**
     * 今週金曜日を基準日として設定して、蓄積系のracingDetailsを叩く。
     * 欲しいデータがとれている！
     * @return
     */
    public Flux<RacingDetails> raceDetailsWithFriday() {
        return webClient.get().uri(uriBuilder -> uriBuilder
                .path(racingDetailsWithFriday)
                .build(DateUtil.thisFriday()))
                .retrieve()
                .bodyToFlux(ExternalResponse.class)
                .map(ExternalResponse::getData)
                .map(jvLinkDeserializer.decode()
                        .andThen(jvLinkDeserializer::racingDetailsFunction))
                .doOnError(this::errorHandle);
    }


    public Mono<WinsPlaceBracketQuinella> winsPlaceBracketQuinella(String raceId) {
        return findOneByRaceId(winsPlaceBracketQuinella, raceId)
                .map(jvLinkDeserializer.decode()
                        .andThen(jvLinkDeserializer::winsPlaceBracketQuinellaFunction));
    }

    public Mono<Quinella> quinella(String raceId) {
        return findOneByRaceId(quinella, raceId)
                .map(jvLinkDeserializer.decode()
                        .andThen(jvLinkDeserializer::quinellaFunction));
    }

    public Mono<QuinellaPlace> quinellaPlace(String raceId) {
        return findOneByRaceId(quinellaPlace, raceId)
                .map(jvLinkDeserializer.decode()
                        .andThen(jvLinkDeserializer::quinellaPlaceFunction));
    }

    public Mono<Exacta> exacta(String raceId) {
        return findOneByRaceId(exacta, raceId)
                .map(jvLinkDeserializer.decode()
                        .andThen(jvLinkDeserializer::exactaFunction));
    }

    public Mono<Trio> trio(String raceId) {
        return findOneByRaceId(trio, raceId)
                .map(jvLinkDeserializer.decode()
                        .andThen(jvLinkDeserializer::trioFunction));
    }

    public Mono<Trifecta> trifecta(String raceId) {
        return findOneByRaceId(trifecta, raceId)
                .map(jvLinkDeserializer.decode()
                        .andThen(jvLinkDeserializer::trifectaFunction));
    }

    public Flux<WinsPlaceBracketQuinella> timeseriesWinsPlaceBracketQuinella(String raceId) {
        return findAllByRaceId(timeseriesWinsPlaceBracketQuinella, raceId)
                .map(jvLinkDeserializer.decode()
                        .andThen(jvLinkDeserializer::winsPlaceBracketQuinellaFunction));
    }

    public Flux<Quinella> timeseriesQuinella(String raceId) {
        return findAllByRaceId(timeseriesQuinella, raceId)
                .map(jvLinkDeserializer.decode()
                        .andThen(jvLinkDeserializer::quinellaFunction));
    }


    @Getter
    public static class ExternalResponse {

        private final String data;

        private final String message;

        @JsonCreator
        public ExternalResponse(
                @JsonProperty("data") String data,
                @JsonProperty("message") String message) {
            this.data = data;
            this.message = message;
        }
    }

}
