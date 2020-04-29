package org.uma.cloud.batch;

import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
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
        JpaItemWriter<BloodAncestry> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        writer.setUsePersist(true);
        return writer;
    }

    @Bean
    public ItemWriter<BloodBreeding> bloodBreedingItemWriter() {
        JpaItemWriter<BloodBreeding> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        writer.setUsePersist(true);
        return writer;
    }

    @Bean
    public ItemWriter<BloodLine> bloodLineItemWriter() {
        JpaItemWriter<BloodLine> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        writer.setUsePersist(true);
        return writer;
    }

    @Bean
    public ItemWriter<RacingDetail> racingDetailItemWriter() {
        JpaItemWriter<RacingDetail> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        writer.setUsePersist(true);
        return writer;
    }

    @Bean
    public ItemWriter<RacingHorseDetail> racingHorseDetailItemWriter() {
        JpaItemWriter<RacingHorseDetail> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        writer.setUsePersist(true);
        return writer;
    }

    @Bean
    public ItemWriter<RacingHorseExclusion> racingHorseExclusionItemWriter() {
        JpaItemWriter<RacingHorseExclusion> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        writer.setUsePersist(true);
        return writer;
    }

    @Bean
    public ItemWriter<RacingRefund> racingRefundItemWriter() {
        JpaItemWriter<RacingRefund> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        writer.setUsePersist(true);
        return writer;
    }

    @Bean
    public ItemWriter<RacingVote> racingVoteItemWriter() {
        JpaItemWriter<RacingVote> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        writer.setUsePersist(true);
        return writer;
    }

    @Bean
    public ItemWriter<RaceHorse> raceHorseItemWriter() {
        JpaItemWriter<RaceHorse> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        writer.setUsePersist(true);
        return writer;
    }

    @Bean
    public ItemWriter<Jockey> jockeyItemWriter() {
        JpaItemWriter<Jockey> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        writer.setUsePersist(true);
        return writer;
    }

    @Bean
    public ItemWriter<Owner> ownerItemWriter() {
        JpaItemWriter<Owner> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        writer.setUsePersist(true);
        return writer;
    }

    @Bean
    public ItemWriter<Breeder> breederItemWriter() {
        JpaItemWriter<Breeder> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        writer.setUsePersist(true);
        return writer;
    }

    @Bean
    public ItemWriter<Trainer> trainerItemWriter() {
        JpaItemWriter<Trainer> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        writer.setUsePersist(true);
        return writer;
    }

    @Bean
    public ItemWriter<Course> courseItemWriter() {
        JpaItemWriter<Course> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        writer.setUsePersist(true);
        return writer;
    }

    @Bean
    public ItemWriter<WinsShowBracketQ> winsShowBracketQItemWriter() {
        JpaItemWriter<WinsShowBracketQ> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        writer.setUsePersist(true);
        return writer;
    }

    @Bean
    public ItemWriter<Quinella> quinellaItemWriter() {
        JpaItemWriter<Quinella> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        writer.setUsePersist(true);
        return writer;
    }

    @Bean
    public ItemWriter<QuinellaPlace> quinellaPlaceItemWriter() {
        JpaItemWriter<QuinellaPlace> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        writer.setUsePersist(true);
        return writer;
    }

    @Bean
    public ItemWriter<Exacta> exactaItemWriter() {
        JpaItemWriter<Exacta> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        writer.setUsePersist(true);
        return writer;
    }


    @Bean
    public ItemWriter<Trio> trioItemWriter() {
        JpaItemWriter<Trio> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        writer.setUsePersist(true);
        return writer;
    }

    @Bean
    public ItemWriter<Trifecta> trifectaItemWriter() {
        JpaItemWriter<Trifecta> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        writer.setUsePersist(true);
        return writer;
    }

}
