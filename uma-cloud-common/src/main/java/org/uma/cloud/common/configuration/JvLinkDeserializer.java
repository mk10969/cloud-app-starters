package org.uma.cloud.common.configuration;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.uma.cloud.common.entity.BloodAncestry;
import org.uma.cloud.common.entity.BloodBreeding;
import org.uma.cloud.common.entity.BloodLine;
import org.uma.cloud.common.entity.Course;
import org.uma.cloud.common.entity.DiffBreeder;
import org.uma.cloud.common.entity.DiffJockey;
import org.uma.cloud.common.entity.DiffOwner;
import org.uma.cloud.common.entity.DiffRaceHorse;
import org.uma.cloud.common.entity.DiffTrainer;
import org.uma.cloud.common.entity.OddsExacta;
import org.uma.cloud.common.entity.OddsQuinella;
import org.uma.cloud.common.entity.OddsQuinellaPlace;
import org.uma.cloud.common.entity.OddsTrifecta;
import org.uma.cloud.common.entity.OddsTrio;
import org.uma.cloud.common.entity.OddsWinsShowBracketQ;
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
import org.uma.cloud.common.model.jvlink.BLOD_BT;
import org.uma.cloud.common.model.jvlink.BLOD_HN;
import org.uma.cloud.common.model.jvlink.BLOD_SK;
import org.uma.cloud.common.model.jvlink.COMM_CS;
import org.uma.cloud.common.model.jvlink.DIFF_BN;
import org.uma.cloud.common.model.jvlink.DIFF_BR;
import org.uma.cloud.common.model.jvlink.DIFF_CH;
import org.uma.cloud.common.model.jvlink.DIFF_KS;
import org.uma.cloud.common.model.jvlink.DIFF_UM;
import org.uma.cloud.common.model.jvlink.RACE_H1;
import org.uma.cloud.common.model.jvlink.RACE_HR;
import org.uma.cloud.common.model.jvlink.RACE_JG;
import org.uma.cloud.common.model.jvlink.RACE_O1;
import org.uma.cloud.common.model.jvlink.RACE_O2;
import org.uma.cloud.common.model.jvlink.RACE_O3;
import org.uma.cloud.common.model.jvlink.RACE_O4;
import org.uma.cloud.common.model.jvlink.RACE_O5;
import org.uma.cloud.common.model.jvlink.RACE_O6;
import org.uma.cloud.common.model.jvlink.RACE_RA;
import org.uma.cloud.common.model.jvlink.RACE_SE;
import org.uma.cloud.common.utils.lang.ByteUtil;

import javax.annotation.PostConstruct;
import java.util.function.Function;

@Service
public class JvLinkDeserializer {

    @Autowired
    private JvLinkModelMapper jvLinkModelMapper;

    private static final ModelMapper modelMapper = new ModelMapper();

    private static final Function<String, byte[]> decode = ByteUtil::base64Decode;


    @PostConstruct
    public void init() {
        modelMapper.getConfiguration()
                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE)
                .setFieldMatchingEnabled(true);
    }

    /**
     * レースデータ
     */
    public RacingDetail toRacingDetail(String data) {
        RACE_RA model = jvLinkModelMapper.deserialize(decode.apply(data), RACE_RA.class);
        model.getLapTimeItems().removeIf(time -> time == 0.0);
        model.getCornerPassageRanks().removeIf(cornerPassageRank -> cornerPassageRank.getCorner() == 0
                && cornerPassageRank.getAroundCount() == 0
                && "".equals(cornerPassageRank.getPassageRank()));

        return modelMapper.map(model, RacingDetail.class);
    }

    public RacingHorseDetail toRacingHorseDetail(String data) {
        RACE_SE model = jvLinkModelMapper.deserialize(decode.apply(data), RACE_SE.class);
        return modelMapper.map(model, RacingHorseDetail.class);
    }

    public RacingRefund toRacingRefund(String data) {
        RACE_HR model = jvLinkModelMapper.deserialize(decode.apply(data), RACE_HR.class);
        model.getRefundWins().removeIf(JvLinkDeserializer::refundFilter);
        model.getRefundShows().removeIf(JvLinkDeserializer::refundFilter);
        model.getRefundBracketQs().removeIf(JvLinkDeserializer::refundFilter);
        model.getRefundQuinellas().removeIf(JvLinkDeserializer::refundFilter);
        model.getRefundQuinellaPlaces().removeIf(JvLinkDeserializer::refundFilter);
        model.getRefundSpares().removeIf(JvLinkDeserializer::refundFilter);
        model.getRefundExactas().removeIf(JvLinkDeserializer::refundFilter);
        model.getRefundTrios().removeIf(JvLinkDeserializer::refundFilter);
        model.getRefundTrifectas().removeIf(JvLinkDeserializer::refundFilter);

        return modelMapper.map(model, RacingRefund.class);
    }

    public RacingVote toRacingVote(String data) {
        RACE_H1 model = jvLinkModelMapper.deserialize(decode.apply(data), RACE_H1.class);
        model.getVoteCountWins().removeIf(JvLinkDeserializer::voteFilter);
        model.getVoteCountShows().removeIf(JvLinkDeserializer::voteFilter);
        model.getVoteCountBracketQs().removeIf(JvLinkDeserializer::voteFilter);
        model.getVoteCountQuinellas().removeIf(JvLinkDeserializer::voteFilter);
        model.getVoteCountQuinellaPlaces().removeIf(JvLinkDeserializer::voteFilter);
        model.getVoteCountExactas().removeIf(JvLinkDeserializer::voteFilter);
        model.getVoteCountTrios().removeIf(JvLinkDeserializer::voteFilter);

        return modelMapper.map(model, RacingVote.class);
    }

    public RacingHorseExclusion toRacingHorseExclusion(String data) {
        RACE_JG model = jvLinkModelMapper.deserialize(decode.apply(data), RACE_JG.class);
        return modelMapper.map(model, RacingHorseExclusion.class);
    }


    /**
     * オッズデータ
     */
    public OddsWinsShowBracketQ toWinsShowBracketQ(String data) {
        RACE_O1 model = jvLinkModelMapper.deserialize(decode.apply(data), RACE_O1.class);
        model.getWinOdds().removeIf(JvLinkDeserializer::winOddsFilter);
        model.getShowOdds().removeIf(JvLinkDeserializer::showOddsFilter);
        model.getBracketQOdds().removeIf(JvLinkDeserializer::bracketQOddsFilter);

        return modelMapper.map(model, OddsWinsShowBracketQ.class);
    }

    public OddsQuinella toQuinella(String data) {
        RACE_O2 model = jvLinkModelMapper.deserialize(decode.apply(data), RACE_O2.class);
        model.getQuinellaOdds().removeIf(JvLinkDeserializer::quinellaOddsFilter);

        return modelMapper.map(model, OddsQuinella.class);
    }

    public OddsQuinellaPlace toQuinellaPlace(String data) {
        RACE_O3 model = jvLinkModelMapper.deserialize(decode.apply(data), RACE_O3.class);
        model.getQuinellaPlaceOdds().removeIf(JvLinkDeserializer::quinellaPlaceOddsFilter);
        return modelMapper.map(model, OddsQuinellaPlace.class);
    }

    public OddsExacta toExacta(String data) {
        RACE_O4 model = jvLinkModelMapper.deserialize(decode.apply(data), RACE_O4.class);
        model.getExactaOdds().removeIf(JvLinkDeserializer::exactaOddsFilter);
        return modelMapper.map(model, OddsExacta.class);
    }

    public OddsTrio toTrio(String data) {
        RACE_O5 model = jvLinkModelMapper.deserialize(decode.apply(data), RACE_O5.class);
        model.getTrioOdds().removeIf(JvLinkDeserializer::trioOddsFilter);
        return modelMapper.map(model, OddsTrio.class);
    }

    public OddsTrifecta toTrifecta(String data) {
        RACE_O6 model = jvLinkModelMapper.deserialize(decode.apply(data), RACE_O6.class);
        model.getTrifectaOdds().removeIf(JvLinkDeserializer::trifectaOddsFilter);
        return modelMapper.map(model, OddsTrifecta.class);
    }


    /**
     * 血統データ
     */
    public BloodAncestry toBloodAncestry(String data) {
        BLOD_BT model = jvLinkModelMapper.deserialize(decode.apply(data), BLOD_BT.class);
        return modelMapper.map(model, BloodAncestry.class);
    }

    public BloodBreeding toBloodBreeding(String data) {
        BLOD_HN model = jvLinkModelMapper.deserialize(decode.apply(data), BLOD_HN.class);
        return modelMapper.map(model, BloodBreeding.class);
    }

    public BloodLine toBloodLine(String data) {
        BLOD_SK model = jvLinkModelMapper.deserialize(decode.apply(data), BLOD_SK.class);
        return modelMapper.map(model, BloodLine.class);
    }


    /**
     * 競馬データ
     */
    public DiffRaceHorse toRaceHorse(String data) {
        DIFF_UM model = jvLinkModelMapper.deserialize(decode.apply(data), DIFF_UM.class);
        return modelMapper.map(model, DiffRaceHorse.class);
    }

    public DiffJockey toJockey(String data) {
        DIFF_KS model = jvLinkModelMapper.deserialize(decode.apply(data), DIFF_KS.class);
        return modelMapper.map(model, DiffJockey.class);
    }

    public DiffTrainer toTrainer(String data) {
        DIFF_CH model = jvLinkModelMapper.deserialize(decode.apply(data), DIFF_CH.class);
        return modelMapper.map(model, DiffTrainer.class);
    }

    public DiffBreeder toBreeder(String data) {
        DIFF_BR model = jvLinkModelMapper.deserialize(decode.apply(data), DIFF_BR.class);
        return modelMapper.map(model, DiffBreeder.class);
    }

    public DiffOwner toOwner(String data) {
        DIFF_BN model = jvLinkModelMapper.deserialize(decode.apply(data), DIFF_BN.class);
        return modelMapper.map(model, DiffOwner.class);
    }

    public Course toCourse(String data) {
        COMM_CS model = jvLinkModelMapper.deserialize(decode.apply(data), COMM_CS.class);
        return modelMapper.map(model, Course.class);
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
    private static boolean winOddsFilter(RACE_O1.WinOdds winOdds) {
        return winOdds.getOdds() == null
                && winOdds.getBetRank() == null;
    }

    private static boolean showOddsFilter(RACE_O1.ShowOdds showOdds) {
        return showOdds.getOddsMin() == null
                && showOdds.getOddsMax() == null
                && showOdds.getBetRank() == null;
    }

    private static boolean bracketQOddsFilter(RACE_O1.BracketQOdds bracketQOdds) {
        return bracketQOdds.getOdds() == null
                && bracketQOdds.getBetRank() == null;
    }

    private static boolean quinellaOddsFilter(RACE_O2.QuinellaOdds quinellaOdds) {
        return quinellaOdds.getOdds() == null
                && quinellaOdds.getBetRank() == null;
    }

    private static boolean quinellaPlaceOddsFilter(RACE_O3.QuinellaPlaceOdds quinellaPlaceOdds) {
        return quinellaPlaceOdds.getOddsMin() == null
                && quinellaPlaceOdds.getOddsMax() == null
                && quinellaPlaceOdds.getBetRank() == null;
    }

    private static boolean exactaOddsFilter(RACE_O4.ExactaOdds exactaOdds) {
        return exactaOdds.getOdds() == null
                && exactaOdds.getBetRank() == null;
    }

    private static boolean trioOddsFilter(RACE_O5.TrioOdds trioOdds) {
        return trioOdds.getOdds() == null
                && trioOdds.getBetRank() == null;
    }

    private static boolean trifectaOddsFilter(RACE_O6.TrifectaOdds trifectaOdds) {
        return trifectaOdds.getOdds() == null
                && trifectaOdds.getBetRank() == null;
    }


    private static boolean refundFilter(RACE_HR.refund refund) {
        return refund.getBetRank() == null
                && refund.getRefundMoney() == null;
    }

    private static boolean refundFilter(RACE_HR.refundPair refundPair) {
        return refundPair.getBetRank() == null
                && refundPair.getRefundMoney() == null;
    }

    private static boolean refundFilter(RACE_HR.refundTriplet refundTriplet) {
        return refundTriplet.getBetRank() == null
                && refundTriplet.getRefundMoney() == null;
    }

    private static boolean voteFilter(RACE_H1.Vote vote) {
        return vote.getBetRank() == null
                && vote.getVoteCount() == null;
    }

    private static boolean voteFilter(RACE_H1.VotePair votePair) {
        return votePair.getBetRank() == null
                && votePair.getVoteCount() == null;
    }

    private static boolean voteFilter(RACE_H1.VoteTriplet voteTriplet) {
        return voteTriplet.getBetRank() == null
                && voteTriplet.getVoteCount() == null;
    }

}
