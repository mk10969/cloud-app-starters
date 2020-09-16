package org.uma.cloud.common.model.jvlink;

import lombok.Getter;
import org.uma.cloud.common.model.JvLinkBase;

@Getter
public class DIFF_BR extends JvLinkBase {
    private String dataDiv;
    private Integer breederCd;
    private String breederNameWithCorp;
    private String breederNameWithoutCorp;
    private String breederNameHalfKana;
    private String breederNameEng;
    private String breederHomeAffairName;
}