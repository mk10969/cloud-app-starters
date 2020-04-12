package org.uma.cloud.stream.function;

import org.influxdb.InfluxDB;
import org.influxdb.dto.Point;
import org.reactivestreams.Subscriber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;

import java.util.function.Consumer;

@Configuration
public class ConsumerInfluxDB {

    @Autowired
    private InfluxDB influxDB;

    @Autowired
    private Subscriber<Point> subscriber;

    @Bean
    public Consumer<Flux<Point>> writeToInfluxDB() {
        return points -> points
                .doOnNext(influxDB::write)
                .subscribe(subscriber);
    }

}
