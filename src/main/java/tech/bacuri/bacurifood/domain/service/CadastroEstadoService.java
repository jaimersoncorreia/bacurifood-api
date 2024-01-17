package tech.bacuri.bacurifood.domain.service;

import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import tech.bacuri.bacurifood.domain.exception.EntidadeEmUsoException;
import tech.bacuri.bacurifood.domain.exception.EstadoNaoEncontradoException;
import tech.bacuri.bacurifood.domain.model.Estado;
import tech.bacuri.bacurifood.domain.repository.EstadoRepository;

import java.util.List;

@AllArgsConstructor
@Component
public class CadastroEstadoService {
    //    public static final String MSG_ESTADO_NAO_ENCONTRADO = "Não existe cadastro de Estado com código %d";
    public static final String MSG_ESTADO_EM_USO = "Estado de código %d não pode ser removida, pois está em uso";
    private EstadoRepository estadoRepository;

    public List<Estado> listar() {
        return estadoRepository.findAll();
    }

    public Estado salvar(Estado estado) {
        return estadoRepository.save(estado);
    }

    public Estado obter(Long idEstado) {
        return estadoRepository.findById(idEstado)
                .orElseThrow(() -> new EstadoNaoEncontradoException(idEstado));
    }

    public void remover(Long estadoId) {
        if (!estadoRepository.existsById(estadoId))
            throw new EstadoNaoEncontradoException(estadoId);

        try {
            estadoRepository.delete(obter(estadoId));
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format(MSG_ESTADO_EM_USO, estadoId));
        }
    }
}
