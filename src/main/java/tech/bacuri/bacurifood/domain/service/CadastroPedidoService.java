package tech.bacuri.bacurifood.domain.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tech.bacuri.bacurifood.domain.exception.PedidoNaoEncontradoException;
import tech.bacuri.bacurifood.domain.model.Pedido;
import tech.bacuri.bacurifood.domain.repository.PedidoRepository;

@AllArgsConstructor
@Service
public class CadastroPedidoService {
    private final PedidoRepository pedidoRepository;

    public Pedido obter(Long pedidoId) {
        return pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new PedidoNaoEncontradoException(pedidoId));
    }
}
