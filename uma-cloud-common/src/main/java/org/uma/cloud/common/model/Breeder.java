package org.uma.platform.common.model;

import lombok.Data;
import org.uma.platform.common.config.spec.RecordSpec;

import java.time.LocalDate;

/**
 * {@link RecordSpec.BR}
 */

@Data
public class Breeder {

    private RecordSpec recordType;
    private String dataDiv;
    private LocalDate dataCreateDate;

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
