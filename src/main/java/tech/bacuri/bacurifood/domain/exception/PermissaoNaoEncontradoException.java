package tech.bacuri.bacurifood.domain.exception;

public class PermissaoNaoEncontradoException extends EntidadeNaoEncontradaException{
    public PermissaoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public PermissaoNaoEncontradoException(Long permissaoId) {
        this(String.format("Não existe um cadastro de permissão com código %d", permissaoId));
    }
}
