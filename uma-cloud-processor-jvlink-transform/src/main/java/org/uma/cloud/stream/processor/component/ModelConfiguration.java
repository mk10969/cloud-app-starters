package org.uma.cloud.stream.processor.component;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
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

import java.util.function.Function;


@Configuration
@RequiredArgsConstructor
public class ModelConfiguration {

    private final JvLinkModelMapper jvLinkModelMapper;


    //    @Bean("RA")
    public Function<byte[], RacingDetails> racingDetailsFunction() {
        return data -> jvLinkModelMapper.deserialize(data, RacingDetails.class);
    }

    //    @Bean("SE")
    public Function<byte[], HorseRacingDetails> horseRacingDetailsFunction() {
        return data -> jvLinkModelMapper.deserialize(data, HorseRacingDetails.class);
    }

    //    @Bean("HR")
    public Function<byte[], RaceRefund> refundFunction() {
        return data -> {
            RaceRefund model = jvLinkModelMapper.deserialize(data, RaceRefund.class);
            model.getRefundWins().removeIf(ModelConfiguration::refundFilter);
            model.getRefundPlaces().removeIf(ModelConfiguration::refundFilter);
            model.getRefundBracketQuinellas().removeIf(ModelConfiguration::refundFilter);
            model.getRefundQuinellas().removeIf(ModelConfiguration::refundFilter);
            model.getRefundQuinellaPlaces().removeIf(ModelConfiguration::refundFilter);
            model.getRefundSpares().removeIf(ModelConfiguration::refundFilter);
            model.getRefundExactas().removeIf(ModelConfiguration::refundFilter);
            model.getRefundTrios().removeIf(ModelConfiguration::refundFilter);
            model.getRefundTrifectas().removeIf(ModelConfiguration::refundFilter);
            return model;
        };
    }

    //    @Bean("H1")
    public Function<byte[], VoteCount> voteCountFunction() {
        return data -> {
            VoteCount model = jvLinkModelMapper.deserialize(data, VoteCount.class);
            model.getVoteCountWins().removeIf(ModelConfiguration::voteFilter);
            model.getVoteCountPlaces().removeIf(ModelConfiguration::voteFilter);
            model.getVoteCountBracketQuinellas().removeIf(ModelConfiguration::voteFilter);
            model.getVoteCountQuinellas().removeIf(ModelConfiguration::voteFilter);
            model.getVoteCountQuinellaPlaces().removeIf(ModelConfiguration::voteFilter);
            model.getVoteCountExactas().removeIf(ModelConfiguration::voteFilter);
            model.getVoteCountTrios().removeIf(ModelConfiguration::voteFilter);
            return model;
        };
    }

    //    @Bean("O1")
    public Function<byte[], WinsPlaceBracketQuinella> winsPlaceBracketQuinellaFunction() {
        return data -> {
            WinsPlaceBracketQuinella model = jvLinkModelMapper.deserialize(data, WinsPlaceBracketQuinella.class);
            model.getWinOdds().removeIf(ModelConfiguration::winOddsFilter);
            model.getPlaceOdds().removeIf(ModelConfiguration::placeOddsFilter);
            model.getBracketQuinellaOdds().removeIf(ModelConfiguration::bracketQuinellaOddsFilter);
            return model;
        };
    }

    //    @Bean("O2")
    public Function<byte[], Quinella> quinellaFunction() {
        return data -> {
            Quinella model = jvLinkModelMapper.deserialize(data, Quinella.class);
            model.getQuinellaOdds().removeIf(ModelConfiguration::quinellaOddsFilter);
            return model;
        };
    }

    //    @Bean("O3")
    public Function<byte[], QuinellaPlace> quinellaPlaceFunction() {
        return data -> {
            QuinellaPlace model = jvLinkModelMapper.deserialize(data, QuinellaPlace.class);
            model.getQuinellaPlaceOdds().removeIf(ModelConfiguration::quinellaPlaceOddsFilter);
            return model;
        };
    }

    //    @Bean("O4")
    public Function<byte[], Exacta> exactaFunction() {
        return data -> {
            Exacta model = jvLinkModelMapper.deserialize(data, Exacta.class);
            model.getExactaOdds().removeIf(ModelConfiguration::exactaOddsFilter);
            return model;
        };
    }

    //    @Bean("O5")
    public Function<byte[], Trio> trioFunction() {
        return data -> {
            Trio model = jvLinkModelMapper.deserialize(data, Trio.class);
            model.getTrioOdds().removeIf(ModelConfiguration::trioOddsFilter);
            return model;
        };
    }

    //    @Bean("O6")
    public Function<byte[], Trifecta> trifectaFunction() {
        return data -> {
            Trifecta model = jvLinkModelMapper.deserialize(data, Trifecta.class);
            model.getTrifectaOdds().removeIf(ModelConfiguration::trifectaOddsFilter);
            return model;
        };
    }


    //    @Bean("JG")
    public Function<byte[], RaceHorseExclusion> raceHorseExclusionFunction() {
        return data -> jvLinkModelMapper.deserialize(data, RaceHorseExclusion.class);
    }

    //    @Bean("SK")
    public Function<byte[], Offspring> offspringFunction() {
        return data -> jvLinkModelMapper.deserialize(data, Offspring.class);
    }

    //    @Bean("BT")
    public Function<byte[], Ancestry> ancestryFunction() {
        return data -> jvLinkModelMapper.deserialize(data, Ancestry.class);
    }

    //    @Bean("HN")
    public Function<byte[], BreedingHorse> breedingHorseFunction() {
        return data -> jvLinkModelMapper.deserialize(data, BreedingHorse.class);
    }

    //    @Bean("CS")
    public Function<byte[], Course> courseFunction() {
        return data -> jvLinkModelMapper.deserialize(data, Course.class);
    }

    //    @Bean("UM")
    public Function<byte[], RaceHorse> raceHorseFunction() {
        return data -> jvLinkModelMapper.deserialize(data, RaceHorse.class);
    }

    //    @Bean("KS")
    public Function<byte[], Jockey> jockeyFunction() {
        return data -> jvLinkModelMapper.deserialize(data, Jockey.class);
    }

    //    @Bean("CH")
    public Function<byte[], Trainer> trainerFunction() {
        return data -> jvLinkModelMapper.deserialize(data, Trainer.class);
    }

    //    @Bean("BR")
    public Function<byte[], Breeder> breederFunction() {
        return data -> jvLinkModelMapper.deserialize(data, Breeder.class);
    }

    //    @Bean("BN")
    public Function<byte[], Owner> ownerFunction() {
        return data -> jvLinkModelMapper.deserialize(data, Owner.class);
    }


    static boolean winOddsFilter(WinsPlaceBracketQuinella.WinOdds winOdds) {
        return winOdds.getOdds() == null
                && winOdds.getBetRank() == null;
    }

    static boolean placeOddsFilter(WinsPlaceBracketQuinella.PlaceOdds placeOdds) {
        return placeOdds.getOddsMin() == null
                && placeOdds.getOddsMax() == null
                && placeOdds.getBetRank() == null;
    }

    static boolean bracketQuinellaOddsFilter(WinsPlaceBracketQuinella.BracketQuinellaOdds bracketQuinellaOdds) {
        return bracketQuinellaOdds.getOdds() == null
                && bracketQuinellaOdds.getBetRank() == null;
    }

    static boolean quinellaOddsFilter(Quinella.QuinellaOdds quinellaOdds) {
        return quinellaOdds.getOdds() == null
                && quinellaOdds.getBetRank() == null;
    }

    static boolean quinellaPlaceOddsFilter(QuinellaPlace.QuinellaPlaceOdds quinellaPlaceOdds) {
        return quinellaPlaceOdds.getOddsMin() == null
                && quinellaPlaceOdds.getOddsMax() == null
                && quinellaPlaceOdds.getBetRank() == null;
    }

    static boolean exactaOddsFilter(Exacta.ExactaOdds exactaOdds) {
        return exactaOdds.getOdds() == null
                && exactaOdds.getBetRank() == null;
    }

    static boolean trioOddsFilter(Trio.TrioOdds trioOdds) {
        return trioOdds.getOdds() == null
                && trioOdds.getBetRank() == null;
    }

    static boolean trifectaOddsFilter(Trifecta.TrifectaOdds trifectaOdds) {
        return trifectaOdds.getOdds() == null
                && trifectaOdds.getBetRank() == null;
    }


    static boolean refundFilter(RaceRefund.refund refund) {
        return refund.getBetRank() == null
                && refund.getRefundMoney() == null;
    }

    static boolean refundFilter(RaceRefund.refundPair refundPair) {
        return refundPair.getBetRank() == null
                && refundPair.getRefundMoney() == null;
    }

    static boolean refundFilter(RaceRefund.refundTriplet refundTriplet) {
        return refundTriplet.getBetRank() == null
                && refundTriplet.getRefundMoney() == null;
    }

    static boolean voteFilter(VoteCount.Vote vote) {
        return vote.getBetRank() == null
                && vote.getVoteCount() == null;
    }

    static boolean voteFilter(VoteCount.VotePair votePair) {
        return votePair.getBetRank() == null
                && votePair.getVoteCount() == null;
    }

    static boolean voteFilter(VoteCount.VoteTriplet voteTriplet) {
        return voteTriplet.getBetRank() == null
                && voteTriplet.getVoteCount() == null;
    }

}
