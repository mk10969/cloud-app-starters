package org.uma.cloud.common.utils.javatuples;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;
import java.util.List;

public class Triplet<A, B, C> implements Tuple {

    private static final long serialVersionUID = 3468566296525061358L;

    private final A value1;
    private final B value2;
    private final C value3;


    @JsonCreator
    private Triplet(
            @JsonProperty("value1") A value1,
            @JsonProperty("value2") B value2,
            @JsonProperty("value3") C value3) {
        this.value1 = value1;
        this.value2 = value2;
        this.value3 = value3;
    }

    public static <A, B, C> Triplet<A, B, C> with(final A value1, final B value2, final C value3) {
        return new Triplet<>(value1, value2, value3);
    }

    public A getValue1() {
        return this.value1;
    }

    public B getValue2() {
        return this.value2;
    }

    public C getValue3() {
        return this.value3;
    }


    @Override
    public List<Object> toList() {
        return Arrays.asList(getValue1(), getValue2(), getValue3());
    }

    @Override
    public String toString() {
        return this.value1 + "-" + this.value2 + "-" + this.value3;
    }

}
