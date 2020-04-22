package org.uma.cloud.common.model.event;

import lombok.Getter;
import org.uma.cloud.common.code.RaceCourseCode;
import org.uma.cloud.common.model.BaseModel;
import org.uma.cloud.common.recordSpec.RecordSpec;

import java.time.LocalDate;

/**
 * {@link RecordSpec.WE}
 */
@Getter
public class Weather extends BaseModel {

    ///// TODO このデータを蓄積するかどうか /////
    /**
     * 主キー
     * 天候馬場状態専用キー {@link Weather.changeId}までの連結文字列
     */
    private String weatherId;

    private LocalDate holdingDate;

    private RaceCourseCode courseCd;

    private Integer holdingNo;

    private Integer holdingDay;

    private String announceDate;

    /**
     * 変更識別: 1:天候馬場初期状態  2:天候変更  3:馬場状態変更
     * <p>
     * 1:初期状態の時は天候・馬場ともに有効値を設定。
     * 2:天候変更の時は天候(変更後・変更前)のみ有効値を設定。(馬場は初期値)
     * 3:馬場状態変更の時は馬場(変更後・変更前)のみ有効値を設定。(天候は初期値)
     */
    private Integer changeId;

    private Integer weatherNow;

    private Integer turfNow;

    private Integer dirtNow;

    private Integer weatherBefere;

    private Integer turfBefore;

    private Integer dirtBefore;

}
