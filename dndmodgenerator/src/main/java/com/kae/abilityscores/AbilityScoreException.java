package com.kae.abilityscores;

public class AbilityScoreException extends RuntimeException {
    public AbilityScoreException() {
    }

    public AbilityScoreException(String message) {
        super(message);
    }

    public AbilityScoreException(String message, Throwable cause) {
        super(message, cause);
    }

    public AbilityScoreException(Throwable cause) {
        super(cause);
    }
}
