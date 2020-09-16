package org.uma.cloud.stream.type;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.uma.cloud.common.configuration.JvLinkDeserializer;
import org.uma.cloud.common.entity.BloodAncestry;
import org.uma.cloud.common.entity.BloodBreeding;
import org.uma.cloud.common.entity.BloodLine;
import org.uma.cloud.common.entity.Course;
import org.uma.cloud.common.entity.DiffBreeder;
import org.uma.cloud.common.entity.DiffJockey;
import org.uma.cloud.common.entity.DiffOwner;
import org.uma.cloud.common.entity.DiffRaceHorse;
import org.uma.cloud.common.entity.DiffTrainer;
import org.uma.cloud.common.entity.OddsExacta;
import org.uma.cloud.common.entity.OddsQuinella;
import org.uma.cloud.common.entity.OddsQuinellaPlace;
import org.uma.cloud.common.entity.OddsShow;
import org.uma.cloud.common.entity.OddsTrifecta;
import org.uma.cloud.common.entity.OddsTrio;
import org.uma.cloud.common.entity.OddsWin;
import org.uma.cloud.common.entity.RacingDetail;
import org.uma.cloud.common.entity.RacingHorseDetail;
import org.uma.cloud.common.entity.RacingHorseExclusion;
import org.uma.cloud.common.entity.RacingRefund;
import org.uma.cloud.common.entity.RacingVote;
import org.uma.cloud.common.model.event.Avoid;
import org.uma.cloud.common.model.event.CourseChange;
import org.uma.cloud.common.model.event.JockeyChange;
import org.uma.cloud.common.model.event.TimeChange;
import org.uma.cloud.common.model.event.Weather;
import org.uma.cloud.common.model.event.Weight;
import org.uma.cloud.common.utils.javatuples.Pair;
import org.uma.cloud.common.utils.lang.ModelUtil;
import org.uma.cloud.stream.configuration.WebClientConfiguration.JvLinkWebClientException;
import org.uma.cloud.stream.model.ResponseMessage;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class JvLinkWebSource {

    /**
     * レースID
     */
    private static final String RACE_ID = "raceId";

    @Autowired
    private WebClient webClient;

    @Autowired
    private JvLinkDeserializer jvLinkDeserializer;


    private static class StorePath {
        ///// 蓄積データ /////

        // レース詳細情報
        private static final String racingDetail = "/racingDetail/{epochMilli}";
        // 馬毎レース詳細情報
        private static final String racingHorseDetail = "/racingHorseDetail/{epochMilli}";
        // レース払戻
        private static final String racingRefund = "/racingRefund/{epochMilli}";
        // レース投票数（3連単除く）
        private static final String racingVote = "/racingVote/{epochMilli}";
        // レース除外情報
        private static final String racingHorseExclusion = "/racingHorseExclusion/{epochMilli}";
        // 単勝・複勝・枠連
        private static final String winsShowBracketQ = "/winsShowBracketQ/{epochMilli}";
        // 馬連
        private static final String quinella = "/quinella/{epochMilli}";
        // ワイド
        private static final String quinellaPlace = "/quinellaPlace/{epochMilli}";
        // 馬単
        private static final String exacta = "/exacta/{epochMilli}";
        // 三連複
        private static final String trio = "/trio/{epochMilli}";
        // 三連単
        private static final String trifecta = "/trifecta/{epochMilli}";
        // 系統情報
        private static final String bloodAncestry = "/bloodAncestry/{epochMilli}";
        // 繁殖牝馬
        private static final String bloodBreeding = "/bloodBreeding/{epochMilli}";
        // 産駒
        private static final String bloodLine = "/bloodLine/{epochMilli}";
        // 競走馬
        private static final String raceHorse = "/raceHorse/{epochMilli}";
        // 騎手
        private static final String jockey = "/jockey/{epochMilli}";
        // 調教師
        private static final String trainer = "/trainer/{epochMilli}";
        // 馬主
        private static final String owner = "/owner/{epochMilli}";
        // 生産者
        private static final String breeder = "/breeder/{epochMilli}";
        // コース
        private static final String course = "/course/{epochMilli}";
    }

    private static class RealTimePath {
        ///// リアルタイムデータ /////

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
     * 共通 error handler
     */
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

    /**
     * JvLink store client
     */
    private Flux<String> findAllByBaseDate(String path, long baseDate) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path(path).build(baseDate))
                .retrieve()
                .bodyToFlux(ResponseMessage.class)
                .map(ResponseMessage::getData)
                .onErrorResume(this::errorHandle);
    }

    // レース

    public Flux<RacingDetail> storeRacingDetail(long baseDate) {
        return findAllByBaseDate(StorePath.racingDetail, baseDate)
                .map(jvLinkDeserializer::toRacingDetail)
                .doOnNext(ModelUtil::fieldNotNull);
    }

    public Flux<RacingHorseDetail> storeRacingHorseDetail(long baseDate) {
        return findAllByBaseDate(StorePath.racingHorseDetail, baseDate)
                .map(jvLinkDeserializer::toRacingHorseDetail)
                .doOnNext(ModelUtil::fieldNotNull);
    }

    public Flux<RacingRefund> storeRacingRefund(long baseDate) {
        return findAllByBaseDate(StorePath.racingRefund, baseDate)
                .map(jvLinkDeserializer::toRacingRefund)
                .doOnNext(ModelUtil::fieldNotNull);
    }

    public Flux<RacingVote> storeRacingVote(long baseDate) {
        return findAllByBaseDate(StorePath.racingVote, baseDate)
                .map(jvLinkDeserializer::toRacingVote)
                .doOnNext(ModelUtil::fieldNotNull);
    }

    public Flux<RacingHorseExclusion> storeRacingHorseExclusion(long baseDate) {
        return findAllByBaseDate(StorePath.racingHorseExclusion, baseDate)
                .map(jvLinkDeserializer::toRacingHorseExclusion)
                .doOnNext(ModelUtil::fieldNotNull);
    }

    // オッズ

    public Flux<Pair<OddsWin, OddsShow>> storeWinsShowBracketQ(long baseDate) {
        return findAllByBaseDate(StorePath.winsShowBracketQ, baseDate)
                .map(jvLinkDeserializer::toWinsShowBracketQ)
                .doOnNext(pair -> {
                    ModelUtil.fieldNotNull(pair.getValue1());
                    ModelUtil.fieldNotNull(pair.getValue2());
                });
    }

    public Flux<OddsQuinella> storeQuinella(long baseDate) {
        return findAllByBaseDate(StorePath.quinella, baseDate)
                .map(jvLinkDeserializer::toQuinella)
                .doOnNext(ModelUtil::fieldNotNull);
    }

    public Flux<OddsQuinellaPlace> storeQuinellaPlace(long baseDate) {
        return findAllByBaseDate(StorePath.quinellaPlace, baseDate)
                .map(jvLinkDeserializer::toQuinellaPlace)
                .doOnNext(ModelUtil::fieldNotNull);
    }

    public Flux<OddsExacta> storeExacta(long baseDate) {
        return findAllByBaseDate(StorePath.exacta, baseDate)
                .map(jvLinkDeserializer::toExacta)
                .doOnNext(ModelUtil::fieldNotNull);
    }

    public Flux<OddsTrio> storeTrio(long baseDate) {
        return findAllByBaseDate(StorePath.trio, baseDate)
                .map(jvLinkDeserializer::toTrio)
                .doOnNext(ModelUtil::fieldNotNull);
    }

    public Flux<OddsTrifecta> storeTrifecta(long baseDate) {
        return findAllByBaseDate(StorePath.trifecta, baseDate)
                .map(jvLinkDeserializer::toTrifecta)
                .doOnNext(ModelUtil::fieldNotNull);
    }

    // 血統

    public Flux<BloodAncestry> storeBloodAncestry(long baseDate) {
        return findAllByBaseDate(StorePath.bloodAncestry, baseDate)
                .map(jvLinkDeserializer::toBloodAncestry)
                .doOnNext(ModelUtil::fieldNotNull);
    }

    public Flux<BloodBreeding> storeBloodBreeding(long baseDate) {
        return findAllByBaseDate(StorePath.bloodBreeding, baseDate)
                .map(jvLinkDeserializer::toBloodBreeding)
                .doOnNext(ModelUtil::fieldNotNull);
    }

    public Flux<BloodLine> storeBloodLine(long baseDate) {
        return findAllByBaseDate(StorePath.bloodLine, baseDate)
                .map(jvLinkDeserializer::toBloodLine)
                .doOnNext(ModelUtil::fieldNotNull);
    }

    // 馬

    public Flux<DiffRaceHorse> storeRaceHorse(long baseDate) {
        return findAllByBaseDate(StorePath.raceHorse, baseDate)
                .map(jvLinkDeserializer::toRaceHorse)
                .doOnNext(ModelUtil::fieldNotNull);
    }

    public Flux<DiffJockey> storeJockey(long baseDate) {
        return findAllByBaseDate(StorePath.jockey, baseDate)
                .map(jvLinkDeserializer::toJockey)
                .doOnNext(ModelUtil::fieldNotNull);
    }

    public Flux<DiffTrainer> storeTrainer(long baseDate) {
        return findAllByBaseDate(StorePath.trainer, baseDate)
                .map(jvLinkDeserializer::toTrainer)
                .doOnNext(ModelUtil::fieldNotNull);
    }

    public Flux<DiffOwner> storeOwner(long baseDate) {
        return findAllByBaseDate(StorePath.owner, baseDate)
                .map(jvLinkDeserializer::toOwner)
                .doOnNext(ModelUtil::fieldNotNull);
    }

    public Flux<DiffBreeder> storeBreeder(long baseDate) {
        return findAllByBaseDate(StorePath.breeder, baseDate)
                .map(jvLinkDeserializer::toBreeder)
                .doOnNext(ModelUtil::fieldNotNull);
    }

    public Flux<Course> storeCourse(long baseDate) {
        return findAllByBaseDate(StorePath.course, baseDate)
                .map(jvLinkDeserializer::toCourse)
                .doOnNext(ModelUtil::fieldNotNull);
    }


    /**
     * JvLink realtime client
     */
    private Mono<String> findOneByRaceId(String path, String raceId) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(path)
                        .queryParam(RACE_ID, raceId)
                        .build())
                .retrieve()
                .bodyToMono(ResponseMessage.class)
                .map(ResponseMessage::getData)
                .onErrorResume(this::errorHandle);
    }

    /**
     * JvLink realtime client
     */
    private Flux<String> findAllByRaceId(String path, String raceId) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(path)
                        .queryParam(RACE_ID, raceId)
                        .build())
                .retrieve()
                .bodyToFlux(ResponseMessage.class)
                .map(ResponseMessage::getData)
                .onErrorResume(this::errorHandle);
    }

    /**
     * データ区分＝2|6  weatherCd, turfConditionCd, dirtConditionCd が取れる。
     */
    public Mono<RacingDetail> realtimeRacingDetail(String raceId) {
        return findOneByRaceId(RealTimePath.racingDetail, raceId)
                .map(jvLinkDeserializer::toRacingDetail)
                .doOnNext(ModelUtil::fieldNotNull);
    }

    /**
     * データ区分＝2|6  馬体重が、nullになる -> null check 外しておく。
     */
    public Flux<RacingHorseDetail> realtimeRacingHorseDetail(String raceId) {
        return findAllByRaceId(RealTimePath.racingHorseDetail, raceId)
                .map(jvLinkDeserializer::toRacingHorseDetail);
    }

    public Mono<Pair<OddsWin, OddsShow>> realtimeWinsShowBracketQ(String raceId) {
        return findOneByRaceId(RealTimePath.winsShowBracketQ, raceId)
                .map(jvLinkDeserializer::toWinsShowBracketQ)
                .doOnNext(pair -> {
                    ModelUtil.fieldNotNull(pair.getValue1());
                    ModelUtil.fieldNotNull(pair.getValue2());
                });
    }

    public Mono<OddsQuinella> realtimeQuinella(String raceId) {
        return findOneByRaceId(RealTimePath.quinella, raceId)
                .map(jvLinkDeserializer::toQuinella)
                .doOnNext(ModelUtil::fieldNotNull);
    }

    public Mono<OddsQuinellaPlace> realtimeQuinellaPlace(String raceId) {
        return findOneByRaceId(RealTimePath.quinellaPlace, raceId)
                .map(jvLinkDeserializer::toQuinellaPlace)
                .doOnNext(ModelUtil::fieldNotNull);
    }

    public Mono<OddsExacta> realtimeExacta(String raceId) {
        return findOneByRaceId(RealTimePath.exacta, raceId)
                .map(jvLinkDeserializer::toExacta)
                .doOnNext(ModelUtil::fieldNotNull);
    }

    public Mono<OddsTrio> realtimeTrio(String raceId) {
        return findOneByRaceId(RealTimePath.trio, raceId)
                .map(jvLinkDeserializer::toTrio)
                .doOnNext(ModelUtil::fieldNotNull);
    }

    public Mono<OddsTrifecta> realtimeTrifecta(String raceId) {
        return findOneByRaceId(RealTimePath.trifecta, raceId)
                .map(jvLinkDeserializer::toTrifecta)
                .doOnNext(ModelUtil::fieldNotNull);
    }

    public Flux<Pair<OddsWin, OddsShow>> timeseriesWinsShowBracketQ(String raceId) {
        return findAllByRaceId(RealTimePath.timeseriesWinsShowBracketQ, raceId)
                .map(jvLinkDeserializer::toWinsShowBracketQ)
                .doOnNext(pair -> {
                    ModelUtil.fieldNotNull(pair.getValue1());
                    ModelUtil.fieldNotNull(pair.getValue2());
                });
    }

    public Flux<OddsQuinella> timeseriesQuinella(String raceId) {
        return findAllByRaceId(RealTimePath.timeseriesQuinella, raceId)
                .map(jvLinkDeserializer::toQuinella)
                .doOnNext(ModelUtil::fieldNotNull);
    }

    public Mono<RacingRefund> eventRacingRefund(String eventId) {
        return findOneByRaceId(RealTimePath.raceRefund, eventId)
                .map(jvLinkDeserializer::toRacingRefund)
                .doOnNext(ModelUtil::fieldNotNull);
    }

    public Mono<Weight> eventWeight(String eventId) {
        return findOneByRaceId(RealTimePath.weight, eventId)
                .map(jvLinkDeserializer::toWeight)
                .doOnNext(ModelUtil::fieldNotNull);
    }

    public Mono<TimeChange> eventTimeChange(String eventId) {
        return findOneByRaceId(RealTimePath.timeChange, eventId)
                .map(jvLinkDeserializer::toTimeChange)
                .doOnNext(ModelUtil::fieldNotNull);
    }

    public Mono<JockeyChange> eventJockeyChange(String eventId) {
        return findOneByRaceId(RealTimePath.jockeyChange, eventId)
                .map(jvLinkDeserializer::toJockeyChange)
                .doOnNext(ModelUtil::fieldNotNull);
    }

    public Mono<Weather> eventWeather(String eventId) {
        return findOneByRaceId(RealTimePath.weather, eventId)
                .map(jvLinkDeserializer::toWeather)
                .doOnNext(ModelUtil::fieldNotNull);
    }

    public Mono<Avoid> eventAvoid(String eventId) {
        return findOneByRaceId(RealTimePath.avoid, eventId)
                .map(jvLinkDeserializer::toAvoid)
                .doOnNext(ModelUtil::fieldNotNull);
    }

    public Mono<CourseChange> eventCourseChange(String eventId) {
        return findOneByRaceId(RealTimePath.courseChange, eventId)
                .map(jvLinkDeserializer::toCourseChange)
                .doOnNext(ModelUtil::fieldNotNull);
    }


    /**
     * 念のため入れておく
     */
    public Flux<RacingDetail> storeRacingDetailOnThisWeek() {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/racingDetailThisWeek").build())
                .retrieve()
                .bodyToFlux(ResponseMessage.class)
                .map(ResponseMessage::getData)
                .onErrorResume(this::errorHandle)
                .map(jvLinkDeserializer::toRacingDetail);
    }

}
