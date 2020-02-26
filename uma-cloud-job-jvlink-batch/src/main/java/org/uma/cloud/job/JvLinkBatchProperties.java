package org.uma.cloud.job;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties("job.jvlink.batch")
@Validated
public class JvLinkBatchProperties {

    /**
     * mongo connection
     */
    private String mongodb = "mongodb://localhost";

    /**
     * mongo database name
     */
    private String databaseName = "uma";

    /**
     * input resource
     * ex: file:///tmp/xxx.txt or Http://localhost:8080/xxxxx
     */
    private String inputPath = "file:///tmp/once.txt";


    public void setInputPath(String inputPath) {
        this.inputPath = inputPath;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public void setMongodb(String mongodb) {
        this.mongodb = mongodb;
    }

    public String getInputPath() {
        return this.inputPath;
    }

    public String getDatabaseName() {
        return this.databaseName;
    }

    public String getMongodb() {
        return this.mongodb;
    }

}
