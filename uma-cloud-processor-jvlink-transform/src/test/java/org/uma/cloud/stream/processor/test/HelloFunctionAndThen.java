package org.uma.cloud.stream.processor.test;

import org.junit.jupiter.api.Test;

import java.util.function.Function;
import java.util.stream.Stream;

public class HelloFunctionAndThen {

    private Function<String, String> wrapDoubleQuotation = str -> "\"" + str + "\"";
    private Function<String, String> wrapSingleQuotation = str -> "'" + str + "'";


    @Test
    void test() {

        String str1 = wrapDoubleQuotation.andThen(wrapSingleQuotation).apply("hello");
        String str2 = wrapDoubleQuotation.compose(wrapSingleQuotation).apply("hello");

        System.out.println(str1);
        System.out.println(str2);

    }

    @Test
    void test__(){
        String str = addFunction(wrapDoubleQuotation, wrapSingleQuotation).apply("world");
        System.out.println(str);
    }


    private Function<String, String> addFunction(Function<String, String>... filter){
        // reduceの使い方がうまい。
        return Stream.of(filter).reduce(Function::andThen).orElse(Function.identity());
    }


//
//    private final Function<> function;
//
//    private Flux<StoredOpenCondition> test(RecordSpec... recordSpec) {
//        // filterをresumeしたいね。
//
//        Stream.of(recordSpec).reduce((recordSpec1, next) ->);
//
//        return Flux.fromStream()
//    }
//
//    private Stream<RecordSpec> filter(RecordSpec recordSpec) {
//        return conditions.stream().filter(i -> i.getRecordType() == recordSpec).map()
//    }
//

}
