package org.uma.jvLink.server.configuration;

import com.google.common.collect.Sets;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Set;


@Profile("setup")
@Data
@Configuration
@ConfigurationProperties(prefix = "setup")
public class SetupConfiguration {

    /**
     * 現在から設定した日付までのデータをセットアップする。
     */
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private LocalDateTime yyyyMMddHHmmss;

    /**
     * セットアップデータ種類（選択可能）
     */
    private Set<String> dataTypes = Sets.newHashSet();


}
