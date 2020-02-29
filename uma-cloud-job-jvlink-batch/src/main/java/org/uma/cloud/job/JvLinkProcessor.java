package org.uma.cloud.job;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.function.FunctionItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.uma.cloud.common.component.JvLinkFunction;
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

@Configuration
@ComponentScan("org.uma.cloud.common.component")
public class JvLinkProcessor {

    @Autowired
    private JvLinkFunction jvLinkFunction;


    @Bean
    public ItemProcessor<String, Ancestry> ancestryItemProcessor() {
        return new FunctionItemProcessor<>(jvLinkFunction.decode()
                .andThen(jvLinkFunction::ancestryFunction));
    }

    @Bean
    public ItemProcessor<String, Breeder> breederItemProcessor() {
        return new FunctionItemProcessor<>(jvLinkFunction.decode()
                .andThen(jvLinkFunction::breederFunction));
    }

    @Bean
    public ItemProcessor<String, BreedingHorse> breedingHorseItemProcessor() {
        return new FunctionItemProcessor<>(jvLinkFunction.decode()
                .andThen(jvLinkFunction::breedingHorseFunction));
    }

    @Bean
    public ItemProcessor<String, Course> courseItemProcessor() {
        return new FunctionItemProcessor<>(jvLinkFunction.decode()
                .andThen(jvLinkFunction::courseFunction));
    }

    @Bean
    public ItemProcessor<String, HorseRacingDetails> horseRacingDetailsItemProcessor() {
        return new FunctionItemProcessor<>(jvLinkFunction.decode()
                .andThen(jvLinkFunction::horseRacingDetailsFunction));
    }

    @Bean
    public ItemProcessor<String, Jockey> jockeyItemProcessor() {
        return new FunctionItemProcessor<>(jvLinkFunction.decode()
                .andThen(jvLinkFunction::jockeyFunction));
    }

    @Bean
    public ItemProcessor<String, Offspring> offspringItemProcessor() {
        return new FunctionItemProcessor<>(jvLinkFunction.decode()
                .andThen(jvLinkFunction::offspringFunction));
    }

    @Bean
    public ItemProcessor<String, Owner> ownerItemProcessor() {
        return new FunctionItemProcessor<>(jvLinkFunction.decode()
                .andThen(jvLinkFunction::ownerFunction));
    }

    @Bean
    public ItemProcessor<String, RaceHorse> raceHorseItemProcessor() {
        return new FunctionItemProcessor<>(jvLinkFunction.decode()
                .andThen(jvLinkFunction::raceHorseFunction));
    }

    @Bean
    public ItemProcessor<String, RaceHorseExclusion> raceHorseExclusionItemProcessor() {
        return new FunctionItemProcessor<>(jvLinkFunction.decode()
                .andThen(jvLinkFunction::raceHorseExclusionFunction));
    }

    @Bean
    public ItemProcessor<String, RaceRefund> raceRefundItemProcessor() {
        return new FunctionItemProcessor<>(jvLinkFunction.decode()
                .andThen(jvLinkFunction::raceRefundFunction));
    }

    @Bean
    public ItemProcessor<String, RacingDetails> racingDetailsItemProcessor() {
        return new FunctionItemProcessor<>(jvLinkFunction.decode()
                .andThen(jvLinkFunction::racingDetailsFunction));
    }

    @Bean
    public ItemProcessor<String, Trainer> trainerItemProcessor() {
        return new FunctionItemProcessor<>(jvLinkFunction.decode()
                .andThen(jvLinkFunction::trainerFunction));
    }

    @Bean
    public ItemProcessor<String, VoteCount> voteCountItemProcessor() {
        return new FunctionItemProcessor<>(jvLinkFunction.decode()
                .andThen(jvLinkFunction::voteCountFunction));
    }

}
