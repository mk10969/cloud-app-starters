package org.uma.cloud.common.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.uma.cloud.common.code.EastOrWestBelongCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
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

    @Column(nullable = false)
    private Integer trainerSex;

    @Column(length = 4, nullable = false)
    private EastOrWestBelongCode ewBelongCd;

    @Column(length = 20, nullable = false)
    private String invitationAreaName;


    @Override
    public Object getPrimaryKey() {
        return this.trainerCd;
    }
}
