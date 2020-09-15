package org.uma.cloud.stream.type;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.TestPropertySource;
import org.uma.cloud.stream.StreamFunctionProperties;

@Slf4j
@SpringBootTest(classes = {
        FileSource.class,
        StreamFunctionProperties.class,
        JvLinkFileSourceTest.JvLinkFileSourceConfiguration.class
})
public class JvLinkFileSourceTest {

    @Configuration
    @EnableConfigurationProperties
    @TestPropertySource(value = "classpath:JvLinkRecord.properties")
    @ComponentScan(basePackages = "org.uma.cloud.common.configuration")
    public static class JvLinkFileSourceConfiguration {
    }


    @Autowired
    private FileSource fileSource;


    @Test
    public void test_getRacingDetail(){
        fileSource.getRacingDetail().subscribe(
                i -> System.out.println(i)
        );
    }

}
