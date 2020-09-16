package org.uma.cloud.common.model.jvlink;

import lombok.Getter;
import org.uma.cloud.common.model.JvLinkBase;

@Getter
public class DIFF_BN extends JvLinkBase {
    private String dataDiv;
    private Integer ownerCd;
    private String ownerNameWithCorp;
    private String ownerNameWithoutCorp;
    private String ownerNameHalfKana;
    private String ownerNameEng;
    private String clothingMark;
}