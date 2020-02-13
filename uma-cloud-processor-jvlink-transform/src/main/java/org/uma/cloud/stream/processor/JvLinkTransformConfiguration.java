package org.uma.cloud.stream.processor;


import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.uma.cloud.stream.processor.component.JvLinkModelMapper;
import org.uma.cloud.stream.processor.component.JvLinkModelProperties;
import org.uma.cloud.stream.processor.component.JvLinkModelUtil;

import java.util.Base64;
import java.util.Map;
import java.util.function.Function;


@Configuration
@EnableConfigurationProperties(JvLinkTransformProperties.class)
@RequiredArgsConstructor
public class JvLinkTransformConfiguration {


    private final JvLinkTransformProperties jvLinkTransformProperties;

    private final Map<String, JvLinkModelProperties.RecordSpecItems> recordSpecItems;

    private final JvLinkModelMapper jvLinkModelMapper;


    @Bean
    public Function<String, String> decodeAndDeserialize() {
        return decode().andThen(deserialize());
    }


    @Bean
    public Function<String, String> deserialize() {
        return data -> {
            String recodeSpec = jvLinkTransformProperties.getRecordSpec();

            return "";
//              jvLinkModelMapper.deserialize(data, )
        };


    }

    @Bean
    public Function<String, String> decode() {
        return data -> {
            byte[] bytes = Base64.getDecoder().decode(data);
            return JvLinkModelUtil.toString(bytes);
        };
    }


//@EnableBinding(Processor.class)

//    @StreamListener(Processor.INPUT)
//    @SendTo(Processor.OUTPUT)
//    public String messenger(String data) {
//        return "Hello: " + data + "!";
//    }

}
