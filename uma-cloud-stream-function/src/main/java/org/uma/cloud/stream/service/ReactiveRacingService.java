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
import reactor.core.scheduler.Scheduler;

@Service
public class ReactiveRacingService {

    @Autowired
    private JvLinkWebService jvLinkWebService;

    @Autowired
    private Scheduler scheduler;

    @Autowired
    private BusinessRacingService businessRacingService;

    @Autowired
    private BusinessRacingHorseService businessRacingHorseService;

    @Autowired
    private BusinessRacingRefundService businessRacingRefundService;


    /**
     * 検索: jvLinkWebService#racingDetail
     * 変換: BusinessMapper#toBusinessRacing
     * 登録: racingService#update
     * <p>
     * 新しくなったら更新する。exist check不要。
     */
    public Mono<BusinessRacing> updateBusinessRacing(String raceId) {
        return Mono.just(raceId)
                .flatMap(jvLinkWebService::racingDetail)
                .map(BusinessMapper::toBusinessRacing)
                .flatMap(model -> Mono.fromCallable(
                        () -> businessRacingService.update(model)))
                .publishOn(scheduler);
    }

    /**
     * 検索: jvLinkWebService#racingHorseDetail
     * 変換: BusinessMapper#toBusinessRacingHorse
     * 登録: racingHorseService#update
     * <p>
     * 新しくなったら更新する。exist check不要。
     */
    public Flux<BusinessRacingHorse> updateAllBusinessRacingHorse(String raceId) {
        return Mono.just(raceId)
                .flatMapMany(jvLinkWebService::racingHorseDetail)
                .map(BusinessMapper::toBusinessRacingHorse)
                .flatMap(model -> Mono.fromCallable(
                        () -> businessRacingHorseService.update(model)))
                .publishOn(scheduler);
    }

    /**
     * 検索: jvLinkWebService#eventRacingRefund
     * 変換: BusinessMapper#toBusinessRacingRefund
     * 登録: racingRefundService#update
     * <p>
     * 確定レース払戻
     */
    public Mono<BusinessRacingRefund> updateBusinessRacingRefund(String eventRaceId) {
        return Mono.just(eventRaceId)
                .flatMap(jvLinkWebService::eventRacingRefund)
                .map(BusinessMapper::toBusinessRacingRefund)
                .flatMap(model -> Mono.fromCallable(
                        () -> businessRacingRefundService.update(model)))
                .publishOn(scheduler);
    }

    /**
     * 馬体重更新
     */
    public Flux<BusinessRacingHorse> updateWeight(String eventRaceId) {
        return Mono.just(eventRaceId)
                .flatMap(jvLinkWebService::eventWeight)
                .flatMap(model -> Mono.fromCallable(
                        () -> businessRacingHorseService.updateWeight(model)))
                .flatMapMany(Flux::fromIterable)
                .publishOn(scheduler);
    }

    /**
     * 天候・馬場状態更新
     */
    public Flux<BusinessRacing> updateWeather(String eventWeatherId) {
        return Mono.just(eventWeatherId)
                .flatMap(jvLinkWebService::eventWeather)
                .flatMap(model -> Mono.fromCallable(
                        () -> businessRacingService.updateWeather(model)))
                .flatMapMany(Flux::fromIterable)
                .publishOn(scheduler);
    }

    /**
     * 騎手変更
     */
    public Mono<BusinessRacingHorse> updateJockeyChange(String eventRaceId) {
        return Mono.just(eventRaceId)
                .flatMap(jvLinkWebService::eventJockeyChange)
                .flatMap(model -> Mono.fromCallable(
                        () -> businessRacingHorseService.updateJockeyChange(model)))
                .publishOn(scheduler);
    }

    /**
     * 出走取消 or 競走除外 更新
     */
    public Mono<BusinessRacingHorse> updateAvoid(String eventRaceId) {
        return Mono.just(eventRaceId)
                .flatMap(jvLinkWebService::eventAvoid)
                .flatMap(model -> Mono.fromCallable(
                        () -> businessRacingHorseService.updateAvoid(model)))
                .publishOn(scheduler);
    }

    /**
     * 発走時刻変更
     */
    public Mono<BusinessRacing> updateTimeChange(String eventRaceId) {
        return Mono.just(eventRaceId)
                .flatMap(jvLinkWebService::eventTimeChange)
                .flatMap(model -> Mono.fromCallable(
                        () -> businessRacingService.updateTimeChange(model)))
                .publishOn(scheduler);
    }

    /**
     * コース変更
     */
    public Mono<BusinessRacing> updateCourseChange(String eventRaceId) {
        return Mono.just(eventRaceId)
                .flatMap(jvLinkWebService::eventCourseChange)
                .flatMap(model -> Mono.fromCallable(
                        () -> businessRacingService.updateCourseChange(model)))
                .publishOn(scheduler);
    }

}
