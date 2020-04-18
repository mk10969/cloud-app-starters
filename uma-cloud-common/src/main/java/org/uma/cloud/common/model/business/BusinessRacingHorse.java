package org.uma.cloud.common.model.business;

import lombok.Data;
import org.uma.cloud.common.model.Offspring;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "business_racing_horse")
public class BusinessRacingHorse {

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
     * レース番号
     */
    private Integer raceNo;

    /**
     * 枠番
     */
    private Integer bracketNo;

    /**
     * 馬番
     */
    @Id
    @Column(length = 2)
    private String horseNo;

    /**
     * {@link Offspring.bloodlineNo}
     */
    @Id
    private Long bloodlineNo;

    /**
     * 馬名
     */
    @Column(length = 36)
    private String horseName;

    /**
     * 性別
     * →Stringで保存する。
     */
    @Column(length = 2)
    private String sex;

    /**
     * 毛色
     * →Stringで保存する。
     */
    @Column(length = 3)
    private String hairColor;

    /**
     * 年齢
     */
    private Integer age;

    /**
     * 競走馬の東西
     * →Stringで保存する。
     */
    @Column(length = 4)
    private String ewBelong;

    /**
     * ジョッキー
     */
    @Column(length = 8)
    private String jockeyNameShort;

    /**
     * 負担重量 例:0.1kg
     */
    private Double loadWeight;

    /**
     * 調教師
     */
    @Column(length = 8)
    private String trainerNameShort;

    /**
     * オーナー
     */
    @Column(length = 64)
    private String ownerNameWithoutCorp;

    /**
     * 馬体重
     */
    private Integer horseWeight;

    /**
     * 単勝オッズ
     */
    private BigDecimal oddsWin;

    /**
     * 人気
     */
    private Integer betRankWin;

    /**
     * 競走馬除外
     */
    private boolean exclude = false;


    @Data
    public static class CompositeId implements Serializable {

        private String raceId;

        private String horseNo;

        private Long bloodlineNo;
    }

}
