package org.uma.cloud.common.model;

import lombok.Getter;
import org.uma.cloud.common.recordSpec.RecordSpec;

/**
 * {@link RecordSpec.BN}
 */
@Getter
public class Owner extends BaseModel {

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
