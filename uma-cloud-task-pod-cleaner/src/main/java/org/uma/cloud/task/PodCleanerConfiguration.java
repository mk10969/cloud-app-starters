package org.uma.cloud.task;


import io.fabric8.kubernetes.api.model.ContainerState;
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
    public Config kubenetesClientConfig() {
        return new ConfigBuilder()
                .withMasterUrl(podCleanerProperties.getKubeMasterUrl())
                .build();
    }

    @Bean
    public CommandLineRunner task(Config config) {
        return args -> {

            // autoClosable
            try (final KubernetesClient client = new DefaultKubernetesClient(config)) {
                client.pods().inNamespace("default").list().getItems()
                        .stream()
                        .flatMap(pod -> pod.getStatus().getContainerStatuses()
                                .stream()
                                .map(containerStatus -> MyPod.with(pod.getMetadata().getName(), containerStatus.getState())))
                        .peek(MyPod::log)
                        .filter(MyPod::isTerminated)
                        .forEach(myPod -> {
                            // 完了 かつ 1日前の Podを削除する。
                            if (myPod.isCompleterd() && myPod.isBeforeOneDay()) {
                                boolean delete = client.pods().inNamespace("default").withName(myPod.getName()).delete();

                                if (delete) {
                                    log.info(myPod.getName() + ": deleteしました。");
                                } else {
                                    log.error(myPod.getName() + ": deleteできませんでした。");
                                }
                            }

                            log.info("deleteするpodが、ありませんでした。");
                        });
            }
        };
    }


    private static class MyPod {

        private final String name;
        private final ContainerState containerState;

        public MyPod(String name, ContainerState containerState) {
            this.name = name;
            this.containerState = containerState;
        }

        public static MyPod with(String name, ContainerState containerState) {
            return new MyPod(name, containerState);
        }

        public String getName() {
            return this.name;
        }

        public ContainerState getContainerState() {
            return this.containerState;
        }

        public boolean isTerminated() {
            return this.containerState.getTerminated() != null;
        }

        public void log() {
            log.info(this.name + ":" + this.containerState);
        }

        public boolean isCompleterd() {
            if (isTerminated()) {
                return "Completed".equals(this.containerState.getTerminated().getReason());
            }
            return false;
        }

        public boolean isBeforeOneDay() {
            if (isTerminated()) {
                return beforeOneDayCheck(this.containerState.getTerminated().getFinishedAt());
            }
            return false;
        }

        /**
         * nowから、1日経過しているかチェック
         */
        public static boolean beforeOneDayCheck(String date) {
            OffsetDateTime dateTime = OffsetDateTime.parse(date);
            OffsetDateTime now = OffsetDateTime.now(ZoneOffset.UTC).minusDays(1L);
            return now.isBefore(dateTime);
        }
    }


}
