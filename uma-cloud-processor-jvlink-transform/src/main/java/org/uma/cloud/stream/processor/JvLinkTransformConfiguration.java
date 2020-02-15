package org.uma.cloud.stream.processor;


import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.uma.cloud.common.model.BaseModel;
import org.uma.cloud.common.recordSpec.RecordSpec;
import org.uma.cloud.stream.processor.component.JvLinkModelMapper;

import java.util.Base64;
import java.util.EnumMap;
import java.util.Optional;
import java.util.function.Function;


@Configuration
@EnableConfigurationProperties(JvLinkTransformProperties.class)
@RequiredArgsConstructor
public class JvLinkTransformConfiguration {


    private final JvLinkTransformProperties jvLinkTransformProperties;

    private final EnumMap<RecordSpec, Class<? extends BaseModel>> recordSpecClass;

    private final JvLinkModelMapper jvLinkModelMapper;


    @Bean
    public Function<String, ? extends BaseModel> decodeAndDeserialize() {
        return decode().andThen(deserialize());
    }

//    @Bean
//    public Function<? extends BaseModel, ? extends BaseModel> filter() {
//        return model -> {
//            model.getRecordType();
//            return model;
//        };
//    }

    @Bean
    public Function<byte[], ? extends BaseModel> deserialize() {
        return byteData -> jvLinkModelMapper.deserialize(byteData, findClass());
    }

    @Bean
    public Function<String, byte[]> decode() {
        return data -> Base64.getDecoder().decode(data);
    }


    private Class<? extends BaseModel> findClass() {
        RecordSpec recordSpec = RecordSpec.of(jvLinkTransformProperties.getRecordSpec());
        Class<? extends BaseModel> clazz = recordSpecClass.getOrDefault(recordSpec, null);
        return Optional.ofNullable(clazz).orElseThrow(() ->
                new NullPointerException(recordSpec + "から、対象のクラスが見つかりませんでした。"));
    }

//@EnableBinding(Processor.class)

//    @StreamListener(Processor.INPUT)
//    @SendTo(Processor.OUTPUT)
//    public String messenger(String data) {
//        return "Hello: " + data + "!";
//    }

}
