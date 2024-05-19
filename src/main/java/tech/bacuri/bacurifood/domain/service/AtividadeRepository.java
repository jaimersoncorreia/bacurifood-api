package tech.bacuri.bacurifood.domain.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.bacuri.bacurifood.domain.model.Atividade;

@Repository
public interface AtividadeRepository extends JpaRepository<Atividade, Long> {
}
