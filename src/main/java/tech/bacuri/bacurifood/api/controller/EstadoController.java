package tech.bacuri.bacurifood.api.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.bacuri.bacurifood.domain.exception.EntidadeEmUsoException;
import tech.bacuri.bacurifood.domain.exception.EntidadeNaoEncontradaException;
import tech.bacuri.bacurifood.domain.model.Estado;
import tech.bacuri.bacurifood.domain.service.CadastroEstadoService;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/estados")
public class EstadoController {

    private final CadastroEstadoService cadastroEstadoService;

    @GetMapping
    public ResponseEntity<List<Estado>> listar() {
        return ResponseEntity.ok(cadastroEstadoService.listar());
    }

    @PostMapping
    public ResponseEntity<?> salvar(@RequestBody Estado estado) {
        return new ResponseEntity<>(cadastroEstadoService.salvar(estado), HttpStatus.CREATED);
    }

    @PutMapping("/{estadoId}")
    public ResponseEntity<?> atualizar(@PathVariable Long estadoId, @RequestBody Estado estado) {
        Estado estadoEncontrado = cadastroEstadoService.obter(estadoId);

        if (estadoEncontrado == null)
            return ResponseEntity.notFound().build();

        BeanUtils.copyProperties(estado, estadoEncontrado, "id");
        return ResponseEntity.ok(cadastroEstadoService.salvar(estadoEncontrado));
    }

    @DeleteMapping("/{estadoId}")
    public ResponseEntity<?> remover(@PathVariable Long estadoId) {
        try {
            cadastroEstadoService.remover(estadoId);
            return ResponseEntity.noContent().build();
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.notFound().build();
        } catch (EntidadeEmUsoException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }
}
