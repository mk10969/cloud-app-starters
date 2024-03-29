package org.uma.cloud.common.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.uma.cloud.common.code.EastOrWestBelongCode;
import org.uma.cloud.common.code.JockeyApprenticeCode;
import org.uma.cloud.common.code.JockeyLicenseCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
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


    @Override
    public Object getPrimaryKey() {
        return this.jockeyCd;
    }
}
