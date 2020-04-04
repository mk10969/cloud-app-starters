package org.uma.cloud.stream.configuration;


import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InfluxdbConfiguration {

    private static final String url = "http://localhost:8086";


    @Bean
    public InfluxDB influxDB() {
        return InfluxDBFactory.connect(url);
    }

}
