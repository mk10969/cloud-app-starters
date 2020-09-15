package org.uma.cloud.common.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Type;
import org.uma.cloud.common.code.AbnormalDivisionCode;
import org.uma.cloud.common.code.EastOrWestBelongCode;
import org.uma.cloud.common.code.HairColorCode;
import org.uma.cloud.common.code.HorseSignCode;
import org.uma.cloud.common.code.JockeyApprenticeCode;
import org.uma.cloud.common.code.MarginCode;
import org.uma.cloud.common.code.SexCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;
import java.time.LocalTime;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@IdClass(RacingHorseDetail.CompositeId.class)
public class RacingHorseDetail extends BaseModel {

    /**
     * データ区分
     */
    @Column(length = 1, nullable = false)
    private String dataDiv;

    /**
     * {@link RacingDetail.raceId}
     */
    @Id
    @Column(length = 16, nullable = false)
    private String raceId;

    /**
     * 枠番
     */
    @Column(nullable = false)
    private Integer bracketNo;

    /**
     * 馬番
     */
    @Id
    @Column(length = 2, nullable = false)
    private String horseNo;

    /**
     * {@link BloodLine.bloodlineNo}
     */
    @Id
    @Column(nullable = false)
    private Long bloodlineNo;

    @Column(length = 36, nullable = false)
    private String horseName;

    @Column(length = 9, nullable = false)
    private HorseSignCode horseSignCd;

    @Column(length = 2, nullable = false)
    private SexCode sexCd;

    @Column(length = 3, nullable = false)
    private HairColorCode hairColorCd;

    @Column(nullable = false)
    private Integer age;

    // 競走馬の東西
    @Column(length = 4, nullable = false)
    private EastOrWestBelongCode ewBelongCd;

    @Column(nullable = false)
    private Integer trainerCd;

//    @Column(length = 8, nullable = false)
//    private String trainerNameShort;

    @Column(nullable = false)
    private Integer ownerCd;

//    @Column(length = 64, nullable = false)
//    private String ownerNameWithoutCorp;

    // 負担重量 0.1kg
    @Column(nullable = false)
    private Double loadWeight;

    @Column(nullable = false)
    private Boolean isBlinker;

    @Column(nullable = false)
    private Integer jockeyCd;

//    @Column(length = 8, nullable = false)
//    private String jockeyNameShort;

    @Column(length = 4, nullable = false)
    private JockeyApprenticeCode jockeyApprenticeCd;

    /**
     * Nullable
     */
    private Integer horseWeight;

//    @Column(length = 1, nullable = false)
//    private String changeSign;

    /**
     * Nullable
     * マイナスいけるやん。。。
     */
    private Integer changeAmount;

    @Column(length = 5, nullable = false)
    private AbnormalDivisionCode abnormalDivCd;

    @Column(nullable = false)
    private Integer finishedArrivalOrder;

    @Column(nullable = false)
    private Integer fixedArrivalOrder;

    @Column(nullable = false)
    private Integer deadHeadDiv;

    @Column(nullable = false)
    private Integer deadHeadCount;

    @Column(nullable = false)
    private LocalTime runningTime;

    @Column(length = 6, nullable = false)
    private MarginCode marginCd;

    @Column(length = 6, nullable = false)
    private MarginCode marginCd2;

    /**
     * rankCorner1
     * rankCorner2
     * rankCorner3
     * rankCorner4
     */
    @Type(type = "list")
    @Column(columnDefinition = "integer[]", nullable = false)
    private List<Integer> rankEachCorner;

    @Column(nullable = false)
    private Integer acquirementAddedMoney;

    @Column(nullable = false)
    private Integer acquirementStakesMoney;

    @Column(nullable = false)
    private Double lastFurlong4;

    @Column(nullable = false)
    private Double lastFurlong3;

    /**
     * 血統登録番号を入れておかないと、
     * 海外レースのとき、一意に識別できない。
     */
    @Data
    public static class CompositeId implements Serializable {

        private String raceId;

        private String horseNo;

        private Long bloodlineNo;
    }

}
