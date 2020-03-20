package org.uma.cloud.batch.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import io.r2dbc.postgresql.codec.Json;
import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.connectionfactory.R2dbcTransactionManager;
import org.springframework.lang.NonNull;
import org.springframework.transaction.ReactiveTransactionManager;
import org.uma.cloud.common.model.RacingDetails;
import org.uma.cloud.common.utils.lang.ModelUtil;

import java.util.ArrayList;
import java.util.List;


@Configuration
public class PostgresR2dbcConfiguration extends AbstractR2dbcConfiguration {

    @Bean
    public ReactiveTransactionManager reactiveTransactionManager() {
        return new R2dbcTransactionManager(connectionFactory());
    }

    @NonNull
    @Override
    public ConnectionFactory connectionFactory() {
        ConnectionFactoryOptions options = ConnectionFactoryOptions.builder()
                .option(ConnectionFactoryOptions.DRIVER, "postgresql")
                .option(ConnectionFactoryOptions.HOST, "localhost")
                .option(ConnectionFactoryOptions.PORT, 5432)
                .option(ConnectionFactoryOptions.DATABASE, "postgres")
                .option(ConnectionFactoryOptions.USER, "postgres")
                .option(ConnectionFactoryOptions.PASSWORD, "secret")
                .build();
        return ConnectionFactories.get(options);
    }

    @NonNull
    @Override
    protected List<Object> getCustomConverters() {
        List<Object> converters = new ArrayList<>();
        converters.add(new cornerToJsonConverter());
        converters.add(new JsonToCornerConverter());

        return converters;
    }

    /**
     * ダメだった。。。JDBCにするか。。
     */
    @WritingConverter
    public static class cornerToJsonConverter implements Converter<RacingDetails.CornerPassageRank, Json> {
        @Override
        public Json convert(RacingDetails.CornerPassageRank source) {
            System.out.println("json: " + source);
            return writeJson(source);
        }
    }

    @ReadingConverter
    public static class JsonToCornerConverter implements Converter<Json, RacingDetails.CornerPassageRank> {
        @Override
        public RacingDetails.CornerPassageRank convert(Json json) {
            return readJson(json, new TypeReference<>() {
            });
        }
    }

//
//
//    @WritingConverter
//    public static class MapToJsonConverter implements Converter<Map<String, Object>, Json> {
//        @Override
//        public Json convert(Map<String, Object> source) {
//            return writeJson(source);
//        }
//    }
//
//    @ReadingConverter
//    public static class JsonToMapConverter implements Converter<Json, Map<String, Object>> {
//        @Override
//        public Map<String, Object> convert(Json json) {
//            return readJson(json, new TypeReference<>() {
//            });
//        }
//    }


    public static Json writeJson(Object object) {
        try {
            return Json.of(ModelUtil.getObjectMapper().writeValueAsString(object));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T readJson(Json json, TypeReference<T> TypeReference) {
        try {
            return ModelUtil.getObjectMapper().readValue(json.asString(), TypeReference);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
