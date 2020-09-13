package org.uma.cloud.common.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.uma.cloud.common.entity.BloodAncestry;
import org.uma.cloud.common.entity.BloodBreeding;
import org.uma.cloud.common.entity.BloodLine;
import org.uma.cloud.common.entity.DiffBreeder;
import org.uma.cloud.common.entity.Course;
import org.uma.cloud.common.entity.DiffJockey;
import org.uma.cloud.common.entity.DiffOwner;
import org.uma.cloud.common.entity.DiffRaceHorse;
import org.uma.cloud.common.entity.DiffTrainer;
import org.uma.cloud.common.entity.RacingDetail;
import org.uma.cloud.common.entity.RacingHorseDetail;
import org.uma.cloud.common.entity.RacingHorseExclusion;
import org.uma.cloud.common.entity.RacingRefund;
import org.uma.cloud.common.entity.RacingVote;
import org.uma.cloud.common.model.event.Avoid;
import org.uma.cloud.common.model.event.CourseChange;
import org.uma.cloud.common.model.event.JockeyChange;
import org.uma.cloud.common.model.event.TimeChange;
import org.uma.cloud.common.model.event.Weather;
import org.uma.cloud.common.model.event.Weight;
import org.uma.cloud.common.entity.OddsExacta;
import org.uma.cloud.common.entity.OddsQuinella;
import org.uma.cloud.common.entity.OddsQuinellaPlace;
import org.uma.cloud.common.entity.OddsTrifecta;
import org.uma.cloud.common.entity.OddsTrio;
import org.uma.cloud.common.entity.OddsWinsShowBracketQ;
import org.uma.cloud.common.utils.lang.ByteUtil;

import java.util.function.Function;

@Service
public class JvLinkDeserializer {

    @Autowired
    private JvLinkModelMapper jvLinkModelMapper;

    private static final Function<String, byte[]> decode = ByteUtil::base64Decode;


    /**
     * レースデータ
     */
    public RacingDetail toRacingDetail(String data) {
        RacingDetail model = jvLinkModelMapper.deserialize(decode.apply(data), RacingDetail.class);
        model.getLapTimeItems().removeIf(time -> time == 0.0);
        model.getCornerPassageRanks().removeIf(cornerPassageRank -> cornerPassageRank.getCorner() == 0
                && cornerPassageRank.getAroundCount() == 0
                && "".equals(cornerPassageRank.getPassageRank()));
        return model;
    }

    public RacingHorseDetail toRacingHorseDetail(String data) {
        return jvLinkModelMapper.deserialize(decode.apply(data), RacingHorseDetail.class);
    }

    public RacingRefund toRacingRefund(String data) {
        RacingRefund model = jvLinkModelMapper.deserialize(decode.apply(data), RacingRefund.class);
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

    public RacingVote toRacingVote(String data) {
        RacingVote model = jvLinkModelMapper.deserialize(decode.apply(data), RacingVote.class);
        model.getVoteCountWins().removeIf(JvLinkDeserializer::voteFilter);
        model.getVoteCountShows().removeIf(JvLinkDeserializer::voteFilter);
        model.getVoteCountBracketQs().removeIf(JvLinkDeserializer::voteFilter);
        model.getVoteCountQuinellas().removeIf(JvLinkDeserializer::voteFilter);
        model.getVoteCountQuinellaPlaces().removeIf(JvLinkDeserializer::voteFilter);
        model.getVoteCountExactas().removeIf(JvLinkDeserializer::voteFilter);
        model.getVoteCountTrios().removeIf(JvLinkDeserializer::voteFilter);
        return model;
    }

    public RacingHorseExclusion toRacingHorseExclusion(String data) {
        return jvLinkModelMapper.deserialize(decode.apply(data), RacingHorseExclusion.class);
    }


    /**
     * オッズデータ
     */
    public OddsWinsShowBracketQ toWinsShowBracketQ(String data) {
        OddsWinsShowBracketQ model = jvLinkModelMapper.deserialize(decode.apply(data), OddsWinsShowBracketQ.class);
        model.getWinOdds().removeIf(JvLinkDeserializer::winOddsFilter);
        model.getShowOdds().removeIf(JvLinkDeserializer::showOddsFilter);
        model.getBracketQOdds().removeIf(JvLinkDeserializer::bracketQOddsFilter);
        return model;
    }

    public OddsQuinella toQuinella(String data) {
        OddsQuinella model = jvLinkModelMapper.deserialize(decode.apply(data), OddsQuinella.class);
        model.getQuinellaOdds().removeIf(JvLinkDeserializer::quinellaOddsFilter);
        return model;
    }

    public OddsQuinellaPlace toQuinellaPlace(String data) {
        OddsQuinellaPlace model = jvLinkModelMapper.deserialize(decode.apply(data), OddsQuinellaPlace.class);
        model.getQuinellaPlaceOdds().removeIf(JvLinkDeserializer::quinellaPlaceOddsFilter);
        return model;
    }

    public OddsExacta toExacta(String data) {
        OddsExacta model = jvLinkModelMapper.deserialize(decode.apply(data), OddsExacta.class);
        model.getExactaOdds().removeIf(JvLinkDeserializer::exactaOddsFilter);
        return model;
    }

    public OddsTrio toTrio(String data) {
        OddsTrio model = jvLinkModelMapper.deserialize(decode.apply(data), OddsTrio.class);
        model.getTrioOdds().removeIf(JvLinkDeserializer::trioOddsFilter);
        return model;
    }

    public OddsTrifecta toTrifecta(String data) {
        OddsTrifecta model = jvLinkModelMapper.deserialize(decode.apply(data), OddsTrifecta.class);
        model.getTrifectaOdds().removeIf(JvLinkDeserializer::trifectaOddsFilter);
        return model;
    }


    /**
     * 血統データ
     */
    public BloodLine toBloodLine(String data) {
        return jvLinkModelMapper.deserialize(decode.apply(data), BloodLine.class);
    }

    public BloodAncestry toBloodAncestry(String data) {
        return jvLinkModelMapper.deserialize(decode.apply(data), BloodAncestry.class);
    }

    public BloodBreeding toBloodBreeding(String data) {
        return jvLinkModelMapper.deserialize(decode.apply(data), BloodBreeding.class);
    }


    /**
     * 競馬データ
     */
    public DiffRaceHorse toRaceHorse(String data) {
        return jvLinkModelMapper.deserialize(decode.apply(data), DiffRaceHorse.class);
    }

    public DiffJockey toJockey(String data) {
        return jvLinkModelMapper.deserialize(decode.apply(data), DiffJockey.class);
    }

    public DiffTrainer toTrainer(String data) {
        return jvLinkModelMapper.deserialize(decode.apply(data), DiffTrainer.class);
    }

    public DiffBreeder toBreeder(String data) {
        return jvLinkModelMapper.deserialize(decode.apply(data), DiffBreeder.class);
    }

    public DiffOwner toOwner(String data) {
        return jvLinkModelMapper.deserialize(decode.apply(data), DiffOwner.class);
    }

    public Course toCourse(String data) {
        return jvLinkModelMapper.deserialize(decode.apply(data), Course.class);
    }


    /**
     * イベント通知データ
     */
    public Avoid toAvoid(String data) {
        return jvLinkModelMapper.deserialize(decode.apply(data), Avoid.class);
    }

    public Weight toWeight(String data) {
        return jvLinkModelMapper.deserialize(decode.apply(data), Weight.class);
    }

    public Weather toWeather(String data) {
        return jvLinkModelMapper.deserialize(decode.apply(data), Weather.class);
    }

    public CourseChange toCourseChange(String data) {
        return jvLinkModelMapper.deserialize(decode.apply(data), CourseChange.class);
    }

    public JockeyChange toJockeyChange(String data) {
        return jvLinkModelMapper.deserialize(decode.apply(data), JockeyChange.class);
    }

    public TimeChange toTimeChange(String data) {
        return jvLinkModelMapper.deserialize(decode.apply(data), TimeChange.class);
    }


    /**
     * 不要データのFilter static methods
     */
    private static boolean winOddsFilter(OddsWinsShowBracketQ.WinOdds winOdds) {
        return winOdds.getOdds() == null
                && winOdds.getBetRank() == null;
    }

    private static boolean showOddsFilter(OddsWinsShowBracketQ.ShowOdds showOdds) {
        return showOdds.getOddsMin() == null
                && showOdds.getOddsMax() == null
                && showOdds.getBetRank() == null;
    }

    private static boolean bracketQOddsFilter(OddsWinsShowBracketQ.BracketQOdds bracketQOdds) {
        return bracketQOdds.getOdds() == null
                && bracketQOdds.getBetRank() == null;
    }

    private static boolean quinellaOddsFilter(OddsQuinella.QuinellaOdds quinellaOdds) {
        return quinellaOdds.getOdds() == null
                && quinellaOdds.getBetRank() == null;
    }

    private static boolean quinellaPlaceOddsFilter(OddsQuinellaPlace.QuinellaPlaceOdds quinellaPlaceOdds) {
        return quinellaPlaceOdds.getOddsMin() == null
                && quinellaPlaceOdds.getOddsMax() == null
                && quinellaPlaceOdds.getBetRank() == null;
    }

    private static boolean exactaOddsFilter(OddsExacta.ExactaOdds exactaOdds) {
        return exactaOdds.getOdds() == null
                && exactaOdds.getBetRank() == null;
    }

    private static boolean trioOddsFilter(OddsTrio.TrioOdds trioOdds) {
        return trioOdds.getOdds() == null
                && trioOdds.getBetRank() == null;
    }

    private static boolean trifectaOddsFilter(OddsTrifecta.TrifectaOdds trifectaOdds) {
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
