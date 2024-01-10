package tech.bacuri.bacurifood.domain.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tech.bacuri.bacurifood.domain.exception.EntidadeNaoEncontradaException;
import tech.bacuri.bacurifood.domain.model.Restaurante;
import tech.bacuri.bacurifood.domain.repository.CozinhaRepository;
import tech.bacuri.bacurifood.domain.repository.RestauranteRepository;

import java.util.List;

@AllArgsConstructor
@Service
public class CadastroRestauranteService {
    private final RestauranteRepository restauranteRepository;
    private CozinhaRepository cozinhaRepository;

    public List<Restaurante> listar() {
        return restauranteRepository.findAll();
    }

    public Restaurante obter(Long restauranteId) {
        return restauranteRepository
                .findById(restauranteId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(String
                        .format("Não existe cadastro de restaurante com o código %d", restauranteId)));
    }


    public Restaurante salvar(Restaurante restaurante) {
        Long cozinhaId = restaurante.getCozinha().getId();
        if (!cozinhaRepository.existsById(cozinhaId))
            throw new EntidadeNaoEncontradaException(String.format("Não existe cadastro de cozinha com código %d", cozinhaId));

        return restauranteRepository.save(restaurante);
    }

    public Restaurante atualizar(Restaurante restaurante) {
        return salvar(restaurante);
    }
}
