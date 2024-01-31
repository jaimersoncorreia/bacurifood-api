package tech.bacuri.bacurifood.domain.repository;

import org.springframework.stereotype.Repository;
import tech.bacuri.bacurifood.domain.model.Produto;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProdutoRepository extends CustomJpaRepository<Produto, Long> {

    List<Produto> findAllByRestauranteId(Long restauranteId);

    Optional<Produto> findByRestauranteIdAndId(Long restauranteId, Long produtoId);
}
