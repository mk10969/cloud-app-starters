package org.uma.cloud.common.configuration;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.uma.cloud.common.code.TurfOrDirtConditionCode;
import org.uma.cloud.common.entity.BloodAncestry;
import org.uma.cloud.common.entity.BloodBreeding;
import org.uma.cloud.common.entity.BloodLine;
import org.uma.cloud.common.entity.Course;
import org.uma.cloud.common.entity.DiffBreeder;
import org.uma.cloud.common.entity.DiffJockey;
import org.uma.cloud.common.entity.DiffOwner;
import org.uma.cloud.common.entity.DiffRaceHorse;
import org.uma.cloud.common.entity.DiffTrainer;
import org.uma.cloud.common.entity.RacingDetail;
import org.uma.cloud.common.entity.RacingHorseDetail;
import org.uma.cloud.common.entity.RacingHorseExclusion;
import org.uma.cloud.common.entity.RacingOdds;
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
import org.uma.cloud.common.utils.javatuples.Pair;
import org.uma.cloud.common.utils.lang.ByteUtil;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

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
                .setFieldMatchingEnabled(true)
                .setAmbiguityIgnored(true);
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

        RacingDetail racingDetail = modelMapper.map(model, RacingDetail.class);
        // raceConditionを、５つを１つのリストにまとめる
        List<Integer> raceConditions = new ArrayList<>();
        raceConditions.add(model.getRaceConditionCdOld2());
        raceConditions.add(model.getRaceConditionCdOld3());
        raceConditions.add(model.getRaceConditionCdOld4());
        raceConditions.add(model.getRaceConditionCdOld5());
        raceConditions.add(model.getRaceConditionCdYoungest());
        racingDetail.setRaceConditions(raceConditions);
        // 芝ダートの馬場状態をひとつにする。
        TurfOrDirtConditionCode turfOrDirtCd = TurfOrDirtConditionCode
                .compare(model.getDirtConditionCd(), model.getTurfConditionCd());
        racingDetail.setTurfOrDirtCondition(turfOrDirtCd);

        return racingDetail;
    }

    public RacingHorseDetail toRacingHorseDetail(String data) {
        RACE_SE model = jvLinkModelMapper.deserialize(decode.apply(data), RACE_SE.class);
        RacingHorseDetail racingHorseDetail = modelMapper.map(model, RacingHorseDetail.class);
        // rankCornerをひとつにまとめる。
        List<Integer> corners = new ArrayList<>();
        corners.add(model.getRankCorner1());
        corners.add(model.getRankCorner2());
        corners.add(model.getRankCorner3());
        corners.add(model.getRankCorner4());
        racingHorseDetail.setRankEachCorner(corners);

        // 馬体重増減
        if ("+".equals(model.getChangeSign())) {
            racingHorseDetail.setHorseWeightGainOrLoss(model.getChangeAmount());
        } else if ("-".equals(model.getChangeSign())) {
            racingHorseDetail.setHorseWeightGainOrLoss(-model.getChangeAmount());
        } else if ("".equals(model.getChangeSign())) {
            if (model.getChangeAmount() == null) {
                // nullなら、０を入れる。新馬戦とかの場合、nullになる。（前回出走していないから）
                racingHorseDetail.setHorseWeightGainOrLoss(0);
            } else {
                // 計測不可能フラグの９９９が入る or 前走と増減なしの０が入る。
                racingHorseDetail.setHorseWeightGainOrLoss(model.getChangeAmount());
            }
        }

        // spareの空チェック
        if (!"".equals(model.getSpare1())
                || !"".equals(model.getSpare2())
                || !"".equals(model.getSpare4())
                || !"".equals(model.getSpare5())
        ) {
            throw new IllegalStateException("予備データが存在します。" + model);
        }

        return racingHorseDetail;
    }

    public List<RacingRefund> toRacingRefund(String data) {
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

        // 不成立フラグが、trueでないか、チェックする
        if (model.getFailureFlagWin()
                || model.getFailureFlagShow()
                || model.getFailureFlagBracketQ()
                || model.getFailureFlagQuinellaPlace()
                || model.getFailureFlagQuinella()
                || model.getFailureFlagExacta()
                || model.getFailureFlagTrio()
                || model.getFailureFlagTrifecta()
        ) {
            throw new IllegalStateException("不成立フラグがあります。:" + model);
        }
        // 特払フラグが、trueでないか、チェックする
        if (model.getSpecialRefundFlagWin()
                || model.getSpecialRefundFlagShow()
                || model.getSpecialRefundFlagBracketQ()
                || model.getSpecialRefundFlagQuinellaPlace()
                || model.getSpecialRefundFlagQuinella()
                || model.getSpecialRefundFlagExacta()
                || model.getSpecialRefundFlagTrio()
                || model.getSpecialRefundFlagTrifecta()
        ) {
            throw new IllegalStateException("特払フラグがあります。:" + model);
        }

        // spareの空チェック
        if (!"".equals(model.getSpare1())
                || !"".equals(model.getSpare2())
                || !"".equals(model.getSpare3())
                || !model.getRefundSpares().isEmpty()
        ) {
            throw new IllegalStateException("予備データが存在します。" + model);
        }

        // オッズ式別ごとに分ける。
        List<RacingRefund> refunds = new ArrayList<>();

        // 単勝
        if (!model.getRefundWins().isEmpty()) {
            RacingRefund winRefund = new RacingRefund();
            winRefund.setDataDiv(model.getDataDiv());
            winRefund.setRaceId(model.getRaceId());
            winRefund.setBetting(1); // 単勝
            winRefund.setRestoreFlag(model.getRestoreFlagWin());
            winRefund.setRestoreHorseNoItems(model.getRestoreHorseNoItems());
            winRefund.setRestoreBracketItems(model.getRestoreBracketItems());
            winRefund.setRestoreSameBracketItems(model.getRestoreSameBracketItems());
            winRefund.setRefundTable(model.getRefundWins().stream()
                    .map(win -> {
                        RacingRefund.refund refund = new RacingRefund.refund();
                        refund.setPairHorseNo(win.getHorseNo());
                        refund.setRefundMoney(win.getRefundMoney());
                        refund.setBetRank(win.getBetRank());
                        return refund;
                    }).collect(Collectors.toList())
            );
            refunds.add(winRefund);
        }

        // 複勝
        if (!model.getRefundShows().isEmpty()) {
            RacingRefund showRefund = new RacingRefund();
            showRefund.setDataDiv(model.getDataDiv());
            showRefund.setRaceId(model.getRaceId());
            showRefund.setBetting(2); // 複勝
            showRefund.setRestoreFlag(model.getRestoreFlagShow());
            showRefund.setRestoreHorseNoItems(model.getRestoreHorseNoItems());
            showRefund.setRestoreBracketItems(model.getRestoreBracketItems());
            showRefund.setRestoreSameBracketItems(model.getRestoreSameBracketItems());
            showRefund.setRefundTable(model.getRefundShows().stream()
                    .map(show -> {
                        RacingRefund.refund refund = new RacingRefund.refund();
                        refund.setPairHorseNo(show.getHorseNo());
                        refund.setRefundMoney(show.getRefundMoney());
                        refund.setBetRank(show.getBetRank());
                        return refund;
                    }).collect(Collectors.toList())
            );
            refunds.add(showRefund);
        }

        // 枠連は捨てる。

        // 馬連
        if (!model.getRefundQuinellas().isEmpty()) {
            RacingRefund quinellaRefund = new RacingRefund();
            quinellaRefund.setDataDiv(model.getDataDiv());
            quinellaRefund.setRaceId(model.getRaceId());
            quinellaRefund.setBetting(4); // 馬連
            quinellaRefund.setRestoreFlag(model.getRestoreFlagQuinella());
            quinellaRefund.setRestoreHorseNoItems(model.getRestoreHorseNoItems());
            quinellaRefund.setRestoreBracketItems(model.getRestoreBracketItems());
            quinellaRefund.setRestoreSameBracketItems(model.getRestoreSameBracketItems());
            quinellaRefund.setRefundTable(model.getRefundQuinellas().stream()
                    .map(quinella -> {
                        RacingRefund.refund refund = new RacingRefund.refund();
                        refund.setPairHorseNo(quinella.getPairNo().toString());
                        refund.setRefundMoney(quinella.getRefundMoney());
                        refund.setBetRank(quinella.getBetRank());
                        return refund;
                    }).collect(Collectors.toList())
            );
            refunds.add(quinellaRefund);
        }

        // ワイド
        if (!model.getRefundQuinellaPlaces().isEmpty()) {
            RacingRefund quinellaPlaceRefund = new RacingRefund();
            quinellaPlaceRefund.setDataDiv(model.getDataDiv());
            quinellaPlaceRefund.setRaceId(model.getRaceId());
            quinellaPlaceRefund.setBetting(5); // ワイド
            quinellaPlaceRefund.setRestoreFlag(model.getRestoreFlagQuinellaPlace());
            quinellaPlaceRefund.setRestoreHorseNoItems(model.getRestoreHorseNoItems());
            quinellaPlaceRefund.setRestoreBracketItems(model.getRestoreBracketItems());
            quinellaPlaceRefund.setRestoreSameBracketItems(model.getRestoreSameBracketItems());
            quinellaPlaceRefund.setRefundTable(model.getRefundQuinellaPlaces().stream()
                    .map(quinellaPlace -> {
                        RacingRefund.refund refund = new RacingRefund.refund();
                        refund.setPairHorseNo(quinellaPlace.getPairNo().toString());
                        refund.setRefundMoney(quinellaPlace.getRefundMoney());
                        refund.setBetRank(quinellaPlace.getBetRank());
                        return refund;
                    }).collect(Collectors.toList())
            );
            refunds.add(quinellaPlaceRefund);
        }

        // 馬単
        if (!model.getRefundExactas().isEmpty()) {
            RacingRefund exactaRefund = new RacingRefund();
            exactaRefund.setDataDiv(model.getDataDiv());
            exactaRefund.setRaceId(model.getRaceId());
            exactaRefund.setBetting(6); // 馬単
            exactaRefund.setRestoreFlag(model.getRestoreFlagExacta());
            exactaRefund.setRestoreHorseNoItems(model.getRestoreHorseNoItems());
            exactaRefund.setRestoreBracketItems(model.getRestoreBracketItems());
            exactaRefund.setRestoreSameBracketItems(model.getRestoreSameBracketItems());
            exactaRefund.setRefundTable(model.getRefundExactas().stream()
                    .map(exacta -> {
                        RacingRefund.refund refund = new RacingRefund.refund();
                        refund.setPairHorseNo(exacta.getPairNo().toString());
                        refund.setRefundMoney(exacta.getRefundMoney());
                        refund.setBetRank(exacta.getBetRank());
                        return refund;
                    }).collect(Collectors.toList())
            );
            refunds.add(exactaRefund);
        }

        // 三連複
        if (!model.getRefundTrios().isEmpty()) {
            RacingRefund trioRefund = new RacingRefund();
            trioRefund.setDataDiv(model.getDataDiv());
            trioRefund.setRaceId(model.getRaceId());
            trioRefund.setBetting(7); // 三連複
            trioRefund.setRestoreFlag(model.getRestoreFlagTrio());
            trioRefund.setRestoreHorseNoItems(model.getRestoreHorseNoItems());
            trioRefund.setRestoreBracketItems(model.getRestoreBracketItems());
            trioRefund.setRestoreSameBracketItems(model.getRestoreSameBracketItems());
            trioRefund.setRefundTable(model.getRefundTrios().stream()
                    .map(trio -> {
                        RacingRefund.refund refund = new RacingRefund.refund();
                        refund.setPairHorseNo(trio.getTripletNo().toString());
                        refund.setRefundMoney(trio.getRefundMoney());
                        refund.setBetRank(trio.getBetRank());
                        return refund;
                    }).collect(Collectors.toList())
            );
            refunds.add(trioRefund);
        }

        // 三連単
        if (!model.getRefundTrifectas().isEmpty()) {
            RacingRefund trifectaRefund = new RacingRefund();
            trifectaRefund.setDataDiv(model.getDataDiv());
            trifectaRefund.setRaceId(model.getRaceId());
            trifectaRefund.setBetting(8); // 三連単
            trifectaRefund.setRestoreFlag(model.getRestoreFlagTrifecta());
            trifectaRefund.setRestoreHorseNoItems(model.getRestoreHorseNoItems());
            trifectaRefund.setRestoreBracketItems(model.getRestoreBracketItems());
            trifectaRefund.setRestoreSameBracketItems(model.getRestoreSameBracketItems());
            trifectaRefund.setRefundTable(model.getRefundTrifectas().stream()
                    .map(trifecta -> {
                        RacingRefund.refund refund = new RacingRefund.refund();
                        refund.setPairHorseNo(trifecta.getTripletNo().toString());
                        refund.setRefundMoney(trifecta.getRefundMoney());
                        refund.setBetRank(trifecta.getBetRank());
                        return refund;
                    }).collect(Collectors.toList())
            );
            refunds.add(trifectaRefund);
        }

        return refunds;
    }

    public RacingHorseExclusion toRacingHorseExclusion(String data) {
        RACE_JG model = jvLinkModelMapper.deserialize(decode.apply(data), RACE_JG.class);
        return modelMapper.map(model, RacingHorseExclusion.class);
    }

    /**
     * TODO: 未対応
     */
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

    /**
     * オッズデータ
     */
    public Pair<RacingOdds, RacingOdds> toWinsShowBracketQ(String data) {
        RACE_O1 model = jvLinkModelMapper.deserialize(decode.apply(data), RACE_O1.class);
        model.getWinOdds().removeIf(JvLinkDeserializer::winOddsFilter);
        model.getShowOdds().removeIf(JvLinkDeserializer::showOddsFilter);
        model.getBracketQOdds().removeIf(JvLinkDeserializer::bracketQOddsFilter);

        // 単勝、複勝に分ける。枠連は使わないから捨てる。
        RacingOdds oddsWin = new RacingOdds();
        oddsWin.setDataDiv(model.getDataDiv());
        oddsWin.setRaceId(model.getRaceId());
        oddsWin.setBetting(1); //単勝
        oddsWin.setHoldingDate(model.getHoldingDate());
        oddsWin.setAnnounceDate(model.getAnnounceDate());
        oddsWin.setSaleFlag(model.getSaleFlagWin());
        oddsWin.setShowCashKey(null); //意図的にセット
        oddsWin.setOddsTable(model.getWinOdds().stream()
                .map(win -> {
                    RacingOdds.Odds odds = new RacingOdds.Odds();
                    odds.setPairHorseNo(win.getHorseNo());
                    odds.setOddsMin(win.getOdds());
                    odds.setOddsMax(win.getOdds());
                    odds.setBetRank(win.getBetRank());
                    return odds;
                }).collect(Collectors.toList()));
        oddsWin.setVoteCountTotal(model.getVoteCountTotalWin());

        RacingOdds oddsShow = new RacingOdds();
        oddsShow.setDataDiv(model.getDataDiv());
        oddsShow.setRaceId(model.getRaceId());
        oddsShow.setBetting(2); //複勝
        oddsShow.setHoldingDate(model.getHoldingDate());
        oddsShow.setAnnounceDate(model.getAnnounceDate());
        oddsShow.setSaleFlag(model.getSaleFlagShow());
        oddsShow.setShowCashKey(model.getShowCashKey()); //複勝のみセット
        oddsShow.setOddsTable(model.getShowOdds().stream()
                .map(show -> {
                    RacingOdds.Odds odds = new RacingOdds.Odds();
                    odds.setPairHorseNo(show.getHorseNo());
                    odds.setOddsMin(show.getOddsMin());
                    odds.setOddsMax(show.getOddsMax());
                    odds.setBetRank(show.getBetRank());
                    return odds;
                }).collect(Collectors.toList()));

        oddsShow.setVoteCountTotal(model.getVoteCountTotalShow() == null
                ? 0
                : model.getVoteCountTotalShow());
        // 枠連は捨てる。
        return Pair.with(oddsWin, oddsShow);
    }

    public RacingOdds toQuinella(String data) {
        RACE_O2 model = jvLinkModelMapper.deserialize(decode.apply(data), RACE_O2.class);
        model.getQuinellaOdds().removeIf(JvLinkDeserializer::quinellaOddsFilter);

        // mapper
        RacingOdds oddsQuinella = new RacingOdds();
        oddsQuinella.setDataDiv(model.getDataDiv());
        oddsQuinella.setRaceId(model.getRaceId());
        oddsQuinella.setBetting(4); // 馬連
        oddsQuinella.setHoldingDate(model.getHoldingDate());
        oddsQuinella.setAnnounceDate(model.getAnnounceDate());
        oddsQuinella.setSaleFlag(model.getSaleFlag());
        oddsQuinella.setShowCashKey(null); //意図的にセット
        oddsQuinella.setOddsTable(model.getQuinellaOdds().stream()
                .map(quinellaOdds -> {
                    RacingOdds.Odds odds = new RacingOdds.Odds();
                    odds.setPairHorseNo(quinellaOdds.getPairNo().toString());
                    odds.setOddsMin(quinellaOdds.getOdds());
                    odds.setOddsMax(quinellaOdds.getOdds());
                    odds.setBetRank(quinellaOdds.getBetRank());
                    return odds;
                }).collect(Collectors.toList()));
        oddsQuinella.setVoteCountTotal(model.getVoteCountTotal());
        return oddsQuinella;
    }

    public RacingOdds toQuinellaPlace(String data) {
        RACE_O3 model = jvLinkModelMapper.deserialize(decode.apply(data), RACE_O3.class);
        model.getQuinellaPlaceOdds().removeIf(JvLinkDeserializer::quinellaPlaceOddsFilter);

        // mapper
        RacingOdds oddsQuinellaPlace = new RacingOdds();
        oddsQuinellaPlace.setDataDiv(model.getDataDiv());
        oddsQuinellaPlace.setRaceId(model.getRaceId());
        oddsQuinellaPlace.setBetting(5); // ワイド
        oddsQuinellaPlace.setHoldingDate(model.getHoldingDate());
        oddsQuinellaPlace.setAnnounceDate(model.getAnnounceDate());
        oddsQuinellaPlace.setSaleFlag(model.getSaleFlag());
        oddsQuinellaPlace.setShowCashKey(null); //意図的にセット
        oddsQuinellaPlace.setOddsTable(model.getQuinellaPlaceOdds().stream()
                .map(quinellaPlaceOdds -> {
                    RacingOdds.Odds odds = new RacingOdds.Odds();
                    odds.setPairHorseNo(quinellaPlaceOdds.getPairNo().toString());
                    odds.setOddsMin(quinellaPlaceOdds.getOddsMin());
                    odds.setOddsMax(quinellaPlaceOdds.getOddsMax());
                    odds.setBetRank(quinellaPlaceOdds.getBetRank());
                    return odds;
                }).collect(Collectors.toList()));
        oddsQuinellaPlace.setVoteCountTotal(model.getVoteCountTotal());
        return oddsQuinellaPlace;
    }

    public RacingOdds toExacta(String data) {
        RACE_O4 model = jvLinkModelMapper.deserialize(decode.apply(data), RACE_O4.class);
        model.getExactaOdds().removeIf(JvLinkDeserializer::exactaOddsFilter);

        // mapper
        RacingOdds oddsExacta = new RacingOdds();
        oddsExacta.setDataDiv(model.getDataDiv());
        oddsExacta.setRaceId(model.getRaceId());
        oddsExacta.setBetting(6); // ワイド
        oddsExacta.setHoldingDate(model.getHoldingDate());
        oddsExacta.setAnnounceDate(model.getAnnounceDate());
        oddsExacta.setSaleFlag(model.getSaleFlag());
        oddsExacta.setShowCashKey(null); //意図的にセット
        oddsExacta.setOddsTable(model.getExactaOdds().stream()
                .map(exactaOdds -> {
                    RacingOdds.Odds odds = new RacingOdds.Odds();
                    odds.setPairHorseNo(exactaOdds.getPairNo().toString());
                    odds.setOddsMin(exactaOdds.getOdds());
                    odds.setOddsMax(exactaOdds.getOdds());
                    odds.setBetRank(exactaOdds.getBetRank());
                    return odds;
                }).collect(Collectors.toList()));
        oddsExacta.setVoteCountTotal(model.getVoteCountTotal());
        return oddsExacta;
    }

    public RacingOdds toTrio(String data) {
        RACE_O5 model = jvLinkModelMapper.deserialize(decode.apply(data), RACE_O5.class);
        model.getTrioOdds().removeIf(JvLinkDeserializer::trioOddsFilter);

        // mapper
        RacingOdds oddsTrio = new RacingOdds();
        oddsTrio.setDataDiv(model.getDataDiv());
        oddsTrio.setRaceId(model.getRaceId());
        oddsTrio.setBetting(7); // 三連複
        oddsTrio.setHoldingDate(model.getHoldingDate());
        oddsTrio.setAnnounceDate(model.getAnnounceDate());
        oddsTrio.setSaleFlag(model.getSaleFlag());
        oddsTrio.setShowCashKey(null); //意図的にセット
        oddsTrio.setOddsTable(model.getTrioOdds().stream()
                .map(trioOdds -> {
                    RacingOdds.Odds odds = new RacingOdds.Odds();
                    odds.setPairHorseNo(trioOdds.getPairNo().toString());
                    odds.setOddsMin(trioOdds.getOdds());
                    odds.setOddsMax(trioOdds.getOdds());
                    odds.setBetRank(trioOdds.getBetRank());
                    return odds;
                }).collect(Collectors.toList()));
        oddsTrio.setVoteCountTotal(model.getVoteCountTotal());
        return oddsTrio;
    }

    public RacingOdds toTrifecta(String data) {
        RACE_O6 model = jvLinkModelMapper.deserialize(decode.apply(data), RACE_O6.class);
        model.getTrifectaOdds().removeIf(JvLinkDeserializer::trifectaOddsFilter);

        // mapper
        RacingOdds oddsTrifecta = new RacingOdds();
        oddsTrifecta.setDataDiv(model.getDataDiv());
        oddsTrifecta.setRaceId(model.getRaceId());
        oddsTrifecta.setBetting(8); // 三連単
        oddsTrifecta.setHoldingDate(model.getHoldingDate());
        oddsTrifecta.setAnnounceDate(model.getAnnounceDate());
        oddsTrifecta.setSaleFlag(model.getSaleFlag());
        oddsTrifecta.setShowCashKey(null); //意図的にセット
        oddsTrifecta.setOddsTable(model.getTrifectaOdds().stream()
                .map(trifectaOdds -> {
                    RacingOdds.Odds odds = new RacingOdds.Odds();
                    odds.setPairHorseNo(trifectaOdds.getPairNo().toString());
                    odds.setOddsMin(trifectaOdds.getOdds());
                    odds.setOddsMax(trifectaOdds.getOdds());
                    odds.setBetRank(trifectaOdds.getBetRank());
                    return odds;
                }).collect(Collectors.toList()));
        oddsTrifecta.setVoteCountTotal(model.getVoteCountTotal());
        return oddsTrifecta;
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
