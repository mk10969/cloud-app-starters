package org.uma.jvLink.server.component;

import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JvSetupCommandLineRunnerTest {

    @Test
    void test() {
        assertEquals("a", "a");
    }

    private Stream<Double> numberStream() {
        return Stream.generate(Math::random)
                .limit(300)
                .onClose(() -> System.out.println("close呼ばれました"));
    }

    @Test
    void test_() {
        Stream<Double> once = numberStream();
        once.forEach(System.out::println);
        System.out.println("foreach 終わり");
        once.close();
    }


    private Stream<Double> tryWithResource() {
        try (Stream<Double> test = numberStream()) {
            return test.map(i -> i * 100);
        }
    }

    @Test
    void test_tryWithResource() {
        // 先にcloseが呼ばれる。そりゃそうか。。
        tryWithResource().forEach(System.out::println);
        System.out.println("foreach 終わり");

    }


}