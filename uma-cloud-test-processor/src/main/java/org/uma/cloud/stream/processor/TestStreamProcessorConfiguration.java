package org.uma.cloud.stream.processor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.annotation.SendTo;


@EnableBinding(Processor.class)
@Configuration
@EnableConfigurationProperties(TestStreamProcessorProperties.class)
public class TestStreamProcessorConfiguration {

    @Autowired
    private TestStreamProcessorProperties processorProperties;


    @StreamListener(Processor.INPUT)
    @SendTo(Processor.OUTPUT)
    public String messenger(UsageDetail usageDetail) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(usageDetail) + ": transformed!!!";
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Data
    static class UsageDetail {
        private String userId;
        private Integer duration;
        private Integer data;
    }

}
