package tech.bacuri.bacurifood.domain.exception;

public class CozinhaNaoEncontradaException extends EntidadeNaoEncontradaException {
    public CozinhaNaoEncontradaException(String mensagem) {
        super(mensagem);
    }

    public CozinhaNaoEncontradaException(Long cidadeId) {
        this(String.format("Não existe um cadastro de cozinha com código %d", cidadeId));
    }
}
