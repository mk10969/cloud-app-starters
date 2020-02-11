package org.uma.cloud.stream.source;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;


@ConfigurationProperties("test.source")
@Validated
public class TestStreamSourceProperties {

    /**
     * 現在から設定した日付までのデータをセットアップ。
     */
    private String yyyyMMdd = "20190101";

    /**
     * セットアップデータ種類（選択可能）
     */
    private boolean RACE = true;

    private boolean DIFF = true;

    private boolean BLOD = true;

    private boolean COMM = true;

    public String getYyyyMMdd() {
        return this.yyyyMMdd;
    }

    public boolean isRACE() {
        return this.RACE;
    }

    public boolean isDIFF() {
        return this.DIFF;
    }

    public boolean isBLOD() {
        return this.BLOD;
    }

    public boolean isCOMM() {
        return this.COMM;
    }

    public void setYyyyMMdd(String yyyyMMdd) {
        this.yyyyMMdd = yyyyMMdd;
    }

    public void setRACE(boolean RACE) {
        this.RACE = RACE;
    }

    public void setDIFF(boolean DIFF) {
        this.DIFF = DIFF;
    }

    public void setBLOD(boolean BLOD) {
        this.BLOD = BLOD;
    }

    public void setCOMM(boolean COMM) {
        this.COMM = COMM;
    }
}
