package org.uma.cloud.common.model.jvlink;

import lombok.Getter;
import org.uma.cloud.common.model.JvLinkBase;

@Getter
public class BLOD_BT extends JvLinkBase {
    private String dataDiv;
    private Integer breedingNo;
    private String ancestryId;
    private String ancestryName;
    private String ancestryDescription;
}