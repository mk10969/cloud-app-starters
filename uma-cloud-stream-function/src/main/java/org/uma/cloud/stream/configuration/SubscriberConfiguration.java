package org.uma.cloud.stream.configuration;

import lombok.extern.slf4j.Slf4j;
import org.influxdb.dto.Point;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.uma.cloud.common.model.business.BusinessRace;

@Slf4j
@Configuration
public class SubscriberConfiguration {

    @Bean
    public Subscriber<BusinessRace> businessRaceSubscriber() {
        return new Subscriber<>() {
            @Override
            public void onSubscribe(Subscription s) {
            }

            @Override
            public void onNext(BusinessRace businessRace) {
                log.info("{} : {}",
                        businessRace.getRaceId(),
                        businessRace.getRaceStartDateTime()
                );
            }

            @Override
            public void onError(Throwable t) {
                log.error("Init Error", t);
            }

            @Override
            public void onComplete() {
                log.info("Init Complete!!!");
            }
        };
    }


    @Bean
    public Subscriber<Point> pointSubscriber() {
        return new Subscriber<>() {
            @Override
            public void onSubscribe(Subscription s) {
                // no ops
            }

            @Override
            public void onNext(org.influxdb.dto.Point point) {
                // no ops
            }

            @Override
            public void onError(Throwable t) {
                log.error("InfluxDb Subscriber Error: ", t);
            }

            @Override
            public void onComplete() {
                log.info("InfluxDb Subscriber Complete!!!");
            }
        };
    }


}
