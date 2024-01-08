package tech.bacuri.bacurifood.domain.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tech.bacuri.bacurifood.domain.exception.EntidadeNaoEncontradaException;
import tech.bacuri.bacurifood.domain.model.Cidade;
import tech.bacuri.bacurifood.domain.repository.CidadeRepository;

import java.util.List;

@AllArgsConstructor
@Service
public class CadastroCidadeService {
    private CidadeRepository cidadeRepository;

    public List<Cidade> listar() {
        return cidadeRepository.listar();
    }

    public Cidade salvar(Cidade cidade) {
        return cidadeRepository.salvar(cidade);
    }

    public Cidade obter(Long cidadeId) {
        return cidadeRepository.obter(cidadeId);
    }

    public void remover(Long cidadeId) {
        Cidade cidadeRetornada = cidadeRepository.obter(cidadeId);
        if (cidadeRetornada == null) {
            throw new EntidadeNaoEncontradaException(String.format("Estado de código %d não foi encontrado", cidadeId));
        }

        cidadeRepository.remover(cidadeRetornada);
    }
}
