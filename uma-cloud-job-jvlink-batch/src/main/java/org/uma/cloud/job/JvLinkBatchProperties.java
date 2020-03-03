package org.uma.cloud.job;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties("job.jvlink.batch")
@Validated
public class JvLinkBatchProperties {

    /**
     * mongodb host
     */
    private String host = "localhost";

    /**
     * mongodb port
     */
    private int port = 27017;

//    /**
//     * mongodb user
//     */
//    private String user = "admin";
//
//    /**
//     * mongodb password
//     */
//    private String password = "password";

    /**
     * mongodb database name
     */
    private String databaseName = "uma";

    /**
     * input resource
     * ex: file:///tmp/xxx.txt or Http://localhost:8080/xxxxx
     */
    private String inputPath = "file:///Users/m-kakiuchi/mydata/once.txt";


    public String connectionString() {
//        return "mongodb://" + this.user + ":" + this.password + "@" + this.host + ":" + this.port;
        return "mongodb://" + this.host + ":" + this.port;
    }

    public String getHost() {
        return this.host;
    }

    public int getPort() {
        return this.port;
    }

//    public String getUser() {
//        return this.user;
//    }
//
//    public String getPassword() {
//        return this.password;
//    }

    public String getDatabaseName() {
        return this.databaseName;
    }

    public String getInputPath() {
        return this.inputPath;
    }


    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(int port) {
        this.port = port;
    }

//    public void setUser(String user) {
//        this.user = user;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public void setInputPath(String inputPath) {
        this.inputPath = inputPath;
    }
}
