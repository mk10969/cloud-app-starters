package org.uma.cloud.batch;


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

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.stream.Stream;

@Configuration
public class JvLinkReaders {

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private JvLinkBatchProperties properties;

    private String readerName;

    private Resource resource;


    public String getReaderName() {
        return this.readerName;
    }

    public Resource getResource() {
        return this.resource;
    }


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

    private IteratorItemReader<String> httpReader() throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .connectTimeout(Duration.ofSeconds(5))
                .build();

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(resource.getURI()).GET().build();

        HttpResponse<Stream<String>> response = httpClient
                .send(httpRequest, HttpResponse.BodyHandlers.ofLines());

        if (response.statusCode() != 200) {
            throw new RuntimeException("Status Code: [" + response.statusCode() + "]" +
                    " データの読み込みに失敗しました。");
        }

        return new IteratorItemReader<>(response.body().iterator());
    }

}
