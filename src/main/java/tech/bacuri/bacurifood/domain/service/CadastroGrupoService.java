package tech.bacuri.bacurifood.domain.service;

import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.bacuri.bacurifood.domain.exception.EntidadeEmUsoException;
import tech.bacuri.bacurifood.domain.exception.GrupoNaoEncontradoException;
import tech.bacuri.bacurifood.domain.model.Grupo;
import tech.bacuri.bacurifood.domain.model.Permissao;
import tech.bacuri.bacurifood.domain.repository.GrupoRepository;

import java.util.List;

@AllArgsConstructor
@Service
public class CadastroGrupoService {
    private static final String MSG_GRUPO_EM_USO = "Grupo de código %d não pode ser removida, pois está em uso";
    private final GrupoRepository grupoRepository;
    private final CadastroPermissaoService cadastroPermissaoService;

    public List<Grupo> listar() {
        return grupoRepository.findAll();
    }

    public Grupo obter(Long grupoId) {
        return grupoRepository
                .findById(grupoId)
                .orElseThrow(() -> new GrupoNaoEncontradoException(grupoId));
    }

    @Transactional
    public Grupo salvar(Grupo grupo) {
        return grupoRepository.save(grupo);
    }

    @Transactional
    public void remover(Long grupoId) {
        try {
            grupoRepository.deleteById(grupoId);
            grupoRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new GrupoNaoEncontradoException(grupoId);
        } catch (DataIntegrityViolationException e) {
            String msg = String.format(MSG_GRUPO_EM_USO, grupoId);
            throw new EntidadeEmUsoException(msg);
        }
    }

    @Transactional
    public void removerPermissao(Long grupoId, Long pemissaoId) {
        Grupo grupo = obter(grupoId);
        Permissao permissao = cadastroPermissaoService.obter(pemissaoId);
        grupo.removerPermissao(permissao);
    }

    @Transactional
    public void atribuirPermissao(Long grupoId, Long pemissaoId) {
        Grupo grupo = obter(grupoId);
        Permissao permissao = cadastroPermissaoService.obter(pemissaoId);
        grupo.atribuirPermissao(permissao);
    }
}
