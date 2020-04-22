package org.uma.cloud.common.model.business;

import lombok.Data;
import org.uma.cloud.common.model.BloodLine;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Entity
@IdClass(BusinessRacingHorse.BusinessRacingHorseId.class)
@Table(name = "business_racing_horse")
public class BusinessRacingHorse implements Serializable {

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
     * レース番号
     */
    @Column(nullable = false)
    private Integer raceNo;

    /**
     * 枠番
     */
    @Column(nullable = false)
    private Integer bracketNo;

    /**
     * 馬番
     */
    @Id
    @Column(length = 2)
    private String horseNo;

    /**
     * {@link BloodLine.bloodlineNo}
     */
    @Id
    private Long bloodlineNo;

    /**
     * 馬名
     */
    @Column(length = 36, nullable = false)
    private String horseName;

    /**
     * 性別
     * →Stringで保存する。
     */
    @Column(length = 2, nullable = false)
    private String sex;

    /**
     * 毛色
     * →Stringで保存する。
     */
    @Column(length = 3, nullable = false)
    private String hairColor;

    /**
     * 年齢
     */
    @Column(nullable = false)
    private Integer age;

    /**
     * 競走馬の東西
     * →Stringで保存する。
     */
    @Column(length = 4, nullable = false)
    private String ewBelong;

    /**
     * ジョッキー
     */
    @Column(length = 8, nullable = false)
    private String jockeyNameShort;

    /**
     * 負担重量 例:0.1kg
     */
    @Column(nullable = false)
    private Double loadWeight;

    /**
     * 調教師
     */
    @Column(length = 8, nullable = false)
    private String trainerNameShort;

    /**
     * オーナー
     */
    @Column(length = 64, nullable = false)
    private String ownerNameWithoutCorp;

    /**
     * 馬体重
     */
    @Column(nullable = false)
    private Integer horseWeight;

    /**
     * 単勝オッズ
     */
    @Column(nullable = false)
    private BigDecimal oddsWin;

    /**
     * 人気
     */
    @Column(nullable = false)
    private Integer betRankWin;

    /**
     * 競走馬除外
     */
    private boolean exclude = false;


    /**
     * TODO レース結果のカラムも足す！！！
     */


    @Data
    public static class BusinessRacingHorseId implements Serializable {

        private String raceId;

        private String horseNo;

        private Long bloodlineNo;
    }

}
