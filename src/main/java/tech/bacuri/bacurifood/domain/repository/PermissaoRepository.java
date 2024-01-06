package tech.bacuri.bacurifood.domain.repository;

import tech.bacuri.bacurifood.domain.model.Permissao;

import java.util.List;

public interface PermissaoRepository {

    List<Permissao> todas();

    Permissao obter(Long id);

    Permissao salvar(Permissao permissao);

    void remover(Permissao permissao);
}
