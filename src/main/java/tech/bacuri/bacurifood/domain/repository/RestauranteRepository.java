package tech.bacuri.bacurifood.domain.repository;

import tech.bacuri.bacurifood.domain.model.Restaurante;

import java.util.List;

public interface RestauranteRepository {

    List<Restaurante> todas();

    Restaurante obter(Long id);

    Restaurante salvar(Restaurante restaurante);

    void remover(Restaurante restaurante);
}
