package org.uma.cloud.batch.configuration;


import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.batch.BatchDataSourceInitializer;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration extends DefaultBatchConfigurer {

    @Override
    @Autowired
    public void setDataSource(@Qualifier("taskDataSource") DataSource dataSource) {
        super.setDataSource(dataSource);
    }

    @Bean
    public BatchDataSourceInitializer batchDataSourceInitializer(
            @Qualifier("taskDataSource") DataSource dataSource,
            ResourceLoader resourceLoader) {
        return new BatchDataSourceInitializer(dataSource, resourceLoader, new BatchProperties());
    }
}