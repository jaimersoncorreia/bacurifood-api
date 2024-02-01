package tech.bacuri.bacurifood.api.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tech.bacuri.bacurifood.api.assembler.RestauranteInputDisassembler;
import tech.bacuri.bacurifood.api.assembler.RestauranteModelAssembler;
import tech.bacuri.bacurifood.api.model.RestauranteModel;
import tech.bacuri.bacurifood.api.model.input.RestauranteInput;
import tech.bacuri.bacurifood.domain.exception.CidadeNaoEncontradaException;
import tech.bacuri.bacurifood.domain.exception.CozinhaNaoEncontradaException;
import tech.bacuri.bacurifood.domain.exception.NegocioException;
import tech.bacuri.bacurifood.domain.model.Restaurante;
import tech.bacuri.bacurifood.domain.service.CadastroRestauranteService;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@AllArgsConstructor
@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

    private final CadastroRestauranteService cadastroRestauranteService;
    private final RestauranteModelAssembler restauranteModelAssembler;
    private final RestauranteInputDisassembler restauranteInputDisassembler;

    @GetMapping
    public List<RestauranteModel> listar() {
        List<Restaurante> restaurantes = cadastroRestauranteService.listar();
        return restauranteModelAssembler.toCollectionModel(restaurantes);
    }

    @GetMapping("/{restauranteId}")
    public RestauranteModel obter(@PathVariable Long restauranteId) {
        return restauranteModelAssembler.toModel(cadastroRestauranteService.obter(restauranteId));
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public RestauranteModel salvar(@RequestBody @Valid RestauranteInput restauranteInput) {
        try {
            Restaurante restaurante = restauranteInputDisassembler.toEntity(restauranteInput);
            return restauranteModelAssembler.toModel(cadastroRestauranteService.salvar(restaurante));
        } catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @PutMapping("/{restauranteId}")
    public RestauranteModel atualizar(@PathVariable Long restauranteId,
                                      @RequestBody @Valid RestauranteInput restauranteInput) {
        try {
            Restaurante restauranteObtido = cadastroRestauranteService.obter(restauranteId);
            restauranteInputDisassembler.copyToEntity(restauranteInput, restauranteObtido);
            return restauranteModelAssembler.toModel(cadastroRestauranteService.atualizar(restauranteObtido));
        } catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @PutMapping("/{restauranteId}/ativo")
    @ResponseStatus(NO_CONTENT)
    public void ativar(@PathVariable Long restauranteId) {
        cadastroRestauranteService.ativar(restauranteId);
    }

    @DeleteMapping("/{restauranteId}/ativo")
    @ResponseStatus(NO_CONTENT)
    public void inativar(@PathVariable Long restauranteId) {
        cadastroRestauranteService.inativar(restauranteId);
    }

    @PutMapping("/{restauranteId}/fechamento")
    @ResponseStatus(NO_CONTENT)
    public void fechamento(@PathVariable Long restauranteId) {
        System.out.println("restauranteId = [" + restauranteId + "] fechamento");
        cadastroRestauranteService.fechar(restauranteId);
    }

    @PutMapping("/{restauranteId}/abertura")
    @ResponseStatus(NO_CONTENT)
    public void abertura(@PathVariable Long restauranteId) {
        System.out.println("restauranteId = [" + restauranteId + "] abertura");
        cadastroRestauranteService.abrir(restauranteId);
    }
}
