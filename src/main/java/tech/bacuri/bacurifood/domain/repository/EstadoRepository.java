package tech.bacuri.bacurifood.domain.repository;

import tech.bacuri.bacurifood.domain.model.Estado;

import java.util.List;

public interface EstadoRepository {

    List<Estado> listar();

    Estado obter(Long id);

    Estado salvar(Estado estado);

    void remover(Estado estado);
}
