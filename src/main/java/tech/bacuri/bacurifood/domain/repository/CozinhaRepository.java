package tech.bacuri.bacurifood.domain.repository;

import tech.bacuri.bacurifood.domain.model.Cozinha;

import java.util.List;

public interface CozinhaRepository {

    List<Cozinha> listar();

    List<Cozinha> consultarPorNome(String nome);

    Cozinha obter(Long id);

    Cozinha salvar(Cozinha cozinha);

    void remover(Long cozinhaId);
}
