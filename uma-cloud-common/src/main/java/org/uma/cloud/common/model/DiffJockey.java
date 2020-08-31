package org.uma.cloud.common.model;

import lombok.Getter;
import org.uma.cloud.common.code.EastOrWestBelongCode;
import org.uma.cloud.common.code.JockeyApprenticeCode;
import org.uma.cloud.common.code.JockeyLicenseCode;
import org.uma.cloud.common.code.RecordSpec;

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
public class DiffJockey extends BaseModel {

    /**
     * データ区分
     */
    @Column(length = 1, nullable = false)
    private String dataDiv;

    /**
     * 騎手コード 5桁
     */
    @Id
    @Column(nullable = false)
    private Integer jockeyCd;

    @Column(nullable = false)
    private Boolean isJockeyErase;

    @Column(nullable = false)
    private LocalDate jockeyLicenseIssueDate;

    /**
     * Nullable
     */
    private LocalDate jockeyLicenseEraseDate;

    /**
     * Nullable
     */
    private LocalDate birthDate;

    @Column(length = 34, nullable = false)
    private String jockeyName;

    @Column(length = 34, nullable = false)
    private String spare1;

    @Column(length = 30, nullable = false)
    private String jockeyNameHalfKana;

    @Column(length = 8, nullable = false)
    private String jockeyNameShort;

    @Column(length = 80, nullable = false)
    private String jockeyNameEng;

    @Column(nullable = false)
    private Integer jockeySex;

    @Column(length = 5, nullable = false)
    private JockeyLicenseCode jockeyLicenseCd;

    @Column(length = 4, nullable = false)
    private JockeyApprenticeCode jockeyApprenticeCd;

    @Column(length = 4, nullable = false)
    private EastOrWestBelongCode ewBelongCd;

    @Column(length = 20, nullable = false)
    private String invitationAreaName;

    /**
     * 騎手の所属厩舎 => 調教師コード
     * フリー騎手    => 00000
     */
    @Column(length = 5, nullable = false)
    private String belongingTrainerCd;

    @Column(length = 8, nullable = false)
    private String belongingTrainerNameShort;

}
