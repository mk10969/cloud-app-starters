package org.uma.cloud.job;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties("job.jvlink.batch")
@Validated
public class JvLinkBatchProperties {

    /**
     * input resource
     * ex: file:///tmp/xxx.txt or Http://localhost:8080/xxxxx
     */
    private String inputPath = "file:///Users/m-kakiuchi/mydata/once.txt";


    public String getInputPath() {
        return this.inputPath;
    }

    public void setInputPath(String inputPath) {
        this.inputPath = inputPath;
    }
}
