package org.uma.cloud.stream.processor.component;

import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
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
import org.uma.cloud.common.model.Breeder;
import org.uma.cloud.common.model.BreedingHorse;
import org.uma.cloud.common.model.Course;
import org.uma.cloud.common.model.HorseRacingDetails;
import org.uma.cloud.common.model.Jockey;
import org.uma.cloud.common.model.Offspring;
import org.uma.cloud.common.model.Owner;
import org.uma.cloud.common.model.RaceHorse;
import org.uma.cloud.common.model.RaceRefund;
import org.uma.cloud.common.model.RacingDetails;
import org.uma.cloud.common.model.Trainer;
import org.uma.cloud.common.model.VoteCount;
import org.uma.cloud.common.model.odds.Quinella;
import org.uma.cloud.common.model.odds.WinsPlaceBracketQuinella;
import org.uma.cloud.common.recordSpec.RecordSpec;
import org.uma.cloud.common.utils.javatuples.Pair;
import org.uma.cloud.common.utils.javatuples.Triplet;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.EnumMap;


@Configuration
public class JvLinkModelConfiguration {

    private static final Converter<String, LocalDate> toLocalDate = new AbstractConverter<String, LocalDate>() {
        @Override
        protected LocalDate convert(String source) {
            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMdd");
            // 日付が設定されていない場合のデフォルト値
            if ("00000000".equals(source)) {
                //return LocalDate.MIN;
                // ↑これダメっぽい。一旦null。
                return null;
            }
            return LocalDate.parse(source, format);
        }
    };

    private static final Converter<String, RaceCourseCode> toRaceCourseCode = new AbstractConverter<String, RaceCourseCode>() {
        @Override
        protected RaceCourseCode convert(String source) {
            return RaceCourseCode.of(source);
        }
    };

    private static final Converter<String, WeekDayCode> toWeekDayCode = new AbstractConverter<String, WeekDayCode>() {
        @Override
        protected WeekDayCode convert(String source) {
            Integer code = Integer.valueOf(source);
            return WeekDayCode.of(code);
        }
    };

    private static final Converter<String, RaceGradeCode> toRaceGradeCode = new AbstractConverter<String, RaceGradeCode>() {
        @Override
        protected RaceGradeCode convert(String source) {
            return RaceGradeCode.of(source);
        }
    };

    private static final Converter<String, JockeyApprenticeCode> toJockeyApprenticeCode = new AbstractConverter<String, JockeyApprenticeCode>() {
        @Override
        protected JockeyApprenticeCode convert(String source) {
            Integer code = Integer.valueOf(source);
            return JockeyApprenticeCode.of(code);
        }
    };

    private static final Converter<String, AbnormalDivisionCode> toAbnormalDivisionCode = new AbstractConverter<String, AbnormalDivisionCode>() {
        @Override
        protected AbnormalDivisionCode convert(String source) {
            Integer code = Integer.valueOf(source);
            return AbnormalDivisionCode.of(code);
        }
    };

    private static final Converter<String, BreedCode> toBredCode = new AbstractConverter<String, BreedCode>() {
        @Override
        protected BreedCode convert(String source) {
            Integer code = Integer.valueOf(source);
            return BreedCode.of(code);
        }
    };

    private static final Converter<String, EastOrWestBelongCode> toEastOrWestBelongCode = new AbstractConverter<String, EastOrWestBelongCode>() {
        @Override
        protected EastOrWestBelongCode convert(String source) {
            Integer code = Integer.valueOf(source);
            return EastOrWestBelongCode.of(code);
        }
    };

    private static final Converter<String, RaceSignCode> toRaceSignCode = new AbstractConverter<String, RaceSignCode>() {
        @Override
        protected RaceSignCode convert(String source) {
            return RaceSignCode.of(source);
        }
    };

    private static final Converter<String, SexCode> toSexCode = new AbstractConverter<String, SexCode>() {
        @Override
        protected SexCode convert(String source) {
            Integer code = Integer.valueOf(source);
            return SexCode.of(code);
        }
    };

    private static final Converter<String, MarginCode> toMarginCode = new AbstractConverter<String, MarginCode>() {
        @Override
        protected MarginCode convert(String source) {
            return MarginCode.of(source);
        }
    };

    private static final Converter<String, HorseSignCode> toHorseSignCode = new AbstractConverter<String, HorseSignCode>() {
        @Override
        protected HorseSignCode convert(String source) {
            return HorseSignCode.of(source);
        }
    };

    private static final Converter<String, TurfOrDirtConditionCode> toTurfOrDirtConditionCode = new AbstractConverter<String, TurfOrDirtConditionCode>() {
        @Override
        protected TurfOrDirtConditionCode convert(String source) {
            Integer code = Integer.valueOf(source);
            return TurfOrDirtConditionCode.of(code);
        }
    };

    private static final Converter<String, WeatherCode> toWeatherCode = new AbstractConverter<String, WeatherCode>() {
        @Override
        protected WeatherCode convert(String source) {
            Integer code = Integer.valueOf(source);
            return WeatherCode.of(code);
        }
    };

    private static final Converter<String, WeightTypeCode> toWeightTypeCode = new AbstractConverter<String, WeightTypeCode>() {
        @Override
        protected WeightTypeCode convert(String source) {
            Integer code = Integer.valueOf(source);
            return WeightTypeCode.of(code);
        }
    };

    private static final Converter<String, TrackCode> toTrackCode = new AbstractConverter<String, TrackCode>() {
        @Override
        protected TrackCode convert(String source) {
            Integer code = Integer.valueOf(source);
            return TrackCode.of(code);
        }
    };

    private static final Converter<String, JockeyLicenseCode> toJockeyLicenseCode = new AbstractConverter<String, JockeyLicenseCode>() {
        @Override
        protected JockeyLicenseCode convert(String source) {
            Integer code = Integer.valueOf(source);
            return JockeyLicenseCode.of(code);
        }
    };

    private static final Converter<String, RaceTypeCode> toRaceTypeCode = new AbstractConverter<String, RaceTypeCode>() {
        @Override
        protected RaceTypeCode convert(String source) {
            Integer code = Integer.valueOf(source);
            return RaceTypeCode.of(code);
        }
    };

    private static final Converter<String, HairColorCode> toHairColorCode = new AbstractConverter<String, HairColorCode>() {
        @Override
        protected HairColorCode convert(String source) {
            return HairColorCode.of(source);
        }
    };

    private static final Converter<String, Integer> toInteger = new AbstractConverter<String, Integer>() {
        @Override
        protected Integer convert(String source) {
            if ("  ".equals(source)
                    || "   ".equals(source)
                    || "    ".equals(source)
                    || "     ".equals(source)
                    || "      ".equals(source)) {
                return null;
            }
            if ("--".equals(source)
                    || "---".equals(source)
                    || "----".equals(source)
                    || "-----".equals(source)
                    || "------".equals(source)) {
                return -100;
            }
            if ("**".equals(source)
                    || "***".equals(source)
                    || "****".equals(source)
                    || "*****".equals(source)
                    || "******".equals(source)) {
                return -999;
            }
            return Integer.valueOf(source);
        }
    };

    private static final Converter<String, Pair<String, String>> toPair = new AbstractConverter<String, Pair<String, String>>() {
        @Override
        protected Pair<String, String> convert(String source) {
            if ("    ".equals(source)) {
                return Pair.with(null, null);
            }
            if (source.length() != 4) {
                throw new IllegalArgumentException("sizeが 4ではありません。");
            }
            return Pair.with(source.substring(0, 2), source.substring(2, 4));
        }
    };

    private static final Converter<String, Triplet<String, String, String>> toTriplet = new AbstractConverter<String, Triplet<String, String, String>>() {
        @Override
        protected Triplet<String, String, String> convert(String source) {
            if ("      ".equals(source)) {
                return Triplet.with(null, null, null);
            }
            if (source.length() != 6) {
                throw new IllegalArgumentException("sizeが 6ではありません。");
            }
            return Triplet.with(source.substring(0, 2), source.substring(2, 4), source.substring(4, 6));
        }
    };


    @Bean
    public EnumMap<RecordSpec, Class<?>> recordSpecPairEnumMap() {
        EnumMap<RecordSpec, Class<?>> enumMap = new EnumMap<>(RecordSpec.class);
        // RACE
        enumMap.put(RecordSpec.RA, RacingDetails.class);
        enumMap.put(RecordSpec.SE, HorseRacingDetails.class);
        enumMap.put(RecordSpec.HR, RaceRefund.class);
        enumMap.put(RecordSpec.H1, VoteCount.class);
        // ODDS
        enumMap.put(RecordSpec.O1, WinsPlaceBracketQuinella.class);
        enumMap.put(RecordSpec.O2, Quinella.class);
        // BLOD
        enumMap.put(RecordSpec.SK, Offspring.class);
        enumMap.put(RecordSpec.BT, Ancestry.class);
        enumMap.put(RecordSpec.HN, BreedingHorse.class);
        // DIFF
        enumMap.put(RecordSpec.CS, Course.class);
        enumMap.put(RecordSpec.UM, RaceHorse.class);
        enumMap.put(RecordSpec.KS, Jockey.class);
        enumMap.put(RecordSpec.CH, Trainer.class);
        enumMap.put(RecordSpec.BR, Breeder.class);
        enumMap.put(RecordSpec.BN, Owner.class);
        return enumMap;
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addConverter(toLocalDate);
        modelMapper.addConverter(toRaceCourseCode);
        modelMapper.addConverter(toWeekDayCode);
        modelMapper.addConverter(toRaceGradeCode);
        modelMapper.addConverter(toJockeyApprenticeCode);
        modelMapper.addConverter(toAbnormalDivisionCode);
        modelMapper.addConverter(toBredCode);
        modelMapper.addConverter(toEastOrWestBelongCode);
        modelMapper.addConverter(toRaceSignCode);
        modelMapper.addConverter(toSexCode);
        modelMapper.addConverter(toMarginCode);
        modelMapper.addConverter(toHorseSignCode);
        modelMapper.addConverter(toTurfOrDirtConditionCode);
        modelMapper.addConverter(toWeatherCode);
        modelMapper.addConverter(toWeightTypeCode);
        modelMapper.addConverter(toTrackCode);
        modelMapper.addConverter(toJockeyLicenseCode);
        modelMapper.addConverter(toRaceTypeCode);
        modelMapper.addConverter(toHairColorCode);
        modelMapper.addConverter(toInteger);
        modelMapper.addConverter(toPair);
        modelMapper.addConverter(toTriplet);
        return modelMapper;
    }

}
