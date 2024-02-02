package tech.bacuri.bacurifood.api.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tech.bacuri.bacurifood.api.assembler.UsuarioModelAssembler;
import tech.bacuri.bacurifood.api.model.UsuarioModel;
import tech.bacuri.bacurifood.domain.model.Restaurante;
import tech.bacuri.bacurifood.domain.service.CadastroRestauranteService;

import java.util.Collection;

import static org.springframework.http.HttpStatus.NO_CONTENT;

@AllArgsConstructor
@RestController
@RequestMapping("/restaurantes/{restauranteId}/responsaveis")
public class RestauranteResponsavelController {

    private final CadastroRestauranteService restauranteService;
    private final UsuarioModelAssembler usuarioModelAssembler;

    @GetMapping
    public Collection<UsuarioModel> listar(@PathVariable Long restauranteId) {
        Restaurante restaurante = restauranteService.obter(restauranteId);
        return usuarioModelAssembler.toCollectionModel(restaurante.getResponsaveis());
    }

    @DeleteMapping("/{responsavelId}")
    @ResponseStatus(NO_CONTENT)
    public void removerResponsavel(@PathVariable Long restauranteId, @PathVariable Long responsavelId) {
        restauranteService.removerResponsavel(restauranteId, responsavelId);
    }

    @PutMapping("/{responsavelId}")
    @ResponseStatus(NO_CONTENT)
    public void associar(@PathVariable Long restauranteId, @PathVariable Long responsavelId) {
        restauranteService.atribuirResponsavel(restauranteId, responsavelId);
    }
}
