package org.uma.cloud.batch.configuration;

import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration extends DefaultBatchConfigurer {

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    @Override
    public void setDataSource(@Qualifier("taskDataSource") @NonNull DataSource dataSource) {
        super.setDataSource(dataSource);
    }

    /**
     * JpaTransactionManagerを利用する。
     */
    @NonNull
    @Override
    public PlatformTransactionManager getTransactionManager() {
        return new JpaTransactionManager(entityManagerFactory);
    }
}