package org.uma.cloud.stream.function;

import org.influxdb.InfluxDB;
import org.influxdb.dto.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;

import java.util.function.Consumer;

@Configuration
public class ConsumerInfluxDB {

    @Autowired
    private InfluxDB influxDB;


    @Bean
    public Consumer<Flux<Point>> writePoint() {
        return points -> points
                .doOnNext(influxDB::write)
                .subscribe();
    }

}
