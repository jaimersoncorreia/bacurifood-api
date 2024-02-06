package tech.bacuri.bacurifood.domain.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.bacuri.bacurifood.domain.exception.NegocioException;
import tech.bacuri.bacurifood.domain.model.Pedido;

import java.time.OffsetDateTime;

import static tech.bacuri.bacurifood.domain.model.StatusPedido.CONFIRMADO;
import static tech.bacuri.bacurifood.domain.model.StatusPedido.CRIADO;

@AllArgsConstructor
@Service
public class FluxoPedidoServide {

    private EmissaoPedidoService emissaoPedido;

    @Transactional
    public void confirmar(Long pedidoId) {
        Pedido pedido = emissaoPedido.obter(pedidoId);
        if (!pedido.getStatusPedido().equals(CRIADO)) {
            throw new NegocioException(
                    String.format("Status do pedido %d não pode ser alterado de %s para %s.",
                            pedido.getId(), pedido.getStatusPedido().getDescricao(), CONFIRMADO.getDescricao()));
        }
        pedido.setStatusPedido(CONFIRMADO);
        pedido.setDataConfirmacao(OffsetDateTime.now());
    }
}
