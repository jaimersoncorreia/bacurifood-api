package tech.bacuri.bacurifood.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;
import tech.bacuri.bacurifood.domain.exception.EntidadeNaoEncontradaException;
import tech.bacuri.bacurifood.domain.model.Cidade;
import tech.bacuri.bacurifood.domain.service.CadastroCidadeService;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Objects;

@AllArgsConstructor
@RestController
@RequestMapping("/cidades")
public class CidadeController {

    private final CadastroCidadeService cadastroCidadeService;

    @GetMapping
    public ResponseEntity<?> listar() {
        return ResponseEntity.ok(cadastroCidadeService.listar());
    }

    @PostMapping
    public ResponseEntity<?> salvar(@RequestBody Cidade cidade) {
        return new ResponseEntity<>(cadastroCidadeService.salvar(cidade), HttpStatus.CREATED);
    }

    @PutMapping("/{cidadeId}")
    public ResponseEntity<?> atualizar(@PathVariable Long cidadeId, @RequestBody Cidade cidade) {
        Cidade cidadeEncontrado = cadastroCidadeService.obter(cidadeId);

        if (cidadeEncontrado == null)
            return ResponseEntity.notFound().build();

        BeanUtils.copyProperties(cidade, cidadeEncontrado, "id");
        return ResponseEntity.ok(cadastroCidadeService.salvar(cidadeEncontrado));
    }

    @DeleteMapping("/{cidadeId}")
    public ResponseEntity<?> remover(@PathVariable Long cidadeId) {
        try {
            cadastroCidadeService.remover(cidadeId);
            return ResponseEntity.noContent().build();
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{cidadeId}")
    public ResponseEntity<?> atualizarParcial(@PathVariable Long cidadeId, @RequestBody Map<String, Object> cidade) {
        Cidade cidadeEncontrado = cadastroCidadeService.obter(cidadeId);

        if (cidadeEncontrado == null)
            return ResponseEntity.notFound().build();

        merge(cidade, cidadeEncontrado);

        return atualizar(cidadeId, cidadeEncontrado);
    }

    private void merge(Map<String, Object> dadosOrigem, Cidade cidadeDestino) {
        ObjectMapper objectMapper = new ObjectMapper();
        Cidade cidadeOrigem = objectMapper.convertValue(dadosOrigem, Cidade.class);
        dadosOrigem.forEach((propriedade, valor) -> {
            Field field = ReflectionUtils.findField(Cidade.class, propriedade);
            Objects.requireNonNull(field).setAccessible(true);

            Object novoValor = ReflectionUtils.getField(field, cidadeOrigem);

            ReflectionUtils.setField(field, cidadeDestino, novoValor);
        });
    }
}
