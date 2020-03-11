package org.uma.cloud.common.model;

import lombok.Getter;
import org.uma.cloud.common.code.EastOrWestBelongCode;
import org.uma.cloud.common.recordSpec.RecordSpec;

import java.time.LocalDate;

/**
 * {@link RecordSpec.CH}
 */
@Getter
public class Trainer extends BaseModel {

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
