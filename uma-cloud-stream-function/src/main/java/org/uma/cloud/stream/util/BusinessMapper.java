package org.uma.cloud.stream.util;

import org.uma.cloud.common.code.AbnormalDivisionCode;
import org.uma.cloud.common.entity.RacingDetail;
import org.uma.cloud.common.entity.RacingHorseDetail;
import org.uma.cloud.common.entity.RacingRefund;
import org.uma.cloud.common.business.BusinessRacing;
import org.uma.cloud.common.business.BusinessRacingHorse;
import org.uma.cloud.common.business.BusinessRacingRefund;
import org.uma.cloud.common.utils.javatuples.Pair;
import org.uma.cloud.common.utils.javatuples.Triplet;

import java.util.List;
import java.util.stream.Collectors;

public class BusinessMapper {

    public static BusinessRacing toBusinessRacing(RacingDetail racingDetail) {
        BusinessRacing model = new BusinessRacing();
        model.setRaceId(racingDetail.getRaceId());
        model.setDataDiv(racingDetail.getDataDiv());
        model.setHoldingDate(racingDetail.getHoldingDate());
        model.setStartTime(racingDetail.getStartTime());
        model.setRaceNo(racingDetail.getRaceNo());
        model.setRaceNameFull(racingDetail.getRaceNameFull());
        model.setCourseCd(racingDetail.getCourseCd());
        model.setRaceType(racingDetail.getRaceTypeCd());
        model.setRaceSignCd(racingDetail.getRaceSignCd());
        model.setWeightTypeCd(racingDetail.getWeightTypeCd());
//        model.setRaceConditionCdOld2(racingDetail.getRaceConditionCdOld2());
//        model.setRaceConditionCdOld3(racingDetail.getRaceConditionCdOld3());
//        model.setRaceConditionCdOld4(racingDetail.getRaceConditionCdOld4());
//        model.setRaceConditionCdOld5(racingDetail.getRaceConditionCdOld5());
//        model.setRaceConditionCdYoungest(racingDetail.getRaceConditionCdYoungest());
        model.setDistance(racingDetail.getDistance());
        model.setTrack(racingDetail.getTrackCd());
        model.setTurfOrDirtCondition(racingDetail.getTurfOrDirtCondition());
        model.setWeather(racingDetail.getWeatherCd());
        // レース結果
        model.setLapTimeItems(racingDetail.getLapTimeItems());
        model.setFirstFurlong3(racingDetail.getFirstFurlong3());
        model.setFirstFurlong4(racingDetail.getFirstFurlong4());
        model.setLastFurlong3(racingDetail.getLastFurlong3());
        model.setLastFurlong4(racingDetail.getLastFurlong4());
        model.setCornerPassageRanks(racingDetail.getCornerPassageRanks().stream()
                .map(i -> {
                    BusinessRacing.CornerPassageRank cornerPassageRank
                            = new BusinessRacing.CornerPassageRank();
                    cornerPassageRank.setCorner(i.getCorner());
                    cornerPassageRank.setAroundCount(i.getAroundCount());
                    cornerPassageRank.setPassageRank(i.getPassageRank());
                    return cornerPassageRank;
                }).collect(Collectors.toList()));

        return model;
    }

    public static BusinessRacingHorse toBusinessRacingHorse(RacingHorseDetail racingHorseDetail) {
        BusinessRacingHorse model = new BusinessRacingHorse();
        model.setRaceId(racingHorseDetail.getRaceId());
        model.setDataDiv(racingHorseDetail.getDataDiv());
        model.setBracketNo(racingHorseDetail.getBracketNo());
        model.setHorseNo(racingHorseDetail.getHorseNo());
        model.setBloodlineNo(racingHorseDetail.getBloodlineNo());
        model.setHorseName(racingHorseDetail.getHorseName());
        model.setSex(racingHorseDetail.getSexCd());
        model.setHairColor(racingHorseDetail.getHairColorCd());
        model.setAge(racingHorseDetail.getAge());
        model.setBlinker(racingHorseDetail.getIsBlinker());
        model.setEwBelong(racingHorseDetail.getEwBelongCd());
        model.setJockeyCd(racingHorseDetail.getJockeyCd());
//        model.setJockeyNameShort(racingHorseDetail.getJockeyNameShort());
        model.setLoadWeight(racingHorseDetail.getLoadWeight());
        model.setJockeyApprentice(racingHorseDetail.getJockeyApprenticeCd());
        model.setTrainerCd(racingHorseDetail.getTrainerCd());
//        model.setTrainerNameShort(racingHorseDetail.getTrainerNameShort());
        model.setOwnerCd(racingHorseDetail.getOwnerCd());
//        model.setOwnerNameWithoutCorp(racingHorseDetail.getOwnerNameWithoutCorp());
        model.setHorseWeight(racingHorseDetail.getHorseWeight());
//        model.setChangeSign(racingHorseDetail.getChangeSign());
        model.setChangeAmount(racingHorseDetail.getChangeAmount());
//        model.setOddsWin(racingHorseDetail.getOddsWin());
//        model.setBetRankWin(racingHorseDetail.getBetRankWin());
        if (racingHorseDetail.getAbnormalDivCd() != AbnormalDivisionCode.DEFAULT) {
            // とりあえずこれいれとく。
            if (racingHorseDetail.getAbnormalDivCd().getCode() == 1) {
                model.setExclude(1);
            } else {
                model.setExclude(2);
            }
            model.setExcludeReason("001"); // とりあえずこれいれとく。
        }
        // レース結果
        model.setAbnormalDivCd(racingHorseDetail.getAbnormalDivCd());
        model.setFinishedArrivalOrder(racingHorseDetail.getFinishedArrivalOrder());
        model.setFixedArrivalOrder(racingHorseDetail.getFixedArrivalOrder());
        model.setRunningTime(racingHorseDetail.getRunningTime());
        model.setMarginCd(racingHorseDetail.getMarginCd());
        model.setMarginCd2(racingHorseDetail.getMarginCd2());
//        model.setMarginCd3(racingHorseDetail.getMarginCd3());
//        model.setRankCorner1(racingHorseDetail.getRankCorner1());
//        model.setRankCorner2(racingHorseDetail.getRankCorner2());
//        model.setRankCorner3(racingHorseDetail.getRankCorner3());
//        model.setRankCorner4(racingHorseDetail.getRankCorner4());
//        model.setTimeMargin(racingHorseDetail.getTimeMargin());
        return model;
    }

    public static BusinessRacingRefund toBusinessRacingRefund(RacingRefund racingRefund) {
        BusinessRacingRefund model = new BusinessRacingRefund();
        model.setRaceId(racingRefund.getRaceId());
        model.setDataDiv(racingRefund.getDataDiv());
        model.setRestoreHorseNoItems(List.copyOf(racingRefund.getRestoreHorseNoItems()));
        model.setRefundWins(racingRefund.getRefundWins().stream()
                .map(win -> {
                    BusinessRacingRefund.refund refund = new BusinessRacingRefund.refund();
                    refund.setHorseNo(win.getHorseNo());
                    refund.setRefundMoney(win.getRefundMoney());
                    refund.setBetRank(win.getBetRank());
                    return refund;
                }).collect(Collectors.toList()));
        model.setRefundShows(racingRefund.getRefundShows().stream()
                .map(show -> {
                    BusinessRacingRefund.refund refund = new BusinessRacingRefund.refund();
                    refund.setHorseNo(show.getHorseNo());
                    refund.setRefundMoney(show.getRefundMoney());
                    refund.setBetRank(show.getBetRank());
                    return refund;
                }).collect(Collectors.toList()));
        model.setRefundBracketQs(racingRefund.getRefundBracketQs().stream()
                .map(bracketQ -> {
                    BusinessRacingRefund.refund refund = new BusinessRacingRefund.refund();
                    refund.setHorseNo(bracketQ.getHorseNo());
                    refund.setRefundMoney(bracketQ.getRefundMoney());
                    refund.setBetRank(bracketQ.getBetRank());
                    return refund;
                }).collect(Collectors.toList()));
        model.setRefundQuinellas(racingRefund.getRefundQuinellas().stream()
                .map(quinella -> {
                    BusinessRacingRefund.refundPair refundPair = new BusinessRacingRefund.refundPair();
                    refundPair.setPairNo(Pair.copyOf(quinella.getPairNo()));
                    refundPair.setRefundMoney(quinella.getRefundMoney());
                    refundPair.setBetRank(quinella.getBetRank());
                    return refundPair;
                }).collect(Collectors.toList()));
        model.setRefundQuinellaPlaces(racingRefund.getRefundQuinellaPlaces().stream()
                .map(quinellaPlace -> {
                    BusinessRacingRefund.refundPair refundPair = new BusinessRacingRefund.refundPair();
                    refundPair.setPairNo(Pair.copyOf(quinellaPlace.getPairNo()));
                    refundPair.setRefundMoney(quinellaPlace.getRefundMoney());
                    refundPair.setBetRank(quinellaPlace.getBetRank());
                    return refundPair;
                }).collect(Collectors.toList()));
        model.setRefundExactas(racingRefund.getRefundExactas().stream()
                .map(exactas -> {
                    BusinessRacingRefund.refundPair refundPair = new BusinessRacingRefund.refundPair();
                    refundPair.setPairNo(Pair.copyOf(exactas.getPairNo()));
                    refundPair.setRefundMoney(exactas.getRefundMoney());
                    refundPair.setBetRank(exactas.getBetRank());
                    return refundPair;
                }).collect(Collectors.toList()));
        model.setRefundTrios(racingRefund.getRefundTrios().stream()
                .map(trio -> {
                    BusinessRacingRefund.refundTriplet refundTriplet = new BusinessRacingRefund.refundTriplet();
                    refundTriplet.setTripletNo(Triplet.copyOf(trio.getTripletNo()));
                    refundTriplet.setRefundMoney(trio.getRefundMoney());
                    refundTriplet.setBetRank(trio.getBetRank());
                    return refundTriplet;
                }).collect(Collectors.toList()));
        model.setRefundTrifectas(racingRefund.getRefundTrifectas().stream()
                .map(trifectas -> {
                    BusinessRacingRefund.refundTriplet refundTriplet = new BusinessRacingRefund.refundTriplet();
                    refundTriplet.setTripletNo(Triplet.copyOf(trifectas.getTripletNo()));
                    refundTriplet.setRefundMoney(trifectas.getRefundMoney());
                    refundTriplet.setBetRank(trifectas.getBetRank());
                    return refundTriplet;
                }).collect(Collectors.toList()));

        return model;
    }

}
