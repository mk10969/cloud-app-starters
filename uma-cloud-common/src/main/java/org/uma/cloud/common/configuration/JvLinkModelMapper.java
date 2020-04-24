package org.uma.cloud.common.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.uma.cloud.common.model.BloodAncestry;
import org.uma.cloud.common.model.BaseModel;
import org.uma.cloud.common.model.Breeder;
import org.uma.cloud.common.model.BloodBreeding;
import org.uma.cloud.common.model.Course;
import org.uma.cloud.common.model.RacingHorseDetail;
import org.uma.cloud.common.model.Jockey;
import org.uma.cloud.common.model.BloodLine;
import org.uma.cloud.common.model.Owner;
import org.uma.cloud.common.model.RaceHorse;
import org.uma.cloud.common.model.RacingHorseExclusion;
import org.uma.cloud.common.model.RacingRefund;
import org.uma.cloud.common.model.RacingDetail;
import org.uma.cloud.common.model.Trainer;
import org.uma.cloud.common.model.RacingVote;
import org.uma.cloud.common.model.event.Avoid;
import org.uma.cloud.common.model.event.CourseChange;
import org.uma.cloud.common.model.event.JockeyChange;
import org.uma.cloud.common.model.event.TimeChange;
import org.uma.cloud.common.model.event.Weather;
import org.uma.cloud.common.model.event.Weight;
import org.uma.cloud.common.model.odds.Exacta;
import org.uma.cloud.common.model.odds.Quinella;
import org.uma.cloud.common.model.odds.QuinellaPlace;
import org.uma.cloud.common.model.odds.Trifecta;
import org.uma.cloud.common.model.odds.Trio;
import org.uma.cloud.common.model.odds.WinsShowBracketQ;
import org.uma.cloud.common.recordSpec.RecordSpec;
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

    private final EnumMap<RecordSpec, Class<? extends BaseModel>> recordSpecClass = new EnumMap<>(RecordSpec.class);


    @PostConstruct
    void init() {
        // RACE
        recordSpecClass.put(RecordSpec.RA, RacingDetail.class);
        recordSpecClass.put(RecordSpec.SE, RacingHorseDetail.class);
        recordSpecClass.put(RecordSpec.HR, RacingRefund.class);
        recordSpecClass.put(RecordSpec.H1, RacingVote.class);
        recordSpecClass.put(RecordSpec.JG, RacingHorseExclusion.class);
        // ODDS
        recordSpecClass.put(RecordSpec.O1, WinsShowBracketQ.class);
        recordSpecClass.put(RecordSpec.O2, Quinella.class);
        recordSpecClass.put(RecordSpec.O3, QuinellaPlace.class);
        recordSpecClass.put(RecordSpec.O4, Exacta.class);
        recordSpecClass.put(RecordSpec.O5, Trio.class);
        recordSpecClass.put(RecordSpec.O6, Trifecta.class);
        // BLOD
        recordSpecClass.put(RecordSpec.SK, BloodLine.class);
        recordSpecClass.put(RecordSpec.BT, BloodAncestry.class);
        recordSpecClass.put(RecordSpec.HN, BloodBreeding.class);
        // DIFF
        recordSpecClass.put(RecordSpec.UM, RaceHorse.class);
        recordSpecClass.put(RecordSpec.KS, Jockey.class);
        recordSpecClass.put(RecordSpec.CH, Trainer.class);
        recordSpecClass.put(RecordSpec.BR, Breeder.class);
        recordSpecClass.put(RecordSpec.BN, Owner.class);
        // COMM
        recordSpecClass.put(RecordSpec.CS, Course.class);
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


    public <T> T deserialize(final byte[] byteArrayLine, final Class<T> clazz) {
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