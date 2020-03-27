package org.uma.cloud.batch.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfiguration {

    /**
     * use application DataSource
     */
    @Bean
    @Primary
    @ConfigurationProperties("spring.app.datasource")
    public DataSource appDataSource() {
        return DataSourceBuilder.create().build();
    }


    /**
     * use spring batch and spring task DataSource
     */
    @Bean("batchDataSource")
    @ConfigurationProperties("spring.batch.datasource")
    public DataSource batchDataSource() {
        return DataSourceBuilder.create().build();
    }

}
