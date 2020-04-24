package org.uma.cloud.batch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.json.JacksonJsonObjectMarshaller;
import org.springframework.batch.item.json.builder.JsonFileItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.FileSystemResource;
import org.uma.cloud.common.model.BloodAncestry;
import org.uma.cloud.common.model.BloodBreeding;
import org.uma.cloud.common.model.BloodLine;
import org.uma.cloud.common.model.Breeder;
import org.uma.cloud.common.model.Course;
import org.uma.cloud.common.model.Jockey;
import org.uma.cloud.common.model.Owner;
import org.uma.cloud.common.model.RaceHorse;
import org.uma.cloud.common.model.RacingDetail;
import org.uma.cloud.common.model.RacingHorseDetail;
import org.uma.cloud.common.model.RacingHorseExclusion;
import org.uma.cloud.common.model.RacingRefund;
import org.uma.cloud.common.model.RacingVote;
import org.uma.cloud.common.model.Trainer;
import org.uma.cloud.common.model.odds.Exacta;
import org.uma.cloud.common.model.odds.Quinella;
import org.uma.cloud.common.model.odds.QuinellaPlace;
import org.uma.cloud.common.model.odds.Trifecta;
import org.uma.cloud.common.model.odds.Trio;
import org.uma.cloud.common.model.odds.WinsShowBracketQ;
import org.uma.cloud.common.utils.lang.JacksonUtil;

@Profile("local")
@Slf4j
@Configuration
public class JvLInkWritersLocal {

    private static final String URI = "/Users/m-kakiuchi/mydata/json/";


    private <T> ItemWriter<T> createJsonWriter(Class<T> baseModel, String filename) {
        JacksonJsonObjectMarshaller<T> jacksonJsonObjectMarshaller = new JacksonJsonObjectMarshaller<>();
        jacksonJsonObjectMarshaller.setObjectMapper(JacksonUtil.getJvLinkObjectMapper());

        return new JsonFileItemWriterBuilder<T>()
                .jsonObjectMarshaller(jacksonJsonObjectMarshaller)
                .resource(new FileSystemResource(URI + filename))
                .name(baseModel.getSimpleName())
                .build();
    }


    @Bean
    public ItemWriter<BloodAncestry> bloodAncestryItemWriter() {
        return createJsonWriter(BloodAncestry.class, "BLOD_BT.json");
    }

    @Bean
    public ItemWriter<Breeder> breederItemWriter() {
        return createJsonWriter(Breeder.class, "DIFF_BR.json");
    }

    @Bean
    public ItemWriter<BloodBreeding> bloodBreedingItemWriter() {
        return createJsonWriter(BloodBreeding.class, "BLOD_HN.json");
    }

    @Bean
    public ItemWriter<Course> courseItemWriter() {
        return createJsonWriter(Course.class, "COMM_CS.json");
    }

    @Bean
    public ItemWriter<RacingHorseDetail> racingHorseDetailItemWriter() {
        return createJsonWriter(RacingHorseDetail.class, "RACE_SE.json");
    }

    @Bean
    public ItemWriter<Jockey> jockeyItemWriter() {
        return createJsonWriter(Jockey.class, "DIFF_KS.json");
    }

    @Bean
    public ItemWriter<BloodLine> bloodLineItemWriter() {
        return createJsonWriter(BloodLine.class, "BLOD_SK.json");
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
    public ItemWriter<RacingHorseExclusion> racingHorseExclusionItemWriter() {
        return createJsonWriter(RacingHorseExclusion.class, "RACE_JG.json");
    }

    @Bean
    public ItemWriter<RacingRefund> racingRefundItemWriter() {
        return createJsonWriter(RacingRefund.class, "RACE_HR.json");
    }

    @Bean
    public ItemWriter<RacingDetail> racingDetailItemWriter() {
        return createJsonWriter(RacingDetail.class, "RACE_RA.json");
    }

    @Bean
    public ItemWriter<Trainer> trainerItemWriter() {
        return createJsonWriter(Trainer.class, "DIFF_CH.json");
    }

    @Bean
    public ItemWriter<RacingVote> racingVoteItemWriter() {
        return createJsonWriter(RacingVote.class, "RACE_H1.json");
    }

    @Bean
    public ItemWriter<WinsShowBracketQ> winsShowBracketQItemWriter() {
        return createJsonWriter(WinsShowBracketQ.class, "RACE_O1.json");
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
