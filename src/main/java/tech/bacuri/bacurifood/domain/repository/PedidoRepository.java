package tech.bacuri.bacurifood.domain.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tech.bacuri.bacurifood.domain.model.Pedido;

import java.util.List;

@Repository
public interface PedidoRepository extends CustomJpaRepository<Pedido, Long> {
    @Override
    @Query("from Pedido p join fetch p.cliente join fetch p.restaurante r join fetch r.cozinha")
    List<Pedido> findAll();
}
