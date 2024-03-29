package org.uma.cloud.common.utils.javatuples;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;
import java.util.List;

public class Pair<A, B> implements Tuple {

    private static final long serialVersionUID = -8813360138277013769L;

    private final A value1;
    private final B value2;

    @JsonCreator
    private Pair(@JsonProperty("value1") A value1, @JsonProperty("value2") B value2) {
        this.value1 = value1;
        this.value2 = value2;
    }

    public static <A, B> Pair<A, B> with(final A value1, final B value2) {
        return new Pair<>(value1, value2);
    }

    public static <A, B> Pair<A, B> copyOf(final Pair<A, B> pair) {
        return new Pair<>(pair.getValue1(), pair.getValue2());
    }


    public A getValue1() {
        return this.value1;
    }

    public B getValue2() {
        return this.value2;
    }


    @Override
    public final List<Object> toList() {
        return Arrays.asList(getValue1(), getValue2());
    }


    @Override
    public final String toString() {
        return this.value1 + "-" + this.value2;
    }

}
