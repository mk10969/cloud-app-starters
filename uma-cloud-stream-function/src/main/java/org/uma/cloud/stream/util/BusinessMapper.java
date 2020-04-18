package org.uma.cloud.stream.util;

import org.uma.cloud.common.model.HorseRacingDetails;
import org.uma.cloud.common.model.RacingDetails;
import org.uma.cloud.common.model.business.BusinessRacingHorse;
import org.uma.cloud.common.model.business.BusinessRacing;

import java.time.LocalDateTime;

public class BusinessMapper {

    public static BusinessRacing toBusinessRace(RacingDetails racingDetails) {
        BusinessRacing model = new BusinessRacing();
        model.setRaceId(racingDetails.getRaceId());
        model.setDataDiv(racingDetails.getDataDiv());
        model.setRaceStartDateTime(LocalDateTime.of(racingDetails.getHoldingDate(), racingDetails.getStartTime()));
        model.setRaceNameFull(racingDetails.getRaceNameFull());
        model.setCourse(racingDetails.getCourseCd().getRaceCourseNameShort());
        model.setRaceType(racingDetails.getRaceTypeCd().getRaceTypeNameShort());
        model.setDistance(racingDetails.getDistance());
        model.setTrack(racingDetails.getTrackCd().getCodeName());
        model.setTurfCondition(racingDetails.getTurfConditionCd().getCodeName());
        model.setDirtCondition(racingDetails.getDirtConditionCd().getCodeName());
        model.setWeather(racingDetails.getWeatherCd().getCodeName());
        return model;
    }

    public static BusinessRacingHorse toBusinessHorseRace(HorseRacingDetails horseRacingDetails) {
        BusinessRacingHorse model = new BusinessRacingHorse();
        model.setRaceId(horseRacingDetails.getRaceId());
        model.setDataDiv(horseRacingDetails.getDataDiv());
        model.setRaceNo(horseRacingDetails.getRaceNo());
        model.setBracketNo(horseRacingDetails.getBracketNo());
        model.setHorseNo(horseRacingDetails.getHorseNo());
        model.setBloodlineNo(horseRacingDetails.getBloodlineNo());
        model.setHorseName(horseRacingDetails.getHorseName());
        model.setSex(horseRacingDetails.getSexCd().getCodeName());
        model.setHairColor(horseRacingDetails.getHairColorCd().getCodeName());
        model.setAge(horseRacingDetails.getAge());
        model.setEwBelong(horseRacingDetails.getEwBelongCd().getCodeName());
        model.setJockeyNameShort(horseRacingDetails.getJockeyNameShort());
        model.setLoadWeight(horseRacingDetails.getLoadWeight());
        model.setTrainerNameShort(horseRacingDetails.getTrainerNameShort());
        model.setOwnerNameWithoutCorp(horseRacingDetails.getOwnerNameWithoutCorp());
        model.setHorseWeight(horseRacingDetails.getHorseWeight());
        model.setOddsWin(horseRacingDetails.getOddsWin());
        model.setBetRankWin(horseRacingDetails.getBetRankWin());
        return model;
    }

}
