package tech.bacuri.bacurifood.domain.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.bacuri.bacurifood.domain.exception.RestauranteNaoEncontradoException;
import tech.bacuri.bacurifood.domain.model.*;
import tech.bacuri.bacurifood.domain.repository.RestauranteRepository;

import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@Service
public class CadastroRestauranteService {
    private final RestauranteRepository restauranteRepository;
    private final CadastroCozinhaService cadastroCozinha;
    private final CadastroCidadeService cadastroCidade;
    private final CadastroFormaPagamentoService formaPagamentoService;
    private final CadastroUsuarioService usuarioService;

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
    public void ativar(List<Long> restauranteIds) {
        restauranteIds.forEach(this::ativar);
    }

    @Transactional
    public void inativar(List<Long> restauranteIds) {
        restauranteIds.forEach(this::inativar);
    }

    @Transactional
    public void desassociarFormaPagamento(Long restauranteId, Long formaPagamentoId) {
        Restaurante restaurante = obter(restauranteId);
        FormaPagamento formaPagamento = formaPagamentoService.obter(formaPagamentoId);
        restaurante.removerFormaPagamento(formaPagamento);
    }

    @Transactional
    public void associarFormaPagamento(Long restauranteId, Long formaPagamentoId) {
        Restaurante restaurante = obter(restauranteId);
        FormaPagamento formaPagamento = formaPagamentoService.obter(formaPagamentoId);
        restaurante.adicionarFormaPagamento(formaPagamento);
    }

    @Transactional
    public Restaurante atualizar(Restaurante restaurante) {
        return salvar(restaurante);
    }

    @Transactional
    public void fechar(Long restauranteId) {
        obter(restauranteId).fechar();
    }

    @Transactional
    public void abrir(Long restauranteId) {
        obter(restauranteId).abrir();
    }

    @Transactional
    public void removerResponsavel(Long restauranteId, Long responsavelId) {
        Restaurante restaurante = obter(restauranteId);
        Usuario responsavel = usuarioService.obter(responsavelId);
        restaurante.removerUsuario(responsavel);
    }

    @Transactional
    public void atribuirResponsavel(Long restauranteId, Long responsavelId) {
        Restaurante restaurante = obter(restauranteId);
        Usuario responsavel = usuarioService.obter(responsavelId);
        restaurante.atribuirResponsavel(responsavel);
    }
}