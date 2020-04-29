package org.uma.cloud.common.service.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.uma.cloud.common.model.business.BusinessRacing;
import org.uma.cloud.common.model.event.CourseChange;
import org.uma.cloud.common.model.event.TimeChange;
import org.uma.cloud.common.model.event.Weather;
import org.uma.cloud.common.repository.business.BusinessRacingRepository;
import org.uma.cloud.common.utils.lang.DateUtil;

import java.time.LocalDateTime;
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
        return repository.findByRaceStartDateTimeAfter(DateUtil.toLocalDateTime(now.get()));
    }

    /**
     * 現在時刻 以前の未確定レース一覧を取得。
     */
    public List<BusinessRacing> findFinishedRaces() {
        return repository.findByDataDivAndRaceStartDateTimeBefore(
                pending, DateUtil.toLocalDateTime(now.get()));
    }

    /**
     * イベント通知 => Weather => 天候 or 馬場状態 のみ変更
     *
     * @param weather 天候 or 馬場状態
     */
    public List<BusinessRacing> updateWeather(Weather weather) {
        List<BusinessRacing> updatingRacing = repository.findByRaceStartDateTimeAfter(weather.getTimestamp())
                .stream()
                .filter(racing -> racing.getCourse() == weather.getCourseCd())
                .peek(racing -> {
                    switch (weather.getChangeId()) {
                        case 1:
                            racing.setWeather(weather.getWeatherNow());
                            racing.setTurf(weather.getTurfNow());
                            racing.setDirt(weather.getDirtNow());
                        case 2:
                            racing.setWeather(weather.getWeatherNow());
                        case 3:
                            racing.setTurf(weather.getTurfNow());
                            racing.setDirt(weather.getDirtNow());
                        default:
                            throw new IllegalArgumentException("weather: " + weather);
                    }
                })
                .collect(Collectors.toUnmodifiableList());

        // TODO: Transactionalされるか？
        return this.updateAll(updatingRacing);
    }

    /**
     * イベント通知 => TimeChange => 発走時刻のみ変更
     *
     * @param timeChange 発走時刻変更
     */
    public BusinessRacing updateTimeChange(TimeChange timeChange) {
        BusinessRacing updatingRacing = repository.findById(timeChange.getRaceId()).orElseThrow();
        updatingRacing.setRaceStartDateTime(LocalDateTime.of(
                timeChange.getHoldingDate(), timeChange.getStartTimeAfter()));

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
