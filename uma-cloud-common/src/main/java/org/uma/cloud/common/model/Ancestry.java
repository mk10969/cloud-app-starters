package org.uma.platform.common.model;

import lombok.Data;
import org.uma.platform.common.config.spec.RecordSpec;

import java.time.LocalDate;

/**
 * {@link RecordSpec.BT}
 */

@Data
public class Ancestry {

    private RecordSpec recordType;
    private String dataDiv;
    private LocalDate dataCreateDate;

    /**
     * 繁殖登録番号 8桁
     */
    private Integer breedingNo;

    private String ancestryId;
    private String ancestryName;
    private String ancestryDescription;


}
