package tech.bacuri.bacurifood.domain.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.bacuri.bacurifood.domain.exception.NegocioException;
import tech.bacuri.bacurifood.domain.exception.PedidoNaoEncontradoException;
import tech.bacuri.bacurifood.domain.model.*;
import tech.bacuri.bacurifood.domain.repository.PedidoRepository;

@AllArgsConstructor
@Service
public class EmissaoPedidoService {
    private final PedidoRepository pedidoRepository;
    private final CadastroProdutoService produtoService;
    private final CadastroCidadeService cidadeService;
    private final CadastroUsuarioService usuarioService;
    private final CadastroRestauranteService restauranteService;
    private final CadastroFormaPagamentoService formaPagamentoService;

    public Pedido obter(String pedidoCodigo) {
        return pedidoRepository.findByCodigo(pedidoCodigo)
                .orElseThrow(() -> new PedidoNaoEncontradoException(pedidoCodigo));
    }

    @Transactional
    public Pedido salvar(Pedido pedido) {
        validarPedido(pedido);
        validarItens(pedido);

        pedido.setTaxaFrete(pedido.getRestaurante().getTaxaFrete());
        pedido.calcularValorTotal();

        return pedidoRepository.save(pedido);
    }

    private void validarItens(Pedido pedido) {
        pedido.getItens().forEach(item -> {
            Produto produto = produtoService.obter(pedido.getRestaurante().getId(), item.getProduto().getId());
            item.setPedido(pedido);
            item.setProduto(produto);
            item.setPrecoUnitario(produto.getPreco());
        });
    }

    private void validarPedido(Pedido pedido) {
        Cidade cidade = cidadeService.obter(pedido.getEnderecoEntrega().getCidade().getId());
        Usuario cliente = usuarioService.obter(pedido.getCliente().getId());
        Restaurante restaurante = restauranteService.obter(pedido.getRestaurante().getId());
        FormaPagamento formaPagamento = formaPagamentoService.obter(pedido.getFormaPagamento().getId());

        pedido.getEnderecoEntrega().setCidade(cidade);
        pedido.setCliente(cliente);
        pedido.setRestaurante(restaurante);
        pedido.setFormaPagamento(formaPagamento);

        if (restaurante.naoAceitaFormaPagamento(formaPagamento)) {
            throw new NegocioException(String.format("Forma de pagamento '%s' não é aceita por esse restaurante.",
                    formaPagamento.getDescricao()));
        }
    }
}
