package org.uma.cloud.common.component;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.uma.cloud.common.model.Ancestry;
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
import org.uma.cloud.common.utils.lang.ByteUtil;

import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class JvLinkFunction {

    private final JvLinkModelMapper jvLinkModelMapper;


    public Function<String, byte[]> decode() {
        return ByteUtil::base64Decode;
    }


    /**
     * recordSpecごとの、transform Configuration
     */
    public RacingDetails racingDetailsFunction(byte[] data) {
        RacingDetails model = jvLinkModelMapper.deserialize(data, RacingDetails.class);
        model.getLapTimeItems().removeIf(time -> time.getMinute() == 0
                && time.getSecond() == 0
                && time.getNano() == 0);
        return model;
    }


    public HorseRacingDetails horseRacingDetailsFunction(byte[] data) {
        return jvLinkModelMapper.deserialize(data, HorseRacingDetails.class);
    }

    public RaceRefund raceRefundFunction(byte[] data) {
        RaceRefund model = jvLinkModelMapper.deserialize(data, RaceRefund.class);
        model.getRefundWins().removeIf(JvLinkFunction::refundFilter);
        model.getRefundPlaces().removeIf(JvLinkFunction::refundFilter);
        model.getRefundBracketQuinellas().removeIf(JvLinkFunction::refundFilter);
        model.getRefundQuinellas().removeIf(JvLinkFunction::refundFilter);
        model.getRefundQuinellaPlaces().removeIf(JvLinkFunction::refundFilter);
        model.getRefundSpares().removeIf(JvLinkFunction::refundFilter);
        model.getRefundExactas().removeIf(JvLinkFunction::refundFilter);
        model.getRefundTrios().removeIf(JvLinkFunction::refundFilter);
        model.getRefundTrifectas().removeIf(JvLinkFunction::refundFilter);
        return model;
    }

    public VoteCount voteCountFunction(byte[] data) {
        VoteCount model = jvLinkModelMapper.deserialize(data, VoteCount.class);
        model.getVoteCountWins().removeIf(JvLinkFunction::voteFilter);
        model.getVoteCountPlaces().removeIf(JvLinkFunction::voteFilter);
        model.getVoteCountBracketQuinellas().removeIf(JvLinkFunction::voteFilter);
        model.getVoteCountQuinellas().removeIf(JvLinkFunction::voteFilter);
        model.getVoteCountQuinellaPlaces().removeIf(JvLinkFunction::voteFilter);
        model.getVoteCountExactas().removeIf(JvLinkFunction::voteFilter);
        model.getVoteCountTrios().removeIf(JvLinkFunction::voteFilter);
        return model;
    }

    public RaceHorseExclusion raceHorseExclusionFunction(byte[] data) {
        return jvLinkModelMapper.deserialize(data, RaceHorseExclusion.class);
    }

    public WinsPlaceBracketQuinella winsPlaceBracketQuinellaFunction(byte[] data) {
        WinsPlaceBracketQuinella model = jvLinkModelMapper.deserialize(data, WinsPlaceBracketQuinella.class);
        model.getWinOdds().removeIf(JvLinkFunction::winOddsFilter);
        model.getPlaceOdds().removeIf(JvLinkFunction::placeOddsFilter);
        model.getBracketQuinellaOdds().removeIf(JvLinkFunction::bracketQuinellaOddsFilter);
        return model;
    }

    public Quinella quinellaFunction(byte[] data) {
        Quinella model = jvLinkModelMapper.deserialize(data, Quinella.class);
        model.getQuinellaOdds().removeIf(JvLinkFunction::quinellaOddsFilter);
        return model;
    }

    public QuinellaPlace quinellaPlaceFunction(byte[] data) {
        QuinellaPlace model = jvLinkModelMapper.deserialize(data, QuinellaPlace.class);
        model.getQuinellaPlaceOdds().removeIf(JvLinkFunction::quinellaPlaceOddsFilter);
        return model;
    }

    public Exacta exactaFunction(byte[] data) {
        Exacta model = jvLinkModelMapper.deserialize(data, Exacta.class);
        model.getExactaOdds().removeIf(JvLinkFunction::exactaOddsFilter);
        return model;
    }

    public Trio trioFunction(byte[] data) {
        Trio model = jvLinkModelMapper.deserialize(data, Trio.class);
        model.getTrioOdds().removeIf(JvLinkFunction::trioOddsFilter);
        return model;
    }

    public Trifecta trifectaFunction(byte[] data) {
        Trifecta model = jvLinkModelMapper.deserialize(data, Trifecta.class);
        model.getTrifectaOdds().removeIf(JvLinkFunction::trifectaOddsFilter);
        return model;
    }

    public Offspring offspringFunction(byte[] data) {
        return jvLinkModelMapper.deserialize(data, Offspring.class);
    }

    public Ancestry ancestryFunction(byte[] data) {
        return jvLinkModelMapper.deserialize(data, Ancestry.class);
    }

    public BreedingHorse breedingHorseFunction(byte[] data) {
        return jvLinkModelMapper.deserialize(data, BreedingHorse.class);
    }

    public RaceHorse raceHorseFunction(byte[] data) {
        return jvLinkModelMapper.deserialize(data, RaceHorse.class);
    }

    public Jockey jockeyFunction(byte[] data) {
        return jvLinkModelMapper.deserialize(data, Jockey.class);
    }

    public Trainer trainerFunction(byte[] data) {
        return jvLinkModelMapper.deserialize(data, Trainer.class);
    }

    public Breeder breederFunction(byte[] data) {
        return jvLinkModelMapper.deserialize(data, Breeder.class);
    }

    public Owner ownerFunction(byte[] data) {
        return jvLinkModelMapper.deserialize(data, Owner.class);
    }

    public Course courseFunction(byte[] data) {
        return jvLinkModelMapper.deserialize(data, Course.class);
    }


    /**
     * 不要データのFilter static methods
     */
    private static boolean winOddsFilter(WinsPlaceBracketQuinella.WinOdds winOdds) {
        return winOdds.getOdds() == null
                && winOdds.getBetRank() == null;
    }

    private static boolean placeOddsFilter(WinsPlaceBracketQuinella.PlaceOdds placeOdds) {
        return placeOdds.getOddsMin() == null
                && placeOdds.getOddsMax() == null
                && placeOdds.getBetRank() == null;
    }

    private static boolean bracketQuinellaOddsFilter(WinsPlaceBracketQuinella.BracketQuinellaOdds
                                                             bracketQuinellaOdds) {
        return bracketQuinellaOdds.getOdds() == null
                && bracketQuinellaOdds.getBetRank() == null;
    }

    private static boolean quinellaOddsFilter(Quinella.QuinellaOdds quinellaOdds) {
        return quinellaOdds.getOdds() == null
                && quinellaOdds.getBetRank() == null;
    }

    private static boolean quinellaPlaceOddsFilter(QuinellaPlace.QuinellaPlaceOdds quinellaPlaceOdds) {
        return quinellaPlaceOdds.getOddsMin() == null
                && quinellaPlaceOdds.getOddsMax() == null
                && quinellaPlaceOdds.getBetRank() == null;
    }

    private static boolean exactaOddsFilter(Exacta.ExactaOdds exactaOdds) {
        return exactaOdds.getOdds() == null
                && exactaOdds.getBetRank() == null;
    }

    private static boolean trioOddsFilter(Trio.TrioOdds trioOdds) {
        return trioOdds.getOdds() == null
                && trioOdds.getBetRank() == null;
    }

    private static boolean trifectaOddsFilter(Trifecta.TrifectaOdds trifectaOdds) {
        return trifectaOdds.getOdds() == null
                && trifectaOdds.getBetRank() == null;
    }


    private static boolean refundFilter(RaceRefund.refund refund) {
        return refund.getBetRank() == null
                && refund.getRefundMoney() == null;
    }

    private static boolean refundFilter(RaceRefund.refundPair refundPair) {
        return refundPair.getBetRank() == null
                && refundPair.getRefundMoney() == null;
    }

    private static boolean refundFilter(RaceRefund.refundTriplet refundTriplet) {
        return refundTriplet.getBetRank() == null
                && refundTriplet.getRefundMoney() == null;
    }

    private static boolean voteFilter(VoteCount.Vote vote) {
        return vote.getBetRank() == null
                && vote.getVoteCount() == null;
    }

    private static boolean voteFilter(VoteCount.VotePair votePair) {
        return votePair.getBetRank() == null
                && votePair.getVoteCount() == null;
    }

    private static boolean voteFilter(VoteCount.VoteTriplet voteTriplet) {
        return voteTriplet.getBetRank() == null
                && voteTriplet.getVoteCount() == null;
    }

}
