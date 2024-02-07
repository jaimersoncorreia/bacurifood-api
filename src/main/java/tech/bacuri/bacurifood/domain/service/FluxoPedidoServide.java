package tech.bacuri.bacurifood.domain.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class FluxoPedidoServide {

    private EmissaoPedidoService emissaoPedido;

    @Transactional
    public void confirmar(Long pedidoId) {
        emissaoPedido.obter(pedidoId).confirmar();
    }

    @Transactional
    public void entregar(Long pedidoId) {
        emissaoPedido.obter(pedidoId).entregar();
    }

    @Transactional
    public void cancelar(Long pedidoId) {
        emissaoPedido.obter(pedidoId).cancelar();
    }
}
