package tech.bacuri.bacurifood.domain.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tech.bacuri.bacurifood.domain.exception.PermissaoNaoEncontradoException;
import tech.bacuri.bacurifood.domain.model.Permissao;
import tech.bacuri.bacurifood.domain.repository.PermissaoRepository;

@AllArgsConstructor
@Service
public class CadastroPermissaoService {

    private final PermissaoRepository permissaoRepository;

    public Permissao obter(Long permissaoId) {
        return permissaoRepository
                .findById(permissaoId)
                .orElseThrow(() -> new PermissaoNaoEncontradoException(permissaoId));
    }
}
