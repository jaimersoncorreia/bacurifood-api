package tech.bacuri.bacurifood.domain.exception;

public class EntidadeEmUsoException extends RuntimeException {
    public EntidadeEmUsoException() {
    }

    public EntidadeEmUsoException(String message) {
        super(message);
    }

    public EntidadeEmUsoException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntidadeEmUsoException(Throwable cause) {
        super(cause);
    }

    public EntidadeEmUsoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
