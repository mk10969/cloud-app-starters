package org.uma.cloud.batch;

import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
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

import javax.persistence.EntityManagerFactory;

@Profile("prod")
@Configuration
public class JvLinkWriters {

    @Autowired
    private EntityManagerFactory entityManagerFactory;


    @Bean
    public ItemWriter<BloodAncestry> bloodAncestryItemWriter() {
        return new JpaItemWriterBuilder<BloodAncestry>()
                .entityManagerFactory(entityManagerFactory).build();
    }

    @Bean
    public ItemWriter<Breeder> breederItemWriter() {
        return new JpaItemWriterBuilder<Breeder>()
                .entityManagerFactory(entityManagerFactory).build();
    }

    @Bean
    public ItemWriter<BloodBreeding> bloodBreedingItemWriter() {
        return new JpaItemWriterBuilder<BloodBreeding>()
                .entityManagerFactory(entityManagerFactory).build();
    }

    @Bean
    public ItemWriter<Course> courseItemWriter() {
        return new JpaItemWriterBuilder<Course>()
                .entityManagerFactory(entityManagerFactory).build();
    }

    @Bean
    public ItemWriter<RacingHorseDetail> racingHorseDetailItemWriter() {
        return new JpaItemWriterBuilder<RacingHorseDetail>()
                .entityManagerFactory(entityManagerFactory).build();
    }

    @Bean
    public ItemWriter<Jockey> jockeyItemWriter() {
        return new JpaItemWriterBuilder<Jockey>()
                .entityManagerFactory(entityManagerFactory).build();
    }

    @Bean
    public ItemWriter<BloodLine> bloodLineItemWriter() {
        return new JpaItemWriterBuilder<BloodLine>()
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
    public ItemWriter<RacingHorseExclusion> racingHorseExclusionItemWriter() {
        return new JpaItemWriterBuilder<RacingHorseExclusion>()
                .entityManagerFactory(entityManagerFactory).build();
    }

    @Bean
    public ItemWriter<RacingRefund> racingRefundItemWriter() {
        return new JpaItemWriterBuilder<RacingRefund>()
                .entityManagerFactory(entityManagerFactory).build();
    }

    @Bean
    public ItemWriter<RacingDetail> racingDetailItemWriter() {
        return new JpaItemWriterBuilder<RacingDetail>()
                .entityManagerFactory(entityManagerFactory).build();
    }

    @Bean
    public ItemWriter<Trainer> trainerItemWriter() {
        return new JpaItemWriterBuilder<Trainer>()
                .entityManagerFactory(entityManagerFactory).build();
    }

    @Bean
    public ItemWriter<RacingVote> racingVoteItemWriter() {
        return new JpaItemWriterBuilder<RacingVote>()
                .entityManagerFactory(entityManagerFactory).build();
    }

    @Bean
    public ItemWriter<WinsShowBracketQ> winsShowBracketQItemWriter() {
        return new JpaItemWriterBuilder<WinsShowBracketQ>()
                .entityManagerFactory(entityManagerFactory).build();
    }

    @Bean
    public ItemWriter<Quinella> quinellaItemWriter() {
        return new JpaItemWriterBuilder<Quinella>()
                .entityManagerFactory(entityManagerFactory).build();
    }

    @Bean
    public ItemWriter<QuinellaPlace> quinellaPlaceItemWriter() {
        return new JpaItemWriterBuilder<QuinellaPlace>()
                .entityManagerFactory(entityManagerFactory).build();
    }

    @Bean
    public ItemWriter<Exacta> exactaItemWriter() {
        return new JpaItemWriterBuilder<Exacta>()
                .entityManagerFactory(entityManagerFactory).build();
    }


    @Bean
    public ItemWriter<Trio> trioItemWriter() {
        return new JpaItemWriterBuilder<Trio>()
                .entityManagerFactory(entityManagerFactory).build();
    }

    @Bean
    public ItemWriter<Trifecta> trifectaItemWriter() {
        return new JpaItemWriterBuilder<Trifecta>()
                .entityManagerFactory(entityManagerFactory).build();
    }

}
