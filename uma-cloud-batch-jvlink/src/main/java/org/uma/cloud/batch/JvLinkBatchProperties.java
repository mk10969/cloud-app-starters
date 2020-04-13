package org.uma.cloud.batch;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties("uma.batch.jvlink")
@Validated
public class JvLinkBatchProperties {

    /**
     * Java Object変換先のモデル名
     */
    @Getter
    @Setter
    private String modelName = "course";

    /**
     * input resource
     * ex: file:///tmp/xxx.txt or Http://localhost:8080/xxxxx
     */
    @Getter
    @Setter
    private String inputPath = "file:///Users/m-kakiuchi/mydata/data/COMM_CS.txt";

}
