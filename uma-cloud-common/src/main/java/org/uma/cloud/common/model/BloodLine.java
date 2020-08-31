package org.uma.cloud.common.model;

import lombok.Getter;
import org.hibernate.annotations.Type;
import org.uma.cloud.common.code.BreedCode;
import org.uma.cloud.common.code.HairColorCode;
import org.uma.cloud.common.code.RecordSpec;
import org.uma.cloud.common.code.SexCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.List;

/**
 * {@link RecordSpec.SK}
 */
@Getter
@Entity
@Table
public class BloodLine extends BaseModel {

    /**
     * データ区分
     */
    @Column(length = 1, nullable = false)
    private String dataDiv;

    /**
     * 血統登録番号 10桁
     * {@link RacingHorseDetail.bloodlineNo}
     * {@link BloodBreeding.bloodlineNo}
     */
    @Id
    @Column(nullable = false)
    private Long bloodlineNo;

    /**
     * Nullable
     */
    private LocalDate birthDate;

    @Column(length = 2, nullable = false)
    private SexCode sexCd;

    @Column(length = 8, nullable = false)
    private BreedCode breedCd;

    @Column(length = 3, nullable = false)
    private HairColorCode hairColorCd;

    @Column(nullable = false)
    private Integer sonBringingDiv;

    @Column(nullable = false)
    private Integer importYear;

    @Column(nullable = false)
    private Integer breederCd;

    @Column(length = 20, nullable = false)
    private String sourceName;

    /**
     * ３代血統 繁殖登録番号
     * <p>
     * 父･母･
     * 父父･父母･母父･母母･
     * 父父父･父父母･父母父･父母母･母父父･母父母･母母父･母母母
     * の順
     * {@link BloodBreeding.breedingNo}
     */
    @Type(type = "list")
    @Column(columnDefinition = "integer[]", nullable = false)
    private List<Integer> breeding3rd;

}
