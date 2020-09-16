package org.uma.cloud.batch;

import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.uma.cloud.common.entity.BaseModel;
import org.uma.cloud.common.entity.BloodAncestry;
import org.uma.cloud.common.entity.BloodBreeding;
import org.uma.cloud.common.entity.BloodLine;
import org.uma.cloud.common.entity.DiffBreeder;
import org.uma.cloud.common.entity.Course;
import org.uma.cloud.common.entity.DiffJockey;
import org.uma.cloud.common.entity.DiffOwner;
import org.uma.cloud.common.entity.DiffRaceHorse;
import org.uma.cloud.common.entity.RacingDetail;
import org.uma.cloud.common.entity.RacingHorseDetail;
import org.uma.cloud.common.entity.RacingHorseExclusion;
import org.uma.cloud.common.entity.RacingRefund;
import org.uma.cloud.common.entity.RacingVote;
import org.uma.cloud.common.entity.DiffTrainer;
import org.uma.cloud.common.entity.OddsWinsShowBracketQ;

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
    public ItemWriter<DiffRaceHorse> raceHorseItemWriter() {
        return createJpaItemWriter();
    }

    @Bean
    public ItemWriter<DiffJockey> jockeyItemWriter() {
        return createJpaItemWriter();
    }

    @Bean
    public ItemWriter<DiffOwner> ownerItemWriter() {
        return createJpaItemWriter();
    }

    @Bean
    public ItemWriter<DiffBreeder> breederItemWriter() {
        return createJpaItemWriter();
    }

    @Bean
    public ItemWriter<DiffTrainer> trainerItemWriter() {
        return createJpaItemWriter();
    }

    @Bean
    public ItemWriter<Course> courseItemWriter() {
        return createJpaItemWriter();
    }

    @Bean
    public ItemWriter<OddsWinsShowBracketQ> winsShowBracketQItemWriter() {
        return createJpaItemWriter();
    }

    @Bean
    public ItemWriter<OddsQuinella> quinellaItemWriter() {
        return createJpaItemWriter();
    }

    @Bean
    public ItemWriter<OddsQuinellaPlace> quinellaPlaceItemWriter() {
        return createJpaItemWriter();
    }

    @Bean
    public ItemWriter<OddsExacta> exactaItemWriter() {
        return createJpaItemWriter();
    }


    @Bean
    public ItemWriter<OddsTrio> trioItemWriter() {
        return createJpaItemWriter();
    }

    @Bean
    public ItemWriter<OddsTrifecta> trifectaItemWriter() {
        return createJpaItemWriter();
    }

}
