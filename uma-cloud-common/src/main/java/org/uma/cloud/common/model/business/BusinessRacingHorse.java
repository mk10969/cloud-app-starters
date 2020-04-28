package org.uma.cloud.common.model.business;

import lombok.Data;
import org.uma.cloud.common.code.EastOrWestBelongCode;
import org.uma.cloud.common.code.HairColorCode;
import org.uma.cloud.common.code.JockeyApprenticeCode;
import org.uma.cloud.common.code.SexCode;
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
@IdClass(BusinessRacingHorse.CompositeId.class)
@Table
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
    @Column(nullable = false)
    private Long bloodlineNo;

    /**
     * 馬名
     */
    @Column(length = 36, nullable = false)
    private String horseName;

    /**
     * 性別
     */
    @Column(length = 2, nullable = false)
    private SexCode sex;

    /**
     * 毛色
     */
    @Column(length = 3, nullable = false)
    private HairColorCode hairColor;

    /**
     * 年齢
     */
    @Column(nullable = false)
    private Integer age;

    /**
     * 競走馬の東西
     */
    @Column(length = 4, nullable = false)
    private EastOrWestBelongCode ewBelong;

    /**
     * ジョッキー
     */
    @Column(length = 8, nullable = false)
    private String jockeyNameShort;

    /**
     * ジョッキー負担重量 例:0.1kg
     */
    @Column(nullable = false)
    private Double loadWeight;

    /**
     * ジョッキー見習コード
     */
    @Column(length = 4, nullable = false)
    private JockeyApprenticeCode jockeyApprentice;

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
     * @Nullable
     */
    private Integer horseWeight;

    /**
     * 増減: + or -
     * @Nullable
     */
    @Column(length = 1)
    private String changeSign;

    /**
     * 馬体重変化量
     * @Nullable
     */
    private Integer changeAmount;

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
     * 競走馬出走可能フラグ
     * <p>
     * 0: 出走可能
     * 1: 出走取消
     * 2: 競走除外
     */
    @Column(nullable = false)
    private Integer exclude = 0;

    /**
     * 競走馬除外理由
     * <p>
     * 000: 初期値
     * 001: 疾病
     * 002: 事故
     * 003: その他
     */
    @Column(length = 3, nullable = false)
    private String excludeReason = "000";


    /**
     * TODO レース結果のカラムも足す！！！
     */


    @Data
    public static class CompositeId implements Serializable {

        private String raceId;

        private String horseNo;
    }

}
