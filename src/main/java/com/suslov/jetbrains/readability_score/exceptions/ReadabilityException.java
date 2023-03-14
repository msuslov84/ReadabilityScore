package com.suslov.jetbrains.readability_score.exceptions;

/**
 * @author Mikhail Suslov
 */
public class ReadabilityException extends RuntimeException {

    public ReadabilityException(String message) {
        super(message);
    }

    public ReadabilityException(String message, Throwable cause) {
        super(message, cause);
    }
}
