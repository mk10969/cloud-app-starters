package org.uma.cloud.common.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.uma.cloud.common.model.BaseModel;
import org.uma.cloud.common.recordSpec.RecordSpec;
import org.uma.cloud.common.utils.exception.JvLinkModelMappingException;
import org.uma.cloud.common.utils.lang.ByteUtil;
import org.uma.cloud.common.utils.lang.ModelUtil;

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
     * {@link JvLInkModelMapperConfiguration#objectMapper}
     */
    private final ObjectMapper objectMapper;

    /**
     * {@link JvLInkModelMapperConfiguration#recordSpecPairEnumMap}
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