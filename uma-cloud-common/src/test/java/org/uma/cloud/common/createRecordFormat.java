package org.uma.cloud.common;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;


@SpringBootTest
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class createRecordFormat {

    private final ResourceLoader resourceLoader;

    @Test
    void test_Format作成() throws IOException {
        createRecord("jg.txt");
    }

    private Stream<String> readLine(String filename) throws IOException {
        return Files.lines(resourceLoader
                .getResource("classpath:test-record-format/" + filename)
                .getFile()
                .toPath());
    }

    private void createRecord(String filename) throws IOException {
        Objects.requireNonNull(filename);
        String prefix = filename.substring(0, 2);

        List<Header> once = readLine(filename)
                .filter(Objects::nonNull)
                .map(format -> format.split("\t"))
                .map(format -> new Header(format[0], format[1], format[2], format[3]))
                .collect(Collectors.toList());

        List<String> result = new ArrayList<>();
        IntStream.range(0, once.size())
                .forEach(i -> {
                    result.add(String.format("%s.recordItems[" + i + "].column=" + once.get(i).getColumn(), prefix));
                    result.add(String.format("%s.recordItems[" + i + "].start=" + once.get(i).getStart(), prefix));
                    result.add(String.format("%s.recordItems[" + i + "].length=" + once.get(i).getLength(), prefix));
                    result.add(String.format("%s.recordItems[" + i + "].repeat=" + once.get(i).getRepeat(), prefix));
                });

        result.forEach(System.out::println);
    }

    @Data
    @RequiredArgsConstructor
    private static class Header {
        private final String column;
        private final String start;
        private final String length;
        private final String repeat;
    }


//    ConfigurationProperties は、小文字しか設定できないかも。。
//    @Test
//    void test_JvLinkRecordPropertiesのprefixをUpperにする() throws IOException {
//        Path inputPath = getPath("JvLinkRecord.properties");
////        Path outputPath = getPath("JvLinkRecord.properties2");
//        Files.lines(inputPath)
//                .filter(i -> !i.startsWith("#"))
//                .filter(i -> !"".equals(i))
//                .map(line -> line.replace(line.substring(0, 2), line.substring(0, 2).toUpperCase()))
//                .forEach(System.out::println);
//
////                .forEach(line -> JvLink.writer(outputPath, StandardCharsets.UTF_8, line));
//    }
//
//    private Path getPath(String filename) {
//        Objects.requireNonNull(filename);
//        try {
//            return resourceLoader
//                    .getResource("classpath:" + filename)
//                    .getFile()
//                    .toPath();
//        } catch (IOException e) {
//            e.printStackTrace();
//            throw new RuntimeException();
//        }
//
//    }


}
