//package org.uma.cloud.common.test;
//
//import org.junit.jupiter.api.Test;
//import reactor.core.publisher.Flux;
//import reactor.core.publisher.Mono;
//import reactor.core.publisher.SignalType;
//
//import java.time.Duration;
//import java.util.Arrays;
//import java.util.List;
//import java.util.concurrent.atomic.AtomicInteger;
//import java.util.stream.Stream;
//
//class JvDataSetupServiceTest {
//
//    private enum Status {
//        WAIT, RUNNNIG, END, ERROR
//    }
//
//    @Test
//    void tests() throws InterruptedException {
//
//        Flux.fromStream(Stream.of(1, 2, 3, 4, 5))
//                .flatMap(i -> Flux.just(i * 2)
//                        .doOnNext(j -> System.out.println(Status.RUNNNIG)))
//                .doOnNext(System.out::println)
//                .then(Mono.just(Status.END))
//                .subscribe(System.out::println);
//
//        Thread.sleep(3000L);
//    }
//
//
//    private final Flux<String> flux = Flux.just("A", "B", "C", "D")
//            .map(a -> {
//                if (a.equals("B")) {
//                    throw new RuntimeException("ERROR");
//                }
//                return a;
//            });
//
//    // 型が固定されるから、微妙かも。
//    @Test
//    void tests_errorHandle1() throws InterruptedException {
//        flux.onErrorReturn("C")
//                .log()
//                .subscribe(System.out::println);
//        Thread.sleep(1000L);
//    }
//
//    // Monoで返せるから、これは使えるかも。
//    @Test
//    void tests_errorHandle2() throws InterruptedException {
//        // 継承関係がアレなんですね。
//        flux.doOnError(System.out::println)
//                .onErrorResume(RuntimeException.class, (e) -> Mono.just("errorです"))
////                .log()
//                .subscribe(System.out::println);
//        Thread.sleep(1000L);
//    }
//
//
//    @Test
//    void tests_errorHandle3() throws InterruptedException {
//        flux.doOnError(System.out::println)
//                .log()
//                .subscribe(System.out::println);
//        Thread.sleep(1000L);
//    }
//
//    @Test
//    void test_doFinally() throws InterruptedException {
//        AtomicInteger atomicInteger = new AtomicInteger(0);
//
//        Flux<String> flux111 = Flux.just("A", "B", "C", "D")
//                .log()
//                .delayElements(Duration.ofMillis(100))
//                .doOnNext(element -> {
//                    int i = atomicInteger.incrementAndGet();
//                    System.out.println("event number" + i + "emitted ");
//                })
//                .doFinally(type -> {
//                    // doFinally consumes a SignalType for the type of termination
//                    System.out.println("Signal Type :" + type.name());
//                    if (type == SignalType.CANCEL) {
//                        System.out.println("Final number of events emitted" + atomicInteger.get());
//                    }
//                    // we take only three events, then a cancel signal should be emitted.
//                }).take(3);
//
//        flux111.subscribe();
//        Thread.sleep(1000L);
//    }
//
//
//    @Test
//    void aaaa(){
//        // いまいちだな
//        List<Integer> aaa = Arrays.asList(1,2,4);
//        System.out.println(aaa.getClass().getDeclaredFields()[0].getGenericType().getTypeName());
//    }
//
//}