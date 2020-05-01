package org.uma.cloud.stream.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import org.uma.cloud.stream.StreamFunctionProperties;
import org.uma.cloud.stream.model.ResponseMessage;
import reactor.core.publisher.Mono;

import java.util.function.Function;
import java.util.function.Predicate;

@Configuration
public class WebClientConfiguration {

    @Autowired
    private StreamFunctionProperties properties;


    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl(properties.getJvLinkWebUrl())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .filter(statusError(HttpStatus::is4xxClientError, WebClientConfiguration::is4xxErrorFunction))
                .filter(statusError(HttpStatus::is5xxServerError, WebClientConfiguration::is5xxErrorFunction))
                .build();
    }


    public static ExchangeFilterFunction statusError(
            Predicate<HttpStatus> statusPredicate,
            Function<ClientResponse, Mono<? extends Throwable>> exceptionFunction) {

        Assert.notNull(statusPredicate, "Predicate must not be null");
        Assert.notNull(exceptionFunction, "Function must not be null");

        return ExchangeFilterFunction.ofResponseProcessor(
                response -> (statusPredicate.test(response.statusCode())
                        ? exceptionFunction.apply(response).flatMap(Mono::error)
                        : Mono.just(response)));
    }

    private static Mono<JvLinkWebClientException> is4xxErrorFunction(ClientResponse clientResponse) {
        return clientResponse.bodyToMono(ResponseMessage.class)
                .map(ResponseMessage::getMessage)
                .map(message -> new JvLinkWebClientException(message, clientResponse.statusCode()));
    }

    private static Mono<JvLinkWebServerException> is5xxErrorFunction(ClientResponse clientResponse) {
        return clientResponse.bodyToMono(ResponseMessage.class)
                .map(ResponseMessage::getMessage)
                .map(message -> new JvLinkWebServerException(message, clientResponse.statusCode()));
    }


    public static class JvLinkWebClientException extends RuntimeException {

        private final HttpStatus httpStatus;

        public JvLinkWebClientException(String message, HttpStatus httpStatus) {
            super(message);
            this.httpStatus = httpStatus;
        }

        public HttpStatus getHttpStatus() {
            return this.httpStatus;
        }
    }

    public static class JvLinkWebServerException extends RuntimeException {

        private final HttpStatus httpStatus;

        public JvLinkWebServerException(String message, HttpStatus httpStatus) {
            super(message);
            this.httpStatus = httpStatus;
        }

        public HttpStatus getHttpStatus() {
            return this.httpStatus;
        }
    }

}
