package org.uma.cloud.common.model.jvlink;

import lombok.Getter;
import org.uma.cloud.common.code.EastOrWestBelongCode;
import org.uma.cloud.common.code.JockeyApprenticeCode;
import org.uma.cloud.common.code.JockeyLicenseCode;

import java.time.LocalDate;

@Getter
class DIFF_KS {
    private String dataDiv;
    private Integer jockeyCd;
    private Boolean isJockeyErase;
    private LocalDate jockeyLicenseIssueDate;
    private LocalDate jockeyLicenseEraseDate;
    private LocalDate birthDate;
    private String jockeyName;
    private String spare1;
    private String jockeyNameHalfKana;
    private String jockeyNameShort;
    private String jockeyNameEng;
    private Integer jockeySex;
    private JockeyLicenseCode jockeyLicenseCd;
    private JockeyApprenticeCode jockeyApprenticeCd;
    private EastOrWestBelongCode ewBelongCd;
    private String invitationAreaName;
    private String belongingTrainerCd;
    private String belongingTrainerNameShort;
}