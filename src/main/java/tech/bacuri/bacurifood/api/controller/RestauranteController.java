package tech.bacuri.bacurifood.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;
import tech.bacuri.bacurifood.domain.exception.EntidadeNaoEncontradaException;
import tech.bacuri.bacurifood.domain.model.Restaurante;
import tech.bacuri.bacurifood.domain.service.CadastroRestauranteService;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@AllArgsConstructor
@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

    private final CadastroRestauranteService cadastroRestauranteService;

    @GetMapping
    public ResponseEntity<List<Restaurante>> listar() {
        return ResponseEntity.ok(cadastroRestauranteService.listar());
    }

    @GetMapping("/{restauranteId}")
    public ResponseEntity<Restaurante> obter(@PathVariable Long restauranteId) {
        try {
            return ResponseEntity.ok(cadastroRestauranteService.obter(restauranteId));
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> salvar(@RequestBody Restaurante restaurante) {
        try {
            return new ResponseEntity<>(cadastroRestauranteService.salvar(restaurante), HttpStatus.CREATED);
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{restauranteId}")
    public ResponseEntity<?> atualizar(@PathVariable Long restauranteId, @RequestBody Restaurante restaurante) {
        Restaurante restauranteObtido;
        try {
            restauranteObtido = cadastroRestauranteService.obter(restauranteId);
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.notFound().build();
        }

        try {
            BeanUtils.copyProperties(restaurante, restauranteObtido, "id");
            return new ResponseEntity<>(cadastroRestauranteService.atualizar(restauranteObtido), HttpStatus.OK);
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/{restauranteId}")
    public ResponseEntity<?> atualizarParcial(@PathVariable Long restauranteId, @RequestBody Map<String, Object> restaurante) {
        Restaurante restauranteEncontrado = cadastroRestauranteService.obter(restauranteId);

        if (restauranteEncontrado == null)
            return ResponseEntity.notFound().build();

        merge(restaurante, restauranteEncontrado);

        return atualizar(restauranteId, restauranteEncontrado);
    }

    private void merge(Map<String, Object> dadosOrigem, Restaurante restauranteDestino) {
        ObjectMapper objectMapper = new ObjectMapper();
        Restaurante restauranteOrigem = objectMapper.convertValue(dadosOrigem, Restaurante.class);
        dadosOrigem.forEach((propriedade, valor) -> {
            Field field = ReflectionUtils.findField(Restaurante.class, propriedade);
            Objects.requireNonNull(field).setAccessible(true);

            Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);

            ReflectionUtils.setField(field, restauranteDestino, novoValor);
        });
    }
}
