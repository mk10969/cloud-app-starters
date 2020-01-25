package org.uma.platform.common.utils.exception;

import java.io.Serializable;

public class InvocationTargetRuntimeException extends RuntimeException implements Serializable {
    private static final long serialVersionUID = 4100189281389867578L;

    public InvocationTargetRuntimeException(String message) {
        super(message);
    }

    public InvocationTargetRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvocationTargetRuntimeException(Throwable cause) {
        super(cause);
    }
}
