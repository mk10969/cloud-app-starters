package org.uma.cloud.common.model;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.Test;
import org.uma.cloud.common.ReflectionUtils;
import org.uma.cloud.common.utils.javatuples.Pair;

import java.util.List;
import java.util.stream.Stream;

public class ModelTypeCheckTest {

    private static final List<String> excludeList = Lists.newArrayList(
            // 特にnullでも問題ない
            "dataCreateDate",
            // 可能性があるので・・・
            "horseWeight",
            "changeAmount",
            // 複勝、枠連は、出走頭数が少ないと、発売されない場合がある
            "voteCountTotalShow",
            "voteCountTotalBracketQuinella",
            // 返還票数
            "restoreVoteCountTotalWin",
            "restoreVoteCountTotalPlace",
            "restoreVoteCountTotalBracketQuinella",
            "restoreVoteCountTotalQuinella",
            "restoreVoteCountTotalQuinellaPlace",
            "restoreVoteCountTotalExacta",
            "restoreVoteCountTotalTrio",
            // 競走馬登録年月日
            // 競走馬自身が古い（1950年代）から仕方ないかも。
            "raceHorseEntryDate",
            // 競走馬抹消年月日
            "raceHorseEraseDate",
            // 騎手抹消年月日
            "jockeyLicenseEraseDate",
            // 調教師抹消年月日
            "trainerLicenseEraseDate",
            // 外国人騎手の場合、nullになる
            "birthDate"
    );


    @Test
    void test_type_check() {
        ReflectionUtils.getClassesFrom("org.uma.cloud.common.model")
                .stream()
                .flatMap(clazz -> Stream.of(clazz.getDeclaredFields())
                        .map(i -> Pair.with(clazz, i.getName())))
                .filter(i -> excludeList.contains(i.getValue2()))
//                .filter(pair -> pair.getValue2().getTypeName().contains("LocalTime"))
//                .map(Type::getTypeName)
//                .sorted()
//                .distinct()
                .forEach(System.out::println);
    }


    @Test
    void test_show_ALL() {
        ReflectionUtils.getClassesFrom("org.uma.cloud.common.model")
                .stream()
                .map(Class::getSimpleName)
                .map(i -> i.substring(0, 1).toLowerCase() + i.substring(1))
                .sorted()
                .forEach(System.out::println);
    }

    @Test
    void test_showOdds_ALL() {
        ReflectionUtils.getClassesFrom("org.uma.cloud.common.model.odds")
                .stream()
                .map(Class::getSimpleName)
                .map(i -> i.substring(0, 1).toLowerCase() + i.substring(1))
                .sorted()
                .forEach(System.out::println);
    }

}
