package org.uma.cloud.stream.function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.uma.cloud.common.configuration.JvLinkFunction;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Objects;


@Component
public class JvLinkRestTemplate {

    @Autowired
    private JvLinkFunction jvLinkFunction;


    //    @PostConstruct
    public void init() throws URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();

        URI uri = new URI("http://192.168.56.104:8888/timeseries/quinella");
//        String raceId = "2020032009010911";
        String raceId = "2020031506010911";


        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uri.toString())
                .queryParam("raceId", raceId);


        ResponseEntity<String[]> responseEntity = restTemplate.getForEntity(builder.toUriString(), String[].class);

        Arrays.stream(Objects.requireNonNull(responseEntity.getBody()))
                .map(jvLinkFunction.decode().andThen(jvLinkFunction::quinellaFunction))
                .forEach(System.out::println);

//        System.out.println("合計:" + count);
    }


//    public List<String> getRaceId() throws URISyntaxException {
//
//        RestTemplate restTemplate = new RestTemplate();
//        URI uri = new URI("Http://192.168.56.104:8888/racingDetails/thisWeek");
//
//        ResponseEntity<List<String>> responseEntity = restTemplate
//                .exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<>() {
//                });
//
//        return Objects.requireNonNull(responseEntity.getBody())
//                .stream()
//                .map(jvLinkFunction.decode().andThen(jvLinkFunction::racingDetailsFunction))
//                .map(RacingDetails::getRaceId)
//                .peek(System.out::println)
//                .collect(Collectors.toList());
//    }

}
