package org.uma.cloud.stream.processor.repository.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ResourceLoader;
import org.uma.cloud.common.model.RaceRefund;
import org.uma.cloud.common.model.RacingDetails;
import org.uma.cloud.common.model.VoteCount;
import org.uma.cloud.common.model.odds.Quinella;
import org.uma.cloud.stream.processor.component.JvLinkModelMapper;
import org.uma.cloud.stream.processor.component.JvLinkModelUtil;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;


@SpringBootTest
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class JvStoredRepositoryModelMapperTest {

    private final JvLinkModelMapper jvLinkModelMapper;
    private final ResourceLoader resourceLoader;


    private List<String> readTestData(String filename) {
        try {
            return Files.readAllLines(resourceLoader
                            .getResource("classpath:test-data/" + filename)
                            .getFile()
                            .toPath(),
                    Charset.forName("SHIFT-JIS"));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("ファイルが存在しませんでした");
        }
    }


    private String toJson(Object model) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(model);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("jsonに変換できませんでした。");
        }
    }


    private void test(Supplier<Stream<Object>> streamSupplier) {
        streamSupplier.get()
                .map(this::toJson)
                .forEach(System.out::println);
    }

    @SafeVarargs
    private final void testAll(Supplier<Stream<Object>>... streamSupplier) {
        Stream.of(streamSupplier).forEach(this::test);
    }

    @Test
    void test_ALL_デシリアライズテスト() {
        testAll(
                () -> readTestData("RA")
                        .stream()
                        .map(line -> jvLinkModelMapper.deserialize(JvLinkModelUtil.toByte(line), RacingDetails.class)),
//                () -> readTestData("SE")
//                        .stream()
//                        .map(line -> jvLinkModelMapper.deserialize(line, HorseRacingDetails.class)),
                () -> readTestData("HR")
                        .stream()
                        .map(line -> jvLinkModelMapper.deserialize(JvLinkModelUtil.toByte(line), RaceRefund.class)),
//                () -> readTestData("CS")
//                        .stream()
//                        .map(line -> jvLinkModelMapper.deserialize(line, Course.class)),
//                () -> readTestData("SK")
//                        .stream()
//                        .map(line -> jvLinkModelMapper.deserialize(line, Offspring.class)),
//                () -> readTestData("HN")
//                        .stream()
//                        .map(line -> jvLinkModelMapper.deserialize(line, BreedingHorse.class)),
//                () -> readTestData("BT")
//                        .stream()
//                        .map(line -> jvLinkModelMapper.deserialize(line, Ancestry.class)),
//                () -> readTestData("BR")
//                        .stream()
//                        .map(line -> jvLinkModelMapper.deserialize(line, Breeder.class)),
//                () -> readTestData("UM")
//                        .stream()
//                        .map(line -> jvLinkModelMapper.deserialize(line, RaceHorse.class)),
//                () -> readTestData("KS")
//                        .stream()
//                        .map(line -> jvLinkModelMapper.deserialize(line, Jockey.class)),
//                () -> readTestData("CH")
//                        .stream()
//                        .map(line -> jvLinkModelMapper.deserialize(line, Trainer.class)),
//                () -> readTestData("H1")
//                        .stream()
//                        .map(line -> jvLinkModelMapper.deserialize(line, VoteCount.class)),
//                () -> readTestData("O1")
//                        .stream()
//                        .map(line -> jvLinkModelMapper.deserialize(line, WinsPlaceBracketQuinella.class)),
                () -> readTestData("O2")
                        .stream()
                        .map(line -> jvLinkModelMapper.deserialize(JvLinkModelUtil.toByte(line), Quinella.class))
        );
    }

    @Test
    void test_H1モデルマッパー_データは単一ファイル() {
        // この子は、特別nullを許容しているので。
        readTestData("H1")
                .stream()
                .map(line -> jvLinkModelMapper.deserialize(JvLinkModelUtil.toByte(line), VoteCount.class))
                .map(this::toJson)
                .forEach(System.out::println);
    }


    public static List<List<Integer>> nPr(final int n, final int r) {
        List<Integer> input = IntStream.rangeClosed(1, n)
                .boxed()
                .collect(Collectors.toList());

        return Collections2.permutations(input).stream()
                .map(list -> Lists.partition(list, r).get(0))
                .distinct()
                .collect(Collectors.toList());
    }

    public static Set<Set<Integer>> nCr(final int n, final int r) {
        return nPr(n, r).stream()
                .map(Sets::newHashSet)
                .collect(Collectors.toSet());
    }


    /**
     * データの長さ調査
     */
    private Stream<String> readLines(Path filePath) {
        try {
            return Files.lines(filePath, Charset.forName("SHIFT-JIS"));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("ファイルが存在しませんでした");
        }
    }

    private Path getDirectoryPath() throws IOException {
        return resourceLoader
                .getResource("classpath:test-data/")
                .getFile()
                .toPath();
    }

//    @Test
//    void test_ディレクトリ配下のファイルたちのデータの長さ確認() throws IOException {
//        Files.list(getDirectoryPath())
//                .flatMap(filePath -> readLines(filePath)
//                        .filter(Objects::nonNull)
//                        .map(i -> i.replace("\\n", "")) // 仮おきデータを除外
//                        .map(line -> JvLinkModelUtil.toByte(line).length)
//                        .map(i -> Tuples.of(filePath.toFile().getName(), i)))
//                .forEach(i ->
//                        assertEquals(i.getT2() + 2, RecordSpec.of(i.getT1()).getLength())
//                );
//    }

}