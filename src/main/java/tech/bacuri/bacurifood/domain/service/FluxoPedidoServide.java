package tech.bacuri.bacurifood.domain.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class FluxoPedidoServide {

    private EmissaoPedidoService emissaoPedido;

    @Transactional
    public void confirmar(String pedidoCodigo) {
        emissaoPedido.obter(pedidoCodigo).confirmar();
    }

    @Transactional
    public void entregar(String pedidoCodigo) {
        emissaoPedido.obter(pedidoCodigo).entregar();
    }

    @Transactional
    public void cancelar(String pedidoCodigo) {
        emissaoPedido.obter(pedidoCodigo).cancelar();
    }
}
