package tech.bacuri.bacurifood.domain.exception;

public class NegocioException extends RuntimeException {
    public NegocioException(String mensagem) {
        super(mensagem);
    }

    public NegocioException(String message, Throwable causa) {
        super(message, causa);
    }
}
