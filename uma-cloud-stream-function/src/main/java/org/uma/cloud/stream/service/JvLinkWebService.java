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
import org.uma.cloud.common.model.RacingDetail;
import org.uma.cloud.common.model.RacingHorseDetail;
import org.uma.cloud.common.model.RacingRefund;
import org.uma.cloud.common.model.event.Avoid;
import org.uma.cloud.common.model.event.CourseChange;
import org.uma.cloud.common.model.event.JockeyChange;
import org.uma.cloud.common.model.event.TimeChange;
import org.uma.cloud.common.model.event.Weather;
import org.uma.cloud.common.model.event.Weight;
import org.uma.cloud.common.model.odds.Exacta;
import org.uma.cloud.common.model.odds.Quinella;
import org.uma.cloud.common.model.odds.QuinellaPlace;
import org.uma.cloud.common.model.odds.Trifecta;
import org.uma.cloud.common.model.odds.Trio;
import org.uma.cloud.common.model.odds.WinsShowBracketQ;
import org.uma.cloud.common.utils.lang.DateUtil;
import org.uma.cloud.common.utils.lang.ModelUtil;
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


    // 今週のレース情報
    private static final String racingDetailWithFriday = "/racingDetail/{epochMilli}";


    // レースID
    private static final String RACE_ID = "raceId";

    // レース詳細情報
    private static final String racingDetail = "/racingDetail";

    // 馬毎レース詳細情報
    private static final String racingHorseDetail = "/racingHorseDetail";

    // 単勝・複勝・枠連
    private static final String winsShowBracketQ = "/winsShowBracketQ";

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
    private static final String timeseriesWinsShowBracketQ = "/timeseries/winsShowBracketQ";

    // 馬連時系列
    private static final String timeseriesQuinella = "/timeseries/quinella";

    ///// イベント通知 /////

    // レース払戻(確定)
    private static final String raceRefund = "/event/racingRefund";

    // 馬体重
    private static final String weight = "/event/weight";

    // 騎手変更
    private static final String jockeyChange = "/event/jockeyChange";

    // 天候馬場状態変更
    private static final String weather = "/event/weather";

    // コース変更
    private static final String courseChange = "/event/courseChange";

    // 出走取り消し・競争除外
    private static final String avoid = "/event/avoid";

    // 発走時刻変更
    private static final String timeChange = "/event/timeChange";


    private Mono<String> errorHandle(Throwable throwable) {
        if (throwable instanceof JvLinkWebClientException) {
            if (HttpStatus.NOT_FOUND == ((JvLinkWebClientException) throwable).getHttpStatus()) {
                // 該当データなし。
                log.warn(throwable.getMessage());
            } else {
                // リクエスト形式に誤りあり。
                log.error("クライアントのリクエストに問題あり : ", throwable);
            }
        } else {
            // サーバ側に問題あり。
            log.error("JvLinkWebServiceでエラー発生: ", throwable);
        }
        return Mono.empty();
    }


    private Mono<String> findOneByRaceId(String path, String raceId) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(path)
                        .queryParam(RACE_ID, raceId)
                        .build())
                .retrieve()
                .bodyToMono(ExternalResponse.class)
                .map(ExternalResponse::getData)
                .onErrorResume(this::errorHandle);
    }

    private Flux<String> findAllByRaceId(String path, String raceId) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(path)
                        .queryParam(RACE_ID, raceId)
                        .build())
                .retrieve()
                .bodyToFlux(ExternalResponse.class)
                .map(ExternalResponse::getData)
                .onErrorResume(this::errorHandle);
    }

    /**
     * 今週金曜日を基準日として設定して、蓄積系のracingDetailsを叩く。
     * データ区分＝2
     * ただし、weatherCd, turfConditionCd, dirtConditionCdが取れない。
     * ==> raceIdを取得する専用APIにする。
     */
    public Flux<String> getRaceIds() {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(racingDetailWithFriday)
                        .build(DateUtil.thisFriday()))
                .retrieve()
                .bodyToFlux(ExternalResponse.class)
                .map(ExternalResponse::getData)
                .map(jvLinkDeserializer.decode()
                        .andThen(jvLinkDeserializer::racingDetailFunction))
                .map(RacingDetail::getRaceId)
                .onErrorResume(this::errorHandle);
    }

    /**
     * データ区分＝2
     * weatherCd, turfConditionCd, dirtConditionCd が取れる。
     */
    public Mono<RacingDetail> racingDetail(String raceId) {
        return findOneByRaceId(racingDetail, raceId)
                .map(jvLinkDeserializer.decode()
                        .andThen(jvLinkDeserializer::racingDetailFunction))
                .doOnNext(ModelUtil::fieldNotNull);
    }

    /**
     * データ区分＝2 のとき、馬体重が、nullになる。
     * null check外しておく。
     */
    public Flux<RacingHorseDetail> racingHorseDetail(String raceId) {
        return findAllByRaceId(racingHorseDetail, raceId)
                .map(jvLinkDeserializer.decode()
                        .andThen(jvLinkDeserializer::racingHorseDetailFunction));
    }

    public Mono<WinsShowBracketQ> winsShowBracketQ(String raceId) {
        return findOneByRaceId(winsShowBracketQ, raceId)
                .map(jvLinkDeserializer.decode()
                        .andThen(jvLinkDeserializer::winsShowBracketQFunction))
                .doOnNext(ModelUtil::fieldNotNull);
    }

    public Mono<Quinella> quinella(String raceId) {
        return findOneByRaceId(quinella, raceId)
                .map(jvLinkDeserializer.decode()
                        .andThen(jvLinkDeserializer::quinellaFunction))
                .doOnNext(ModelUtil::fieldNotNull);
    }

    public Mono<QuinellaPlace> quinellaPlace(String raceId) {
        return findOneByRaceId(quinellaPlace, raceId)
                .map(jvLinkDeserializer.decode()
                        .andThen(jvLinkDeserializer::quinellaPlaceFunction))
                .doOnNext(ModelUtil::fieldNotNull);
    }

    public Mono<Exacta> exacta(String raceId) {
        return findOneByRaceId(exacta, raceId)
                .map(jvLinkDeserializer.decode()
                        .andThen(jvLinkDeserializer::exactaFunction))
                .doOnNext(ModelUtil::fieldNotNull);
    }

    public Mono<Trio> trio(String raceId) {
        return findOneByRaceId(trio, raceId)
                .map(jvLinkDeserializer.decode()
                        .andThen(jvLinkDeserializer::trioFunction))
                .doOnNext(ModelUtil::fieldNotNull);
    }

    public Mono<Trifecta> trifecta(String raceId) {
        return findOneByRaceId(trifecta, raceId)
                .map(jvLinkDeserializer.decode()
                        .andThen(jvLinkDeserializer::trifectaFunction))
                .doOnNext(ModelUtil::fieldNotNull);
    }

    public Flux<WinsShowBracketQ> timeseriesWinsShowBracketQ(String raceId) {
        return findAllByRaceId(timeseriesWinsShowBracketQ, raceId)
                .map(jvLinkDeserializer.decode()
                        .andThen(jvLinkDeserializer::winsShowBracketQFunction))
                .doOnNext(ModelUtil::fieldNotNull);
    }

    public Flux<Quinella> timeseriesQuinella(String raceId) {
        return findAllByRaceId(timeseriesQuinella, raceId)
                .map(jvLinkDeserializer.decode()
                        .andThen(jvLinkDeserializer::quinellaFunction))
                .doOnNext(ModelUtil::fieldNotNull);
    }


    /**
     * イベント通知系
     */
    public Mono<RacingRefund> eventRacingRefund(String raceId) {
        return findOneByRaceId(raceRefund, raceId)
                .map(jvLinkDeserializer.decode()
                        .andThen(jvLinkDeserializer::racingRefundFunction))
                .doOnNext(ModelUtil::fieldNotNull);
    }

    public Mono<Weight> eventWeight(String raceId) {
        return findOneByRaceId(weight, raceId)
                .map(jvLinkDeserializer.decode()
                        .andThen(jvLinkDeserializer::weightFunction))
                .doOnNext(ModelUtil::fieldNotNull);
    }

    public Mono<TimeChange> eventTimeChange(String raceId) {
        return findOneByRaceId(timeChange, raceId)
                .map(jvLinkDeserializer.decode()
                        .andThen(jvLinkDeserializer::timeChangeFunction))
                .doOnNext(ModelUtil::fieldNotNull);
    }


    public Mono<JockeyChange> eventJockeyChange(String raceId) {
        return findOneByRaceId(jockeyChange, raceId)
                .map(jvLinkDeserializer.decode()
                        .andThen(jvLinkDeserializer::jockeyChangeFunction))
                .doOnNext(ModelUtil::fieldNotNull);
    }

    public Mono<Weather> eventWeather(String raceId) {
        return findOneByRaceId(weather, raceId)
                .map(jvLinkDeserializer.decode()
                        .andThen(jvLinkDeserializer::weatherFunction))
                .doOnNext(ModelUtil::fieldNotNull);
    }

    public Mono<Avoid> eventAvoid(String raceId) {
        return findOneByRaceId(avoid, raceId)
                .map(jvLinkDeserializer.decode()
                        .andThen(jvLinkDeserializer::avoidFunction))
                .doOnNext(ModelUtil::fieldNotNull);
    }

    public Mono<CourseChange> eventCourseChange(String raceId) {
        return findOneByRaceId(courseChange, raceId)
                .map(jvLinkDeserializer.decode()
                        .andThen(jvLinkDeserializer::courseChangeFunction))
                .doOnNext(ModelUtil::fieldNotNull);
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
