package org.uma.cloud.common.model.jvlink;

import lombok.Getter;
import org.uma.cloud.common.code.BreedCode;
import org.uma.cloud.common.code.HairColorCode;
import org.uma.cloud.common.code.SexCode;
import org.uma.cloud.common.model.JvLinkBase;

@Getter
public class BLOD_HN extends JvLinkBase {
    private String dataDiv;
    private Integer breedingNo;
    private String spare1;
    private Long bloodlineNo;
    private String spare2;
    private String horseName;
    private String horseNameHalfKana;
    private String horseNameEng;
    private Integer birthYear;
    private SexCode sexCd;
    private BreedCode breedCd;
    private HairColorCode hairColorCd;
    private Integer breedingHorseBringingDiv;
    private Integer importYear;
    private String sourceName;
    private Integer breedingNoFather;
    private Integer breedingNoMother;
}