package org.uma.cloud.common.model.business;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "business_racing")
public class BusinessRacing {

    /**
     * レースID
     */
    @Id
    private String raceId;

    /**
     * データ区分
     * "2" になるデータになるはず
     */
    @Column(length = 1)
    private String dataDiv;

    /**
     * レース開始時刻
     */
    @Column(nullable = false)
    private LocalDateTime raceStartDateTime;

    /**
     * レース名
     * gradeが高いものだけ設定される。
     */
    @Column(length = 60)
    private String raceNameFull;

    /**
     * コース
     * →コース名
     */
    @Column(length = 6)
    private String course;

    /**
     * レースタイプ
     * →レースタイプ ショート名
     */
    @Column(length = 4)
    private String raceType;

    /**
     * レースの距離
     */
    private Integer distance;

    /**
     * レースのトラック
     * →トラック名
     */
    @Column(length = 6)
    private String track;

    /**
     * レースのターフコンディション
     * →コンディション名
     */
    @Column(length = 2)
    private String turfCondition;

    /**
     * レースのダートコンディション
     * →コンディション名
     */
    @Column(length = 2)
    private String dirtCondition;

    /**
     * レース天候
     * →天候名
     */
    @Column(length = 2)
    private String weather;

}
