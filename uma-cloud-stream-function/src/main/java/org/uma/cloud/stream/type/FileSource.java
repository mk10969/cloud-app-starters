package org.uma.cloud.stream.type;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.uma.cloud.common.configuration.JvLinkDeserializer;
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
import org.uma.cloud.common.utils.lang.ModelUtil;
import reactor.core.publisher.Flux;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.BaseStream;

@Service
public class FileSource {

    @Autowired
    private JvLinkDeserializer jvLinkDeserializer;

    private static final String filePath = "file:///Users/m-kakiuchi/mydata/data/";

    private static final String racingDetail = "RACE_RA.txt";
    private static final String racingHorseDetail = "RACE_SE.txt";
    private static final String racingRefund = "RACE_HR.txt";
    private static final String racingVote = "RACE_H1.txt";
    private static final String racingHorseExclusion = "RACE_JG.txt";
    private static final String winsShowBracketQ = "RACE_O1.txt";
    private static final String quinella = "RACE_O2.txt";
    private static final String quinellaPlace = "RACE_O3.txt";
    private static final String exacta = "RACE_O4.txt";
    private static final String trio = "RACE_O5.txt";
    private static final String trifecta = "RACE_O6.txt";
    private static final String bloodAncestry = "BLOD_BT.txt";
    private static final String bloodBreeding = "BLOD_HN.txt";
    private static final String bloodLine = "BLOD_SK.txt";
    private static final String raceHorse = "DIFF_UM.txt";
    private static final String jockey = "DIFF_KS.txt";
    private static final String trainer = "DIFF_CH.txt";
    private static final String owner = "DIFF_BN.txt";
    private static final String breeder = "DIFF_BR.txt";
    private static final String course = "COMM_CS.txt";


    private static Flux<String> fromUri(URI uri) {
        return Flux.using(() -> Files.lines(Path.of(uri)),
                Flux::fromStream,
                BaseStream::close
        );
    }


    // レース

    public Flux<RacingDetail> getRacingDetail() {
        return fromUri(URI.create(filePath + racingDetail))
                .map(jvLinkDeserializer::toRacingDetail)
                .doOnNext(ModelUtil::fieldNotNull);
    }

    public Flux<RacingHorseDetail> getRacingHorseDetail() {
        return fromUri(URI.create(filePath + racingHorseDetail))
                .map(jvLinkDeserializer::toRacingHorseDetail)
                .doOnNext(ModelUtil::fieldNotNull);
    }

    public Flux<RacingRefund> getRacingRefund() {
        return fromUri(URI.create(filePath + racingRefund))
                .map(jvLinkDeserializer::toRacingRefund)
                .doOnNext(ModelUtil::fieldNotNull);
    }

    public Flux<RacingVote> getRacingVote() {
        return fromUri(URI.create(filePath + racingVote))
                .map(jvLinkDeserializer::toRacingVote)
                .doOnNext(ModelUtil::fieldNotNull);
    }

    public Flux<RacingHorseExclusion> getRacingHorseExclusion() {
        return fromUri(URI.create(filePath + racingHorseExclusion))
                .map(jvLinkDeserializer::toRacingHorseExclusion)
                .doOnNext(ModelUtil::fieldNotNull);
    }

    // オッズ

    public Flux<WinsShowBracketQ> getWinsShowBracketQ() {
        return fromUri(URI.create(filePath + winsShowBracketQ))
                .map(jvLinkDeserializer::toWinsShowBracketQ)
                .doOnNext(ModelUtil::fieldNotNull);
    }

    public Flux<Quinella> getQuinella() {
        return fromUri(URI.create(filePath + quinella))
                .map(jvLinkDeserializer::toQuinella)
                .doOnNext(ModelUtil::fieldNotNull);
    }

    public Flux<QuinellaPlace> getQuinellaPlace() {
        return fromUri(URI.create(filePath + quinellaPlace))
                .map(jvLinkDeserializer::toQuinellaPlace)
                .doOnNext(ModelUtil::fieldNotNull);
    }

    public Flux<Exacta> getExacta() {
        return fromUri(URI.create(filePath + exacta))
                .map(jvLinkDeserializer::toExacta)
                .doOnNext(ModelUtil::fieldNotNull);
    }

    public Flux<Trio> getTrio() {
        return fromUri(URI.create(filePath + trio))
                .map(jvLinkDeserializer::toTrio)
                .doOnNext(ModelUtil::fieldNotNull);
    }

    public Flux<Trifecta> getTrifecta() {
        return fromUri(URI.create(filePath + trifecta))
                .map(jvLinkDeserializer::toTrifecta)
                .doOnNext(ModelUtil::fieldNotNull);
    }

    // 血統

    public Flux<BloodAncestry> getBloodAncestry() {
        return fromUri(URI.create(filePath + bloodAncestry))
                .map(jvLinkDeserializer::toBloodAncestry)
                .doOnNext(ModelUtil::fieldNotNull);
    }

    public Flux<BloodBreeding> getBloodBreeding() {
        return fromUri(URI.create(filePath + bloodBreeding))
                .map(jvLinkDeserializer::toBloodBreeding)
                .doOnNext(ModelUtil::fieldNotNull);
    }

    public Flux<BloodLine> getBloodLine() {
        return fromUri(URI.create(filePath + bloodLine))
                .map(jvLinkDeserializer::toBloodLine)
                .doOnNext(ModelUtil::fieldNotNull);
    }

    // 馬

    public Flux<RaceHorse> getRaceHorse() {
        return fromUri(URI.create(filePath + raceHorse))
                .map(jvLinkDeserializer::toRaceHorse)
                .doOnNext(ModelUtil::fieldNotNull);
    }

    public Flux<Jockey> getJockey() {
        return fromUri(URI.create(filePath + jockey))
                .map(jvLinkDeserializer::toJockey)
                .doOnNext(ModelUtil::fieldNotNull);
    }

    public Flux<Trainer> getTrainer() {
        return fromUri(URI.create(filePath + trainer))
                .map(jvLinkDeserializer::toTrainer)
                .doOnNext(ModelUtil::fieldNotNull);
    }

    public Flux<Owner> getOwner() {
        return fromUri(URI.create(filePath + owner))
                .map(jvLinkDeserializer::toOwner)
                .doOnNext(ModelUtil::fieldNotNull);
    }

    public Flux<Breeder> getBreeder() {
        return fromUri(URI.create(filePath + breeder))
                .map(jvLinkDeserializer::toBreeder)
                .doOnNext(ModelUtil::fieldNotNull);
    }

    public Flux<Course> getCourse() {
        return fromUri(URI.create(filePath + course))
                .map(jvLinkDeserializer::toCourse)
                .doOnNext(ModelUtil::fieldNotNull);
    }

}
