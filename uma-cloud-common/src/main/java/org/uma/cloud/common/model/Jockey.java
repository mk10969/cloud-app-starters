package org.uma.cloud.common.model;

import lombok.Getter;
import org.uma.cloud.common.code.EastOrWestBelongCode;
import org.uma.cloud.common.code.JockeyApprenticeCode;
import org.uma.cloud.common.code.JockeyLicenseCode;
import org.uma.cloud.common.recordSpec.RecordSpec;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

/**
 * {@link RecordSpec.KS}
 */
@Getter
@Entity
@Table
public class Jockey extends BaseModel {

    /**
     * 騎手コード 5桁
     */
    @Id
    private Integer jockeyCd;

    private Boolean isJockeyErase;
    private LocalDate jockeyLicenseIssueDate;
    private LocalDate jockeyLicenseEraseDate;
    private LocalDate birthDate;

    @Column(length = 34)
    private String jockeyName;

    @Column(length = 34)
    private String spare1;

    @Column(length = 30)
    private String jockeyNameHalfKana;

    @Column(length = 8)
    private String jockeyNameShort;

    @Column(length = 80)
    private String jockeyNameEng;

    private Integer jockeySex;
    private JockeyLicenseCode jockeyLicenseCd;
    private JockeyApprenticeCode jockeyApprenticeCd;
    private EastOrWestBelongCode ewBelongCd;

    @Column(length = 20)
    private String invitationAreaName;

    /**
     * 騎手の所属厩舎 => 調教師コード
     * フリー騎手    => 00000
     */
    @Column(length = 5)
    private String belongingTrainerCd;

    @Column(length = 8)
    private String belongingTrainerNameShort;

}
