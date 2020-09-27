package org.uma.cloud.stream.type;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.uma.cloud.common.entity.WeekendRacingDetail;
import org.uma.cloud.common.entity.WeekendRacingHorseDetail;
import org.uma.cloud.common.entity.WeekendRacingRefund;
import org.uma.cloud.common.model.event.Avoid;
import org.uma.cloud.common.model.event.CourseChange;
import org.uma.cloud.common.model.event.JockeyChange;
import org.uma.cloud.common.model.event.TimeChange;
import org.uma.cloud.common.model.event.Weather;
import org.uma.cloud.common.model.event.Weight;
import org.uma.cloud.common.service.WeekendRacingHorseDetailService;
import org.uma.cloud.common.service.WeekendRacingRefundService;
import org.uma.cloud.common.service.WeekendRacingDetailService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;

@Service
public class WeekendRacingSink {

    @Autowired
    private Scheduler scheduler;

    @Autowired
    private WeekendRacingDetailService weekendRacingDetailService;

    @Autowired
    private WeekendRacingHorseDetailService weekendRacingHorseDetailService;

    @Autowired
    private WeekendRacingRefundService weekendRacingRefundService;


    public Mono<WeekendRacingDetail> update(WeekendRacingDetail weekendRacingDetail) {
        return Mono.fromCallable(() -> weekendRacingDetailService.update(weekendRacingDetail))
                .publishOn(scheduler);
    }

    public Mono<WeekendRacingHorseDetail> update(WeekendRacingHorseDetail weekendRacingHorseDetail) {
        return Mono.fromCallable(() -> weekendRacingHorseDetailService.update(weekendRacingHorseDetail))
                .publishOn(scheduler);
    }

    public Mono<WeekendRacingRefund> update(WeekendRacingRefund weekendRacingRefund) {
        return Mono.fromCallable(() -> weekendRacingRefundService.update(weekendRacingRefund))
                .publishOn(scheduler);
    }

    public Flux<WeekendRacingHorseDetail> update(Weight weight) {
        return Mono.fromCallable(() -> weekendRacingHorseDetailService.updateAllWeight(weight))
                .flatMapMany(Flux::fromIterable)
                .publishOn(scheduler);
    }

    public Flux<WeekendRacingDetail> update(Weather weather) {
        return Mono.fromCallable(() -> weekendRacingDetailService.updateAllWeather(weather))
                .flatMapMany(Flux::fromIterable)
                .publishOn(scheduler);
    }

    public Mono<WeekendRacingHorseDetail> update(JockeyChange jockeyChange) {
        return Mono.fromCallable(() -> weekendRacingHorseDetailService.updateJockeyChange(jockeyChange))
                .publishOn(scheduler);
    }

    public Mono<WeekendRacingHorseDetail> update(Avoid avoid) {
        return Mono.fromCallable(() -> weekendRacingHorseDetailService.updateAvoid(avoid))
                .publishOn(scheduler);
    }

    public Mono<WeekendRacingDetail> update(TimeChange timeChange) {
        return Mono.fromCallable(() -> weekendRacingDetailService.updateTimeChange(timeChange))
                .publishOn(scheduler);
    }

    public Mono<WeekendRacingDetail> update(CourseChange courseChange) {
        return Mono.fromCallable(() -> weekendRacingDetailService.updateCourseChange(courseChange))
                .publishOn(scheduler);
    }

}
