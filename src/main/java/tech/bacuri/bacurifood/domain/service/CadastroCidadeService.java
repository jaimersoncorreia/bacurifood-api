package tech.bacuri.bacurifood.domain.service;

import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import tech.bacuri.bacurifood.domain.exception.CidadeNaoEncontradaException;
import tech.bacuri.bacurifood.domain.exception.EntidadeEmUsoException;
import tech.bacuri.bacurifood.domain.model.Cidade;
import tech.bacuri.bacurifood.domain.repository.CidadeRepository;

import java.util.List;

@AllArgsConstructor
@Service
public class CadastroCidadeService {
    public static final String MSG_CIDADE_EM_USO = "Cidade de código %d não pode ser removida, pois está em uso";
    private CidadeRepository cidadeRepository;
    private CadastroEstadoService cadastroEstado;

    public List<Cidade> listar() {
        return cidadeRepository.findAll();
    }

    public Cidade salvar(Cidade cidade) {
        cidade.setEstado(cadastroEstado.obter(cidade.getEstado().getId()));

        return cidadeRepository.save(cidade);
    }

    public Cidade obter(Long cidadeId) {
        return cidadeRepository.findById(cidadeId)
                .orElseThrow(() -> new CidadeNaoEncontradaException(cidadeId));
    }

    public void remover(Long cidadeId) {
        try {
            cidadeRepository.delete(obter(cidadeId));
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format(MSG_CIDADE_EM_USO, cidadeId));
        }
    }
}
