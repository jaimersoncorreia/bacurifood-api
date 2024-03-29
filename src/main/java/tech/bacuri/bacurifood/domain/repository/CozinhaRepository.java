package tech.bacuri.bacurifood.domain.repository;

import org.springframework.stereotype.Repository;
import tech.bacuri.bacurifood.domain.model.Cozinha;

import java.util.List;

@Repository
public interface CozinhaRepository extends CustomJpaRepository<Cozinha, Long> {
    List<Cozinha> findAllByNomeContaining(String string);

    boolean existsByNome(String nome);
}
