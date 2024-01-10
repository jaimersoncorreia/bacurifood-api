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
        return cidadeRepository.findAll();
    }

    public Cidade salvar(Cidade cidade) {
        return cidadeRepository.save(cidade);
    }

    public Cidade obter(Long cidadeId) {
        return cidadeRepository.findById(cidadeId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(String
                        .format("Cidade de código %d não foi encontrado", cidadeId)));
    }

    public void remover(Long cidadeId) {
        cidadeRepository.delete(obter(cidadeId));
    }
}
