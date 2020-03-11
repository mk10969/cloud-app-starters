package org.uma.cloud.common.model;

import lombok.Getter;
import org.uma.cloud.common.recordSpec.RecordSpec;

/**
 * {@link RecordSpec.BT}
 */
@Getter
public class Ancestry extends BaseModel {

    /**
     * 繁殖登録番号 8桁
     */
    private Integer breedingNo;

    private String ancestryId;
    private String ancestryName;
    private String ancestryDescription;

}
