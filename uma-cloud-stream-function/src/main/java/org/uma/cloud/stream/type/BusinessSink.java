package org.uma.cloud.stream.type;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.uma.cloud.common.model.business.BusinessRacing;
import org.uma.cloud.common.model.business.BusinessRacingHorse;
import org.uma.cloud.common.model.business.BusinessRacingRefund;
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


    public Mono<BusinessRacing> update(BusinessRacing businessRacing) {
        return Mono.fromCallable(() -> businessRacingService.update(businessRacing))
                .publishOn(scheduler);
    }

    public Mono<BusinessRacingHorse> update(BusinessRacingHorse businessRacingHorse) {
        return Mono.fromCallable(() -> businessRacingHorseService.update(businessRacingHorse))
                .publishOn(scheduler);
    }

    public Mono<BusinessRacingRefund> update(BusinessRacingRefund businessRacingRefund) {
        return Mono.fromCallable(() -> businessRacingRefundService.update(businessRacingRefund))
                .publishOn(scheduler);
    }

    public Flux<BusinessRacingHorse> update(Weight weight) {
        return Mono.fromCallable(() -> businessRacingHorseService.updateAllWeight(weight))
                .flatMapMany(Flux::fromIterable)
                .publishOn(scheduler);
    }

    public Flux<BusinessRacing> update(Weather weather) {
        return Mono.fromCallable(() -> businessRacingService.updateAllWeather(weather))
                .flatMapMany(Flux::fromIterable)
                .publishOn(scheduler);
    }

    public Mono<BusinessRacingHorse> update(JockeyChange jockeyChange) {
        return Mono.fromCallable(() -> businessRacingHorseService.updateJockeyChange(jockeyChange))
                .publishOn(scheduler);
    }

    public Mono<BusinessRacingHorse> update(Avoid avoid) {
        return Mono.fromCallable(() -> businessRacingHorseService.updateAvoid(avoid))
                .publishOn(scheduler);
    }

    public Mono<BusinessRacing> update(TimeChange timeChange) {
        return Mono.fromCallable(() -> businessRacingService.updateTimeChange(timeChange))
                .publishOn(scheduler);
    }

    public Mono<BusinessRacing> update(CourseChange courseChange) {
        return Mono.fromCallable(() -> businessRacingService.updateCourseChange(courseChange))
                .publishOn(scheduler);
    }

}
