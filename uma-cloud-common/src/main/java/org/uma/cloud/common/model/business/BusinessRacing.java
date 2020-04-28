package org.uma.cloud.common.model.business;

import lombok.Data;
import org.uma.cloud.common.code.RaceCourseCode;
import org.uma.cloud.common.code.RaceTypeCode;
import org.uma.cloud.common.code.TrackCode;
import org.uma.cloud.common.code.TurfOrDirtConditionCode;
import org.uma.cloud.common.code.WeatherCode;
import org.uma.cloud.common.model.event.Weather;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table
public class BusinessRacing implements Serializable {

    /**
     * レースID
     */
    @Id
    private String raceId;

    /**
     * データ区分
     * "2" 出馬表（未確定）
     * "6" 速報成績（確定）
     */
    @Column(length = 1, nullable = false)
    private String dataDiv;

    /**
     * レース開始時刻
     */
    @Column(nullable = false)
    private LocalDateTime raceStartDateTime;

    /**
     * レース番号
     */
    @Column(nullable = false)
    private Integer raceNo;

    /**
     * レース名
     * gradeが高いものだけ設定される。
     */
    @Column(length = 60, nullable = false)
    private String raceNameFull;

    /**
     * コース
     */
    @Column(length = 6, nullable = false)
    private RaceCourseCode course;

    /**
     * レースタイプ
     */
    @Column(length = 4, nullable = false)
    private RaceTypeCode raceType;

    /**
     * レースの距離
     */
    @Column(nullable = false)
    private Integer distance;

    /**
     * レースのトラック
     */
    @Column(length = 6, nullable = false)
    private TrackCode track;

    /**
     * コース変更理由
     * 0: なし
     * 1: 強風
     * 2: 台風
     * 3: 雪
     * 4: その他
     */
    @Column(nullable = false)
    private Integer courseChangeReason = 0;

    /**
     * レースのターフコンディション
     * {@link Weather}
     */
    @Column(length = 2, nullable = false)
    private TurfOrDirtConditionCode turf;

    /**
     * レースのダートコンディション
     * {@link Weather}
     */
    @Column(length = 2, nullable = false)
    private TurfOrDirtConditionCode dirt;

    /**
     * レース天候
     * {@link Weather}
     */
    @Column(length = 2, nullable = false)
    private WeatherCode weather;

}
