package org.uma.cloud.stream.type;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.uma.cloud.common.entity.ThisWeekRacingDetail;
import org.uma.cloud.common.entity.ThisWeekRacingHorseDetail;
import org.uma.cloud.common.entity.ThisWeekRacingRefund;
import org.uma.cloud.common.model.event.Avoid;
import org.uma.cloud.common.model.event.CourseChange;
import org.uma.cloud.common.model.event.JockeyChange;
import org.uma.cloud.common.model.event.TimeChange;
import org.uma.cloud.common.model.event.Weather;
import org.uma.cloud.common.model.event.Weight;
import org.uma.cloud.common.service.business.BusinessRacingHorseService;
import org.uma.cloud.common.service.business.BusinessRacingRefundService;
import org.uma.cloud.common.service.business.BusinessRacingService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;

@Service
public class BusinessSink {

    @Autowired
    private Scheduler scheduler;

    @Autowired
    private BusinessRacingService businessRacingService;

    @Autowired
    private BusinessRacingHorseService businessRacingHorseService;

    @Autowired
    private BusinessRacingRefundService businessRacingRefundService;


    public Mono<ThisWeekRacingDetail> update(ThisWeekRacingDetail thisWeekRacingDetail) {
        return Mono.fromCallable(() -> businessRacingService.update(thisWeekRacingDetail))
                .publishOn(scheduler);
    }

    public Mono<ThisWeekRacingHorseDetail> update(ThisWeekRacingHorseDetail thisWeekRacingHorseDetail) {
        return Mono.fromCallable(() -> businessRacingHorseService.update(thisWeekRacingHorseDetail))
                .publishOn(scheduler);
    }

    public Mono<ThisWeekRacingRefund> update(ThisWeekRacingRefund thisWeekRacingRefund) {
        return Mono.fromCallable(() -> businessRacingRefundService.update(thisWeekRacingRefund))
                .publishOn(scheduler);
    }

    public Flux<ThisWeekRacingHorseDetail> update(Weight weight) {
        return Mono.fromCallable(() -> businessRacingHorseService.updateAllWeight(weight))
                .flatMapMany(Flux::fromIterable)
                .publishOn(scheduler);
    }

    public Flux<ThisWeekRacingDetail> update(Weather weather) {
        return Mono.fromCallable(() -> businessRacingService.updateAllWeather(weather))
                .flatMapMany(Flux::fromIterable)
                .publishOn(scheduler);
    }

    public Mono<ThisWeekRacingHorseDetail> update(JockeyChange jockeyChange) {
        return Mono.fromCallable(() -> businessRacingHorseService.updateJockeyChange(jockeyChange))
                .publishOn(scheduler);
    }

    public Mono<ThisWeekRacingHorseDetail> update(Avoid avoid) {
        return Mono.fromCallable(() -> businessRacingHorseService.updateAvoid(avoid))
                .publishOn(scheduler);
    }

    public Mono<ThisWeekRacingDetail> update(TimeChange timeChange) {
        return Mono.fromCallable(() -> businessRacingService.updateTimeChange(timeChange))
                .publishOn(scheduler);
    }

    public Mono<ThisWeekRacingDetail> update(CourseChange courseChange) {
        return Mono.fromCallable(() -> businessRacingService.updateCourseChange(courseChange))
                .publishOn(scheduler);
    }

}
