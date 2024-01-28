package tech.bacuri.bacurifood.domain.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.bacuri.bacurifood.domain.exception.RestauranteNaoEncontradoException;
import tech.bacuri.bacurifood.domain.model.Cozinha;
import tech.bacuri.bacurifood.domain.model.Restaurante;
import tech.bacuri.bacurifood.domain.repository.RestauranteRepository;

import java.util.List;

@AllArgsConstructor
@Service
public class CadastroRestauranteService {
    private final RestauranteRepository restauranteRepository;
    private CadastroCozinhaService cadastroCozinha;

    public List<Restaurante> listar() {
        return restauranteRepository.findAll();
    }

    public Restaurante obter(Long restauranteId) {
        return restauranteRepository
                .findById(restauranteId)
                .orElseThrow(() -> new RestauranteNaoEncontradoException(restauranteId));
    }

    @Transactional
    public Restaurante salvar(Restaurante restaurante) {
        Cozinha cozinha = cadastroCozinha.obter(restaurante.getCozinha().getId());
        restaurante.setCozinha(cozinha);
        return restauranteRepository.save(restaurante);
    }

    @Transactional
    public Restaurante atualizar(Restaurante restaurante) {
        return salvar(restaurante);
    }
}