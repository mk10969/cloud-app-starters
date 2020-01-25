package org.uma.platform.common.utils.javatuples;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

public interface Tuple extends Serializable {


    List<Object> toList();


    default boolean contains(Object value) {
        for (final Object val : toList()) {
            if (val == null) {
                if (value == null) {
                    return true;
                }
            } else {
                if (val.equals(value)) {
                    return true;
                }
            }
        }
        return false;
    }

    default boolean containsAll(Collection<?> collection) {
        for (final Object value : collection) {
            if (!contains(value)) {
                return false;
            }
        }
        return true;
    }

    default boolean containsAll(Object... values) {
        if (values == null) {
            throw new IllegalArgumentException("Values array cannot be null");
        }
        for (final Object value : values) {
            if (!contains(value)) {
                return false;
            }
        }
        return true;
    }

}
