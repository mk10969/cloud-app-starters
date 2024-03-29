package org.uma.cloud.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.uma.cloud.common.code.TurfOrDirtConditionCode;
import org.uma.cloud.common.entity.WeekendRacingDetail;
import org.uma.cloud.common.model.event.CourseChange;
import org.uma.cloud.common.model.event.TimeChange;
import org.uma.cloud.common.model.event.Weather;
import org.uma.cloud.common.repository.WeekendRacingDetailRepository;
import org.uma.cloud.common.utils.lang.DateUtil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
public class WeekendRacingDetailService {

    private static final Supplier<Long> now = System::currentTimeMillis;

    private static final String pending = "2";


    @Autowired
    private WeekendRacingDetailRepository repository;

    /**
     * 現在時刻 以降のレース一覧を取得。
     */
    public List<WeekendRacingDetail> findComingRaces() {
        LocalDateTime localDateTime = DateUtil.toLocalDateTime(now.get());
        return repository.findByHoldingDateAfterAndStartTimeAfter(
                localDateTime.toLocalDate(), localDateTime.toLocalTime());
    }

    /**
     * 現在時刻 以前の未確定レース一覧を取得。
     */
    @Deprecated
    public List<WeekendRacingDetail> findFinishedRaces() {
        return new ArrayList<>();
//        return repository.findByDataDivAndRaceStartDateTimeBefore(
//                pending, DateUtil.toLocalDateTime(now.get()));
    }

    /**
     * イベント通知 => Weather => 天候 or 馬場状態 のみ変更
     *
     * @param weather 天候 or 馬場状態
     */
    public List<WeekendRacingDetail> updateAllWeather(Weather weather) {
        LocalDateTime localDateTime = weather.timestamp();
        List<WeekendRacingDetail> updatingRacing = repository.findByHoldingDateAfterAndStartTimeAfter(
                localDateTime.toLocalDate(), localDateTime.toLocalTime())
                .stream()
                .filter(racing -> racing.getCourseCd() == weather.getCourseCd())
                .peek(racing -> {
                    if (weather.getChangeId() == 1) {
                        racing.setWeatherCd(weather.getWeatherNow());
                        TurfOrDirtConditionCode turfOrDirt = TurfOrDirtConditionCode
                                .compare(weather.getTurfNow(), weather.getDirtNow());
                        racing.setTurfOrDirtCondition(turfOrDirt);

                    } else if (weather.getChangeId() == 2) {
                        racing.setWeatherCd(weather.getWeatherNow());

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
    public WeekendRacingDetail updateTimeChange(TimeChange timeChange) {
        WeekendRacingDetail updatingRacing = repository.findById(timeChange.getRaceId()).orElseThrow();
        updatingRacing.setStartTime(timeChange.getStartTimeAfter());

        return this.update(updatingRacing);
    }

    /**
     * イベント通知 => CourseChange => コースのみ変更   *ほぼないけどw
     *
     * @param courseChange コース変更
     */
    public WeekendRacingDetail updateCourseChange(CourseChange courseChange) {
        WeekendRacingDetail updatingRacing = repository.findById(courseChange.getRaceId()).orElseThrow();
        updatingRacing.setDistance(courseChange.getDistanceAfter());
        updatingRacing.setTrackCd(courseChange.getTrackCdAfter());
        updatingRacing.setCourseChangeReason(courseChange.getReason());

        return this.update(updatingRacing);
    }


    @Transactional
    public WeekendRacingDetail update(WeekendRacingDetail model) {
        return repository.save(model);
    }

    @Transactional
    public List<WeekendRacingDetail> updateAll(List<WeekendRacingDetail> model) {
        return repository.saveAll(model);
    }

    @Transactional
    public void delete(WeekendRacingDetail model) {
        repository.delete(model);
    }

    @Transactional
    public void deleteByRaceId(String raceId) {
        repository.deleteById(raceId);
    }

}
