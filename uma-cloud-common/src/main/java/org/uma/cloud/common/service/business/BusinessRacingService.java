package org.uma.cloud.common.service.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.uma.cloud.common.business.BusinessRacing;
import org.uma.cloud.common.code.TurfOrDirtConditionCode;
import org.uma.cloud.common.model.event.CourseChange;
import org.uma.cloud.common.model.event.TimeChange;
import org.uma.cloud.common.model.event.Weather;
import org.uma.cloud.common.repository.business.BusinessRacingRepository;
import org.uma.cloud.common.utils.lang.DateUtil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
public class BusinessRacingService {

    private static final Supplier<Long> now = System::currentTimeMillis;

    private static final String pending = "2";


    @Autowired
    private BusinessRacingRepository repository;

    /**
     * 現在時刻 以降のレース一覧を取得。
     */
    public List<BusinessRacing> findComingRaces() {
        LocalDateTime localDateTime = DateUtil.toLocalDateTime(now.get());
        return repository.findByHoldingDateAfterAndStartTimeAfter(
                localDateTime.toLocalDate(), localDateTime.toLocalTime());
    }

    /**
     * 現在時刻 以前の未確定レース一覧を取得。
     */
    @Deprecated
    public List<BusinessRacing> findFinishedRaces() {
        return new ArrayList<>();
//        return repository.findByDataDivAndRaceStartDateTimeBefore(
//                pending, DateUtil.toLocalDateTime(now.get()));
    }

    /**
     * イベント通知 => Weather => 天候 or 馬場状態 のみ変更
     *
     * @param weather 天候 or 馬場状態
     */
    public List<BusinessRacing> updateAllWeather(Weather weather) {
        LocalDateTime localDateTime = weather.timestamp();
        List<BusinessRacing> updatingRacing = repository.findByHoldingDateAfterAndStartTimeAfter(
                localDateTime.toLocalDate(), localDateTime.toLocalTime())
                .stream()
                .filter(racing -> racing.getCourseCd() == weather.getCourseCd())
                .peek(racing -> {
                    if (weather.getChangeId() == 1) {
                        racing.setWeather(weather.getWeatherNow());
                        TurfOrDirtConditionCode turfOrDirt = TurfOrDirtConditionCode
                                .compare(weather.getTurfNow(), weather.getDirtNow());
                        racing.setTurfOrDirtCondition(turfOrDirt);

                    } else if (weather.getChangeId() == 2) {
                        racing.setWeather(weather.getWeatherNow());

                    } else if (weather.getChangeId() == 3) {
                        TurfOrDirtConditionCode turfOrDirt = TurfOrDirtConditionCode
                                .compare(weather.getTurfNow(), weather.getDirtNow());
                        racing.setTurfOrDirtCondition(turfOrDirt);

                    } else {
                        throw new IllegalArgumentException("weather: " + weather);
                    }
                })
                .collect(Collectors.toUnmodifiableList());

        return this.updateAll(updatingRacing);
    }

    /**
     * イベント通知 => TimeChange => 発走時刻のみ変更
     *
     * @param timeChange 発走時刻変更
     */
    public BusinessRacing updateTimeChange(TimeChange timeChange) {
        BusinessRacing updatingRacing = repository.findById(timeChange.getRaceId()).orElseThrow();
        updatingRacing.setStartTime(timeChange.getStartTimeAfter());

        return this.update(updatingRacing);
    }

    /**
     * イベント通知 => CourseChange => コースのみ変更   *ほぼないけどw
     *
     * @param courseChange コース変更
     */
    public BusinessRacing updateCourseChange(CourseChange courseChange) {
        BusinessRacing updatingRacing = repository.findById(courseChange.getRaceId()).orElseThrow();
        updatingRacing.setDistance(courseChange.getDistanceAfter());
        updatingRacing.setTrack(courseChange.getTrackCdAfter());
        updatingRacing.setCourseChangeReason(courseChange.getReason());

        return this.update(updatingRacing);
    }


    @Transactional
    public BusinessRacing update(BusinessRacing model) {
        return repository.save(model);
    }

    @Transactional
    public List<BusinessRacing> updateAll(List<BusinessRacing> model) {
        return repository.saveAll(model);
    }

    @Transactional
    public void delete(BusinessRacing model) {
        repository.delete(model);
    }

    @Transactional
    public void deleteByRaceId(String raceId) {
        repository.deleteById(raceId);
    }

}
