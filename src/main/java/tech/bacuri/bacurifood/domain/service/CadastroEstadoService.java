package tech.bacuri.bacurifood.domain.service;

import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import tech.bacuri.bacurifood.domain.exception.EntidadeEmUsoException;
import tech.bacuri.bacurifood.domain.exception.EntidadeNaoEncontradaException;
import tech.bacuri.bacurifood.domain.model.Estado;
import tech.bacuri.bacurifood.domain.repository.EstadoRepository;

import java.util.List;

@AllArgsConstructor
@Component
public class CadastroEstadoService {
    private EstadoRepository estadoRepository;

    public List<Estado> listar() {
        return estadoRepository.listar();
    }

    public Estado salvar(Estado estado) {
        return estadoRepository.salvar(estado);
    }

    public Estado obter(Long idEstado) {
        return estadoRepository.obter(idEstado);
    }

    public void remover(Long estadoId) {
        Estado estadoEncontrado = obter(estadoId);
        if (estadoEncontrado == null)
            throw new EntidadeNaoEncontradaException(String.format("Estado de código %d não foi encontrado", estadoId));

        try {
            estadoRepository.remover(estadoEncontrado);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format("Estado de código %d não pode ser removida, pois está em uso", estadoId));
        }
    }
}
