package org.uma.cloud.common.component;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
import org.uma.cloud.common.model.Ancestry;
import org.uma.cloud.common.model.BaseModel;
import org.uma.cloud.common.model.Breeder;
import org.uma.cloud.common.model.BreedingHorse;
import org.uma.cloud.common.model.Course;
import org.uma.cloud.common.model.HorseRacingDetails;
import org.uma.cloud.common.model.Jockey;
import org.uma.cloud.common.model.Offspring;
import org.uma.cloud.common.model.Owner;
import org.uma.cloud.common.model.RaceHorse;
import org.uma.cloud.common.model.RaceHorseExclusion;
import org.uma.cloud.common.model.RaceRefund;
import org.uma.cloud.common.model.RacingDetails;
import org.uma.cloud.common.model.Trainer;
import org.uma.cloud.common.model.VoteCount;
import org.uma.cloud.common.model.odds.Exacta;
import org.uma.cloud.common.model.odds.Quinella;
import org.uma.cloud.common.model.odds.QuinellaPlace;
import org.uma.cloud.common.model.odds.Trifecta;
import org.uma.cloud.common.model.odds.Trio;
import org.uma.cloud.common.model.odds.WinsPlaceBracketQuinella;
import org.uma.cloud.common.recordSpec.RecordSpec;
import org.uma.cloud.common.utils.javatuples.Pair;
import org.uma.cloud.common.utils.javatuples.Triplet;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.EnumMap;


@Configuration
public class JvLInkModelMapperConfiguration {

    @Bean
    public ObjectMapper objectMapper(SimpleModule simpleModule) {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.registerModule(simpleModule);
    }

    @Bean
    public EnumMap<RecordSpec, Class<? extends BaseModel>> recordSpecPairEnumMap() {
        EnumMap<RecordSpec, Class<? extends BaseModel>> enumMap = new EnumMap<>(RecordSpec.class);
        // RACE
        enumMap.put(RecordSpec.RA, RacingDetails.class);
        enumMap.put(RecordSpec.SE, HorseRacingDetails.class);
        enumMap.put(RecordSpec.HR, RaceRefund.class);
        enumMap.put(RecordSpec.H1, VoteCount.class);
        enumMap.put(RecordSpec.JG, RaceHorseExclusion.class);
        // ODDS
        enumMap.put(RecordSpec.O1, WinsPlaceBracketQuinella.class);
        enumMap.put(RecordSpec.O2, Quinella.class);
        enumMap.put(RecordSpec.O3, QuinellaPlace.class);
        enumMap.put(RecordSpec.O4, Exacta.class);
        enumMap.put(RecordSpec.O5, Trio.class);
        enumMap.put(RecordSpec.O6, Trifecta.class);
        // BLOD
        enumMap.put(RecordSpec.SK, Offspring.class);
        enumMap.put(RecordSpec.BT, Ancestry.class);
        enumMap.put(RecordSpec.HN, BreedingHorse.class);
        // DIFF
        enumMap.put(RecordSpec.UM, RaceHorse.class);
        enumMap.put(RecordSpec.KS, Jockey.class);
        enumMap.put(RecordSpec.CH, Trainer.class);
        enumMap.put(RecordSpec.BR, Breeder.class);
        enumMap.put(RecordSpec.BN, Owner.class);
        // COMM
        enumMap.put(RecordSpec.CS, Course.class);
        return enumMap;
    }

    @Bean
    public SimpleModule simpleModule() {
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addDeserializer(RaceCourseCode.class, new RaceCourseCodeDeserializer());
        simpleModule.addDeserializer(WeekDayCode.class, new WeekDayCodeDeserializer());
        simpleModule.addDeserializer(RaceGradeCode.class, new RaceGradeCodeDeserializer());
        simpleModule.addDeserializer(JockeyApprenticeCode.class, new JockeyApprenticeCodeDeserializer());
        simpleModule.addDeserializer(AbnormalDivisionCode.class, new AbnormalDivisionCodeDeserializer());
        simpleModule.addDeserializer(EastOrWestBelongCode.class, new EastOrWestBelongCodeDeserializer());
        simpleModule.addDeserializer(BreedCode.class, new BreedCodeDeserializer());
        simpleModule.addDeserializer(RaceSignCode.class, new RaceSignCodeDeserializer());
        simpleModule.addDeserializer(SexCode.class, new SexCodeDeserializer());
        simpleModule.addDeserializer(MarginCode.class, new MarginCodeDeserializer());
        simpleModule.addDeserializer(HorseSignCode.class, new HorseSignCodeDeserializer());
        simpleModule.addDeserializer(TurfOrDirtConditionCode.class, new TurfOrDirtConditionCodeDeserializer());
        simpleModule.addDeserializer(WeatherCode.class, new WeatherCodeDeserializer());
        simpleModule.addDeserializer(WeightTypeCode.class, new WeightTypeCodeDeserializer());
        simpleModule.addDeserializer(TrackCode.class, new TrackCodeDeserializer());
        simpleModule.addDeserializer(JockeyLicenseCode.class, new JockeyLicenseCodeDeserializer());
        simpleModule.addDeserializer(RaceTypeCode.class, new RaceTypeCodeDeserializer());
        simpleModule.addDeserializer(HairColorCode.class, new HairColorCodeDeserializer());

        simpleModule.addDeserializer(Pair.class, new PairCodeDeserializer());
        simpleModule.addDeserializer(Triplet.class, new TripletCodeDeserializer());

        simpleModule.addDeserializer(LocalDate.class, new LocalDateDeserializer());
        simpleModule.addDeserializer(Integer.class, new IntegerDeserializer());
        simpleModule.addDeserializer(String.class, new StringDeserializer());
        return simpleModule;
    }


    private final static class RaceCourseCodeDeserializer extends JsonDeserializer<RaceCourseCode> {
        @Override
        public RaceCourseCode deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            return RaceCourseCode.of(p.getValueAsString());
        }
    }

    private final static class WeekDayCodeDeserializer extends JsonDeserializer<WeekDayCode> {
        @Override
        public WeekDayCode deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            Integer code = Integer.valueOf(p.getValueAsString());
            return WeekDayCode.of(code);
        }
    }

    private final static class RaceGradeCodeDeserializer extends JsonDeserializer<RaceGradeCode> {
        @Override
        public RaceGradeCode deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            return RaceGradeCode.of(p.getValueAsString());
        }
    }

    private final static class JockeyApprenticeCodeDeserializer extends JsonDeserializer<JockeyApprenticeCode> {
        @Override
        public JockeyApprenticeCode deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            Integer code = Integer.valueOf(p.getValueAsString());
            return JockeyApprenticeCode.of(code);
        }
    }

    private final static class AbnormalDivisionCodeDeserializer extends JsonDeserializer<AbnormalDivisionCode> {
        @Override
        public AbnormalDivisionCode deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            Integer code = Integer.valueOf(p.getValueAsString());
            return AbnormalDivisionCode.of(code);
        }
    }

    private final static class BreedCodeDeserializer extends JsonDeserializer<BreedCode> {
        @Override
        public BreedCode deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            Integer code = Integer.valueOf(p.getValueAsString());
            return BreedCode.of(code);
        }
    }

    private final static class EastOrWestBelongCodeDeserializer extends JsonDeserializer<EastOrWestBelongCode> {
        @Override
        public EastOrWestBelongCode deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            Integer code = Integer.valueOf(p.getValueAsString());
            return EastOrWestBelongCode.of(code);
        }
    }

    private final static class RaceSignCodeDeserializer extends JsonDeserializer<RaceSignCode> {
        @Override
        public RaceSignCode deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            return RaceSignCode.of(p.getValueAsString());
        }
    }

    private final static class SexCodeDeserializer extends JsonDeserializer<SexCode> {
        @Override
        public SexCode deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            Integer code = Integer.valueOf(p.getValueAsString());
            return SexCode.of(code);
        }
    }

    private final static class MarginCodeDeserializer extends JsonDeserializer<MarginCode> {
        @Override
        public MarginCode deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            return MarginCode.of(p.getValueAsString());
        }
    }

    private final static class HorseSignCodeDeserializer extends JsonDeserializer<HorseSignCode> {
        @Override
        public HorseSignCode deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            return HorseSignCode.of(p.getValueAsString());
        }
    }

    private final static class TurfOrDirtConditionCodeDeserializer extends JsonDeserializer<TurfOrDirtConditionCode> {
        @Override
        public TurfOrDirtConditionCode deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            Integer code = Integer.valueOf(p.getValueAsString());
            return TurfOrDirtConditionCode.of(code);
        }
    }

    private final static class WeatherCodeDeserializer extends JsonDeserializer<WeatherCode> {
        @Override
        public WeatherCode deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            Integer code = Integer.valueOf(p.getValueAsString());
            return WeatherCode.of(code);
        }
    }

    private final static class WeightTypeCodeDeserializer extends JsonDeserializer<WeightTypeCode> {
        @Override
        public WeightTypeCode deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            Integer code = Integer.valueOf(p.getValueAsString());
            return WeightTypeCode.of(code);
        }
    }

    private final static class TrackCodeDeserializer extends JsonDeserializer<TrackCode> {
        @Override
        public TrackCode deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            Integer code = Integer.valueOf(p.getValueAsString());
            return TrackCode.of(code);
        }
    }

    private final static class JockeyLicenseCodeDeserializer extends JsonDeserializer<JockeyLicenseCode> {
        @Override
        public JockeyLicenseCode deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            Integer code = Integer.valueOf(p.getValueAsString());
            return JockeyLicenseCode.of(code);
        }
    }

    private final static class RaceTypeCodeDeserializer extends JsonDeserializer<RaceTypeCode> {
        @Override
        public RaceTypeCode deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            Integer code = Integer.valueOf(p.getValueAsString());
            return RaceTypeCode.of(code);
        }
    }

    private final static class HairColorCodeDeserializer extends JsonDeserializer<HairColorCode> {
        @Override
        public HairColorCode deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            return HairColorCode.of(p.getValueAsString());
        }
    }

    private final static class PairCodeDeserializer extends JsonDeserializer<Pair<String, String>> {
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

    private final static class TripletCodeDeserializer extends JsonDeserializer<Triplet<String, String, String>> {
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

    private final static class LocalDateDeserializer extends JsonDeserializer<LocalDate> {
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

    private final static class IntegerDeserializer extends JsonDeserializer<Integer> {
        @Override
        public Integer deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            String source = p.getValueAsString();
            if ("  ".equals(source)
                    || "   ".equals(source)
                    || "    ".equals(source)
                    || "     ".equals(source)
                    || "      ".equals(source)
                    || "       ".equals(source)) {
                return null;
            }
            if ("--".equals(source)
                    || "---".equals(source)
                    || "----".equals(source)
                    || "-----".equals(source)
                    || "------".equals(source)
                    || "-------".equals(source)) {
                return -100;
            }
            if ("**".equals(source)
                    || "***".equals(source)
                    || "****".equals(source)
                    || "*****".equals(source)
                    || "******".equals(source)
                    || "*******".equals(source)) {
                return -999;
            }
            return Integer.valueOf(source);
        }
    }

    private final static class StringDeserializer extends JsonDeserializer<String> {
        @Override
        public String deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            String source = p.getValueAsString();
            // 先頭と末尾の半角全角空白文字を削除。
            return source == null ? null : source.strip();
        }
    }

}
