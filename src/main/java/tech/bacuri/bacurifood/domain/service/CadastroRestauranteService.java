package tech.bacuri.bacurifood.domain.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tech.bacuri.bacurifood.domain.exception.EntidadeNaoEncontradaException;
import tech.bacuri.bacurifood.domain.model.Restaurante;
import tech.bacuri.bacurifood.domain.repository.RestauranteRepository;

import java.util.List;

@AllArgsConstructor
@Service
public class CadastroRestauranteService {
    private final RestauranteRepository restauranteRepository;

    public List<Restaurante> listar() {
        return restauranteRepository.listar();
    }

    public Restaurante obter(Long restauranteId) {
        Restaurante restaurante = restauranteRepository.obter(restauranteId);
        if (restaurante == null) {
            String msg = String.format("Não existe cadastro de restaurante com p código %d", restauranteId);
            throw new EntidadeNaoEncontradaException(msg);
        }
        return restaurante;
    }
}
