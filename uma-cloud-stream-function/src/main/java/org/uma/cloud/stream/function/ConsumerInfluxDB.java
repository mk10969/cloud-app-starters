package org.uma.cloud.stream.function;

import lombok.extern.slf4j.Slf4j;
import org.influxdb.InfluxDB;
import org.influxdb.dto.Point;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;

import java.util.function.Consumer;

@Slf4j
@Configuration
public class ConsumerInfluxDB {

    @Autowired
    private InfluxDB influxDB;


    @Bean
    public Consumer<Flux<Point>> writePoints() {
        return points -> points
                .doOnNext(influxDB::write)
                .subscribe(influxDBSubscriber());
    }

    private Subscriber<Point> influxDBSubscriber() {
        return new Subscriber<>() {
            @Override
            public void onSubscribe(Subscription s) {
                // no ops
            }

            @Override
            public void onNext(Point point) {
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
