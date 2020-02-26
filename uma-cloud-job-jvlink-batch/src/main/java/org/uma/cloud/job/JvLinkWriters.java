package org.uma.cloud.job;

import com.mongodb.client.MongoClients;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.builder.MongoItemWriterBuilder;
import org.springframework.batch.item.json.JacksonJsonObjectMarshaller;
import org.springframework.batch.item.json.builder.JsonFileItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
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
import org.uma.cloud.common.utils.lang.JvLinkModelUtil;

@Configuration
@RequiredArgsConstructor
public class JvLinkWriters {

    private final ResourceLoader resourceLoader;

    private final JvLinkBatchProperties properties;


    @Bean
    public MongoOperations mongoOperations() {
        return new MongoTemplate(
                MongoClients.create(properties.getMongodb()),
                properties.getDatabaseName());
    }


    @Bean
    public ItemWriter<Ancestry> ancestryItemWriter(MongoOperations template) {
        return new MongoItemWriterBuilder<Ancestry>()
                .template(template)
                .collection(Ancestry.class.getSimpleName())
                .build();
    }

    @Bean
    public ItemWriter<Breeder> breederItemWriter(MongoOperations template) {
        return new MongoItemWriterBuilder<Breeder>()
                .template(template)
                .collection(Breeder.class.getSimpleName())
                .build();
    }

    @Bean
    public ItemWriter<BreedingHorse> breedingHorseItemWriter(MongoOperations template) {
        return new MongoItemWriterBuilder<BreedingHorse>()
                .template(template)
                .collection(BreedingHorse.class.getSimpleName())
                .build();
    }

    @Bean
    public ItemWriter<Course> courseItemWriter(MongoOperations template) {
        return new MongoItemWriterBuilder<Course>()
                .template(template)
                .collection(Course.class.getSimpleName())
                .build();
    }

    @Bean
    public ItemWriter<HorseRacingDetails> horseRacingDetailsItemWriter(MongoOperations template) {
        return new MongoItemWriterBuilder<HorseRacingDetails>()
                .template(template)
                .collection(HorseRacingDetails.class.getSimpleName())
                .build();
    }

    @Bean
    public ItemWriter<Jockey> jockeyItemWriter(MongoOperations template) {
        return new MongoItemWriterBuilder<Jockey>()
                .template(template)
                .collection(Jockey.class.getSimpleName())
                .build();
    }

    @Bean
    public ItemWriter<Offspring> offspringItemWriter(MongoOperations template) {
        return new MongoItemWriterBuilder<Offspring>()
                .template(template)
                .collection(Offspring.class.getSimpleName())
                .build();
    }

    @Bean
    public ItemWriter<Owner> ownerItemWriter(MongoOperations template) {
        return new MongoItemWriterBuilder<Owner>()
                .template(template)
                .collection(Owner.class.getSimpleName())
                .build();
    }

    @Bean
    public ItemWriter<RaceHorse> raceHorseItemWriter(MongoOperations template) {
        return new MongoItemWriterBuilder<RaceHorse>()
                .template(template)
                .collection(RaceHorse.class.getSimpleName())
                .build();
    }

    @Bean
    public ItemWriter<RaceHorseExclusion> raceHorseExclusionItemWriter(MongoOperations template) {
        return new MongoItemWriterBuilder<RaceHorseExclusion>()
                .template(template)
                .collection(RaceHorseExclusion.class.getSimpleName())
                .build();
    }

    @Bean
    public ItemWriter<RaceRefund> raceRefundItemWriter(MongoOperations template) {
        return new MongoItemWriterBuilder<RaceRefund>()
                .template(template)
                .collection(RaceRefund.class.getSimpleName())
                .build();
    }

    @Bean
    public ItemWriter<RacingDetails> racingDetailsItemWriter(MongoOperations template) {
//        return new JsonFileItemWriterBuilder<RacingDetails>()
//                .jsonObjectMarshaller(JvLinkModelUtil::toJson)
//                .resource(resourceLoader.getResource("file:///tmp/once.json"))
//                .name(RacingDetails.class.getSimpleName())
//                .build();

        return new MongoItemWriterBuilder<RacingDetails>()
                .template(template)
                .collection(RacingDetails.class.getSimpleName())
                .build();
    }

    @Bean
    public ItemWriter<Trainer> trainerItemWriter(MongoOperations template) {
        return new MongoItemWriterBuilder<Trainer>()
                .template(template)
                .collection(Trainer.class.getSimpleName())
                .build();
    }

    @Bean
    public ItemWriter<VoteCount> voteCountItemWriter(MongoOperations template) {
        return new MongoItemWriterBuilder<VoteCount>()
                .template(template)
                .collection(VoteCount.class.getSimpleName())
                .build();
    }

}

