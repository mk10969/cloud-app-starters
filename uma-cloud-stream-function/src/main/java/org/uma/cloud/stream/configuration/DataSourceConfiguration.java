package org.uma.cloud.stream.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import javax.sql.DataSource;
import java.util.concurrent.Executors;

@Configuration
public class DataSourceConfiguration {

    private static final Integer threadPoolSize = 5;


    /**
     * use application DataSource ==> postgres
     */
    @Bean
    @Primary
    @ConfigurationProperties("spring.app.datasource")
    public DataSource appDataSource() {
        return DataSourceBuilder.create().build();
    }


    @Bean
    public Scheduler jdbcScheduler() {
        return Schedulers.fromExecutor(Executors.newFixedThreadPool(threadPoolSize));
    }

}
