package org.uma.cloud.batch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.uma.cloud.common.configuration.JvLinkDeserializer;
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
import org.uma.cloud.common.utils.exception.JvLinkModelNullPointException;
import org.uma.cloud.common.utils.lang.ModelUtil;

import java.util.function.Function;

@Configuration
public class JvLinkProcessors {

    @Autowired
    private JvLinkDeserializer jvLinkDeserializer;


    @Bean
    public ItemProcessor<String, BloodAncestry> bloodAncestryItemProcessor() {
        return new JvLinkFunctionItemProcessor<>(jvLinkDeserializer::toBloodAncestry);
    }

    @Bean
    public ItemProcessor<String, BloodBreeding> bloodBreedingItemProcessor() {
        return new JvLinkFunctionItemProcessor<>(jvLinkDeserializer::toBloodBreeding);
    }

    @Bean
    public ItemProcessor<String, BloodLine> bloodLineItemProcessor() {
        return new JvLinkFunctionItemProcessor<>(jvLinkDeserializer::toBloodLine);
    }

    @Bean
    public ItemProcessor<String, RacingDetail> racingDetailItemProcessor() {
        return new JvLinkFunctionItemProcessor<>(jvLinkDeserializer::toRacingDetail);
    }

    @Bean
    public ItemProcessor<String, RacingHorseDetail> racingHorseDetailItemProcessor() {
        return new JvLinkFunctionItemProcessor<>(jvLinkDeserializer::toRacingHorseDetail);
    }

    @Bean
    public ItemProcessor<String, RacingHorseExclusion> racingHorseExclusionItemProcessor() {
        return new JvLinkFunctionItemProcessor<>(jvLinkDeserializer::toRacingHorseExclusion);
    }

    @Bean
    public ItemProcessor<String, RacingRefund> racingRefundItemProcessor() {
        return new JvLinkFunctionItemProcessor<>(jvLinkDeserializer::toRacingRefund);
    }

    @Bean
    public ItemProcessor<String, RacingVote> racingVoteItemProcessor() {
        return new JvLinkFunctionItemProcessor<>(jvLinkDeserializer::toRacingVote);
    }

    @Bean
    public ItemProcessor<String, RaceHorse> raceHorseItemProcessor() {
        return new JvLinkFunctionItemProcessor<>(jvLinkDeserializer::toRaceHorse);
    }

    @Bean
    public ItemProcessor<String, Jockey> jockeyItemProcessor() {
        return new JvLinkFunctionItemProcessor<>(jvLinkDeserializer::toJockey);
    }

    @Bean
    public ItemProcessor<String, Owner> ownerItemProcessor() {
        return new JvLinkFunctionItemProcessor<>(jvLinkDeserializer::toOwner);
    }

    @Bean
    public ItemProcessor<String, Trainer> trainerItemProcessor() {
        return new JvLinkFunctionItemProcessor<>(jvLinkDeserializer::toTrainer);
    }

    @Bean
    public ItemProcessor<String, Breeder> breederItemProcessor() {
        return new JvLinkFunctionItemProcessor<>(jvLinkDeserializer::toBreeder);
    }

    @Bean
    public ItemProcessor<String, Course> courseItemProcessor() {
        return new JvLinkFunctionItemProcessor<>(jvLinkDeserializer::toCourse);
    }

    @Bean
    public ItemProcessor<String, WinsShowBracketQ> winsShowBracketQItemProcessor() {
        return new JvLinkFunctionItemProcessor<>(jvLinkDeserializer::toWinsShowBracketQ);
    }

    @Bean
    public ItemProcessor<String, Quinella> quinellaItemProcessor() {
        return new JvLinkFunctionItemProcessor<>(jvLinkDeserializer::toQuinella);
    }

    @Bean
    public ItemProcessor<String, QuinellaPlace> quinellaPlaceItemProcessor() {
        return new JvLinkFunctionItemProcessor<>(jvLinkDeserializer::toQuinellaPlace);
    }

    @Bean
    public ItemProcessor<String, Exacta> exactaItemProcessor() {
        return new JvLinkFunctionItemProcessor<>(jvLinkDeserializer::toExacta);
    }

    @Bean
    public ItemProcessor<String, Trio> trioItemProcessor() {
        return new JvLinkFunctionItemProcessor<>(jvLinkDeserializer::toTrio);
    }

    @Bean
    public ItemProcessor<String, Trifecta> trifectaItemProcessor() {
        return new JvLinkFunctionItemProcessor<>(jvLinkDeserializer::toTrifecta);
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
         * @return nullの場合、writerで書き込まれない。or デシリアライズしたModelオブジェクトを返す。
         * <p>
         * 不要なデータをここでフィルターする。
         * @throws Exception
         */
        @Nullable
        @Override
        public O process(I item) throws Exception {
            O o = this.function.apply(item);
            check(o);
            return o;
        }

        /**
         * {@link JvLinkProcessors}でtransformした後のmodelのフィールドに、nullがないかチェックを行う。
         * <p>
         * 例外的に、下記のフォールドは、nullを許容する。
         * {@link ModelUtil#excludeList}
         *
         * @throws JvLinkModelNullPointException
         */
        private void check(O o) {
            try {
                ModelUtil.fieldNotNull(o);
            } catch (NullPointerException e) {
                throw new JvLinkModelNullPointException(e, o.toString());
            }
        }
    }

}
