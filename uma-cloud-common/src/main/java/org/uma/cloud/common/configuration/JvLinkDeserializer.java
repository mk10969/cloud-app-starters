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
import org.uma.cloud.common.model.event.Avoid;
import org.uma.cloud.common.model.event.CourseChange;
import org.uma.cloud.common.model.event.JockeyChange;
import org.uma.cloud.common.model.event.TimeChange;
import org.uma.cloud.common.model.event.Weather;
import org.uma.cloud.common.model.event.Weight;
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
    public WinsShowBracketQ toWinsShowBracketQ(String data) {
        WinsShowBracketQ model = jvLinkModelMapper.deserialize(decode.apply(data), WinsShowBracketQ.class);
        model.getWinOdds().removeIf(JvLinkDeserializer::winOddsFilter);
        model.getShowOdds().removeIf(JvLinkDeserializer::showOddsFilter);
        model.getBracketQOdds().removeIf(JvLinkDeserializer::bracketQOddsFilter);
        return model;
    }

    public Quinella toQuinella(String data) {
        Quinella model = jvLinkModelMapper.deserialize(decode.apply(data), Quinella.class);
        model.getQuinellaOdds().removeIf(JvLinkDeserializer::quinellaOddsFilter);
        return model;
    }

    public QuinellaPlace toQuinellaPlace(String data) {
        QuinellaPlace model = jvLinkModelMapper.deserialize(decode.apply(data), QuinellaPlace.class);
        model.getQuinellaPlaceOdds().removeIf(JvLinkDeserializer::quinellaPlaceOddsFilter);
        return model;
    }

    public Exacta toExacta(String data) {
        Exacta model = jvLinkModelMapper.deserialize(decode.apply(data), Exacta.class);
        model.getExactaOdds().removeIf(JvLinkDeserializer::exactaOddsFilter);
        return model;
    }

    public Trio toTrio(String data) {
        Trio model = jvLinkModelMapper.deserialize(decode.apply(data), Trio.class);
        model.getTrioOdds().removeIf(JvLinkDeserializer::trioOddsFilter);
        return model;
    }

    public Trifecta toTrifecta(String data) {
        Trifecta model = jvLinkModelMapper.deserialize(decode.apply(data), Trifecta.class);
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
    public RaceHorse toRaceHorse(String data) {
        return jvLinkModelMapper.deserialize(decode.apply(data), RaceHorse.class);
    }

    public Jockey toJockey(String data) {
        return jvLinkModelMapper.deserialize(decode.apply(data), Jockey.class);
    }

    public Trainer toTrainer(String data) {
        return jvLinkModelMapper.deserialize(decode.apply(data), Trainer.class);
    }

    public Breeder toBreeder(String data) {
        return jvLinkModelMapper.deserialize(decode.apply(data), Breeder.class);
    }

    public Owner toOwner(String data) {
        return jvLinkModelMapper.deserialize(decode.apply(data), Owner.class);
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
