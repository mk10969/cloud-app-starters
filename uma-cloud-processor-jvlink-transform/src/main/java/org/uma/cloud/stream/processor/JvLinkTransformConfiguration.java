//package org.uma.cloud.stream.processor;
//
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.cloud.stream.annotation.EnableBinding;
//import org.springframework.cloud.stream.annotation.StreamListener;
//import org.springframework.cloud.stream.messaging.Processor;
//import org.springframework.messaging.handler.annotation.SendTo;
//import org.uma.cloud.common.model.BaseModel;
//import org.uma.cloud.common.recordSpec.RecordSpec;
//import org.uma.cloud.common.component.JvLinkModelMapper;
//
//import java.util.EnumMap;
//import java.util.function.Function;
//
//
//@EnableBinding(Processor.class)
//@EnableConfigurationProperties(JvLinkTransformProperties.class)
//@RequiredArgsConstructor
//public class JvLinkTransformConfiguration {
//
//    private final JvLinkTransformProperties jvLinkTransformProperties;
//
//
//    private RecordSpec findRecordSpec() {
//        return RecordSpec.of(jvLinkTransformProperties.getRecordSpec());
//    }
//
//
////    public Function<byte[], ? extends BaseModel> deserialize() {
////        return functionEnumMap.get(findRecordSpec());
////    }
//
//    @StreamListener(Processor.INPUT)
//    @SendTo(Processor.OUTPUT)
//    public Object jvlinkTransform(String data) {
//        return JvLinkModelMapper.decode.andThen(deserialize()).apply(data);
//    }
//
//
////    public Function<String, ? extends BaseModel> jvlinkTransform() {
////        return decode().andThen(deserialize());
////    }
//
//}
