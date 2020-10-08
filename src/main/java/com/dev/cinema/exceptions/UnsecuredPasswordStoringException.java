package com.dev.cinema.exceptions;

public class UnsecuredPasswordStoringException extends RuntimeException {
    public UnsecuredPasswordStoringException(String message, Throwable cause) {
        super(message, cause);
    }
}
