package org.uma.cloud.stream.processor;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;


@ConfigurationProperties("processor.jvlink")
@Validated
public class JvLinkTransformProperties {

    /**
     * "RA", "SE", "HR", "H1", "O1", "O2", "JG", "SK", "BT", "HN", "CS",
     * "UM", "KS", "CH", "BR", "BN", "WH", "WE", "AV", "JC", "TC"
     */
    private String recordSpec = "RA";

    public String getRecordSpec() {
        return this.recordSpec;
    }

    public void setRecordSpec(String recordSpec) {
        this.recordSpec = recordSpec;
    }
}
