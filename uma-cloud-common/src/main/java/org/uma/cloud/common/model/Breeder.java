package org.uma.cloud.common.model;

import lombok.Getter;
import org.uma.cloud.common.recordSpec.RecordSpec;

/**
 * {@link RecordSpec.BR}
 */
@Getter
public class Breeder extends BaseModel {

    /**
     * 生産者コード 6桁
     */
    private Integer breederCd;
    private String breederNameWithCorp;
    private String breederNameWithoutCorp;
    private String breederNameHalfKana;
    private String breederNameEng;
    private String breederHomeAffairName;

}
