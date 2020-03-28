package org.uma.cloud.common.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.uma.cloud.common.BaseSpringBootTest;

import javax.sql.DataSource;


public class NoAutoConfigurationTest extends BaseSpringBootTest {

    @Autowired
    ApplicationContext applicationContext;

    @Test
    void givenAutoConfigDisabled_whenStarting_thenNoAutoconfiguredBeansInContext() {

        Assertions.assertThrows(NoSuchBeanDefinitionException.class, () ->
                applicationContext.getAutowireCapableBeanFactory().getBean(DataSource.class));
    }
}
