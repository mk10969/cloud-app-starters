package org.uma.cloud.common.component;

import lombok.Data;
import lombok.Getter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.ArrayList;
import java.util.List;


@Configuration
@PropertySource(value = "classpath:JvLinkRecord.properties")
public class JvLinkRecordProperties {

    /**
     * @return レース詳細
     */
    @Bean(name = "RA")
    @ConfigurationProperties(prefix = "ra")
    RecordSpecItems configRA() {
        return new RecordSpecItems();
    }

    /**
     * @return 出走馬詳細
     */
    @Bean(name = "SE")
    @ConfigurationProperties(prefix = "se")
    RecordSpecItems configSE() {
        return new RecordSpecItems();
    }

    /**
     * @return レース払戻
     */
    @Bean(name = "HR")
    @ConfigurationProperties(prefix = "hr")
    RecordSpecItems configHR() {
        return new RecordSpecItems();
    }

    /**
     * @return レース票数（単勝・複勝・枠連・馬連・ワイド・馬単・三連複）
     */
    @Bean(name = "H1")
    @ConfigurationProperties(prefix = "h1")
    RecordSpecItems configH1() {
        return new RecordSpecItems();
    }

    /**
     * @return 単勝オッズ・複勝オッズ・枠連オッズ
     */
    @Bean(name = "O1")
    @ConfigurationProperties(prefix = "o1")
    RecordSpecItems configO1() {
        return new RecordSpecItems();
    }

    /**
     * @return 馬連オッズ
     */
    @Bean(name = "O2")
    @ConfigurationProperties(prefix = "o2")
    RecordSpecItems configO2() {
        return new RecordSpecItems();
    }

    /**
     * @return ワイドオッズ
     */
    @Bean(name = "O3")
    @ConfigurationProperties(prefix = "o3")
    RecordSpecItems configO3() {
        return new RecordSpecItems();
    }

    /**
     * @return 馬単オッズ
     */
    @Bean(name = "O4")
    @ConfigurationProperties(prefix = "o4")
    RecordSpecItems configO4() {
        return new RecordSpecItems();
    }

    /**
     * @return 三連複オッズ
     */
    @Bean(name = "O5")
    @ConfigurationProperties(prefix = "o5")
    RecordSpecItems configO5() {
        return new RecordSpecItems();
    }

    /**
     * @return 三連単オッズ
     */
    @Bean(name = "O6")
    @ConfigurationProperties(prefix = "o6")
    RecordSpecItems configO6() {
        return new RecordSpecItems();
    }

    /**
     * @return 競走馬除外情報
     */
    @Bean(name = "JG")
    @ConfigurationProperties(prefix = "jg")
    RecordSpecItems configJG() {
        return new RecordSpecItems();
    }

    /**
     * @return 産駒マスタ
     */
    @Bean(name = "SK")
    @ConfigurationProperties(prefix = "sk")
    RecordSpecItems configSK() {
        return new RecordSpecItems();
    }

    /**
     * @return 系統情報
     */
    @Bean(name = "BT")
    @ConfigurationProperties(prefix = "bt")
    RecordSpecItems configBT() {
        return new RecordSpecItems();
    }

    /**
     * @return 繁殖馬マスタ
     */
    @Bean(name = "HN")
    @ConfigurationProperties(prefix = "hn")
    RecordSpecItems configHN() {
        return new RecordSpecItems();
    }

    /**
     * @return コース情報
     */
    @Bean(name = "CS")
    @ConfigurationProperties(prefix = "cs")
    RecordSpecItems configCS() {
        return new RecordSpecItems();
    }


    /**
     * @return 競走馬マスタ
     */
    @Bean(name = "UM")
    @ConfigurationProperties(prefix = "um")
    RecordSpecItems configUM() {
        return new RecordSpecItems();
    }

    /**
     * @return 騎手マスタ
     */
    @Bean(name = "KS")
    @ConfigurationProperties(prefix = "ks")
    RecordSpecItems configKS() {
        return new RecordSpecItems();
    }

    /**
     * @return 調教師マスタ
     */
    @Bean(name = "CH")
    @ConfigurationProperties(prefix = "ch")
    RecordSpecItems configCH() {
        return new RecordSpecItems();
    }

    /**
     * @return 生産者マスタ
     */
    @Bean(name = "BR")
    @ConfigurationProperties(prefix = "br")
    RecordSpecItems configBR() {
        return new RecordSpecItems();
    }

    /**
     * @return 馬主マスタ
     */
    @Bean(name = "BN")
    @ConfigurationProperties(prefix = "bn")
    RecordSpecItems configBN() {
        return new RecordSpecItems();
    }


    ///////// リアルタイム //////////

    /**
     * @return 馬体重情報
     */
    @Bean(name = "WH")
    @ConfigurationProperties(prefix = "wh")
    RecordSpecItems configWH() {
        return new RecordSpecItems();
    }

    /**
     * @return 天候馬場状態
     */
    @Bean(name = "WE")
    @ConfigurationProperties(prefix = "we")
    RecordSpecItems configWE() {
        return new RecordSpecItems();
    }

    /**
     * @return 出走取消競争除外
     */
    @Bean(name = "AV")
    @ConfigurationProperties(prefix = "av")
    RecordSpecItems configAV() {
        return new RecordSpecItems();
    }

    /**
     * @return 騎手変更
     */
    @Bean(name = "JC")
    @ConfigurationProperties(prefix = "jc")
    RecordSpecItems configJC() {
        return new RecordSpecItems();
    }

    /**
     * @return 発走時刻変更
     */
    @Bean(name = "TC")
    @ConfigurationProperties(prefix = "tc")
    RecordSpecItems configTC() {
        return new RecordSpecItems();
    }


    /**
     * シリアライズデータを、
     * Javaオブジェクト化するためのフォーマットクラス
     * <p>
     * カラム名、開始位置、データ長さ、繰り返しフラグ
     */
    @Getter
    @ToString
    public static class RecordSpecItems {

        private List<RecordItem> recordItems = new ArrayList<>();

        @Data
        public static class RecordItem {

            private String column;

            private int start;

            private int length;

            private int repeat;

        }
    }

}
