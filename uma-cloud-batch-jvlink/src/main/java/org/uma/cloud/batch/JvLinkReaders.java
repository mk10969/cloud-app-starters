package org.uma.cloud.batch;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.PassThroughLineMapper;
import org.springframework.batch.item.support.IteratorItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.uma.cloud.common.service.business.BusinessBaseDateService;
import org.uma.cloud.common.utils.lang.DateUtil;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Configuration
public class JvLinkReaders {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private JvLinkBatchProperties properties;

    @Autowired
    private BusinessBaseDateService businessBaseDateService;

    // no Getter and Setter
    private String readerName;

    // no Getter and Setter
    private Resource resource;


    @PostConstruct
    void init() {
        String[] level = properties.getInputPath().split("/");
        this.readerName = level[level.length - 1];
        this.resource = resourceLoader.getResource(properties.getInputPath());
    }

    @Bean
    public ItemReader<String> reader() throws IOException, InterruptedException {
        if (resource.isFile()) {
            return fileReader();
        } else {
            return httpReader();
        }
    }

    private FlatFileItemReader<String> fileReader() {
        return new FlatFileItemReaderBuilder<String>()
                .name(readerName)
                .resource(resource)
                .lineMapper(new PassThroughLineMapper()) // mappingせず、lineをそのまま返す。
                .build();
    }

    /**
     * RestTemplate or WebClient 使わないとセンスなくなるw
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    private IteratorItemReader<String> httpReader() throws IOException, InterruptedException {
        // 最新の基準日を取得する
        long baseDate = businessBaseDateService.getLatestBaseDate();
        log.info("BaseDate: {}", DateUtil.toLocalDateTime(baseDate).format(DateTimeFormatter.ofPattern("yyyy/MM/dd")));

        HttpClient httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .connectTimeout(Duration.ofSeconds(5))
                .build();

        URI uri = resourceLoader.getResource(properties.getInputPath() + "/" + baseDate).getURI();

        HttpRequest httpRequest = HttpRequest.newBuilder().GET()
                .uri(uri)
                .build();

        HttpResponse<String> response = httpClient
                .send(httpRequest, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new RuntimeException("Status Code: [" + response.statusCode() + "]" +
                    " データの読み込みに失敗しました。");
        }

        return new IteratorItemReader<>(parse(response.body()));
    }

    private List<String> parse(String json) {
        try {
            return Arrays.stream(objectMapper.readValue(json, String[].class))
                    .collect(Collectors.toList());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
