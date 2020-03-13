package org.uma.cloud.job.r2dbc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.r2dbc.postgresql.codec.Json;
import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Configuration
@RequiredArgsConstructor
public class PostgresR2dbcConfuguration extends AbstractR2dbcConfiguration {

    private final ObjectMapper objectMapper;


    @Override
    public ConnectionFactory connectionFactory() {
        ConnectionFactoryOptions options = ConnectionFactoryOptions.builder()
                .option(ConnectionFactoryOptions.HOST, "locahost")
                .option(ConnectionFactoryOptions.PORT, 5432)
                .build();

        return ConnectionFactories.get(options);
    }

    @Override
    protected List<Object> getCustomConverters() {
        List<Object> converters = new ArrayList<>();
        converters.add(jsonMapConverter);


        return converters;
    }


    // functional で書くか。。。class定義するか。。。ですね。
    private final Converter<Json, Map<String, Object>> jsonMapConverter = json -> {
        try {
            return objectMapper.readValue(json.toString(), new TypeReference<Map<String, Object>>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    };
}
