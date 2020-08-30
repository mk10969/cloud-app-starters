package org.uma.cloud.common.model;

import lombok.Getter;
import org.uma.cloud.common.code.EastOrWestBelongCode;
import org.uma.cloud.common.code.RecordSpec;

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
public class DiffTrainer extends BaseModel {

    /**
     * データ区分
     */
    @Column(length = 1)
    private String dataDiv;

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

    @Column(length = 4)
    private EastOrWestBelongCode ewBelongCd;

    @Column(length = 20)
    private String invitationAreaName;

}
