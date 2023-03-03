package com.parkinglotsapi.exception;

public class BatchSkipException extends RuntimeException {

    public BatchSkipException() {
        super();
    }

    public BatchSkipException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public BatchSkipException(final String message) {
        super(message);
    }

    public BatchSkipException(final Throwable cause) {
        super(cause);
    }

}
