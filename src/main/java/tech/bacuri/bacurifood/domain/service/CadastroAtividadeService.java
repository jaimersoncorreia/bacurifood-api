package tech.bacuri.bacurifood.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.bacuri.bacurifood.domain.model.Atividade;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CadastroAtividadeService {

    private final AtividadeRepository atividadeRepository;

    public List<Atividade> listar() {
        return atividadeRepository.findAll();
    }

    @Transactional
    public Atividade salvar(Atividade atividade) {
        return atividadeRepository.save(atividade);
    }
}
