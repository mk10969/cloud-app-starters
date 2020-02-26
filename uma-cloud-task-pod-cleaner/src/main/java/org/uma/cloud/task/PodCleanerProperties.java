package org.uma.cloud.task;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties("pod.cleaner")
@Validated
public class PodCleanerProperties {

    /**
     * kube Master Url
     */
    private String kubeMasterUrl = "https://kubernetes.docker.internal:6443";


    public void setKubeMasterUrl(String kubeMasterUrl) {
        this.kubeMasterUrl = kubeMasterUrl;
    }

    public String getKubeMasterUrl() {
        return this.kubeMasterUrl;
    }
}
