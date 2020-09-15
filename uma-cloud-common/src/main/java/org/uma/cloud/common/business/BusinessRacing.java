package org.uma.cloud.common.business;

import lombok.Data;
import org.hibernate.annotations.Type;
import org.uma.cloud.common.code.RaceCourseCode;
import org.uma.cloud.common.code.RaceSignCode;
import org.uma.cloud.common.code.RaceTypeCode;
import org.uma.cloud.common.code.TrackCode;
import org.uma.cloud.common.code.TurfOrDirtConditionCode;
import org.uma.cloud.common.code.WeatherCode;
import org.uma.cloud.common.code.WeightTypeCode;
import org.uma.cloud.common.model.event.Weather;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@Entity
@Table
public class BusinessRacing implements Serializable {

    /**
     * レースID
     */
    @Id
    @Column(length = 16)
    private String raceId;

    /**
     * データ区分
     * "2" 出馬表（未確定）
     * "6" 速報成績（確定）
     */
    @Column(length = 1, nullable = false)
    private String dataDiv;

    /**
     * 開催日
     */
    @Column(nullable = false)
    private LocalDate holdingDate;

    /**
     * レース開始時刻
     */
    @Column(nullable = false)
    private LocalTime startTime;

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
    private RaceCourseCode courseCd;

    /**
     * レースタイプ
     */
    @Column(length = 4, nullable = false)
    private RaceTypeCode raceType;

    /**
     * 競走記号
     */
    @Column(length = 20, nullable = false)
    private RaceSignCode raceSignCd;

    /**
     * 重量種別
     */
    @Column(length = 3, nullable = false)
    private WeightTypeCode weightTypeCd;

    /**
     * 2歳 競走条件
     */
    @Column(nullable = false)
    private Integer raceConditionCdOld2;

    /**
     * 3歳 競走条件
     */
    @Column(nullable = false)
    private Integer raceConditionCdOld3;

    /**
     * 4歳 競走条件
     */
    @Column(nullable = false)
    private Integer raceConditionCdOld4;

    /**
     * 5歳以上 競走条件
     */
    @Column(nullable = false)
    private Integer raceConditionCdOld5;

    /**
     * 最若年条件
     */
    @Column(nullable = false)
    private Integer raceConditionCdYoungest;

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
     */
    @Column(length = 2, nullable = false)
    private TurfOrDirtConditionCode turfOrDirtCondition;

    /**
     * レース天候
     * {@link Weather}
     */
    @Column(length = 2, nullable = false)
    private WeatherCode weather;


    ///// レース結果 //////

    /**
     * ラップ
     */
    @Type(type = "list")
    @Column(columnDefinition = "double precision[]")
    private List<Double> lapTimeItems;

    /**
     * 障害レースのみ設定
     */
    private Double jumpMileTime;

    /**
     * テン3ハロン
     */
    private Double firstFurlong3;

    /**
     * テン4ハロン
     */
    private Double firstFurlong4;

    /**
     * 上がり3ハロン
     */
    private Double lastFurlong3;

    /**
     * 上がり4ハロン
     */
    private Double lastFurlong4;

    /**
     * コーナ通過順位
     */
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private List<CornerPassageRank> cornerPassageRanks;

    @Data
    public static class CornerPassageRank {

        private Integer corner;

        private Integer aroundCount;

        private String passageRank;
    }


    /**
     * TODO 追加したいカラム
     *
     * タイムを見て、実際の馬場状態を決定したい。
     * 良でも、重みたいな時計がかかる馬場の可能性がある。
     *
     *
     *
     *
     *
     */


}
