package org.uma.cloud.common.utils.lang;

import com.github.wnameless.json.flattener.JsonFlattener;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.springframework.lang.NonNull;
import org.uma.cloud.common.utils.exception.CharacterCodingRuntimeException;

import java.lang.reflect.Type;
import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;


final public class JvLinkModelUtil {

    /**
     * Shift Jis 周りのお話
     * https://weblabo.oscasierra.net/shift_jis-windows31j/
     * <p>
     * SHIFT-JIS、x-SJIS_0213、ともにダメ。
     * java.nio.charset.MalformedInputException: Input length = 1
     */
    private static final Charset SHIFT_JIS = Charset.forName("MS932");

    /**
     * Json library ==> Use Gson
     * <p>
     * nullのフィールドを、jsonに含める設定を追加。
     */
    private static final Gson GSON = new GsonBuilder()
            .serializeNulls()
            .create();

    @NonNull
    public static String toJson(Object object) {
        return GSON.toJson(object);
    }


    public static Map<String, Integer> jsonToMap(String json) {
        Objects.requireNonNull(json);
        Type type = new TypeToken<Map<String, Integer>>() {
        }.getType();
        return GSON.fromJson(json, type);
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


    // horseWeightは、とりあえず。nullのデータは、div=9 だった。つまりいらんデータ
    private static final List<String> excludeList = Lists.newArrayList(
            // 出走馬詳細
            "horseWeight",
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
            // 外国人騎手の場合、nullになる
            "birthDate",
            // 調教師抹消年月日
            "trainerLicenseEraseDate"
    );

    public static void fieldNotNull(Object model) {
        Map<String, Object> json = JsonFlattener.flattenAsMap(toJson(model));
        json.entrySet().stream()
                .filter(JvLinkModelUtil::nullOkFieldName)
                .forEach(e -> Objects.requireNonNull(e.getValue(), e.getKey() + "が、nullです。"));
    }

    private static boolean nullOkFieldName(Map.Entry<String, Object> stringObjectEntry) {
        return excludeList.stream().noneMatch(i -> i.contains(stringObjectEntry.getKey()));
    }

}