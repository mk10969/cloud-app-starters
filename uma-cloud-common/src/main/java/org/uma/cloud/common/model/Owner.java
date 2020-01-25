package org.uma.platform.common.model;

import lombok.Data;
import org.uma.platform.common.config.spec.RecordSpec;

import java.time.LocalDate;

/**
 * {@link RecordSpec.BN}
 */

@Data
public class Owner {

    private RecordSpec recordType;
    private String dataDiv;
    private LocalDate dataCreateDate;

    /**
     * 馬主コード 6桁
     */
    private Integer ownerCd;
    private String ownerNameWithCorp;
    private String ownerNameWithoutCorp;
    private String ownerNameHalfKana;
    private String ownerNameEng;
    private String clothingMark;

}
