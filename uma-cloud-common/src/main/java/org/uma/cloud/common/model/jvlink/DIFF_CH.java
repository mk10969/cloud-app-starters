package org.uma.cloud.common.model.jvlink;

import lombok.Getter;
import org.uma.cloud.common.code.EastOrWestBelongCode;
import org.uma.cloud.common.model.BaseJvLink;

import java.time.LocalDate;

@Getter
public class DIFF_CH extends BaseJvLink {
    private String dataDiv;
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