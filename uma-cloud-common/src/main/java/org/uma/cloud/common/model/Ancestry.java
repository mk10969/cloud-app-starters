package org.uma.cloud.common.model;

import lombok.Getter;
import lombok.Setter;
import org.uma.cloud.common.recordSpec.RecordSpec;

/**
 * {@link RecordSpec.BT}
 */
@Getter
@Setter
public class Ancestry extends BaseModel {

    /**
     * 繁殖登録番号 8桁
     */
    private Integer breedingNo;

    private String ancestryId;
    private String ancestryName;
    private String ancestryDescription;

}
