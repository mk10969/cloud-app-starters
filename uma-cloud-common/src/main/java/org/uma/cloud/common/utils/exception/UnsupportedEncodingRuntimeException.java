package org.uma.platform.common.utils.exception;

import java.io.Serializable;

public class UnsupportedEncodingRuntimeException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = 307987415240755458L;

    public UnsupportedEncodingRuntimeException(String message) {
        super(message);
    }

    public UnsupportedEncodingRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnsupportedEncodingRuntimeException(Throwable cause) {
        super(cause);
    }

}
