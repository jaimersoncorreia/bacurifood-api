package tech.bacuri.bacurifood.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class EntidadeEmUsoException extends ResponseStatusException {
    public EntidadeEmUsoException(String message) {
        this(HttpStatus.CONFLICT, message);
    }

    public EntidadeEmUsoException(HttpStatus status, String mensagem) {
        super(status, mensagem);
    }
}
