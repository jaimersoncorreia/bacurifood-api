package tech.bacuri.bacurifood.domain.service;

import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import tech.bacuri.bacurifood.domain.exception.EntidadeEmUsoException;
import tech.bacuri.bacurifood.domain.exception.EntidadeNaoEncontradaException;
import tech.bacuri.bacurifood.domain.model.Cidade;
import tech.bacuri.bacurifood.domain.repository.CidadeRepository;
import tech.bacuri.bacurifood.domain.repository.EstadoRepository;

import java.util.List;

@AllArgsConstructor
@Service
public class CadastroCidadeService {
    public static final String MSG_CIDADE_NAO_ENCONTRADA = "Cidade de código %d não foi encontrado";
    public static final String MSG_ESTADO_NAO_ENCONTRADO = "Não existe cadastro de Estado com código %d";
    public static final String MSG_CIDADE_EM_USO = "Cidade de código %d não pode ser removida, pois está em uso";
    private CidadeRepository cidadeRepository;
    private EstadoRepository estadoRepository;

    public List<Cidade> listar() {
        return cidadeRepository.findAll();
    }

    public Cidade salvar(Cidade cidade) {
        if (!estadoRepository.existsById(cidade.getEstado().getId())) {
            throw new EntidadeNaoEncontradaException(String.format(MSG_ESTADO_NAO_ENCONTRADO, cidade.getEstado().getId()));
        }

        return cidadeRepository.save(cidade);
    }

    public Cidade obter(Long cidadeId) {
        return cidadeRepository.findById(cidadeId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(String
                        .format(MSG_CIDADE_NAO_ENCONTRADA, cidadeId)));
    }

    public void remover(Long cidadeId) {
        try {
            cidadeRepository.delete(obter(cidadeId));
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format(MSG_CIDADE_EM_USO, cidadeId));
        }
    }
}
