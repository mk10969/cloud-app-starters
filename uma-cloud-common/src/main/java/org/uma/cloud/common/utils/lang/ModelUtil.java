package org.uma.cloud.common.utils.lang;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import org.uma.cloud.common.model.BaseModel;
import org.uma.cloud.common.utils.exception.CharacterCodingRuntimeException;

import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ModelUtil {

    private ModelUtil() {
    }

    /**
     * Shift Jis 周りのお話
     * https://weblabo.oscasierra.net/shift_jis-windows31j/
     * <p>
     * SHIFT-JIS、x-SJIS_0213、ともにダメ。
     * java.nio.charset.MalformedInputException: Input length = 1
     */
    private static final Charset SHIFT_JIS = Charset.forName("MS932");

    private static final ObjectMapper objectMapper = JacksonUtil.getDefaultObjectMapper();


    public static String toJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static Map<String, Integer> jsonToMap(String json) {
        Objects.requireNonNull(json);
        try {
            return objectMapper.readValue(json, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static byte[] toByte(String str) {
        Objects.requireNonNull(str);
        return str.getBytes(SHIFT_JIS);
    }


    public static String sliceAndToString(byte[] array, int start, int end) {
        final byte[] slice = Arrays.copyOfRange(array, start, end);
        final ByteBuffer byteBuffer = ByteBuffer.wrap(slice);

        try {
            return SHIFT_JIS.newDecoder().decode(byteBuffer).toString();
        } catch (CharacterCodingException e) {
            throw new CharacterCodingRuntimeException(Arrays.toString(slice), e);
        }
    }


    private static final List<String> excludeList = Lists.newArrayList(
            // postgresのMetadata
            "myCreateDateTime",
            "myUpdateDateTime",
            "changeAmount",
            // 枠連は、出走頭数が少ないと、発売されない場合がある
            "voteCountTotalBracketQuinella",
            // 返還票数
            "restoreVoteCountTotalWin",
            "restoreVoteCountTotalPlace",
            "restoreVoteCountTotalBracketQuinella",
            "restoreVoteCountTotalQuinella",
            "restoreVoteCountTotalQuinellaPlace",
            "restoreVoteCountTotalExacta",
            "restoreVoteCountTotalTrio",
            // 競走馬登録年月日
            // TODO: 競走馬自身が古い（1950年代）から仕方ないかも。
            "raceHorseEntryDate",
            // 競走馬抹消年月日
            "raceHorseEraseDate",
            // 騎手抹消年月日
            "jockeyLicenseEraseDate",
            // 調教師抹消年月日
            "trainerLicenseEraseDate",
            // 外国人騎手の場合、nullになる
            "birthDate"
    );

    public static void fieldNotNull(BaseModel model) {
        Map<String, Object> json = objectMapper.convertValue(model, new TypeReference<>() {
        });
        json.entrySet().stream()
                .filter(ModelUtil::nullOkFieldName)
                .forEach(e -> Objects.requireNonNull(e.getValue(), e.getKey() + "が、nullです。\n" + model));
    }

    private static boolean nullOkFieldName(Map.Entry<String, Object> stringObjectEntry) {
        return excludeList.stream().noneMatch(i -> i.contains(stringObjectEntry.getKey()));
    }

}