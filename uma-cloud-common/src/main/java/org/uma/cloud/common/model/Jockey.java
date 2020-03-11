package org.uma.cloud.common.model;

import lombok.Getter;
import org.uma.cloud.common.code.EastOrWestBelongCode;
import org.uma.cloud.common.code.JockeyApprenticeCode;
import org.uma.cloud.common.code.JockeyLicenseCode;
import org.uma.cloud.common.recordSpec.RecordSpec;

import java.time.LocalDate;

/**
 * {@link RecordSpec.KS}
 */
@Getter
public class Jockey extends BaseModel {

    /**
     * 騎手コード 5桁
     */
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

    /**
     * 騎手の所属厩舎 => 調教師コード
     * フリー騎手    => 00000
     */
    private String belongingTrainerCd;
    private String belongingTrainerNameShort;

}
