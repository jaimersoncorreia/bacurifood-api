package tech.bacuri.bacurifood.domain.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.bacuri.bacurifood.domain.exception.RestauranteNaoEncontradoException;
import tech.bacuri.bacurifood.domain.model.Cidade;
import tech.bacuri.bacurifood.domain.model.Cozinha;
import tech.bacuri.bacurifood.domain.model.Restaurante;
import tech.bacuri.bacurifood.domain.repository.RestauranteRepository;

import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@Service
public class CadastroRestauranteService {
    private final RestauranteRepository restauranteRepository;
    private final CadastroCozinhaService cadastroCozinha;
    private final CadastroCidadeService cadastroCidade;

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
        if (Objects.nonNull(restaurante.getEndereco())) {
            Cidade cidade = cadastroCidade.obter(restaurante.getEndereco().getCidade().getId());
            restaurante.getEndereco().setCidade(cidade);
        }

        restaurante.setCozinha(cozinha);
        return restauranteRepository.save(restaurante);
    }

    @Transactional
    public void ativar(Long restauranteId) {
        obter(restauranteId).ativar();
    }

    @Transactional
    public void inativar(Long restauranteId) {
        obter(restauranteId).inativar();
    }

    @Transactional
    public Restaurante atualizar(Restaurante restaurante) {
        return salvar(restaurante);
    }
}