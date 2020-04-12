package org.uma.cloud.stream.configuration;


import org.influxdb.BatchOptions;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InfluxdbConfiguration {

    private static final String url = "http://localhost:8086";

    private static final String databaseName = "uma";


    @Bean
    public InfluxDB influxDB() {
        return InfluxDBFactory.connect(url)
                .setDatabase(databaseName)
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
