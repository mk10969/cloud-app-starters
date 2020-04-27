package org.uma.cloud.stream.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.uma.cloud.common.model.business.BusinessRacing;
import org.uma.cloud.common.model.business.BusinessRacingHorse;
import org.uma.cloud.common.model.business.BusinessRacingRefund;
import org.uma.cloud.common.service.business.BusinessRacingHorseService;
import org.uma.cloud.common.service.business.BusinessRacingRefundService;
import org.uma.cloud.common.service.business.BusinessRacingService;
import org.uma.cloud.stream.util.BusinessMapper;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class BusinessRxService {

    @Autowired
    private JvLinkWebService jvLinkWebService;

    @Autowired
    private BusinessRacingService racingService;

    @Autowired
    private BusinessRacingHorseService racingHorseService;

    @Autowired
    private BusinessRacingRefundService racingRefundService;


    /**
     * 検索: jvLinkWebService#racingDetail
     * 変換: BusinessMapper#toBusinessRacing
     * 登録: racingService#update
     * <p>
     * 新しくなったら更新する。exist check不要。
     */
    public Mono<BusinessRacing> updateOneRacing(String raceId) {
        return Mono.just(raceId)
                .flatMap(jvLinkWebService::racingDetail)
                .map(BusinessMapper::toBusinessRacing)
                .doOnNext(racingService::update);
    }

    /**
     * 検索: jvLinkWebService#racingHorseDetail
     * 変換: BusinessMapper#toBusinessRacingHorse
     * 登録: racingHorseService#update
     * <p>
     * 新しくなったら更新する。exist check不要。
     */
    public Flux<BusinessRacingHorse> updateAllRacingHorse(String raceId) {
        return Mono.just(raceId)
                .flatMapMany(jvLinkWebService::racingHorseDetail)
                .map(BusinessMapper::toBusinessRacingHorse)
                .doOnNext(racingHorseService::update);
    }

    /**
     * 検索: jvLinkWebService#eventRacingRefund
     * 変換: BusinessMapper#toBusinessRacingRefund
     * 登録: racingRefundService#update
     * <p>
     * 確定レース払戻
     */
    public Mono<BusinessRacingRefund> updateRacingRefund(String eventRaceId) {
        return Mono.just(eventRaceId)
                .flatMap(jvLinkWebService::eventRacingRefund)
                .map(BusinessMapper::toBusinessRacingRefund)
                .doOnNext(racingRefundService::update);
    }

    /**
     * 馬体重更新
     */
    public Mono<Void> updateWeight(String eventRaceId) {
        return Mono.just(eventRaceId)
                .flatMap(jvLinkWebService::eventWeight)
                .doOnNext(racingHorseService::updateWeight)
                .then();
    }

    /**
     * 天候・馬場状態更新
     */
    public Mono<Void> updateWeather(String eventWeatherId) {
        return Mono.just(eventWeatherId)
                .flatMap(jvLinkWebService::eventWeather)
                .doOnNext(racingService::updateWeather)
                .then();
    }

    /**
     * 騎手変更
     */
    public Mono<Void> updateJockeyChange(String eventRaceId) {
        return Mono.just(eventRaceId)
                .flatMap(jvLinkWebService::eventJockeyChange)
                .doOnNext(racingHorseService::updateJockeyChange)
                .then();
    }

    /**
     * 出走取消 or 競走除外 更新
     */
    public Mono<Void> updateAvoid(String eventRaceId) {
        return Mono.just(eventRaceId)
                .flatMap(jvLinkWebService::eventAvoid)
                .doOnNext(racingHorseService::updateAvoid)
                .then();
    }

    /**
     * 発走時刻変更
     */
    public Mono<Void> updateTimeChange(String eventRaceId) {
        return Mono.just(eventRaceId)
                .flatMap(jvLinkWebService::eventTimeChange)
                .doOnNext(racingService::updateTimeChange)
                .then();
    }

    /**
     * コース変更
     */
    public Mono<Void> updateCourseChange(String eventRaceId) {
        return Mono.just(eventRaceId)
                .flatMap(jvLinkWebService::eventCourseChange)
                .doOnNext(racingService::updateCourseChange)
                .then();
    }

}
