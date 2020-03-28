package org.uma.cloud.batch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
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
import org.uma.cloud.common.model.odds.Exacta;
import org.uma.cloud.common.model.odds.Quinella;
import org.uma.cloud.common.model.odds.QuinellaPlace;
import org.uma.cloud.common.model.odds.Trifecta;
import org.uma.cloud.common.model.odds.Trio;
import org.uma.cloud.common.model.odds.WinsPlaceBracketQuinella;

@Profile("local")
@Slf4j
@Configuration
public class JvLInkWritersLocal {

    @Bean
    public ItemWriter<Ancestry> ancestryItemWriter() {
        return items -> items.forEach(item -> log.info("{}", item));
    }

    @Bean
    public ItemWriter<Breeder> breederItemWriter() {
        return items -> items.forEach(item -> log.info("{}", item));
    }

    @Bean
    public ItemWriter<BreedingHorse> breedingHorseItemWriter() {
        return items -> items.forEach(item -> log.info("{}", item));
    }

    @Bean
    public ItemWriter<Course> courseItemWriter() {
        return items -> items.forEach(item -> log.info("{}", item));
    }

    @Bean
    public ItemWriter<HorseRacingDetails> horseRacingDetailsItemWriter() {
        return items -> items.forEach(item -> log.info("{}", item));
    }

    @Bean
    public ItemWriter<Jockey> jockeyItemWriter() {
        return items -> items.forEach(item -> log.info("{}", item));
    }

    @Bean
    public ItemWriter<Offspring> offspringItemWriter() {
        return items -> items.forEach(item -> log.info("{}", item));
    }

    @Bean
    public ItemWriter<Owner> ownerItemWriter() {
        return items -> items.forEach(item -> log.info("{}", item));
    }

    @Bean
    public ItemWriter<RaceHorse> raceHorseItemWriter() {
        return items -> items.forEach(item -> log.info("{}", item));
    }

    @Bean
    public ItemWriter<RaceHorseExclusion> raceHorseExclusionItemWriter() {
        return items -> items.forEach(item -> log.info("{}", item));
    }

    @Bean
    public ItemWriter<RaceRefund> raceRefundItemWriter() {
        return items -> items.forEach(item -> log.info("{}", item));
    }

    @Bean
    public ItemWriter<RacingDetails> racingDetailsItemWriter() {
        return items -> items.forEach(item -> log.info("{}", item));
    }

    @Bean
    public ItemWriter<Trainer> trainerItemWriter() {
        return items -> items.forEach(item -> log.info("{}", item));
    }

    @Bean
    public ItemWriter<VoteCount> voteCountItemWriter() {
        return items -> items.forEach(item -> log.info("{}", item));
    }

    @Bean
    public ItemWriter<WinsPlaceBracketQuinella> winsPlaceBracketQuinellaItemWriter() {
        return items -> items.forEach(item -> log.info("{}", item));
    }

    @Bean
    public ItemWriter<Quinella> quinellaItemWriter() {
        return items -> items.forEach(item -> log.info("{}", item));
    }

    @Bean
    public ItemWriter<QuinellaPlace> quinellaPlaceItemWriter() {
        return items -> items.forEach(item -> log.info("{}", item));
    }

    @Bean
    public ItemWriter<Exacta> exactaItemWriter() {
        return items -> items.forEach(item -> log.info("{}", item));
    }

    @Bean
    public ItemWriter<Trio> trioItemWriter() {
        return items -> items.forEach(item -> log.info("{}", item));
    }

    @Bean
    public ItemWriter<Trifecta> trifectaItemWriter() {
        return items -> items.forEach(item -> log.info("{}", item));
    }

}
