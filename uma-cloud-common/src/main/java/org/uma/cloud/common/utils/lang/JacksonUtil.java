package org.uma.cloud.common.utils.lang;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import org.uma.cloud.common.code.AbnormalDivisionCode;
import org.uma.cloud.common.code.BreedCode;
import org.uma.cloud.common.code.EastOrWestBelongCode;
import org.uma.cloud.common.code.HairColorCode;
import org.uma.cloud.common.code.HorseSignCode;
import org.uma.cloud.common.code.JockeyApprenticeCode;
import org.uma.cloud.common.code.JockeyLicenseCode;
import org.uma.cloud.common.code.MarginCode;
import org.uma.cloud.common.code.RaceCourseCode;
import org.uma.cloud.common.code.RaceGradeCode;
import org.uma.cloud.common.code.RaceSignCode;
import org.uma.cloud.common.code.RaceTypeCode;
import org.uma.cloud.common.code.SexCode;
import org.uma.cloud.common.code.TrackCode;
import org.uma.cloud.common.code.TurfOrDirtConditionCode;
import org.uma.cloud.common.code.WeatherCode;
import org.uma.cloud.common.code.WeekDayCode;
import org.uma.cloud.common.code.WeightTypeCode;
import org.uma.cloud.common.entity.RacingDetail;
import org.uma.cloud.common.entity.RacingHorseDetail;
import org.uma.cloud.common.utils.javatuples.Pair;
import org.uma.cloud.common.utils.javatuples.Triplet;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class JacksonUtil {

    /**
     * デフォルト ObjectMapper
     */
    public static ObjectMapper getDefaultObjectMapper() {
        return DefaultObjectMapper.objectMapper;
    }

    private static class DefaultObjectMapper {
        public static final ObjectMapper objectMapper = new ObjectMapper()
                .registerModule(DefaultModule.simpleModule);
    }

    private static class DefaultModule {
        public static final SimpleModule simpleModule = new SimpleModule()
                // Serializer
                .addSerializer(new LocalDateSerializer(DateTimeFormatter.ofPattern("yyyy/MM/dd")))
                .addSerializer(new LocalTimeSerializer(DateTimeFormatter.ofPattern("HH:mm:ss.SSS")));
    }

    /**
     * JvLink専用 ObjectMapper
     */
    public static ObjectMapper getJvLinkObjectMapper() {
        return JvLinkObjectMapper.objectMapper;
    }

    private static class JvLinkObjectMapper {
        public static final ObjectMapper objectMapper = new ObjectMapper()
                .registerModule(JvLinkDeserializerModule.simpleModule);
    }

    private static class JvLinkDeserializerModule {

        public static final SimpleModule simpleModule = new SimpleModule()
                // Deserializer
                // my enum
                .addDeserializer(RaceCourseCode.class, new RaceCourseCodeDeserializer())
                .addDeserializer(WeekDayCode.class, new WeekDayCodeDeserializer())
                .addDeserializer(RaceGradeCode.class, new RaceGradeCodeDeserializer())
                .addDeserializer(JockeyApprenticeCode.class, new JockeyApprenticeCodeDeserializer())
                .addDeserializer(AbnormalDivisionCode.class, new AbnormalDivisionCodeDeserializer())
                .addDeserializer(EastOrWestBelongCode.class, new EastOrWestBelongCodeDeserializer())
                .addDeserializer(BreedCode.class, new BreedCodeDeserializer())
                .addDeserializer(RaceSignCode.class, new RaceSignCodeDeserializer())
                .addDeserializer(SexCode.class, new SexCodeDeserializer())
                .addDeserializer(MarginCode.class, new MarginCodeDeserializer())
                .addDeserializer(HorseSignCode.class, new HorseSignCodeDeserializer())
                .addDeserializer(TurfOrDirtConditionCode.class, new TurfOrDirtConditionCodeDeserializer())
                .addDeserializer(WeatherCode.class, new WeatherCodeDeserializer())
                .addDeserializer(WeightTypeCode.class, new WeightTypeCodeDeserializer())
                .addDeserializer(TrackCode.class, new TrackCodeDeserializer())
                .addDeserializer(JockeyLicenseCode.class, new JockeyLicenseCodeDeserializer())
                .addDeserializer(RaceTypeCode.class, new RaceTypeCodeDeserializer())
                .addDeserializer(HairColorCode.class, new HairColorCodeDeserializer())
                // my object
                .addDeserializer(Pair.class, new PairCodeDeserializer())
                .addDeserializer(Triplet.class, new TripletCodeDeserializer())
                // java object
                .addDeserializer(LocalDate.class, new LocalDateDeserializer())
                .addDeserializer(LocalTime.class, new LocalTimeDeserializer())
                .addDeserializer(Integer.class, new IntegerDeserializer())
                .addDeserializer(Double.class, new DoubleDeserializer())
                .addDeserializer(BigDecimal.class, new BigDecimalDeserializer())
                .addDeserializer(String.class, new StringDeserializer())
                .addDeserializer(Boolean.class, new BooleanDeserializer());


        private static final class RaceCourseCodeDeserializer extends JsonDeserializer<RaceCourseCode> {
            @Override
            public RaceCourseCode deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
                return RaceCourseCode.of(p.getValueAsString());
            }
        }

        private static final class WeekDayCodeDeserializer extends JsonDeserializer<WeekDayCode> {
            @Override
            public WeekDayCode deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
                Integer code = Integer.valueOf(p.getValueAsString());
                return WeekDayCode.of(code);
            }
        }

        private static final class RaceGradeCodeDeserializer extends JsonDeserializer<RaceGradeCode> {
            @Override
            public RaceGradeCode deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
                return RaceGradeCode.of(p.getValueAsString());
            }
        }

        private static final class JockeyApprenticeCodeDeserializer extends JsonDeserializer<JockeyApprenticeCode> {
            @Override
            public JockeyApprenticeCode deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
                Integer code = Integer.valueOf(p.getValueAsString());
                return JockeyApprenticeCode.of(code);
            }
        }

        private static final class AbnormalDivisionCodeDeserializer extends JsonDeserializer<AbnormalDivisionCode> {
            @Override
            public AbnormalDivisionCode deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
                Integer code = Integer.valueOf(p.getValueAsString());
                return AbnormalDivisionCode.of(code);
            }
        }

        private static final class BreedCodeDeserializer extends JsonDeserializer<BreedCode> {
            @Override
            public BreedCode deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
                Integer code = Integer.valueOf(p.getValueAsString());
                return BreedCode.of(code);
            }
        }

        private static final class EastOrWestBelongCodeDeserializer extends JsonDeserializer<EastOrWestBelongCode> {
            @Override
            public EastOrWestBelongCode deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
                Integer code = Integer.valueOf(p.getValueAsString());
                return EastOrWestBelongCode.of(code);
            }
        }

        private static final class RaceSignCodeDeserializer extends JsonDeserializer<RaceSignCode> {
            @Override
            public RaceSignCode deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
                return RaceSignCode.of(p.getValueAsString());
            }
        }

        private static final class SexCodeDeserializer extends JsonDeserializer<SexCode> {
            @Override
            public SexCode deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
                Integer code = Integer.valueOf(p.getValueAsString());
                return SexCode.of(code);
            }
        }

        private static final class MarginCodeDeserializer extends JsonDeserializer<MarginCode> {
            @Override
            public MarginCode deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
                return MarginCode.of(p.getValueAsString());
            }
        }

        private static final class HorseSignCodeDeserializer extends JsonDeserializer<HorseSignCode> {
            @Override
            public HorseSignCode deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
                return HorseSignCode.of(p.getValueAsString());
            }
        }

        private static final class TurfOrDirtConditionCodeDeserializer extends JsonDeserializer<TurfOrDirtConditionCode> {
            @Override
            public TurfOrDirtConditionCode deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
                Integer code = Integer.valueOf(p.getValueAsString());
                return TurfOrDirtConditionCode.of(code);
            }
        }

        private static final class WeatherCodeDeserializer extends JsonDeserializer<WeatherCode> {
            @Override
            public WeatherCode deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
                Integer code = Integer.valueOf(p.getValueAsString());
                return WeatherCode.of(code);
            }
        }

        private static final class WeightTypeCodeDeserializer extends JsonDeserializer<WeightTypeCode> {
            @Override
            public WeightTypeCode deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
                Integer code = Integer.valueOf(p.getValueAsString());
                return WeightTypeCode.of(code);
            }
        }

        private static final class TrackCodeDeserializer extends JsonDeserializer<TrackCode> {
            @Override
            public TrackCode deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
                Integer code = Integer.valueOf(p.getValueAsString());
                return TrackCode.of(code);
            }
        }

        private static final class JockeyLicenseCodeDeserializer extends JsonDeserializer<JockeyLicenseCode> {
            @Override
            public JockeyLicenseCode deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
                Integer code = Integer.valueOf(p.getValueAsString());
                return JockeyLicenseCode.of(code);
            }
        }

        private static final class RaceTypeCodeDeserializer extends JsonDeserializer<RaceTypeCode> {
            @Override
            public RaceTypeCode deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
                Integer code = Integer.valueOf(p.getValueAsString());
                return RaceTypeCode.of(code);
            }
        }

        private static final class HairColorCodeDeserializer extends JsonDeserializer<HairColorCode> {
            @Override
            public HairColorCode deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
                return HairColorCode.of(p.getValueAsString());
            }
        }

        private static final class PairCodeDeserializer extends JsonDeserializer<Pair<String, String>> {
            @Override
            public Pair<String, String> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
                String source = p.getValueAsString();
                if ("    ".equals(source)) {
                    return Pair.with(null, null);
                }
                if (source.length() != 4) {
                    throw new IllegalArgumentException("sizeが 4ではありません。");
                }
                return Pair.with(source.substring(0, 2), source.substring(2, 4));
            }
        }

        private static final class TripletCodeDeserializer extends JsonDeserializer<Triplet<String, String, String>> {
            @Override
            public Triplet<String, String, String> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
                String source = p.getValueAsString();
                if ("      ".equals(source)) {
                    return Triplet.with(null, null, null);
                }
                if (source.length() != 6) {
                    throw new IllegalArgumentException("sizeが 6ではありません。");
                }
                return Triplet.with(source.substring(0, 2), source.substring(2, 4), source.substring(4, 6));
            }
        }

        private static final class LocalDateDeserializer extends JsonDeserializer<LocalDate> {
            @Override
            public LocalDate deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
                DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMdd");
                // 日付が設定されていない場合のデフォルト値
                if ("00000000".equals(p.getValueAsString())) {
                    //return LocalDate.MIN;
                    // ↑これダメっぽい。一旦null。
                    return null;
                }
                return LocalDate.parse(p.getValueAsString(), format);
            }
        }

        private static final class LocalTimeDeserializer extends JsonDeserializer<LocalTime> {
            @Override
            public LocalTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
                String source = p.getValueAsString();
                if ("    ".equals(source)) {
                    return LocalTime.of(0, 0, 0);
                    /**
                     * BaseModelのサブクラスに、annotationをつけたくなかったので名前で判定
                     * @see RacingHorseDetail#runningTime
                     */
                } else if ("runningTime".equals(p.getCurrentName())) {
                    int minute = Integer.parseInt(source.substring(0, 1));
                    int second = Integer.parseInt(source.substring(1, 3));
                    int nano = Integer.parseInt(source.substring(3, 4)) * 100 * 1000 * 1000;
                    return LocalTime.of(0, minute, second, nano);
                    /**
                     * @see RacingDetail#startTime
                     * @see RacingDetail#startTimeBefore
                     */
                } else {
                    int hour = Integer.parseInt(source.substring(0, 2));
                    int minute = Integer.parseInt(source.substring(2, 4));
                    return LocalTime.of(hour, minute, 0, 0);
                }
            }
        }

        private static final class IntegerDeserializer extends JsonDeserializer<Integer> {
            @Override
            public Integer deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
                String source = p.getValueAsString();
                if ("  ".equals(source)
                        || "   ".equals(source)
                        || "    ".equals(source)
                        || "         ".equals(source)) {
                    return null;
                }
                if ("--".equals(source)
                        || "---".equals(source)
                        || "----".equals(source)) {
                    return -100;
                }
                if ("**".equals(source)
                        || "***".equals(source)
                        || "****".equals(source)) {
                    return -999;
                }
                return Integer.valueOf(source);
            }
        }

        private static final class DoubleDeserializer extends JsonDeserializer<Double> {
            @Override
            public Double deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
                String source = p.getValueAsString();
                // 少数点を追加。（仕様上こうするしかない。。。）
                StringBuilder stringBuilder = new StringBuilder(source);
                stringBuilder.insert(source.length() - 1, ".");
                return Double.valueOf(stringBuilder.toString());
            }
        }

        private static final class BigDecimalDeserializer extends JsonDeserializer<BigDecimal> {
            @Override
            public BigDecimal deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
                String source = p.getValueAsString();
                if ("    ".equals(source)
                        || "     ".equals(source)
                        || "      ".equals(source)
                        || "       ".equals(source)) {
                    return null;
                }
                if ("----".equals(source)
                        || "-----".equals(source)
                        || "------".equals(source)
                        || "-------".equals(source)) {
                    // 数字の場合、static factoryを呼ぶ。
                    return BigDecimal.valueOf(-100.0);
                }
                if ("****".equals(source)
                        || "*****".equals(source)
                        || "******".equals(source)
                        || "*******".equals(source)) {
                    return BigDecimal.valueOf(-999.0);
                }
                // 少数点を追加。（仕様上こうするしかない。。。）
                StringBuilder stringBuilder = new StringBuilder(source);
                stringBuilder.insert(source.length() - 1, ".");
                // stringの場合、コンストラクターを呼ぶ。
                return new BigDecimal(stringBuilder.toString());
            }
        }

        private static final class StringDeserializer extends JsonDeserializer<String> {
            @Override
            public String deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
                String source = p.getValueAsString();
                // 先頭と末尾の半角全角空白文字を削除。
                return source == null ? null : source.strip();
            }
        }

        private static final class BooleanDeserializer extends JsonDeserializer<Boolean> {
            @Override
            public Boolean deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
                if ("0".equals(p.getValueAsString())) {
                    return false;
                } else if ("1".equals(p.getValueAsString())) {
                    return true;
                } else {
                    throw new IllegalArgumentException("0 or 1ではありません。");
                }
            }
        }
    }

}


