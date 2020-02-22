package org.uma.cloud.common.utils.exception;

public class CharacterCodingRuntimeException extends RuntimeException {

    public CharacterCodingRuntimeException(String message) {
        super(message);
    }

    public CharacterCodingRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public CharacterCodingRuntimeException(Throwable cause) {
        super(cause);
    }
}
