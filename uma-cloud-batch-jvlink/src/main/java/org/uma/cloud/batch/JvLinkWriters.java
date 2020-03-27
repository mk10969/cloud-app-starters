package org.uma.cloud.batch;

import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
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

import javax.persistence.EntityManagerFactory;

@Profile("prod")
@Configuration
public class JvLinkWriters {
    
    @Autowired
    public EntityManagerFactory entityManagerFactory;


    @Bean
    public ItemWriter<Ancestry> ancestryItemWriter() {
        return new JpaItemWriterBuilder<Ancestry>()
                .entityManagerFactory(entityManagerFactory).build();
    }

    @Bean
    public ItemWriter<Breeder> breederItemWriter() {
        return new JpaItemWriterBuilder<Breeder>()
                .entityManagerFactory(entityManagerFactory).build();
    }

    @Bean
    public ItemWriter<BreedingHorse> breedingHorseItemWriter() {
        return new JpaItemWriterBuilder<BreedingHorse>()
                .entityManagerFactory(entityManagerFactory).build();
    }

    @Bean
    public ItemWriter<Course> courseItemWriter() {
        return new JpaItemWriterBuilder<Course>()
                .entityManagerFactory(entityManagerFactory).build();
    }

    @Bean
    public ItemWriter<HorseRacingDetails> horseRacingDetailsItemWriter() {
        return new JpaItemWriterBuilder<HorseRacingDetails>()
                .entityManagerFactory(entityManagerFactory).build();
    }

    @Bean
    public ItemWriter<Jockey> jockeyItemWriter() {
        return new JpaItemWriterBuilder<Jockey>()
                .entityManagerFactory(entityManagerFactory).build();
    }

    @Bean
    public ItemWriter<Offspring> offspringItemWriter() {
        return new JpaItemWriterBuilder<Offspring>()
                .entityManagerFactory(entityManagerFactory).build();
    }

    @Bean
    public ItemWriter<Owner> ownerItemWriter() {
        return new JpaItemWriterBuilder<Owner>()
                .entityManagerFactory(entityManagerFactory).build();
    }

    @Bean
    public ItemWriter<RaceHorse> raceHorseItemWriter() {
        return new JpaItemWriterBuilder<RaceHorse>()
                .entityManagerFactory(entityManagerFactory).build();
    }

    @Bean
    public ItemWriter<RaceHorseExclusion> raceHorseExclusionItemWriter() {
        return new JpaItemWriterBuilder<RaceHorseExclusion>()
                .entityManagerFactory(entityManagerFactory).build();
    }

    @Bean
    public ItemWriter<RaceRefund> raceRefundItemWriter() {
        return new JpaItemWriterBuilder<RaceRefund>()
                .entityManagerFactory(entityManagerFactory).build();
    }

    @Bean
    public ItemWriter<RacingDetails> racingDetailsItemWriter() {
        return new JpaItemWriterBuilder<RacingDetails>()
                .entityManagerFactory(entityManagerFactory).build();
    }

    @Bean
    public ItemWriter<Trainer> trainerItemWriter() {
        return new JpaItemWriterBuilder<Trainer>()
                .entityManagerFactory(entityManagerFactory).build();
    }

    @Bean
    public ItemWriter<VoteCount> voteCountItemWriter() {
        return new JpaItemWriterBuilder<VoteCount>()
                .entityManagerFactory(entityManagerFactory).build();
    }

}
