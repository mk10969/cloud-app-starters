package org.uma.platform.common.utils.exception;

import java.io.Serializable;

public class NoSuchMethodRuntimeException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = -116389164740347929L;


    public NoSuchMethodRuntimeException(String message) {
        super(message);
    }

    public NoSuchMethodRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchMethodRuntimeException(Throwable cause) {
        super(cause);
    }
}
