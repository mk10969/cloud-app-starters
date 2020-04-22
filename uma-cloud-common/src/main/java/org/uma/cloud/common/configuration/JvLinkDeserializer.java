package org.uma.cloud.common.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.uma.cloud.common.model.BloodAncestry;
import org.uma.cloud.common.model.BloodBreeding;
import org.uma.cloud.common.model.BloodLine;
import org.uma.cloud.common.model.Breeder;
import org.uma.cloud.common.model.Course;
import org.uma.cloud.common.model.Jockey;
import org.uma.cloud.common.model.Owner;
import org.uma.cloud.common.model.RaceHorse;
import org.uma.cloud.common.model.RacingDetail;
import org.uma.cloud.common.model.RacingHorseDetail;
import org.uma.cloud.common.model.RacingHorseExclusion;
import org.uma.cloud.common.model.RacingRefund;
import org.uma.cloud.common.model.RacingVote;
import org.uma.cloud.common.model.Trainer;
import org.uma.cloud.common.model.odds.Exacta;
import org.uma.cloud.common.model.odds.Quinella;
import org.uma.cloud.common.model.odds.QuinellaPlace;
import org.uma.cloud.common.model.odds.Trifecta;
import org.uma.cloud.common.model.odds.Trio;
import org.uma.cloud.common.model.odds.WinsShowBracketQ;
import org.uma.cloud.common.utils.lang.ByteUtil;

import java.util.function.Function;

@Service
public class JvLinkDeserializer {

    @Autowired
    private JvLinkModelMapper jvLinkModelMapper;


    public Function<String, byte[]> decode() {
        return ByteUtil::base64Decode;
    }

    /**
     * recordSpecごとの、transform Configuration
     */
    public RacingDetail racingDetailFunction(byte[] data) {
        RacingDetail model = jvLinkModelMapper.deserialize(data, RacingDetail.class);
        model.getLapTimeItems().removeIf(time -> time == 0.0);
        model.getCornerPassageRanks().removeIf(cornerPassageRank -> cornerPassageRank.getCorner() == 0
                && cornerPassageRank.getAroundCount() == 0
                && "".equals(cornerPassageRank.getPassageRank()));
        return model;
    }

    public RacingHorseDetail racingHorseDetailFunction(byte[] data) {
        return jvLinkModelMapper.deserialize(data, RacingHorseDetail.class);
    }

    public RacingRefund racingRefundFunction(byte[] data) {
        RacingRefund model = jvLinkModelMapper.deserialize(data, RacingRefund.class);
        model.getRefundWins().removeIf(JvLinkDeserializer::refundFilter);
        model.getRefundShows().removeIf(JvLinkDeserializer::refundFilter);
        model.getRefundBracketQs().removeIf(JvLinkDeserializer::refundFilter);
        model.getRefundQuinellas().removeIf(JvLinkDeserializer::refundFilter);
        model.getRefundQuinellaPlaces().removeIf(JvLinkDeserializer::refundFilter);
        model.getRefundSpares().removeIf(JvLinkDeserializer::refundFilter);
        model.getRefundExactas().removeIf(JvLinkDeserializer::refundFilter);
        model.getRefundTrios().removeIf(JvLinkDeserializer::refundFilter);
        model.getRefundTrifectas().removeIf(JvLinkDeserializer::refundFilter);
        return model;
    }

    public RacingVote racingVoteFunction(byte[] data) {
        RacingVote model = jvLinkModelMapper.deserialize(data, RacingVote.class);
        model.getVoteCountWins().removeIf(JvLinkDeserializer::voteFilter);
        model.getVoteCountShows().removeIf(JvLinkDeserializer::voteFilter);
        model.getVoteCountBracketQs().removeIf(JvLinkDeserializer::voteFilter);
        model.getVoteCountQuinellas().removeIf(JvLinkDeserializer::voteFilter);
        model.getVoteCountQuinellaPlaces().removeIf(JvLinkDeserializer::voteFilter);
        model.getVoteCountExactas().removeIf(JvLinkDeserializer::voteFilter);
        model.getVoteCountTrios().removeIf(JvLinkDeserializer::voteFilter);
        return model;
    }

    public RacingHorseExclusion racingHorseExclusionFunction(byte[] data) {
        return jvLinkModelMapper.deserialize(data, RacingHorseExclusion.class);
    }

    public WinsShowBracketQ winsShowBracketQFunction(byte[] data) {
        WinsShowBracketQ model = jvLinkModelMapper.deserialize(data, WinsShowBracketQ.class);
        model.getWinOdds().removeIf(JvLinkDeserializer::winOddsFilter);
        model.getShowOdds().removeIf(JvLinkDeserializer::showOddsFilter);
        model.getBracketQOdds().removeIf(JvLinkDeserializer::bracketQOddsFilter);
        return model;
    }

    public Quinella quinellaFunction(byte[] data) {
        Quinella model = jvLinkModelMapper.deserialize(data, Quinella.class);
        model.getQuinellaOdds().removeIf(JvLinkDeserializer::quinellaOddsFilter);
        return model;
    }

    public QuinellaPlace quinellaPlaceFunction(byte[] data) {
        QuinellaPlace model = jvLinkModelMapper.deserialize(data, QuinellaPlace.class);
        model.getQuinellaPlaceOdds().removeIf(JvLinkDeserializer::quinellaPlaceOddsFilter);
        return model;
    }

    public Exacta exactaFunction(byte[] data) {
        Exacta model = jvLinkModelMapper.deserialize(data, Exacta.class);
        model.getExactaOdds().removeIf(JvLinkDeserializer::exactaOddsFilter);
        return model;
    }

    public Trio trioFunction(byte[] data) {
        Trio model = jvLinkModelMapper.deserialize(data, Trio.class);
        model.getTrioOdds().removeIf(JvLinkDeserializer::trioOddsFilter);
        return model;
    }

    public Trifecta trifectaFunction(byte[] data) {
        Trifecta model = jvLinkModelMapper.deserialize(data, Trifecta.class);
        model.getTrifectaOdds().removeIf(JvLinkDeserializer::trifectaOddsFilter);
        return model;
    }

    public BloodLine bloodLineFunction(byte[] data) {
        return jvLinkModelMapper.deserialize(data, BloodLine.class);
    }

    public BloodAncestry bloodAncestryFunction(byte[] data) {
        return jvLinkModelMapper.deserialize(data, BloodAncestry.class);
    }

    public BloodBreeding bloodBreedingFunction(byte[] data) {
        return jvLinkModelMapper.deserialize(data, BloodBreeding.class);
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
    private static boolean winOddsFilter(WinsShowBracketQ.WinOdds winOdds) {
        return winOdds.getOdds() == null
                && winOdds.getBetRank() == null;
    }

    private static boolean showOddsFilter(WinsShowBracketQ.ShowOdds showOdds) {
        return showOdds.getOddsMin() == null
                && showOdds.getOddsMax() == null
                && showOdds.getBetRank() == null;
    }

    private static boolean bracketQOddsFilter(WinsShowBracketQ.BracketQOdds bracketQOdds) {
        return bracketQOdds.getOdds() == null
                && bracketQOdds.getBetRank() == null;
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


    private static boolean refundFilter(RacingRefund.refund refund) {
        return refund.getBetRank() == null
                && refund.getRefundMoney() == null;
    }

    private static boolean refundFilter(RacingRefund.refundPair refundPair) {
        return refundPair.getBetRank() == null
                && refundPair.getRefundMoney() == null;
    }

    private static boolean refundFilter(RacingRefund.refundTriplet refundTriplet) {
        return refundTriplet.getBetRank() == null
                && refundTriplet.getRefundMoney() == null;
    }

    private static boolean voteFilter(RacingVote.Vote vote) {
        return vote.getBetRank() == null
                && vote.getVoteCount() == null;
    }

    private static boolean voteFilter(RacingVote.VotePair votePair) {
        return votePair.getBetRank() == null
                && votePair.getVoteCount() == null;
    }

    private static boolean voteFilter(RacingVote.VoteTriplet voteTriplet) {
        return voteTriplet.getBetRank() == null
                && voteTriplet.getVoteCount() == null;
    }

}
