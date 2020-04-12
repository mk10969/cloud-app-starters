package org.uma.cloud.stream.configuration;

import org.influxdb.BatchOptions;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.uma.cloud.stream.StreamFunctionProperties;

@Configuration
public class InfluxDBConfiguration {

    @Autowired
    private StreamFunctionProperties properties;

    @Bean
    public InfluxDB influxDB() {
        return InfluxDBFactory.connect(properties.getInfluxDBUrl())
                .setDatabase(properties.getInfluxDBDatabase())
                .enableBatch(batchOptions());
    }

    /**
     * InfluxDBに、定期的にPointを書き込む設定。
     * thread は、InfluxDBのものを利用する。
     */
    private BatchOptions batchOptions() {
        return BatchOptions.DEFAULTS
                .actions(1000)
                .bufferLimit(1000)
                .flushDuration(5000);
    }

}
