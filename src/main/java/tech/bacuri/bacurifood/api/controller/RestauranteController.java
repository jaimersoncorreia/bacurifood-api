package tech.bacuri.bacurifood.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.*;
import tech.bacuri.bacurifood.api.model.CozinhaModel;
import tech.bacuri.bacurifood.api.model.RestauranteModel;
import tech.bacuri.bacurifood.api.model.input.CozinhaIdInput;
import tech.bacuri.bacurifood.api.model.input.RestauranteInput;
import tech.bacuri.bacurifood.core.validation.ValidacaoException;
import tech.bacuri.bacurifood.domain.exception.CozinhaNaoEncontradaException;
import tech.bacuri.bacurifood.domain.exception.NegocioException;
import tech.bacuri.bacurifood.domain.model.Cozinha;
import tech.bacuri.bacurifood.domain.model.Restaurante;
import tech.bacuri.bacurifood.domain.service.CadastroRestauranteService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES;
import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;
import static org.springframework.beans.BeanUtils.copyProperties;
import static org.springframework.http.HttpStatus.CREATED;

@AllArgsConstructor
@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

    private final CadastroRestauranteService cadastroRestauranteService;
    private final SmartValidator smartValidator;

    @GetMapping
    public List<RestauranteModel> listar() {
        List<Restaurante> restaurantes = cadastroRestauranteService.listar();
        return toCollectionModel(restaurantes);
    }

    @GetMapping("/{restauranteId}")
    public RestauranteModel obter(@PathVariable Long restauranteId) {
        return toModel(cadastroRestauranteService.obter(restauranteId));
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public RestauranteModel salvar(@RequestBody @Valid RestauranteInput restauranteInput) {
        try {
            Restaurante restaurante = toEntity(restauranteInput);
            return toModel(cadastroRestauranteService.salvar(restaurante));
        } catch (CozinhaNaoEncontradaException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @PutMapping("/{restauranteId}")
    public RestauranteModel atualizar(@PathVariable Long restauranteId, @RequestBody @Valid RestauranteInput restauranteInput) {
        Restaurante restauranteObtido;
        restauranteObtido = cadastroRestauranteService.obter(restauranteId);

        copyProperties(toEntity(restauranteInput), restauranteObtido,
                "id", "formasPagamento", "endereco", "dataCadastro", "produtos");
        try {
            return toModel(cadastroRestauranteService.atualizar(restauranteObtido));
        } catch (CozinhaNaoEncontradaException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @PatchMapping("/{restauranteId}")
    public RestauranteModel atualizarParcial(@PathVariable Long restauranteId,
                                             @RequestBody Map<String, Object> restaurante,
                                             HttpServletRequest request) {
        Restaurante restauranteEncontrado = cadastroRestauranteService.obter(restauranteId);
        merge(restaurante, restauranteEncontrado, request);

        validate(restauranteEncontrado, "restaurante");

        return atualizar(restauranteId, toInputModel(restauranteEncontrado));
    }

    private RestauranteInput toInputModel(Restaurante restaurante) {
        CozinhaIdInput cozinha = CozinhaIdInput.builder().id(restaurante.getCozinha().getId()).build();
        return RestauranteInput.builder()
                .nome(restaurante.getNome())
                .taxaFrete(restaurante.getTaxaFrete())
                .cozinha(cozinha)
                .build();
    }

    private void validate(Restaurante restauranteEncontrado, String objectName) {
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(restauranteEncontrado, objectName);
        smartValidator.validate(restauranteEncontrado, bindingResult);

        if (bindingResult.hasErrors()) {
            throw new ValidacaoException(bindingResult);
        }
    }

    private void merge(Map<String, Object> dadosOrigem,
                       Restaurante restauranteDestino,
                       HttpServletRequest request) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(FAIL_ON_IGNORED_PROPERTIES, true);
            objectMapper.configure(FAIL_ON_UNKNOWN_PROPERTIES, true);
            Restaurante restauranteOrigem = objectMapper.convertValue(dadosOrigem, Restaurante.class);
            dadosOrigem.forEach((propriedade, valor) -> {
                Field field = ReflectionUtils.findField(Restaurante.class, propriedade);
                Objects.requireNonNull(field).setAccessible(true);

                Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);

                ReflectionUtils.setField(field, restauranteDestino, novoValor);
            });
        } catch (IllegalArgumentException e) {
            throw new HttpMessageNotReadableException(e.getMessage(),
                    ExceptionUtils.getRootCause(e),
                    new ServletServerHttpRequest(request));
        }
    }

    private RestauranteModel toModel(Restaurante restaurante) {
        CozinhaModel cozinhaModel = CozinhaModel.builder()
                .id(restaurante.getCozinha().getId())
                .nome(restaurante.getNome()).build();

        return RestauranteModel.builder()
                .id(restaurante.getId())
                .nome(restaurante.getNome())
                .taxaFrete(restaurante.getTaxaFrete())
                .cozinha(cozinhaModel)
                .build();
    }

    private List<RestauranteModel> toCollectionModel(List<Restaurante> restaurantes) {
        return restaurantes.stream().map(this::toModel).toList();
    }

    private Restaurante toEntity(RestauranteInput restauranteInput) {
        Cozinha cozinha = Cozinha.builder().id(restauranteInput.getCozinha().getId()).build();
        return Restaurante.builder()
                .nome(restauranteInput.getNome())
                .taxaFrete(restauranteInput.getTaxaFrete())
                .cozinha(cozinha)
                .build();
    }
}
