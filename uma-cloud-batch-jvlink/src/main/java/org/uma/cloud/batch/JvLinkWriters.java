package org.uma.cloud.batch;

import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.uma.cloud.common.model.BaseModel;
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

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManagerFactory;

@Profile("prod")
@Configuration
public class JvLinkWriters {

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    /**
     * persist = true に設定する。
     *
     * @throws EntityExistsException if the entity already exists.
     */
    private <T extends BaseModel> JpaItemWriter<T> createJpaItemWriter() {
        JpaItemWriter<T> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        writer.setUsePersist(true);
        return writer;
    }

    @Bean
    public ItemWriter<BloodAncestry> bloodAncestryItemWriter() {
        return createJpaItemWriter();
    }

    @Bean
    public ItemWriter<BloodBreeding> bloodBreedingItemWriter() {
        return createJpaItemWriter();
    }

    @Bean
    public ItemWriter<BloodLine> bloodLineItemWriter() {
        return createJpaItemWriter();
    }

    @Bean
    public ItemWriter<RacingDetail> racingDetailItemWriter() {
        return createJpaItemWriter();
    }

    @Bean
    public ItemWriter<RacingHorseDetail> racingHorseDetailItemWriter() {
        return createJpaItemWriter();
    }

    @Bean
    public ItemWriter<RacingHorseExclusion> racingHorseExclusionItemWriter() {
        return createJpaItemWriter();
    }

    @Bean
    public ItemWriter<RacingRefund> racingRefundItemWriter() {
        return createJpaItemWriter();
    }

    @Bean
    public ItemWriter<RacingVote> racingVoteItemWriter() {
        return createJpaItemWriter();
    }

    @Bean
    public ItemWriter<RaceHorse> raceHorseItemWriter() {
        return createJpaItemWriter();
    }

    @Bean
    public ItemWriter<Jockey> jockeyItemWriter() {
        return createJpaItemWriter();
    }

    @Bean
    public ItemWriter<Owner> ownerItemWriter() {
        return createJpaItemWriter();
    }

    @Bean
    public ItemWriter<Breeder> breederItemWriter() {
        return createJpaItemWriter();
    }

    @Bean
    public ItemWriter<Trainer> trainerItemWriter() {
        return createJpaItemWriter();
    }

    @Bean
    public ItemWriter<Course> courseItemWriter() {
        return createJpaItemWriter();
    }

    @Bean
    public ItemWriter<WinsShowBracketQ> winsShowBracketQItemWriter() {
        return createJpaItemWriter();
    }

    @Bean
    public ItemWriter<Quinella> quinellaItemWriter() {
        return createJpaItemWriter();
    }

    @Bean
    public ItemWriter<QuinellaPlace> quinellaPlaceItemWriter() {
        return createJpaItemWriter();
    }

    @Bean
    public ItemWriter<Exacta> exactaItemWriter() {
        return createJpaItemWriter();
    }


    @Bean
    public ItemWriter<Trio> trioItemWriter() {
        return createJpaItemWriter();
    }

    @Bean
    public ItemWriter<Trifecta> trifectaItemWriter() {
        return createJpaItemWriter();
    }

}
