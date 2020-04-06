package org.uma.cloud.common;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.sql.DataSource;


/**
 * デフォルトで、JvLinkDeserializerは、bean登録しておく。
 */
@Configuration
@ComponentScan(basePackages = "org.uma.cloud.common.configuration")
public class UmaAutoConfiguration {

    /**
     * DataSourceがbean登録されていたら、JpaとEntityをスキャンする。
     * <p>
     * Embedded Databaseの場合は、JpaとEntityをスキャンしない。
     */
    @Configuration
    @ConditionalOnBean({DataSource.class})
    @Import(EnableComponentScanOnDataSourceBean.class)
    @EnableJpaRepositories(basePackages = "org.uma.cloud.common.repository")
    @EntityScan(basePackages = "org.uma.cloud.common.model")
    protected static class EnableJpaRepositoryAndEntityScanOnDataSourceBean {

    }

    /**
     * `@ComponentScan は、importして読み取る。
     */
    @ComponentScan(basePackages = "org.uma.cloud.common.service")
    protected static class EnableComponentScanOnDataSourceBean {

    }

}
