package tech.bacuri.bacurifood.domain.repository;

import org.springframework.stereotype.Repository;
import tech.bacuri.bacurifood.domain.model.Pedido;

@Repository
public interface PedidoRepository extends CustomJpaRepository<Pedido, Long> {
}
