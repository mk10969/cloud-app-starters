package org.uma.cloud.task;


import io.fabric8.kubernetes.api.model.ContainerState;
import io.fabric8.kubernetes.api.model.ContainerStateTerminated;
import io.fabric8.kubernetes.client.Config;
import io.fabric8.kubernetes.client.ConfigBuilder;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.task.configuration.EnableTask;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;


@Slf4j
@EnableTask
@Configuration
@EnableConfigurationProperties(PodCleanerProperties.class)
public class PodCleanerConfiguration {

    @Autowired
    private PodCleanerProperties podCleanerProperties;

    @Bean
    public KubernetesClient kubernetesClient() {
        Config config = new ConfigBuilder()
                .withMasterUrl(podCleanerProperties.getKubeMasterUrl())
                .build();

        return new DefaultKubernetesClient(config);
    }

    @Bean
    public CommandLineRunner task(KubernetesClient kubernetesClient) {
        return args -> {
            kubernetesClient.pods().inNamespace("default").list().getItems()
                    .stream()
                    .flatMap(pod -> pod.getStatus().getContainerStatuses()
                            .stream()
                            .map(containerStatus ->
                                    Pair.with(pod.getMetadata().getName(), containerStatus.getState())))
                    .peek(pair -> log.info(pair.getName() + ":" + pair.getContainerState()))
                    .forEach(pair -> {
                        ContainerStateTerminated terminated = pair.getContainerState().getTerminated();

                        // running or waiting
                        if (terminated == null) {
                            log.info("deleteするpodが、ありませんでした。");
                            return;
                        }
                        // 完了している。かつ。1日経過していれば、削除対象
                        if ("Completed".equals(terminated.getReason())
                                && isBeforeOneDay(terminated.getFinishedAt())) {
                            boolean delete = kubernetesClient.pods()
                                    .inNamespace("default")
                                    .withName(pair.getName())
                                    .delete();
                            if (delete) {
                                log.info(pair.getName() + ": deleteしました。");
                            } else {
                                log.error(pair.getName() + ": deleteできませんでした。");
                            }
                        }
                    });
        };
    }

    /**
     * nowから、1日経過しているかチェック
     */
    public static boolean isBeforeOneDay(String date) {
        OffsetDateTime dateTime = OffsetDateTime.parse(date);
        OffsetDateTime now = OffsetDateTime.now(ZoneOffset.UTC);
        return now.isBefore(dateTime);
    }


    private static class Pair {

        private final String name;
        private final ContainerState containerState;

        public Pair(String name, ContainerState containerState) {
            this.name = name;
            this.containerState = containerState;
        }

        public static Pair with(String name, ContainerState containerState) {
            return new Pair(name, containerState);
        }

        public String getName() {
            return this.name;
        }

        public ContainerState getContainerState() {
            return this.containerState;
        }
    }


}
