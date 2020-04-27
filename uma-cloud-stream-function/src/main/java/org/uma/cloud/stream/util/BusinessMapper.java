package org.uma.cloud.stream.util;

import org.uma.cloud.common.model.RacingHorseDetail;
import org.uma.cloud.common.model.RacingDetail;
import org.uma.cloud.common.model.RacingRefund;
import org.uma.cloud.common.model.business.BusinessRacingHorse;
import org.uma.cloud.common.model.business.BusinessRacing;
import org.uma.cloud.common.model.business.BusinessRacingRefund;

import java.time.LocalDateTime;

public class BusinessMapper {

    public static BusinessRacing toBusinessRacing(RacingDetail racingDetail) {
        BusinessRacing model = new BusinessRacing();
        model.setRaceId(racingDetail.getRaceId());
        model.setDataDiv(racingDetail.getDataDiv());
        model.setRaceStartDateTime(LocalDateTime.of(racingDetail.getHoldingDate(), racingDetail.getStartTime()));
        model.setRaceNo(racingDetail.getRaceNo());
        model.setRaceNameFull(racingDetail.getRaceNameFull());
        model.setCourse(racingDetail.getCourseCd().getCodeName());
        model.setRaceType(racingDetail.getRaceTypeCd().getCodeName());
        model.setDistance(racingDetail.getDistance());
        model.setTrack(racingDetail.getTrackCd().getCodeName());
//        model.setTurfCondition(racingDetails.getTurfConditionCd().getCodeName());
//        model.setDirtCondition(racingDetails.getDirtConditionCd().getCodeName());
//        model.setWeather(racingDetails.getWeatherCd().getCodeName());
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
        model.setSex(racingHorseDetail.getSexCd().getCodeName());
        model.setHairColor(racingHorseDetail.getHairColorCd().getCodeName());
        model.setAge(racingHorseDetail.getAge());
        model.setEwBelong(racingHorseDetail.getEwBelongCd().getCodeName());
        model.setJockeyNameShort(racingHorseDetail.getJockeyNameShort());
        model.setLoadWeight(racingHorseDetail.getLoadWeight());
        model.setTrainerNameShort(racingHorseDetail.getTrainerNameShort());
        model.setOwnerNameWithoutCorp(racingHorseDetail.getOwnerNameWithoutCorp());
        model.setHorseWeight(racingHorseDetail.getHorseWeight());
        model.setOddsWin(racingHorseDetail.getOddsWin());
        model.setBetRankWin(racingHorseDetail.getBetRankWin());
        return model;
    }

    public static BusinessRacingRefund toBusinessRacingRefund(RacingRefund racingRefund) {
        BusinessRacingRefund model = new BusinessRacingRefund();
        return model;
    }

}
