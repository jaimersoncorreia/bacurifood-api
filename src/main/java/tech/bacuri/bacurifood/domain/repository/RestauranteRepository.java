package tech.bacuri.bacurifood.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tech.bacuri.bacurifood.domain.model.Restaurante;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface RestauranteRepository extends JpaRepository<Restaurante, Long>, RestauranteRepositoryQueries {
    List<Restaurante> findAllByTaxaFreteBetween(BigDecimal taxaInicial, BigDecimal taxaFinal);

    @Query("from Restaurante where nome like %:nome% and cozinha.id = :id")
    List<Restaurante> consultarPorNome(String nome, @Param("id") Long cozinhaId);

    List<Restaurante> consultarPorNome2(String nome, @Param("id") Long cozinhaId);

    List<Restaurante> findAllByNomeContainingAndCozinhaId(String nome, Long cozinha);

    Optional<Restaurante> findFirstRestauranteByNomeContaining(String nome);

    List<Restaurante> findTop2RestauranteByNomeContaining(String nome);

    Long countByCozinhaId(Long id);
}
