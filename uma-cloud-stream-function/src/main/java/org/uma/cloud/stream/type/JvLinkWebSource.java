package org.uma.cloud.stream.service;

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
import org.uma.cloud.stream.model.ResponseMessage;
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


    private static class RealTimePath {

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
                .bodyToFlux(ResponseMessage.class)
                .map(ResponseMessage::getData)
                .map(jvLinkDeserializer::toRacingDetail)
                .map(RacingDetail::getRaceId)
                .onErrorResume(this::errorHandle);
    }

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
                        .queryParam(RealTimePath.RACE_ID, raceId)
                        .build())
                .retrieve()
                .bodyToMono(ResponseMessage.class)
                .map(ResponseMessage::getData)
                .onErrorResume(this::errorHandle);
    }

    private Flux<String> findAllByRaceId(String path, String raceId) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(path)
                        .queryParam(RealTimePath.RACE_ID, raceId)
                        .build())
                .retrieve()
                .bodyToFlux(ResponseMessage.class)
                .map(ResponseMessage::getData)
                .onErrorResume(this::errorHandle);
    }

    /**
     * データ区分＝2
     * weatherCd, turfConditionCd, dirtConditionCd が取れる。
     */
    public Mono<RacingDetail> racingDetail(String raceId) {
        return findOneByRaceId(RealTimePath.racingDetail, raceId)
                .map(jvLinkDeserializer::toRacingDetail)
                .doOnNext(ModelUtil::fieldNotNull);
    }

    /**
     * データ区分＝2 のとき、馬体重が、nullになる。
     * null check外しておく。
     */
    public Flux<RacingHorseDetail> racingHorseDetail(String raceId) {
        return findAllByRaceId(RealTimePath.racingHorseDetail, raceId)
                .map(jvLinkDeserializer::toRacingHorseDetail);
    }

    public Mono<WinsShowBracketQ> winsShowBracketQ(String raceId) {
        return findOneByRaceId(RealTimePath.winsShowBracketQ, raceId)
                .map(jvLinkDeserializer::toWinsShowBracketQ)
                .doOnNext(ModelUtil::fieldNotNull);
    }

    public Mono<Quinella> quinella(String raceId) {
        return findOneByRaceId(RealTimePath.quinella, raceId)
                .map(jvLinkDeserializer::toQuinella)
                .doOnNext(ModelUtil::fieldNotNull);
    }

    public Mono<QuinellaPlace> quinellaPlace(String raceId) {
        return findOneByRaceId(RealTimePath.quinellaPlace, raceId)
                .map(jvLinkDeserializer::toQuinellaPlace)
                .doOnNext(ModelUtil::fieldNotNull);
    }

    public Mono<Exacta> exacta(String raceId) {
        return findOneByRaceId(RealTimePath.exacta, raceId)
                .map(jvLinkDeserializer::toExacta)
                .doOnNext(ModelUtil::fieldNotNull);
    }

    public Mono<Trio> trio(String raceId) {
        return findOneByRaceId(RealTimePath.trio, raceId)
                .map(jvLinkDeserializer::toTrio)
                .doOnNext(ModelUtil::fieldNotNull);
    }

    public Mono<Trifecta> trifecta(String raceId) {
        return findOneByRaceId(RealTimePath.trifecta, raceId)
                .map(jvLinkDeserializer::toTrifecta)
                .doOnNext(ModelUtil::fieldNotNull);
    }

    public Flux<WinsShowBracketQ> timeseriesWinsShowBracketQ(String raceId) {
        return findAllByRaceId(RealTimePath.timeseriesWinsShowBracketQ, raceId)
                .map(jvLinkDeserializer::toWinsShowBracketQ)
                .doOnNext(ModelUtil::fieldNotNull);
    }

    public Flux<Quinella> timeseriesQuinella(String raceId) {
        return findAllByRaceId(RealTimePath.timeseriesQuinella, raceId)
                .map(jvLinkDeserializer::toQuinella)
                .doOnNext(ModelUtil::fieldNotNull);
    }


    /**
     * イベント通知系
     */
    public Mono<RacingRefund> eventRacingRefund(String raceId) {
        return findOneByRaceId(RealTimePath.raceRefund, raceId)
                .map(jvLinkDeserializer::toRacingRefund)
                .doOnNext(ModelUtil::fieldNotNull);
    }

    public Mono<Weight> eventWeight(String raceId) {
        return findOneByRaceId(RealTimePath.weight, raceId)
                .map(jvLinkDeserializer::toWeight)
                .doOnNext(ModelUtil::fieldNotNull);
    }

    public Mono<TimeChange> eventTimeChange(String raceId) {
        return findOneByRaceId(RealTimePath.timeChange, raceId)
                .map(jvLinkDeserializer::toTimeChange)
                .doOnNext(ModelUtil::fieldNotNull);
    }


    public Mono<JockeyChange> eventJockeyChange(String raceId) {
        return findOneByRaceId(RealTimePath.jockeyChange, raceId)
                .map(jvLinkDeserializer::toJockeyChange)
                .doOnNext(ModelUtil::fieldNotNull);
    }

    public Mono<Weather> eventWeather(String raceId) {
        return findOneByRaceId(RealTimePath.weather, raceId)
                .map(jvLinkDeserializer::toWeather)
                .doOnNext(ModelUtil::fieldNotNull);
    }

    public Mono<Avoid> eventAvoid(String raceId) {
        return findOneByRaceId(RealTimePath.avoid, raceId)
                .map(jvLinkDeserializer::toAvoid)
                .doOnNext(ModelUtil::fieldNotNull);
    }

    public Mono<CourseChange> eventCourseChange(String raceId) {
        return findOneByRaceId(RealTimePath.courseChange, raceId)
                .map(jvLinkDeserializer::toCourseChange)
                .doOnNext(ModelUtil::fieldNotNull);
    }

}
