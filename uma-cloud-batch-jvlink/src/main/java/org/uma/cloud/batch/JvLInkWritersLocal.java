package org.uma.cloud.batch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.json.JacksonJsonObjectMarshaller;
import org.springframework.batch.item.json.builder.JsonFileItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.FileSystemResource;
import org.uma.cloud.common.model.Ancestry;
import org.uma.cloud.common.model.Breeder;
import org.uma.cloud.common.model.BreedingHorse;
import org.uma.cloud.common.model.Course;
import org.uma.cloud.common.model.HorseRacingDetails;
import org.uma.cloud.common.model.Jockey;
import org.uma.cloud.common.model.Offspring;
import org.uma.cloud.common.model.Owner;
import org.uma.cloud.common.model.RaceHorse;
import org.uma.cloud.common.model.RaceHorseExclusion;
import org.uma.cloud.common.model.RaceRefund;
import org.uma.cloud.common.model.RacingDetails;
import org.uma.cloud.common.model.Trainer;
import org.uma.cloud.common.model.VoteCount;
import org.uma.cloud.common.model.odds.Exacta;
import org.uma.cloud.common.model.odds.Quinella;
import org.uma.cloud.common.model.odds.QuinellaPlace;
import org.uma.cloud.common.model.odds.Trifecta;
import org.uma.cloud.common.model.odds.Trio;
import org.uma.cloud.common.model.odds.WinsPlaceBracketQuinella;
import org.uma.cloud.common.utils.lang.JacksonUtil;

@Profile("local")
@Slf4j
@Configuration
public class JvLInkWritersLocal {

    private static final String URI = "/Users/m-kakiuchi/mydata/json/";


    private <T> ItemWriter<T> createJsonWriter(Class<T> baseModel, String filename) {
        JacksonJsonObjectMarshaller<T> jacksonJsonObjectMarshaller = new JacksonJsonObjectMarshaller<>();
        jacksonJsonObjectMarshaller.setObjectMapper(JacksonUtil.getObjectMapper());

        return new JsonFileItemWriterBuilder<T>()
                .jsonObjectMarshaller(jacksonJsonObjectMarshaller)
                .resource(new FileSystemResource(URI + filename))
                .name(baseModel.getSimpleName())
                .build();
    }


    @Bean
    public ItemWriter<Ancestry> ancestryItemWriter() {
        return createJsonWriter(Ancestry.class, "BLOD_BT.json");
    }

    @Bean
    public ItemWriter<Breeder> breederItemWriter() {
        return createJsonWriter(Breeder.class, "DIFF_BR.json");
    }

    @Bean
    public ItemWriter<BreedingHorse> breedingHorseItemWriter() {
        return createJsonWriter(BreedingHorse.class, "BLOD_HN.json");
    }

    @Bean
    public ItemWriter<Course> courseItemWriter() {
        return createJsonWriter(Course.class, "COMM_CS.json");
    }

    @Bean
    public ItemWriter<HorseRacingDetails> horseRacingDetailsItemWriter() {
        return createJsonWriter(HorseRacingDetails.class, "RACE_SE.json");
    }

    @Bean
    public ItemWriter<Jockey> jockeyItemWriter() {
        return createJsonWriter(Jockey.class, "DIFF_KS.json");
    }

    @Bean
    public ItemWriter<Offspring> offspringItemWriter() {
        return createJsonWriter(Offspring.class, "BLOD_SK.json");
    }

    @Bean
    public ItemWriter<Owner> ownerItemWriter() {
        return createJsonWriter(Owner.class, "DIFF_BN.json");
    }

    @Bean
    public ItemWriter<RaceHorse> raceHorseItemWriter() {
        return createJsonWriter(RaceHorse.class, "DIFF_UM.json");
    }

    @Bean
    public ItemWriter<RaceHorseExclusion> raceHorseExclusionItemWriter() {
        return createJsonWriter(RaceHorseExclusion.class, "RACE_JG.json");
    }

    @Bean
    public ItemWriter<RaceRefund> raceRefundItemWriter() {
        return createJsonWriter(RaceRefund.class, "RACE_HR.json");
    }

    @Bean
    public ItemWriter<RacingDetails> racingDetailsItemWriter() {
        return createJsonWriter(RacingDetails.class, "RACE_RA.json");
    }

    @Bean
    public ItemWriter<Trainer> trainerItemWriter() {
        return createJsonWriter(Trainer.class, "DIFF_CH.json");
    }

    @Bean
    public ItemWriter<VoteCount> voteCountItemWriter() {
        return createJsonWriter(VoteCount.class, "RACE_H1.json");
    }

    @Bean
    public ItemWriter<WinsPlaceBracketQuinella> winsPlaceBracketQuinellaItemWriter() {
        return createJsonWriter(WinsPlaceBracketQuinella.class, "RACE_O1.json");
    }

    @Bean
    public ItemWriter<Quinella> quinellaItemWriter() {
        return createJsonWriter(Quinella.class, "RACE_O2.json");
    }

    @Bean
    public ItemWriter<QuinellaPlace> quinellaPlaceItemWriter() {
        return createJsonWriter(QuinellaPlace.class, "RACE_O3.json");
    }

    @Bean
    public ItemWriter<Exacta> exactaItemWriter() {
        return createJsonWriter(Exacta.class, "RACE_O4.json");
    }

    @Bean
    public ItemWriter<Trio> trioItemWriter() {
        return createJsonWriter(Trio.class, "RACE_O5.json");
    }

    @Bean
    public ItemWriter<Trifecta> trifectaItemWriter() {
        return createJsonWriter(Trifecta.class, "RACE_O6.json");
    }

}
