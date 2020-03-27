package org.uma.cloud.batch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.uma.cloud.common.configuration.JvLinkFunction;
import org.uma.cloud.common.model.Ancestry;
import org.uma.cloud.common.model.BaseModel;
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

import java.util.function.Function;


@Configuration
public class JvLinkProcessors {

    @Autowired
    private JvLinkFunction jvLinkFunction;


    @Bean
    public ItemProcessor<String, Ancestry> ancestryItemProcessor() {
        return new JvLinkFunctionItemProcessor<>(jvLinkFunction.decode()
                .andThen(jvLinkFunction::ancestryFunction));
    }

    @Bean
    public ItemProcessor<String, Breeder> breederItemProcessor() {
        return new JvLinkFunctionItemProcessor<>(jvLinkFunction.decode()
                .andThen(jvLinkFunction::breederFunction));
    }

    @Bean
    public ItemProcessor<String, BreedingHorse> breedingHorseItemProcessor() {
        return new JvLinkFunctionItemProcessor<>(jvLinkFunction.decode()
                .andThen(jvLinkFunction::breedingHorseFunction));
    }

    @Bean
    public ItemProcessor<String, Course> courseItemProcessor() {
        return new JvLinkFunctionItemProcessor<>(jvLinkFunction.decode()
                .andThen(jvLinkFunction::courseFunction));
    }

    @Bean
    public ItemProcessor<String, HorseRacingDetails> horseRacingDetailsItemProcessor() {
        return new JvLinkFunctionItemProcessor<>(jvLinkFunction.decode()
                .andThen(jvLinkFunction::horseRacingDetailsFunction));
    }

    @Bean
    public ItemProcessor<String, Jockey> jockeyItemProcessor() {
        return new JvLinkFunctionItemProcessor<>(jvLinkFunction.decode()
                .andThen(jvLinkFunction::jockeyFunction));
    }

    @Bean
    public ItemProcessor<String, Offspring> offspringItemProcessor() {
        return new JvLinkFunctionItemProcessor<>(jvLinkFunction.decode()
                .andThen(jvLinkFunction::offspringFunction));
    }

    @Bean
    public ItemProcessor<String, Owner> ownerItemProcessor() {
        return new JvLinkFunctionItemProcessor<>(jvLinkFunction.decode()
                .andThen(jvLinkFunction::ownerFunction));
    }

    @Bean
    public ItemProcessor<String, RaceHorse> raceHorseItemProcessor() {
        return new JvLinkFunctionItemProcessor<>(jvLinkFunction.decode()
                .andThen(jvLinkFunction::raceHorseFunction));
    }

    @Bean
    public ItemProcessor<String, RaceHorseExclusion> raceHorseExclusionItemProcessor() {
        return new JvLinkFunctionItemProcessor<>(jvLinkFunction.decode()
                .andThen(jvLinkFunction::raceHorseExclusionFunction));
    }

    @Bean
    public ItemProcessor<String, RaceRefund> raceRefundItemProcessor() {
        return new JvLinkFunctionItemProcessor<>(jvLinkFunction.decode()
                .andThen(jvLinkFunction::raceRefundFunction));
    }

    @Bean
    public ItemProcessor<String, RacingDetails> racingDetailsItemProcessor() {
        return new JvLinkFunctionItemProcessor<>(jvLinkFunction.decode()
                .andThen(jvLinkFunction::racingDetailsFunction));
    }

    @Bean
    public ItemProcessor<String, Trainer> trainerItemProcessor() {
        return new JvLinkFunctionItemProcessor<>(jvLinkFunction.decode()
                .andThen(jvLinkFunction::trainerFunction));
    }

    @Bean
    public ItemProcessor<String, VoteCount> voteCountItemProcessor() {
        return new JvLinkFunctionItemProcessor<>(jvLinkFunction.decode()
                .andThen(jvLinkFunction::voteCountFunction));
    }

    @Slf4j
    public static class JvLinkFunctionItemProcessor<I, O extends BaseModel> implements ItemProcessor<I, O> {

        private final Function<I, O> function;

        /**
         * @param function the delegate.  Must not be null
         */
        public JvLinkFunctionItemProcessor(Function<I, O> function) {
            Assert.notNull(function, "A function is required");
            this.function = function;
        }

        /**
         * @param item シリアライズ文字列データ
         * @return nullの場合、writerで書き込まれない。
         * 不要なデータをここでフィルターする。
         * @throws Exception
         */
        @Nullable
        @Override
        public O process(I item) throws Exception {
            O o = this.function.apply(item);
            if (o.isNecessary()) {
                return o;
            } else {
                log.info("不要データ: {}", o);
                return null;
            }
        }
    }

}
