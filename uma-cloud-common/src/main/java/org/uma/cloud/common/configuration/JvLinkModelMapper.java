package org.uma.cloud.common.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.uma.cloud.common.code.RecordSpec;
import org.uma.cloud.common.model.jvlink.BLOD_BT;
import org.uma.cloud.common.model.jvlink.BLOD_HN;
import org.uma.cloud.common.model.jvlink.BLOD_SK;
import org.uma.cloud.common.model.JvLinkBase;
import org.uma.cloud.common.model.jvlink.COMM_CS;
import org.uma.cloud.common.model.jvlink.DIFF_BN;
import org.uma.cloud.common.model.jvlink.DIFF_BR;
import org.uma.cloud.common.model.jvlink.DIFF_CH;
import org.uma.cloud.common.model.jvlink.DIFF_KS;
import org.uma.cloud.common.model.jvlink.DIFF_UM;
import org.uma.cloud.common.model.jvlink.RACE_H1;
import org.uma.cloud.common.model.jvlink.RACE_HR;
import org.uma.cloud.common.model.jvlink.RACE_JG;
import org.uma.cloud.common.model.jvlink.RACE_O1;
import org.uma.cloud.common.model.jvlink.RACE_O2;
import org.uma.cloud.common.model.jvlink.RACE_O3;
import org.uma.cloud.common.model.jvlink.RACE_O4;
import org.uma.cloud.common.model.jvlink.RACE_O5;
import org.uma.cloud.common.model.jvlink.RACE_O6;
import org.uma.cloud.common.model.jvlink.RACE_RA;
import org.uma.cloud.common.model.jvlink.RACE_SE;
import org.uma.cloud.common.model.event.Avoid;
import org.uma.cloud.common.model.event.CourseChange;
import org.uma.cloud.common.model.event.JockeyChange;
import org.uma.cloud.common.model.event.TimeChange;
import org.uma.cloud.common.model.event.Weather;
import org.uma.cloud.common.model.event.Weight;
import org.uma.cloud.common.utils.exception.JvLinkModelMappingException;
import org.uma.cloud.common.utils.lang.ByteUtil;
import org.uma.cloud.common.utils.lang.JacksonUtil;
import org.uma.cloud.common.utils.lang.ModelUtil;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
public class JvLinkModelMapper {

    private final ObjectMapper objectMapper = JacksonUtil.getJvLinkObjectMapper();

    @Autowired
    private Map<String, JvLinkRecordProperties.RecordSpecItems> recordSpecItems;

    private final EnumMap<RecordSpec, Class<? extends JvLinkBase>> recordSpecClass = new EnumMap<>(RecordSpec.class);


    @PostConstruct
    void init() {
        // RACE
        recordSpecClass.put(RecordSpec.RA, RACE_RA.class);
        recordSpecClass.put(RecordSpec.SE, RACE_SE.class);
        recordSpecClass.put(RecordSpec.HR, RACE_HR.class);
        recordSpecClass.put(RecordSpec.H1, RACE_H1.class);
        recordSpecClass.put(RecordSpec.JG, RACE_JG.class);
        // ODDS
        recordSpecClass.put(RecordSpec.O1, RACE_O1.class);
        recordSpecClass.put(RecordSpec.O2, RACE_O2.class);
        recordSpecClass.put(RecordSpec.O3, RACE_O3.class);
        recordSpecClass.put(RecordSpec.O4, RACE_O4.class);
        recordSpecClass.put(RecordSpec.O5, RACE_O5.class);
        recordSpecClass.put(RecordSpec.O6, RACE_O6.class);
        // BLOD
        recordSpecClass.put(RecordSpec.SK, BLOD_SK.class);
        recordSpecClass.put(RecordSpec.BT, BLOD_BT.class);
        recordSpecClass.put(RecordSpec.HN, BLOD_HN.class);
        // DIFF
        recordSpecClass.put(RecordSpec.UM, DIFF_UM.class);
        recordSpecClass.put(RecordSpec.KS, DIFF_KS.class);
        recordSpecClass.put(RecordSpec.CH, DIFF_CH.class);
        recordSpecClass.put(RecordSpec.BR, DIFF_BR.class);
        recordSpecClass.put(RecordSpec.BN, DIFF_BN.class);
        // COMM
        recordSpecClass.put(RecordSpec.CS, COMM_CS.class);
        // EVENT
        recordSpecClass.put(RecordSpec.WH, Weight.class);
        recordSpecClass.put(RecordSpec.AV, Avoid.class);
        recordSpecClass.put(RecordSpec.CC, CourseChange.class);
        recordSpecClass.put(RecordSpec.JC, JockeyChange.class);
        recordSpecClass.put(RecordSpec.TC, TimeChange.class);
        recordSpecClass.put(RecordSpec.WE, Weather.class);
    }

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


    public <T extends JvLinkBase> T deserialize(final byte[] byteArrayLine, final Class<T> clazz) {
        Map<String, Object> deSerialMap = new HashMap<>();

        findRecordProperty(clazz).getRecordItems().forEach(record -> {
            // 繰り返しあり。
            if (record.getRepeat() != 0) {
                int start = record.getStart();
                List<Object> tmpList = new ArrayList<>();

                // オブジェクトを繰り返す。
                if (record.getColumn().contains("=")) {
                    String[] columnNameAndJsonString = record.getColumn().split("=");
                    for (int i = 0; i < record.getRepeat(); i++) {
                        Map<String, Object> tmpMap = new HashMap<>(); //ループ毎に初期化
                        for (Map.Entry<String, Integer> entry : ModelUtil.jsonToMap(columnNameAndJsonString[1]).entrySet()) {
                            String tmpString = ModelUtil.sliceAndToString(byteArrayLine, start, start + entry.getValue());
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
                        String tmpString = ModelUtil.sliceAndToString(byteArrayLine, start, start + record.getLength());
                        tmpList.add(tmpString);
                        start = start + record.getLength();
                    }
                    deSerialMap.put(record.getColumn(), tmpList);
                }
            }
            // 繰り返しなし
            else {
                int end = record.getStart() + record.getLength(); // 次のメソッドの引数が長くなるから。
                String tmpString = ModelUtil.sliceAndToString(byteArrayLine, record.getStart(), end);
                deSerialMap.put(record.getColumn(), tmpString);
            }
        });

        try {
            return objectMapper.convertValue(deSerialMap, clazz);

        } catch (IllegalArgumentException e) {
            // マッピングできなかったデータを、エンコードして、throwする
            throw new JvLinkModelMappingException(e, ByteUtil.base64EncodeToString(byteArrayLine));
        }
    }

}