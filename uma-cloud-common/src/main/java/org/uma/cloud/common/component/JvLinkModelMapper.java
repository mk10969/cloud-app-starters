package org.uma.cloud.common.component;

import lombok.RequiredArgsConstructor;
import org.modelmapper.MappingException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.uma.cloud.common.model.BaseModel;
import org.uma.cloud.common.recordSpec.RecordSpec;
import org.uma.cloud.common.utils.exception.JvLinkModelMappingException;
import org.uma.cloud.common.utils.lang.ByteUtil;
import org.uma.cloud.common.utils.lang.JvLinkModelUtil;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


@Component
@RequiredArgsConstructor
public class JvLinkModelMapper {

    /**
     * {@link JvLinkModelMapperProperties#modelMapper()}
     */
    private final ModelMapper modelMapper;

    /**
     * {@link JvLinkModelMapperProperties#recordSpecPairEnumMap()}
     */
    private final EnumMap<RecordSpec, Class<? extends BaseModel>> recordSpecClass;

    /**
     * 同一型のBeanをMap化
     * {@link JvLinkRecordProperties}
     */
    private final Map<String, JvLinkRecordProperties.RecordSpecItems> recordSpecItems;


    /**
     * clazzから、RecordSpecを抽出し、flatMapをつかって、RecordSpecから、RecordSpecItemsを抽出する。
     *
     * @param clazz
     * @return RecordSpecItems
     */
    private JvLinkRecordProperties.RecordSpecItems findRecordProperty(Class<?> clazz) {
        return recordSpecClass.entrySet()
                .stream()
                .filter(i -> Objects.equals(i.getValue(), clazz))
                .map(Map.Entry::getKey)
                .flatMap(i -> recordSpecItems.entrySet()
                        .stream()
                        .filter(e -> Objects.equals(e.getKey(), i.getCode()))
                        .map(Map.Entry::getValue))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }


    public <T> T deserialize(final byte[] byteArrayLine, final Class<T> clazz) {
        Map<String, Object> deSerialMap = new HashMap<>();

        findRecordProperty(clazz).getRecordItems().forEach(record -> {
            // 繰り返しあり。
            if (record.getRepeat() != 0) {
                int start = record.getStart(); // 止むを得ない
                List<Object> tmpList = new ArrayList<>();

                // オブジェクトを繰り返す。
                if (record.getColumn().contains("=")) {
                    String[] columnNameAndJsonString = record.getColumn().split("=");
                    for (int i = 0; i < record.getRepeat(); i++) {
                        Map<String, Object> tmpMap = new HashMap<>(); //ループ毎に初期化
                        for (Map.Entry<String, Integer> entry : JvLinkModelUtil
                                .jsonToMap(columnNameAndJsonString[1]).entrySet()) {
                            String tmpString = JvLinkModelUtil
                                    .sliceAndToString(byteArrayLine, start, start + entry.getValue());
                            tmpMap.put(entry.getKey(), tmpString);
                            start = start + entry.getValue();
                        }
                        tmpList.add(tmpMap);
                    }
                    deSerialMap.put(columnNameAndJsonString[0], tmpList);
                }
                // 単純な繰り返し。
                else {
                    for (int i = 0; i < record.getRepeat(); i++) {
                        String tmpString = JvLinkModelUtil
                                .sliceAndToString(byteArrayLine, start, start + record.getLength());
                        tmpList.add(tmpString);
                        start = start + record.getLength();
                    }
                    deSerialMap.put(record.getColumn(), tmpList);
                }
            }
            // 繰り返しなし
            else {
                int end = record.getStart() + record.getLength(); // 次のメソッドの引数が長くなるから。
                String tmpString = JvLinkModelUtil.sliceAndToString(byteArrayLine, record.getStart(), end);
                deSerialMap.put(record.getColumn(), tmpString);
            }
        });

        try {
            return modelMapper.map(deSerialMap, clazz);

        } catch (MappingException e) {
            // マッピングできなかったデータを、
            // エンコードしてログに出力できるようにデータをセットする
            throw new JvLinkModelMappingException(e, ByteUtil.base64EncodeToString(byteArrayLine));
        }
    }


//    @Bean
//    public EnumMap<RecordSpec, Function<byte[], ? extends BaseModel>> recordSpecFunctionEnumMap() {
//        EnumMap<RecordSpec, Function<byte[], ? extends BaseModel>> enumMap = new EnumMap<>(RecordSpec.class);
//        // RACE
//        enumMap.put(RecordSpec.RA, racingDetailsFunction);
//        enumMap.put(RecordSpec.SE, horseRacingDetailsFunction);
//        enumMap.put(RecordSpec.HR, refundFunction);
//        enumMap.put(RecordSpec.H1, voteCountFunction);
//        enumMap.put(RecordSpec.JG, raceHorseExclusionFunction);
//        // ODDS
//        enumMap.put(RecordSpec.O1, winsPlaceBracketQuinellaFunction);
//        enumMap.put(RecordSpec.O2, quinellaFunction);
//        enumMap.put(RecordSpec.O3, quinellaPlaceFunction);
//        enumMap.put(RecordSpec.O4, exactaFunction);
//        enumMap.put(RecordSpec.O5, trioFunction);
//        enumMap.put(RecordSpec.O6, trifectaFunction);
//        // BLOD
//        enumMap.put(RecordSpec.SK, offspringFunction);
//        enumMap.put(RecordSpec.BT, ancestryFunction);
//        enumMap.put(RecordSpec.HN, breedingHorseFunction);
//        // DIFF
//        enumMap.put(RecordSpec.UM, raceHorseFunction);
//        enumMap.put(RecordSpec.KS, jockeyFunction);
//        enumMap.put(RecordSpec.CH, trainerFunction);
//        enumMap.put(RecordSpec.BR, breederFunction);
//        enumMap.put(RecordSpec.BN, ownerFunction);
//        // COMM
//        enumMap.put(RecordSpec.CS, courseFunction);
//        return enumMap;
//    }


}