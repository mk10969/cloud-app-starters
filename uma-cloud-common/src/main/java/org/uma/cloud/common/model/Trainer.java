package org.uma.cloud.common.model;

import lombok.Getter;
import org.uma.cloud.common.code.EastOrWestBelongCode;
import org.uma.cloud.common.recordSpec.RecordSpec;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

/**
 * {@link RecordSpec.CH}
 */
@Getter
@Entity
@Table
public class Trainer extends BaseModel {

    /**
     * 調教師コード 5桁
     */
    @Id
    private Integer trainerCd;

    private Boolean isTrainerErase;

    private LocalDate trainerLicenseIssueDate;

    private LocalDate trainerLicenseEraseDate;

    private LocalDate birthDate;

    @Column(length = 34)
    private String trainerName;

    @Column(length = 30)
    private String trainerNameHalfKana;

    @Column(length = 8)
    private String trainerNameShort;

    @Column(length = 80)
    private String trainerNameEng;

    private Integer trainerSex;

    private EastOrWestBelongCode ewBelongCd;

    @Column(length = 20)
    private String invitationAreaName;

}
