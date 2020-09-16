package org.uma.cloud.common;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * デフォルトで、JvLinkDeserializerは、bean登録しておく。
 */
@Configuration
@ComponentScan(basePackages = "org.uma.cloud.common.configuration")
public class UmaAutoConfiguration {
    /**
     * DataSourceがbean登録されていたら、
     * Jpaの設定とserviceクラスをBean登録する。
     */
    @Configuration
    @ConditionalOnBean({DataSource.class})
    @Import(EnableComponentScanOnDataSourceBean.class)
    @EnableJpaRepositories(basePackages = "org.uma.cloud.common.repository")
    @EntityScan(basePackages = {
            "org.uma.cloud.common.business",
            "org.uma.cloud.common.code",
            "org.uma.cloud.common.entity"
    })
    @EnableTransactionManagement
    protected static class EnableJpaConfigurationOnDataSourceBean {

        /**
         * {@link HibernateJpaConfiguration} でAutoConfigurationされる。
         */
//        private static final String PostgreSQL82Dialect = "org.hibernate.dialect.PostgreSQL82Dialect";
//        private static final String persistenceUnitName = "umaJpaPersistence";
//        private static final String jpaRepositoryPackage = "org.uma.cloud.common.repository";
//        private static final String entityClassPackage = "org.uma.cloud.common.model";
//        private static final String enumConverterClassPackage = "org.uma.cloud.common.code";
//        private static final String hibernateDdlAuto = "hibernate.hbm2ddl.auto";
//        private static final String hibernateDialect = "hibernate.dialect";
//        private static final String update = "update";
//
//        @Bean
//        public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
//            LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
//            factory.setDataSource(dataSource);
//            factory.setPackagesToScan(jpaRepositoryPackage, entityClassPackage, enumConverterClassPackage);
//            factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
//            factory.setJpaProperties(hibernateProperties());
//            factory.setPersistenceUnitName(persistenceUnitName);
//            factory.afterPropertiesSet();
//            return factory;
//        }
//
//        @Bean
//        public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
//            JpaTransactionManager txManager = new JpaTransactionManager();
//            txManager.setEntityManagerFactory(entityManagerFactory);
//            return txManager;
//        }
//
//        private Properties hibernateProperties() {
//            Properties properties = new Properties();
//            properties.setProperty(hibernateDdlAuto, update);
//            properties.setProperty(hibernateDialect, PostgreSQL82Dialect);
//            return properties;
//        }
    }


    /**
     * `@ComponentScan は、importして読み取る。
     */
    @ComponentScan(basePackages = "org.uma.cloud.common.service")
    protected static class EnableComponentScanOnDataSourceBean {

    }

}
