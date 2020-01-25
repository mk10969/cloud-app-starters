package org.uma.platform.common.model;

import lombok.Data;
import org.uma.platform.common.code.EastOrWestBelongCode;
import org.uma.platform.common.config.spec.RecordSpec;

import java.time.LocalDate;

/**
 * {@link RecordSpec.CH}
 */

@Data
public class Trainer {

    private RecordSpec recordType;
    private String dataDiv;
    private LocalDate dataCreateDate;

    /**
     * 調教師コード 5桁
     */
    private Integer trainerCd;
    private Boolean isTrainerErase;
    private LocalDate trainerLicenseIssueDate;
    private LocalDate trainerLicenseEraseDate;
    private LocalDate birthDate;
    private String trainerName;
    private String trainerNameHalfKana;
    private String trainerNameShort;
    private String trainerNameEng;
    private Integer trainerSex;
    private EastOrWestBelongCode ewBelongCd;
    private String invitationAreaName;

}
