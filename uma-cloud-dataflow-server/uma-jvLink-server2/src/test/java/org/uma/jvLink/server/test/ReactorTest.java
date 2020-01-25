package org.uma.jvLink.server.test;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.DirectProcessor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.GroupedFlux;
import reactor.test.StepVerifier;
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;


public class ReactorTest {

    @Test
    void test_まず初めに() {
        Flux<String> flux = Flux.just("hoge", "foo", "bar");
        StepVerifier
                .create(flux)
                .expectNext("hoge", "foo", "bar")
                .verifyComplete();
    }

    @Test
    void test_allメソッドテスト() throws InterruptedException {
        Flux.just("hoge", "foo", "bar")
                .all(Objects::nonNull) // 中身全てチェックするということね。
                .doOnNext(System.out::println)
                .subscribe();
        Thread.sleep(1000L);
    }


    private Flux<Tuple2<Long, Long>> pass() {
        return Flux.interval(Duration.ofMinutes(1))
                .elapsed();
    }

    @Test
    void test_カンマスピリット() {
        Flux<String> flux = Flux.just("aaa,bb", "b,ccc,ddddd,e", "ee,ff", "ff,g,hh,i", "i", "iii,jjj,")
                .reduce((i, j) -> i + j)
                .flatMapMany(i -> Flux.fromArray(i.split(",")))
                .doOnNext(System.out::println);

        StepVerifier
                .create(flux)
                .expectNext("aaa", "bbb", "ccc", "ddddd", "eee", "ffff", "g", "hh", "iiiii", "jjj")
                .verifyComplete();
    }

    private Flux<String> flux = Flux.just("aaa,bb", "b,ccc,ddddd,e", "ee,ff", "ff,g,hh,i", "i", "iii,jjj,")
            .repeat()
            .bufferUntil(i -> i.endsWith(","))
            .flatMap(i -> Flux.fromArray(i.stream().collect(Collectors.joining()).split(",")))
            .doOnNext(System.out::println);

    @Test
    void test_カンマスピリットwithリピート_and_verifyComplete() {
        StepVerifier.create(flux)
                .expectNext("aaa", "bbb", "ccc", "ddddd", "eee", "ffff", "g", "hh", "iiiii", "jjj")
                .expectNext("aaa", "bbb", "ccc", "ddddd", "eee", "ffff", "g", "hh", "iiiii", "jjj")
                .expectNext("aaa", "bbb", "ccc", "ddddd", "eee", "ffff", "g", "hh", "iiiii", "jjj")
                .verifyComplete();
    }

    @Test
    void test_カンマスピリットwithリピート_and_verify() {
        StepVerifier.create(flux)
                .expectNext("aaa", "bbb", "ccc", "ddddd", "eee", "ffff", "g", "hh", "iiiii", "jjj")
                .expectNext("aaa", "bbb", "ccc", "ddddd", "eee", "ffff", "g", "hh", "iiiii", "jjj")
                .expectNext("aaa", "bbb", "ccc", "ddddd", "eee", "ffff", "g", "hh", "iiiii", "jjj")
                .expectComplete() //完了を期待するので、エラーになる。
                .verify();
    }

    // これを使うと、repeatの意味がなくなる・・・
    // .takeUntil(i -> i.endsWith(",")) trueを返すと終了。ただし条件に合致するデータは含まれる。
    // .takeWhile(i -> !i.endsWith(",")) falseを返すと終了。ただし条件に合致するデータは含まれない。


    @Test
    void testtest() {
        String str = "aaa,bbb,ccc,ddddd,eee,ffff,g,hh,iiiii,jjj,";
        String[] arr = str.split(",");
        // 最後のカンマ以降のデータはなしと見られるのね。
        System.out.println(arr.length);
    }

    @Test
    void test_HotFlux() {
        // これ動きが面白いね。
        DirectProcessor<String> hotSource = DirectProcessor.create();
        Flux<String> hotFlux = hotSource.map(String::toUpperCase);

        hotFlux.subscribe(d -> System.out.println("No1: " + d));
        hotSource.onNext("blue");
        hotSource.onNext("green");

        hotFlux.subscribe(d -> System.out.println("No2: " + d));
        hotSource.onNext("orange");
        hotSource.onNext("purple");

        hotSource.onComplete(); //終わりの合図。
    }


    @Test
    void test_transform() {
        Function<Flux<String>, Flux<String>> fillterAndMap = f -> f
                .filter(i -> !i.equals("b"))
                .map(String::toUpperCase);

        Flux<String> transformFlux = Flux.fromIterable(Arrays.asList("a", "b", "c", "d"))
                .doOnNext(System.out::println)
                .transform(fillterAndMap);
        transformFlux.subscribe(System.out::println);

    }


    private final List<String> tests = Arrays.asList(
            "TEST 1",
            "TEST 2",
            "TEST 3",
            "INTEGRATION 1",
            "INTEGRATION 2");

    @Test
    void test_replaceAll() {
        // 文字クラスの[の直後に^を置くと否定を表現できる。
        // [^ab]とすると「ａ」または「ｂ」以外を表せる。
        String str = "TEST 1";
        System.out.println(str.replaceAll("[^a-zA-Z]", ""));
    }


    @Test
    void test_groupテスト() {
        Flux<GroupedFlux<String, String>> flux = Flux
                .fromIterable(tests)
                .groupBy(test -> test.replaceAll("[^a-zA-Z]", ""));
        StepVerifier.create(flux)
                .expectNextMatches(groupedFlux -> Objects.equals(groupedFlux.key(), "TEST"))
                .expectNextMatches(groupedFlux -> Objects.equals(groupedFlux.key(), "INTEGRATION"))
                .verifyComplete();

        Flux<String> sum = flux
                .flatMap(groupedFlux -> groupedFlux
                        .map(i -> i.replaceAll("[^0-9]", ""))
                        .map(Integer::valueOf)
                        .reduce(Integer::sum)
                        .map(i -> groupedFlux.key() + " TOTAL: " + i))
                .sort();

        StepVerifier.create(sum)
                .expectNext("INTEGRATION TOTAL: 3")
                .expectNext("TEST TOTAL: 6")
                .verifyComplete();
    }


    @Test
    void test_bufferテスト() throws InterruptedException {
        // buffer使うとエラーになる。。なんで。。
        Flux<List<String>> bufferedFlux = Flux
                .fromIterable(tests)
                .buffer(Duration.ofSeconds(5));

        StepVerifier.withVirtualTime(() -> bufferedFlux)
                .expectNext(tests) //見た目上、中身は一致しているようにみえるのだが。
                .verifyComplete();

    }

    @Test
    void test_ListAssertテスト() {
        // エラーにならないね。。。
        List<String> a = Arrays.asList("a1", "a2");
        List<String> b = Arrays.asList("b1", "b2");

        StepVerifier.create(Flux.just(a, b))
                .expectNext(a, b)
                .verifyComplete();

    }


}
