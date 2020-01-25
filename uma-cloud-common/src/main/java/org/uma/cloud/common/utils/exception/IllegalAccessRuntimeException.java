package org.uma.platform.common.utils.exception;

import java.io.Serializable;

public class IllegalAccessRuntimeException extends RuntimeException implements Serializable {
    private static final long serialVersionUID = 1436771036588572456L;

    public IllegalAccessRuntimeException(String message) {
        super(message);
    }

    public IllegalAccessRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalAccessRuntimeException(Throwable cause) {
        super(cause);
    }
}
