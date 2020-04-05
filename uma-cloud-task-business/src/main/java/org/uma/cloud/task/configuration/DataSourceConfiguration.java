package org.uma.cloud.task.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfiguration {

    /**
     * use application DataSource ==> postgres
     */
    @Bean
    @Primary
    @ConfigurationProperties("spring.app.datasource")
    public DataSource appDataSource() {
        return DataSourceBuilder.create().build();
    }


    /**
     * use spring task DataSource ==> MySQL
     */
    @Bean("taskDataSource")
    @ConfigurationProperties("spring.task.datasource")
    public DataSource batchDataSource() {
        return DataSourceBuilder.create().build();
    }

}
