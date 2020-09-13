package org.uma.cloud.common.model.jvlink;

import lombok.Getter;
import org.uma.cloud.common.code.BreedCode;
import org.uma.cloud.common.code.HairColorCode;
import org.uma.cloud.common.code.SexCode;
import org.uma.cloud.common.model.BaseJvLink;

import java.time.LocalDate;
import java.util.List;

@Getter
public class BLOD_SK extends BaseJvLink {
    private String dataDiv;
    private Long bloodlineNo;
    private LocalDate birthDate;
    private SexCode sexCd;
    private BreedCode breedCd;
    private HairColorCode hairColorCd;
    private Integer sonBringingDiv;
    private Integer importYear;
    private Integer breederCd;
    private String sourceName;
    private List<Integer> breeding3rd;
}