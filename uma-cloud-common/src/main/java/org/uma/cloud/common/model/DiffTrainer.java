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
    @Column(length = 1, nullable = false)
    private String dataDiv;

    /**
     * 調教師コード 5桁
     */
    @Id
    @Column(nullable = false)
    private Integer trainerCd;

    @Column(nullable = false)
    private Boolean isTrainerErase;

    @Column(nullable = false)
    private LocalDate trainerLicenseIssueDate;

    /**
     * Nullable
     */
    private LocalDate trainerLicenseEraseDate;

    /**
     * Nullable
     */
    private LocalDate birthDate;

    @Column(length = 34, nullable = false)
    private String trainerName;

    @Column(length = 30, nullable = false)
    private String trainerNameHalfKana;

    @Column(length = 8, nullable = false)
    private String trainerNameShort;

    @Column(length = 80, nullable = false)
    private String trainerNameEng;

    @Column(nullable = false)
    private Integer trainerSex;

    @Column(length = 4, nullable = false)
    private EastOrWestBelongCode ewBelongCd;

    @Column(length = 20, nullable = false)
    private String invitationAreaName;

}
