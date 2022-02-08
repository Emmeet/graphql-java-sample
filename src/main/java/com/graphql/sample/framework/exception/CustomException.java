package com.graphql.sample.framework.exception;

public class CustomException extends RuntimeException {
    private static final long serialVersionUID = 7810584227602028484L;

    public CustomException() { }

    public CustomException(String message) {
        super(message);
    }

    public CustomException(String message, Throwable cause) {
        super(message, cause);
    }

    public CustomException(Throwable cause) {
        super(cause);
    }

    public CustomException(long nCode) {
        super("Custom Exception Info [" + nCode + "]");
    }
}
