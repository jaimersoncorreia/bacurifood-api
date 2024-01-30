package tech.bacuri.bacurifood.domain.repository;

import org.springframework.stereotype.Repository;
import tech.bacuri.bacurifood.domain.model.Grupo;

@Repository
public interface GrupoRepository extends CustomJpaRepository<Grupo, Long> {

}
