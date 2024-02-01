package tech.bacuri.bacurifood.domain.repository;

import org.springframework.stereotype.Repository;
import tech.bacuri.bacurifood.domain.model.Permissao;

@Repository
public interface PermissaoRepository extends CustomJpaRepository<Permissao, Long> {

}
