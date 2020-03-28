package org.uma.cloud.common.model;

import lombok.Getter;
import org.hibernate.annotations.Type;
import org.uma.cloud.common.code.BreedCode;
import org.uma.cloud.common.code.HairColorCode;
import org.uma.cloud.common.code.SexCode;
import org.uma.cloud.common.recordSpec.RecordSpec;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.List;

/**
 * {@link RecordSpec.SK}
 */
@Getter
@Entity
@Table(name = "uma_offspring")
public class Offspring extends BaseModel {

    /**
     * 血統登録番号 10桁
     * {@link HorseRacingDetails.bloodlineNo}
     * {@link BreedingHorse.bloodlineNo}
     */
    @Id
    private Long bloodlineNo;

    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    private SexCode sexCd;

    @Enumerated(EnumType.STRING)
    private BreedCode breedCd;

    @Enumerated(EnumType.STRING)
    private HairColorCode hairColorCd;

    private Integer sonBringingDiv;

    private Integer importYear;

    private Integer breederCd;

    @Column(length = 20)
    private String sourceName;

    /**
     * ３代血統 繁殖登録番号
     * <p>
     * 父･母･
     * 父父･父母･母父･母母･
     * 父父父･父父母･父母父･父母母･母父父･母父母･母母父･母母母
     * の順
     * {@link BreedingHorse.breedingNo}
     */
    @Type(type = "list")
    @Column(columnDefinition = "integer[]")
    private List<Integer> breeding3rd;

}
