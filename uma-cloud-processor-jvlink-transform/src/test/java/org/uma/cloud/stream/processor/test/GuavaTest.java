package org.uma.cloud.stream.processor.test;

import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class GuavaTest {


    @Test
    void aaa() {
        List<Integer> list = Lists.newArrayList(1, 2, 3, 4, 5);
        Collections2.permutations(list).stream().forEach(System.out::println);

    }

    @Test
    void test_list分割() {
        List<Integer> list = Lists.newArrayList(1, 2, 3, 4, 5);
        List<List<Integer>> once = Lists.partition(list, 2);
        once.forEach(System.out::println);
    }

    @Test
    void test_List一致() {
        List<Integer> list1 = Lists.newArrayList(1, 2);
        List<Integer> list2 = Lists.newArrayList(1, 2);
        Stream.of(list1, list2).distinct()
                .forEach(System.out::println);
    }


    @Test
    void test_nPr() {
        System.out.println(nPr(5, 2).size());
        System.out.println(nCr(5, 2).size());
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


//    public static <X> List<List<X>> nPr2(List<X> input, final int r) {
//        Objects.requireNonNull(input);
//        return Collections2.permutations(input).stream()
//                .collect(ArrayList::new,
//                        (list, value) -> list.add(Lists.partition(value, r).get(0)),
//                        (list1, list2) -> list1.addAll(list2));
//    }


}


