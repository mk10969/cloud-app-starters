package org.uma.cloud.job;

import org.springframework.batch.item.ItemProcessor;
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
        return data -> jvLinkFunction.decode()
                .andThen(jvLinkFunction::ancestryFunction)
                .apply(data);
    }

    @Bean
    public ItemProcessor<String, Breeder> breederItemProcessor() {
        return data -> jvLinkFunction.decode()
                .andThen(jvLinkFunction::breederFunction)
                .apply(data);
    }

    @Bean
    public ItemProcessor<String, BreedingHorse> breedingHorseItemProcessor() {
        return data -> jvLinkFunction.decode()
                .andThen(jvLinkFunction::breedingHorseFunction)
                .apply(data);
    }

    @Bean
    public ItemProcessor<String, Course> courseItemProcessor() {
        return data -> jvLinkFunction.decode()
                .andThen(jvLinkFunction::courseFunction)
                .apply(data);
    }

    @Bean
    public ItemProcessor<String, HorseRacingDetails> horseRacingDetailsItemProcessor() {
        return data -> jvLinkFunction.decode()
                .andThen(jvLinkFunction::horseRacingDetailsFunction)
                .apply(data);
    }

    @Bean
    public ItemProcessor<String, Jockey> jockeyItemProcessor() {
        return data -> jvLinkFunction.decode()
                .andThen(jvLinkFunction::jockeyFunction)
                .apply(data);
    }

    @Bean
    public ItemProcessor<String, Offspring> offspringItemProcessor() {
        return data -> jvLinkFunction.decode()
                .andThen(jvLinkFunction::offspringFunction)
                .apply(data);
    }

    @Bean
    public ItemProcessor<String, Owner> ownerItemProcessor() {
        return data -> jvLinkFunction.decode()
                .andThen(jvLinkFunction::ownerFunction)
                .apply(data);
    }

    @Bean
    public ItemProcessor<String, RaceHorse> raceHorseItemProcessor() {
        return data -> jvLinkFunction.decode()
                .andThen(jvLinkFunction::raceHorseFunction)
                .apply(data);
    }

    @Bean
    public ItemProcessor<String, RaceHorseExclusion> raceHorseExclusionItemProcessor() {
        return data -> jvLinkFunction.decode()
                .andThen(jvLinkFunction::raceHorseExclusionFunction)
                .apply(data);
    }

    @Bean
    public ItemProcessor<String, RaceRefund> raceRefundItemProcessor() {
        return data -> jvLinkFunction.decode()
                .andThen(jvLinkFunction::raceRefundFunction)
                .apply(data);
    }

    @Bean
    public ItemProcessor<String, RacingDetails> racingDetailsItemProcessor() {
        return data -> jvLinkFunction.decode()
                .andThen(jvLinkFunction::racingDetailsFunction)
                .apply(data);
    }

    @Bean
    public ItemProcessor<String, Trainer> trainerItemProcessor() {
        return data -> jvLinkFunction.decode()
                .andThen(jvLinkFunction::trainerFunction)
                .apply(data);
    }

    @Bean
    public ItemProcessor<String, VoteCount> voteCountItemProcessor() {
        return data -> jvLinkFunction.decode()
                .andThen(jvLinkFunction::voteCountFunction)
                .apply(data);
    }

}
