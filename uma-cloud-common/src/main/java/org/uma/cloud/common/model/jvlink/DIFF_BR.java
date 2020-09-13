package org.uma.cloud.common.model.jvlink;

import lombok.Getter;
import org.uma.cloud.common.model.BaseJvLink;

@Getter
public class DIFF_BR extends BaseJvLink {
    private String dataDiv;
    private Integer breederCd;
    private String breederNameWithCorp;
    private String breederNameWithoutCorp;
    private String breederNameHalfKana;
    private String breederNameEng;
    private String breederHomeAffairName;
}