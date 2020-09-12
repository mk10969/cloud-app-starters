package org.uma.cloud.batch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.json.JacksonJsonObjectMarshaller;
import org.springframework.batch.item.json.builder.JsonFileItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.FileSystemResource;
import org.uma.cloud.common.model.entity.BloodAncestry;
import org.uma.cloud.common.model.entity.BloodBreeding;
import org.uma.cloud.common.model.entity.BloodLine;
import org.uma.cloud.common.model.entity.DiffBreeder;
import org.uma.cloud.common.model.entity.Course;
import org.uma.cloud.common.model.entity.DiffJockey;
import org.uma.cloud.common.model.entity.DiffOwner;
import org.uma.cloud.common.model.entity.DiffRaceHorse;
import org.uma.cloud.common.model.entity.RacingDetail;
import org.uma.cloud.common.model.entity.RacingHorseDetail;
import org.uma.cloud.common.model.entity.RacingHorseExclusion;
import org.uma.cloud.common.model.entity.RacingRefund;
import org.uma.cloud.common.model.entity.RacingVote;
import org.uma.cloud.common.model.entity.DiffTrainer;
import org.uma.cloud.common.model.entity.OddsExacta;
import org.uma.cloud.common.model.entity.OddsQuinella;
import org.uma.cloud.common.model.entity.OddsQuinellaPlace;
import org.uma.cloud.common.model.entity.OddsTrifecta;
import org.uma.cloud.common.model.entity.OddsTrio;
import org.uma.cloud.common.model.entity.OddsWinsShowBracketQ;
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
    public ItemWriter<DiffBreeder> breederItemWriter() {
        return createJsonWriter(DiffBreeder.class, "DIFF_BR.json");
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
    public ItemWriter<DiffJockey> jockeyItemWriter() {
        return createJsonWriter(DiffJockey.class, "DIFF_KS.json");
    }

    @Bean
    public ItemWriter<BloodLine> bloodLineItemWriter() {
        return createJsonWriter(BloodLine.class, "BLOD_SK.json");
    }

    @Bean
    public ItemWriter<DiffOwner> ownerItemWriter() {
        return createJsonWriter(DiffOwner.class, "DIFF_BN.json");
    }

    @Bean
    public ItemWriter<DiffRaceHorse> raceHorseItemWriter() {
        return createJsonWriter(DiffRaceHorse.class, "DIFF_UM.json");
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
    public ItemWriter<DiffTrainer> trainerItemWriter() {
        return createJsonWriter(DiffTrainer.class, "DIFF_CH.json");
    }

    @Bean
    public ItemWriter<RacingVote> racingVoteItemWriter() {
        return createJsonWriter(RacingVote.class, "RACE_H1.json");
    }

    @Bean
    public ItemWriter<OddsWinsShowBracketQ> winsShowBracketQItemWriter() {
        return createJsonWriter(OddsWinsShowBracketQ.class, "RACE_O1.json");
    }

    @Bean
    public ItemWriter<OddsQuinella> quinellaItemWriter() {
        return createJsonWriter(OddsQuinella.class, "RACE_O2.json");
    }

    @Bean
    public ItemWriter<OddsQuinellaPlace> quinellaPlaceItemWriter() {
        return createJsonWriter(OddsQuinellaPlace.class, "RACE_O3.json");
    }

    @Bean
    public ItemWriter<OddsExacta> exactaItemWriter() {
        return createJsonWriter(OddsExacta.class, "RACE_O4.json");
    }

    @Bean
    public ItemWriter<OddsTrio> trioItemWriter() {
        return createJsonWriter(OddsTrio.class, "RACE_O5.json");
    }

    @Bean
    public ItemWriter<OddsTrifecta> trifectaItemWriter() {
        return createJsonWriter(OddsTrifecta.class, "RACE_O6.json");
    }

}
