package org.uma.cloud.common.configuration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.TestPropertySource;

import java.util.Map;

@SpringBootTest(classes = {
        JvLinkRecordPropertiesTest.DefaultTestConfiguration.class
})
class JvLinkRecordPropertiesTest {

    @Configuration
    @EnableConfigurationProperties
    @TestPropertySource(value = "classpath:JvLinkRecord.properties")
    @ComponentScan(basePackages = "org.uma.cloud.common.configuration")
    public static class DefaultTestConfiguration {
    }

    @Autowired
    private Map<String, JvLinkRecordProperties.RecordSpecItems> recordSpecItems;

    @Test
    void test_read() {
        System.out.println(recordSpecItems);

    }

}