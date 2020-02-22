//package org.uma.cloud.common.test;
//
//import org.junit.jupiter.api.Test;
//import reactor.core.publisher.Flux;
//import reactor.core.publisher.Mono;
//
//import java.util.Objects;
//import java.util.stream.Stream;
//
//public class TestFluxAndMono {
//
//    @Test
//    void test_Mono_create() {
//        // NullPOも拾ってくれるのね。
//        String str = "aaa";
//        throwNullPo(str).subscribe(
//                i -> System.out.println(i),
//                e -> e.printStackTrace(),
//                () -> System.out.println("完了")
//        );
//    }
//
//    public Mono<String> throwNullPo(String str) {
//        return Mono.create(sink -> {
//            Objects.requireNonNull(str);
//            try {
//                sink.success(getName(str));
//            } catch (Exception e) {
//                // ここでスタックとレース吐かなくても、
//                // subscribe側で吐けるよ。
//                sink.error(e);
//            }
//        });
//    }
//
//    private String getName(String str) throws Exception {
//        throw new Exception("ERROR");
//    }
//
//
//    @Test
//    void test_doOnNext() {
//
//        Flux.just("aaa", "bbb", "ccc")
//                .doOnNext(this::str)
//                .subscribe(System.out::println);
//
//
//        Stream.of("aaa", "bbb", "ccc")
//                .peek(this::str)
//                .forEach(System.out::println);
//    }
//
//    private void str(String str){
//        str.substring(0, 1);
//    }
//
//}
