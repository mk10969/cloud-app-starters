package org.uma.cloud.common.model.business;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "business_racing")
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
     * →コース名
     */
    @Column(length = 6, nullable = false)
    private String course;

    /**
     * レースタイプ
     * →レースタイプ ショート名
     */
    @Column(length = 4, nullable = false)
    private String raceType;

    /**
     * レースの距離
     */
    @Column(nullable = false)
    private Integer distance;

    /**
     * レースのトラック
     * →トラック名
     */
    @Column(length = 6, nullable = false)
    private String track;


    /**
     * データとれない・・・
     */
//    /**
//     * レースのターフコンディション
//     * →コンディション名
//     */
//    @Column(length = 2, nullable = false)
//    private String turfCondition;
//
//    /**
//     * レースのダートコンディション
//     * →コンディション名
//     */
//    @Column(length = 2, nullable = false)
//    private String dirtCondition;
//
//    /**
//     * レース天候
//     * →天候名
//     */
//    @Column(length = 2, nullable = false)
//    private String weather;

}
