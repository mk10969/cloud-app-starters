package org.uma.cloud.stream.processor.component;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;


@Configuration
@RequiredArgsConstructor
public class JvLinkModelMapperConfiguration {

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
     * recordSpecごとの、transform Configuration
     */
    private final Function<byte[], RacingDetails> racingDetailsFunction =
            data -> deserialize(data, RacingDetails.class);

    private final Function<byte[], HorseRacingDetails> horseRacingDetailsFunction =
            data -> deserialize(data, HorseRacingDetails.class);

    private final Function<byte[], RaceRefund> refundFunction =
            data -> {
                RaceRefund model = deserialize(data, RaceRefund.class);
                model.getRefundWins().removeIf(this::refundFilter);
                model.getRefundPlaces().removeIf(this::refundFilter);
                model.getRefundBracketQuinellas().removeIf(this::refundFilter);
                model.getRefundQuinellas().removeIf(this::refundFilter);
                model.getRefundQuinellaPlaces().removeIf(this::refundFilter);
                model.getRefundSpares().removeIf(this::refundFilter);
                model.getRefundExactas().removeIf(this::refundFilter);
                model.getRefundTrios().removeIf(this::refundFilter);
                model.getRefundTrifectas().removeIf(this::refundFilter);
                return model;
            };

    private final Function<byte[], VoteCount> voteCountFunction =
            data -> {
                VoteCount model = deserialize(data, VoteCount.class);
                model.getVoteCountWins().removeIf(this::voteFilter);
                model.getVoteCountPlaces().removeIf(this::voteFilter);
                model.getVoteCountBracketQuinellas().removeIf(this::voteFilter);
                model.getVoteCountQuinellas().removeIf(this::voteFilter);
                model.getVoteCountQuinellaPlaces().removeIf(this::voteFilter);
                model.getVoteCountExactas().removeIf(this::voteFilter);
                model.getVoteCountTrios().removeIf(this::voteFilter);
                return model;
            };

    private final Function<byte[], RaceHorseExclusion> raceHorseExclusionFunction =
            data -> deserialize(data, RaceHorseExclusion.class);

    private final Function<byte[], WinsPlaceBracketQuinella> winsPlaceBracketQuinellaFunction =
            data -> {
                WinsPlaceBracketQuinella model = deserialize(data, WinsPlaceBracketQuinella.class);
                model.getWinOdds().removeIf(this::winOddsFilter);
                model.getPlaceOdds().removeIf(this::placeOddsFilter);
                model.getBracketQuinellaOdds().removeIf(this::bracketQuinellaOddsFilter);
                return model;
            };

    private final Function<byte[], Quinella> quinellaFunction =
            data -> {
                Quinella model = deserialize(data, Quinella.class);
                model.getQuinellaOdds().removeIf(this::quinellaOddsFilter);
                return model;
            };

    private final Function<byte[], QuinellaPlace> quinellaPlaceFunction =
            data -> {
                QuinellaPlace model = deserialize(data, QuinellaPlace.class);
                model.getQuinellaPlaceOdds().removeIf(this::quinellaPlaceOddsFilter);
                return model;
            };

    private final Function<byte[], Exacta> exactaFunction =
            data -> {
                Exacta model = deserialize(data, Exacta.class);
                model.getExactaOdds().removeIf(this::exactaOddsFilter);
                return model;
            };

    private final Function<byte[], Trio> trioFunction =
            data -> {
                Trio model = deserialize(data, Trio.class);
                model.getTrioOdds().removeIf(this::trioOddsFilter);
                return model;
            };

    public final Function<byte[], Trifecta> trifectaFunction =
            data -> {
                Trifecta model = deserialize(data, Trifecta.class);
                model.getTrifectaOdds().removeIf(this::trifectaOddsFilter);
                return model;
            };

    public final Function<byte[], Offspring> offspringFunction =
            data -> deserialize(data, Offspring.class);

    public final Function<byte[], Ancestry> ancestryFunction =
            data -> deserialize(data, Ancestry.class);

    public final Function<byte[], BreedingHorse> breedingHorseFunction =
            data -> deserialize(data, BreedingHorse.class);

    public final Function<byte[], RaceHorse> raceHorseFunction =
            data -> deserialize(data, RaceHorse.class);

    public final Function<byte[], Jockey> jockeyFunction =
            data -> deserialize(data, Jockey.class);

    public final Function<byte[], Trainer> trainerFunction =
            data -> deserialize(data, Trainer.class);

    public final Function<byte[], Breeder> breederFunction =
            data -> deserialize(data, Breeder.class);

    public final Function<byte[], Owner> ownerFunction =
            data -> deserialize(data, Owner.class);

    public final Function<byte[], Course> courseFunction =
            data -> deserialize(data, Course.class);


    /**
     * clazzから、RecordSpecを抽出し、flatMapをつかって、RecordSpecから、RecordSpecItemsを抽出する。
     *
     * @param clazz
     * @return RecordSpecItems
     */
    private JvLinkRecordProperties.RecordSpecItems findOne(Class<?> clazz) {
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


    private <T> T deserialize(final byte[] byteArrayLine, final Class<T> clazz) {
        Map<String, Object> deSerialMap = new HashMap<>();

        findOne(clazz).getRecordItems().forEach(record -> {
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

        return modelMapper.map(deSerialMap, clazz);
    }


    @Bean
    public EnumMap<RecordSpec, Function<byte[], ? extends BaseModel>> recordSpecFunctionEnumMap() {
        EnumMap<RecordSpec, Function<byte[], ? extends BaseModel>> enumMap = new EnumMap<>(RecordSpec.class);
        // RACE
        enumMap.put(RecordSpec.RA, racingDetailsFunction);
        enumMap.put(RecordSpec.SE, horseRacingDetailsFunction);
        enumMap.put(RecordSpec.HR, refundFunction);
        enumMap.put(RecordSpec.H1, voteCountFunction);
        enumMap.put(RecordSpec.JG, raceHorseExclusionFunction);
        // ODDS
        enumMap.put(RecordSpec.O1, winsPlaceBracketQuinellaFunction);
        enumMap.put(RecordSpec.O2, quinellaFunction);
        enumMap.put(RecordSpec.O3, quinellaPlaceFunction);
        enumMap.put(RecordSpec.O4, exactaFunction);
        enumMap.put(RecordSpec.O5, trioFunction);
        enumMap.put(RecordSpec.O6, trifectaFunction);
        // BLOD
        enumMap.put(RecordSpec.SK, offspringFunction);
        enumMap.put(RecordSpec.BT, ancestryFunction);
        enumMap.put(RecordSpec.HN, breedingHorseFunction);
        // DIFF
        enumMap.put(RecordSpec.UM, raceHorseFunction);
        enumMap.put(RecordSpec.KS, jockeyFunction);
        enumMap.put(RecordSpec.CH, trainerFunction);
        enumMap.put(RecordSpec.BR, breederFunction);
        enumMap.put(RecordSpec.BN, ownerFunction);
        // COMM
        enumMap.put(RecordSpec.CS, courseFunction);
        return enumMap;
    }


    private boolean winOddsFilter(WinsPlaceBracketQuinella.WinOdds winOdds) {
        return winOdds.getOdds() == null
                && winOdds.getBetRank() == null;
    }

    private boolean placeOddsFilter(WinsPlaceBracketQuinella.PlaceOdds placeOdds) {
        return placeOdds.getOddsMin() == null
                && placeOdds.getOddsMax() == null
                && placeOdds.getBetRank() == null;
    }

    private boolean bracketQuinellaOddsFilter(WinsPlaceBracketQuinella.BracketQuinellaOdds bracketQuinellaOdds) {
        return bracketQuinellaOdds.getOdds() == null
                && bracketQuinellaOdds.getBetRank() == null;
    }

    private boolean quinellaOddsFilter(Quinella.QuinellaOdds quinellaOdds) {
        return quinellaOdds.getOdds() == null
                && quinellaOdds.getBetRank() == null;
    }

    private boolean quinellaPlaceOddsFilter(QuinellaPlace.QuinellaPlaceOdds quinellaPlaceOdds) {
        return quinellaPlaceOdds.getOddsMin() == null
                && quinellaPlaceOdds.getOddsMax() == null
                && quinellaPlaceOdds.getBetRank() == null;
    }

    private boolean exactaOddsFilter(Exacta.ExactaOdds exactaOdds) {
        return exactaOdds.getOdds() == null
                && exactaOdds.getBetRank() == null;
    }

    private boolean trioOddsFilter(Trio.TrioOdds trioOdds) {
        return trioOdds.getOdds() == null
                && trioOdds.getBetRank() == null;
    }

    private boolean trifectaOddsFilter(Trifecta.TrifectaOdds trifectaOdds) {
        return trifectaOdds.getOdds() == null
                && trifectaOdds.getBetRank() == null;
    }


    private boolean refundFilter(RaceRefund.refund refund) {
        return refund.getBetRank() == null
                && refund.getRefundMoney() == null;
    }

    private boolean refundFilter(RaceRefund.refundPair refundPair) {
        return refundPair.getBetRank() == null
                && refundPair.getRefundMoney() == null;
    }

    private boolean refundFilter(RaceRefund.refundTriplet refundTriplet) {
        return refundTriplet.getBetRank() == null
                && refundTriplet.getRefundMoney() == null;
    }

    private boolean voteFilter(VoteCount.Vote vote) {
        return vote.getBetRank() == null
                && vote.getVoteCount() == null;
    }

    private boolean voteFilter(VoteCount.VotePair votePair) {
        return votePair.getBetRank() == null
                && votePair.getVoteCount() == null;
    }

    private boolean voteFilter(VoteCount.VoteTriplet voteTriplet) {
        return voteTriplet.getBetRank() == null
                && voteTriplet.getVoteCount() == null;
    }

}