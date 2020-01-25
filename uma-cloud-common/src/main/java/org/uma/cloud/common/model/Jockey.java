package org.uma.platform.common.model;

import lombok.Data;
import org.uma.platform.common.code.EastOrWestBelongCode;
import org.uma.platform.common.code.JockeyApprenticeCode;
import org.uma.platform.common.code.JockeyLicenseCode;
import org.uma.platform.common.config.spec.RecordSpec;

import java.time.LocalDate;

/**
 * {@link RecordSpec.KS}
 */

@Data
public class Jockey {

    private RecordSpec recordType;
    private String dataDiv;
    private LocalDate dataCreateDate;

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
