package tech.bacuri.bacurifood.domain.service;

import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.bacuri.bacurifood.domain.exception.EntidadeEmUsoException;
import tech.bacuri.bacurifood.domain.exception.NegocioException;
import tech.bacuri.bacurifood.domain.exception.UsuarioNaoEncontradoException;
import tech.bacuri.bacurifood.domain.model.Usuario;
import tech.bacuri.bacurifood.domain.repository.UsuarioRepository;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class CadastroUsuarioService {
    private static final String MSG_USUARIO_EM_USO = "Usuário de código %d não pode ser removido, pois está em uso";
    private final UsuarioRepository usuarioRepository;

    public List<Usuario> listar() {
        return usuarioRepository.findAll();
    }

    public Usuario obter(Long usuarioId) {
        return usuarioRepository
                .findById(usuarioId)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(usuarioId));
    }

    @Transactional
    public Usuario salvar(Usuario usuario) {
        usuarioRepository.detach(usuario);
        Optional<Usuario> byEmail = usuarioRepository.findByEmail(usuario.getEmail());
        if (byEmail.isPresent() && !byEmail.get().equals(usuario)) {
            throw new NegocioException(String.format("Já existe um usuário cadastrado com o e-mail %s", usuario.getEmail()));
        }
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public Usuario atualizar(Usuario usuario) {
        return salvar(usuario);
    }

    @Transactional
    public void atualizarSenha(Long usuarioId, String senhaAtual, String senhaNova) {
        Usuario usuario = obter(usuarioId);
        if (usuario.senhaNaoCoincidemCom(senhaAtual))
            throw new NegocioException("Senha atual informada não coincide com a senha do usuário.");

        usuario.setSenha(senhaNova);
    }

    @Transactional
    public void remover(Long usuarioId) {
        try {
            usuarioRepository.deleteById(usuarioId);
            usuarioRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new UsuarioNaoEncontradoException(usuarioId);
        } catch (DataIntegrityViolationException e) {
            String msg = String.format(MSG_USUARIO_EM_USO, usuarioId);
            throw new EntidadeEmUsoException(msg);
        }
    }
}
