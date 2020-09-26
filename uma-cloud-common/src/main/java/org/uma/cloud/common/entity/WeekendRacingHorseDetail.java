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
@IdClass(WeekendRacingHorseDetail.CompositeId.class)
public class WeekendRacingHorseDetail extends BaseModel {

    @Column(length = 1, nullable = false)
    private String dataDiv;

    @Id
    @Column(length = 16, nullable = false)
    private String raceId;

    @Column(nullable = false)
    private Integer bracketNo;

    @Id
    @Column(length = 2, nullable = false)
    private String horseNo;

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

    @Column(nullable = false)
    private Integer ownerCd;

    // 負担重量 0.1kg
    @Column(nullable = false)
    private Double loadWeight;

    @Column(nullable = false)
    private Boolean isBlinker;

    @Column(nullable = false)
    private Integer jockeyCd;

    @Column(length = 4, nullable = false)
    private JockeyApprenticeCode jockeyApprenticeCd;

    /**
     * 以下、レース直前または確定後、データが入る。
     */

    @Column
    private Integer horseWeight;

    /**
     * 中央
     * 0: 前差なしor新馬戦or出走取消
     * 999: 計量不能
     * <p>
     * 地方
     * 999: 新馬戦or計量不能
     */
    @Column
    private Integer horseWeightGainOrLoss;

    @Column(length = 5)
    private AbnormalDivisionCode abnormalDivCd;

    @Column
    private Integer finishedArrivalOrder;

    @Column
    private Integer fixedArrivalOrder;

    @Column
    private Integer deadHeadDiv;

    @Column
    private Integer deadHeadCount;

    @Column
    private LocalTime runningTime;

    @Column(length = 6)
    private MarginCode marginCd;

    @Column(length = 6)
    private MarginCode marginCd2;

    /**
     * index: rankConer
     * 0: rankCorner1
     * 1: rankCorner2
     * 2: rankCorner3
     * 3: rankCorner4
     */
    @Type(type = "list")
    @Column(columnDefinition = "integer[]")
    private List<Integer> rankEachCorner;

    @Column
    private Double lastFurlong4;

    @Column
    private Double lastFurlong3;

//    // 獲得賞金をのぞいて、タイム差を入れてみる
//    /**
//     * タイム差
//     * +999 or -001 or 9999
//     */
//    @Column(length = 4)
//    private String timeMargin;


    @Override
    public Object getPrimaryKey() {
        CompositeId compositeId = new CompositeId();
        compositeId.setRaceId(this.raceId);
        compositeId.setHorseNo(this.horseNo);
        return compositeId;
    }


    @Data
    public static class CompositeId implements Serializable {

        private String raceId;

        private String horseNo;
    }
}
