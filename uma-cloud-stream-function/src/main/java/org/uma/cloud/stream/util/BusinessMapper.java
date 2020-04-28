package org.uma.cloud.stream.util;

import org.uma.cloud.common.model.RacingDetail;
import org.uma.cloud.common.model.RacingHorseDetail;
import org.uma.cloud.common.model.RacingRefund;
import org.uma.cloud.common.model.business.BusinessRacing;
import org.uma.cloud.common.model.business.BusinessRacingHorse;
import org.uma.cloud.common.model.business.BusinessRacingRefund;
import org.uma.cloud.common.utils.javatuples.Pair;
import org.uma.cloud.common.utils.javatuples.Triplet;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class BusinessMapper {

    // TODO: reflectionつかうか、、迷う。。
    // TODO: reflection使うのなら、field 名を揃えないと無理だね。
    // もう書いちゃったしw

    public static BusinessRacing toBusinessRacing(RacingDetail racingDetail) {
        BusinessRacing model = new BusinessRacing();
        model.setRaceId(racingDetail.getRaceId());
        model.setDataDiv(racingDetail.getDataDiv());
        model.setRaceStartDateTime(LocalDateTime.of(racingDetail.getHoldingDate(), racingDetail.getStartTime()));
        model.setRaceNo(racingDetail.getRaceNo());
        model.setRaceNameFull(racingDetail.getRaceNameFull());
        model.setCourse(racingDetail.getCourseCd());
        model.setRaceType(racingDetail.getRaceTypeCd());
        model.setDistance(racingDetail.getDistance());
        model.setTrack(racingDetail.getTrackCd());
        model.setTurf(racingDetail.getTurfConditionCd());
        model.setDirt(racingDetail.getDirtConditionCd());
        model.setWeather(racingDetail.getWeatherCd());
        return model;
    }

    public static BusinessRacingHorse toBusinessRacingHorse(RacingHorseDetail racingHorseDetail) {
        BusinessRacingHorse model = new BusinessRacingHorse();
        model.setRaceId(racingHorseDetail.getRaceId());
        model.setDataDiv(racingHorseDetail.getDataDiv());
        model.setRaceNo(racingHorseDetail.getRaceNo());
        model.setBracketNo(racingHorseDetail.getBracketNo());
        model.setHorseNo(racingHorseDetail.getHorseNo());
        model.setBloodlineNo(racingHorseDetail.getBloodlineNo());
        model.setHorseName(racingHorseDetail.getHorseName());
        model.setSex(racingHorseDetail.getSexCd());
        model.setHairColor(racingHorseDetail.getHairColorCd());
        model.setAge(racingHorseDetail.getAge());
        model.setEwBelong(racingHorseDetail.getEwBelongCd());
        model.setJockeyNameShort(racingHorseDetail.getJockeyNameShort());
        model.setLoadWeight(racingHorseDetail.getLoadWeight());
        model.setJockeyApprentice(racingHorseDetail.getJockeyApprenticeCd());
        model.setTrainerNameShort(racingHorseDetail.getTrainerNameShort());
        model.setOwnerNameWithoutCorp(racingHorseDetail.getOwnerNameWithoutCorp());
        model.setHorseWeight(racingHorseDetail.getHorseWeight());
        model.setChangeSign(racingHorseDetail.getChangeSign());
        model.setChangeAmount(racingHorseDetail.getChangeAmount());
        model.setOddsWin(racingHorseDetail.getOddsWin());
        model.setBetRankWin(racingHorseDetail.getBetRankWin());
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
                }).collect(Collectors.toUnmodifiableList()));
        model.setRefundShows(racingRefund.getRefundShows().stream()
                .map(show -> {
                    BusinessRacingRefund.refund refund = new BusinessRacingRefund.refund();
                    refund.setHorseNo(show.getHorseNo());
                    refund.setRefundMoney(show.getRefundMoney());
                    refund.setBetRank(show.getBetRank());
                    return refund;
                }).collect(Collectors.toUnmodifiableList()));
        model.setRefundBracketQs(racingRefund.getRefundBracketQs().stream()
                .map(bracketQ -> {
                    BusinessRacingRefund.refund refund = new BusinessRacingRefund.refund();
                    refund.setHorseNo(bracketQ.getHorseNo());
                    refund.setRefundMoney(bracketQ.getRefundMoney());
                    refund.setBetRank(bracketQ.getBetRank());
                    return refund;
                }).collect(Collectors.toUnmodifiableList()));
        model.setRefundQuinellas(racingRefund.getRefundQuinellas().stream()
                .map(quinella -> {
                    BusinessRacingRefund.refundPair refundPair = new BusinessRacingRefund.refundPair();
                    refundPair.setPairNo(Pair.copyOf(quinella.getPairNo()));
                    refundPair.setRefundMoney(quinella.getRefundMoney());
                    refundPair.setBetRank(quinella.getBetRank());
                    return refundPair;
                }).collect(Collectors.toUnmodifiableList()));
        model.setRefundQuinellaPlaces(racingRefund.getRefundQuinellaPlaces().stream()
                .map(quinellaPlace -> {
                    BusinessRacingRefund.refundPair refundPair = new BusinessRacingRefund.refundPair();
                    refundPair.setPairNo(Pair.copyOf(quinellaPlace.getPairNo()));
                    refundPair.setRefundMoney(quinellaPlace.getRefundMoney());
                    refundPair.setBetRank(quinellaPlace.getBetRank());
                    return refundPair;
                }).collect(Collectors.toUnmodifiableList()));
        model.setRefundExactas(racingRefund.getRefundExactas().stream()
                .map(exactas -> {
                    BusinessRacingRefund.refundPair refundPair = new BusinessRacingRefund.refundPair();
                    refundPair.setPairNo(Pair.copyOf(exactas.getPairNo()));
                    refundPair.setRefundMoney(exactas.getRefundMoney());
                    refundPair.setBetRank(exactas.getBetRank());
                    return refundPair;
                }).collect(Collectors.toUnmodifiableList()));
        model.setRefundTrios(racingRefund.getRefundTrios().stream()
                .map(trio -> {
                    BusinessRacingRefund.refundTriplet refundTriplet = new BusinessRacingRefund.refundTriplet();
                    refundTriplet.setTripletNo(Triplet.copyOf(trio.getTripletNo()));
                    refundTriplet.setRefundMoney(trio.getRefundMoney());
                    refundTriplet.setBetRank(trio.getBetRank());
                    return refundTriplet;
                }).collect(Collectors.toUnmodifiableList()));
        model.setRefundTrifectas(racingRefund.getRefundTrifectas().stream()
                .map(trifectas -> {
                    BusinessRacingRefund.refundTriplet refundTriplet = new BusinessRacingRefund.refundTriplet();
                    refundTriplet.setTripletNo(Triplet.copyOf(trifectas.getTripletNo()));
                    refundTriplet.setRefundMoney(trifectas.getRefundMoney());
                    refundTriplet.setBetRank(trifectas.getBetRank());
                    return refundTriplet;
                }).collect(Collectors.toUnmodifiableList()));

        return model;
    }

}
