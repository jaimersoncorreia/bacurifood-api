package tech.bacuri.bacurifood.domain.service;

import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import tech.bacuri.bacurifood.domain.exception.EntidadeEmUsoException;
import tech.bacuri.bacurifood.domain.exception.EntidadeNaoEncontradaException;
import tech.bacuri.bacurifood.domain.model.Cozinha;
import tech.bacuri.bacurifood.domain.repository.CozinhaRepository;

import java.util.List;

@AllArgsConstructor
@Service
public class CadastroCozinhaService {

    private final CozinhaRepository cozinhaRepository;

    public Cozinha salvar(Cozinha cozinha) {
        return cozinhaRepository.salvar(cozinha);
    }

    public List<Cozinha> listar() {
        return cozinhaRepository.listar();
    }

    public Cozinha obter(Long cozinhaId) {
        return cozinhaRepository.obter(cozinhaId);
    }

    public void remover(Long cozinhaId) {
        try {
            cozinhaRepository.remover(cozinhaId);
        } catch (EmptyResultDataAccessException e) {
            String msg = String.format("Não existe cadastro de cozinha com p código %d", cozinhaId);
            throw new EntidadeNaoEncontradaException(msg);
        } catch (DataIntegrityViolationException e) {
            String msg = String.format("Cozinha de código %d não pode ser removida, pois está em uso", cozinhaId);
            throw new EntidadeEmUsoException(msg);
        }


    }
}
