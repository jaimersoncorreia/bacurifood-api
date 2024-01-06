package tech.bacuri.bacurifood.domain.repository;

import tech.bacuri.bacurifood.domain.model.Cidade;

import java.util.List;

public interface CidadeRepository {

    List<Cidade> todas();

    Cidade obter(Long id);

    Cidade salvar(Cidade cidade);

    void remover(Cidade cidade);
}
