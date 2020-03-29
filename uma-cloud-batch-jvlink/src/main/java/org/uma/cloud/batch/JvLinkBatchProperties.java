package org.uma.cloud.batch;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties("job.jvlink.batch")
@Validated
public class JvLinkBatchProperties {

    /**
     * Java Object変換先のモデル名
     */
    private String modelName = "course";

    /**
     * input resource
     * ex: file:///tmp/xxx.txt or Http://localhost:8080/xxxxx
     */
    private String inputPath = "file:///Users/m-kakiuchi/mydata/data/COMM_CS.txt";


    public String getModelName() {
        return this.modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getInputPath() {
        return this.inputPath;
    }

    public void setInputPath(String inputPath) {
        this.inputPath = inputPath;
    }
}
