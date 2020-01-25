package org.uma.jvLink.server.configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.uma.jvLink.core.config.condition.RealTimeOpenCondition;
import org.uma.jvLink.core.config.condition.StoredOpenCondition;

@Configuration
public class JvLinkOpenCondition {

    ///// 蓄積系データ /////

    /**
     * @return レース詳細
     */
    @Bean(name = "RACE_RA")
    StoredOpenCondition config_RACE_RA() {
        return new StoredOpenCondition(RACE, RA);
    }

    /**
     * @return 出走馬詳細
     */
    @Bean(name = "RACE_SE")
    StoredOpenCondition config_RACE_SE() {
        return new StoredOpenCondition(RACE, SE);
    }

    /**
     * @return レース払戻
     */
    @Bean(name = "RACE_HR")
    StoredOpenCondition config_RACE_HR() {
        return new StoredOpenCondition(RACE, HR);
    }

    /**
     * @return 確定票数（3連単以外）
     */
    @Bean(name = "RACE_H1")
    StoredOpenCondition config_RACE_H1() {
        return new StoredOpenCondition(RACE, H1);
    }

    /**
     * @return 確定票数（3連単）
     */
    @Bean(name = "RACE_H6")
    StoredOpenCondition config_RACE_H6() {
        return new StoredOpenCondition(RACE, H6);
    }

    /**
     * @return 確定オッズ（単複枠）
     */
    @Bean(name = "RACE_O1")
    StoredOpenCondition config_RACE_O1() {
        return new StoredOpenCondition(RACE, O1);
    }

    /**
     * @return 確定オッズ（馬連）
     */
    @Bean(name = "RACE_O2")
    StoredOpenCondition config_RACE_O2() {
        return new StoredOpenCondition(RACE, O2);
    }

    /**
     * @return 確定オッズ（ワイド）
     */
    @Bean(name = "RACE_O3")
    StoredOpenCondition config_RACE_O3() {
        return new StoredOpenCondition(RACE, O3);
    }

    /**
     * @return 確定オッズ（馬単）
     */
    @Bean(name = "RACE_O4")
    StoredOpenCondition config_RACE_O4() {
        return new StoredOpenCondition(RACE, O4);
    }

    /**
     * @return 確定オッズ（３連複）
     */
    @Bean(name = "RACE_O5")
    StoredOpenCondition config_RACE_O5() {
        return new StoredOpenCondition(RACE, O5);
    }

    /**
     * @return 確定オッズ（３連単）
     */
    @Bean(name = "RACE_O6")
    StoredOpenCondition config_RACE_O6() {
        return new StoredOpenCondition(RACE, O6);
    }

    /**
     * @return WIN5
     */
    @Bean(name = "RACE_WF")
    StoredOpenCondition config_RACE_WF() {
        return new StoredOpenCondition(RACE, WF);
    }

    /**
     * @return 競走馬除外情報
     */
    @Bean(name = "RACE_JG")
    StoredOpenCondition config_RACE_JG() {
        return new StoredOpenCondition(RACE, JG);
    }

    /**
     * @return 競走馬のマスタの差分
     */
    @Bean(name = "DIFF_UM")
    StoredOpenCondition config_DIFF_UM() {
        return new StoredOpenCondition(DIFF, UM);
    }

    /**
     * @return 騎手マスタの差分
     */
    @Bean(name = "DIFF_KS")
    StoredOpenCondition config_DIFF_KS() {
        return new StoredOpenCondition(DIFF, KS);
    }

    /**
     * @return 調教師マスタの差分
     */
    @Bean(name = "DIFF_CH")
    StoredOpenCondition config_DIFF_CH() {
        return new StoredOpenCondition(DIFF, CH);
    }

    /**
     * @return 生産者マスタの差分
     */
    @Bean(name = "DIFF_BR")
    StoredOpenCondition config_DIFF_BR() {
        return new StoredOpenCondition(DIFF, BR);
    }

    /**
     * @return 馬主マスタの差分
     */
    @Bean(name = "DIFF_BN")
    StoredOpenCondition config_DIFF_BN() {
        return new StoredOpenCondition(DIFF, BN);
    }

    /**
     * @return レコード情報の差分
     */
    @Bean(name = "DIFF_RC")
    StoredOpenCondition config_DIFF_RC() {
        return new StoredOpenCondition(DIFF, RC);
    }

    /**
     * @return 地方、海外レースのレース番組情報
     * <p>
     * セットアップでこの条件は、利用できない
     */
    @Bean(name = "DIFF_RA")
    StoredOpenCondition config_DIFF_RA() {
        return new StoredOpenCondition(DIFF, RA);
    }

    /**
     * @return 地方、海外レースの馬毎レース情報
     * <p>
     * セットアップでこの条件は、利用できない
     */
    @Bean(name = "DIFF_SE")
    StoredOpenCondition config_DIFF_SE() {
        return new StoredOpenCondition(DIFF, SE);
    }

    /**
     * @return 繁殖牝馬マスタ
     */
    @Bean(name = "BLOD_HN")
    StoredOpenCondition config_BLOD_HN() {
        return new StoredOpenCondition(BLOD, HN);
    }

    /**
     * @return 産駒マスタ
     */
    @Bean(name = "BLOD_SK")
    StoredOpenCondition config_BLOD_SK() {
        return new StoredOpenCondition(BLOD, SK);
    }

    /**
     * @return 系統情報
     */
    @Bean(name = "BLOD_BT")
    StoredOpenCondition config_BLOD_BT() {
        return new StoredOpenCondition(BLOD, BT);
    }

    /**
     * データマイニング   →いらない
     * 出走別着度数      →計算可能
     * 坂路調教         →いらない
     * 馬名の由来情報    →いらない
     */

    /**
     * @return 開催スケジュール情報
     */
    @Bean(name = "YSCH_YS")
    StoredOpenCondition config_YSCH_YS() {
        return new StoredOpenCondition(YSCH, YS);
    }

    /**
     * @return 競走馬市場取引価格
     */
    @Bean(name = "HOSE_HS")
    StoredOpenCondition config_HOSE_HS() {
        return new StoredOpenCondition(HOSE, HS);
    }

    /**
     * @return コース情報
     */
    @Bean(name = "COMM_CS")
    StoredOpenCondition config_COMM_CS() {
        return new StoredOpenCondition(COMM, CS);
    }


    ///// リアルタイム系データ /////

    /**
     * @return 成績確定後 レース詳細
     */
    @Bean(name = "0B12_RA")
    RealTimeOpenCondition config_0B12_RA() {
        return new RealTimeOpenCondition(OB12, RA);
    }

    /**
     * @return 成績確定後 馬毎レース情報
     */
    @Bean(name = "0B12_SE")
    RealTimeOpenCondition config_0B12_SE() {
        return new RealTimeOpenCondition(OB12, SE);
    }

    /**
     * @return 成績確定後 払戻
     */
    @Bean(name = "0B12_HR")
    RealTimeOpenCondition config_0B12_HR() {
        return new RealTimeOpenCondition(OB12, HR);
    }

    /**
     * @return 出走馬名表 レース詳細
     */
    @Bean(name = "0B15_RA")
    RealTimeOpenCondition config_0B15_RA() {
        return new RealTimeOpenCondition(OB15, RA);
    }

    /**
     * @return 出走馬名表 馬毎レース情報
     */
    @Bean(name = "0B15_SE")
    RealTimeOpenCondition config_0B15_SE() {
        return new RealTimeOpenCondition(OB15, SE);
    }

    /**
     * @return 出走馬名表 払戻
     */
    @Bean(name = "0B15_HR")
    RealTimeOpenCondition config_0B15_HR() {
        return new RealTimeOpenCondition(OB15, HR);
    }

    /**
     * @return 速報オッズ（単複枠）
     */
    @Bean(name = "0B31_O1")
    RealTimeOpenCondition config_0B31_O1() {
        return new RealTimeOpenCondition(OB31, O1);
    }

    /**
     * @return 速報オッズ（馬連）
     */
    @Bean(name = "0B32_O2")
    RealTimeOpenCondition config_0B32_O2() {
        return new RealTimeOpenCondition(OB32, O2);
    }

    /**
     * @return 速報オッズ（ワイド）
     */
    @Bean(name = "0B33_O3")
    RealTimeOpenCondition config_0B33_O3() {
        return new RealTimeOpenCondition(OB33, O3);
    }

    /**
     * @return 速報オッズ（馬単）
     */
    @Bean(name = "0B34_O4")
    RealTimeOpenCondition config_0B34_O4() {
        return new RealTimeOpenCondition(OB34, O4);
    }

    /**
     * @return 速報オッズ（３連複）
     */
    @Bean(name = "0B35_O5")
    RealTimeOpenCondition config_0B35_O5() {
        return new RealTimeOpenCondition(OB35, O5);
    }

    /**
     * @return 速報オッズ（３連単）
     */
    @Bean(name = "0B36_O6")
    RealTimeOpenCondition config_0B36_O6() {
        return new RealTimeOpenCondition(OB36, O6);
    }

    /**
     * @return 速報票数（３連単以外）
     */
    @Bean(name = "0B20_H1")
    RealTimeOpenCondition config_0B20_H1() {
        return new RealTimeOpenCondition(OB20, H1);
    }

    /**
     * @return 速報票数（３連単）
     */
    @Bean(name = "0B20_H6")
    RealTimeOpenCondition config_0B20_H6() {
        return new RealTimeOpenCondition(OB20, H6);
    }

    /**
     * @return 馬体重
     */
    @Bean(name = "0B11_WH")
    RealTimeOpenCondition config_0B11_WH() {
        return new RealTimeOpenCondition(OB11, WH);
    }

    /**
     * @return 時系列オッズ（単複枠）
     */
    @Bean(name = "0B41_O1")
    RealTimeOpenCondition config_0B41_O1() {
        return new RealTimeOpenCondition(OB41, O1);
    }

    /**
     * @return 時系列オッズ（馬連）
     */
    @Bean(name = "0B42_O2")
    RealTimeOpenCondition config_0B42_O2() {
        return new RealTimeOpenCondition(OB42, O2);
    }

    /**
     * @return WIN5
     */
    @Bean(name = "0B51_WF")
    RealTimeOpenCondition config_0B51_WF() {
        return new RealTimeOpenCondition(OB51, WF);
    }


    ///// イベント通知系データ /////

    /**
     * @return 天候馬場状態
     */
    @Bean(name = "0B16_WE")
    RealTimeOpenCondition config_0B16_WE() {
        return new RealTimeOpenCondition(OB16, WE);
    }

    /**
     * @return 出走取り消し・競争除外
     */
    @Bean(name = "0B16_AV")
    RealTimeOpenCondition config_0B16_AV() {
        return new RealTimeOpenCondition(OB16, AV);
    }

    /**
     * @return 騎手変更
     */
    @Bean(name = "0B16_JC")
    RealTimeOpenCondition config_0B16_JC() {
        return new RealTimeOpenCondition(OB16, JC);
    }

    /**
     * @return 発走時刻変更
     */
    @Bean(name = "0B16_TC")
    RealTimeOpenCondition config_0B16_TC() {
        return new RealTimeOpenCondition(OB16, TC);
    }

    /**
     * @return コース変更
     */
    @Bean(name = "0B16_CC")
    RealTimeOpenCondition config_0B16_CC() {
        return new RealTimeOpenCondition(OB16, CC);
    }


}
