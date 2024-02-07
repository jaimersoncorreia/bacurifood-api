package tech.bacuri.bacurifood.domain.exception;

public class PedidoNaoEncontradoException extends EntidadeNaoEncontradaException {

    public PedidoNaoEncontradoException(String pedidoCodigo) {
        super(String.format("Não existe um cadastro de pedido com código %s", pedidoCodigo));
    }
}
