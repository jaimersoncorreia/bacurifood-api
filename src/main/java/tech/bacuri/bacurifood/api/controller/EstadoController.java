package tech.bacuri.bacurifood.api.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.bacuri.bacurifood.domain.model.Estado;
import tech.bacuri.bacurifood.domain.service.CadastroEstadoService;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

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
    @ResponseStatus(CREATED)
    public Estado salvar(@RequestBody @Valid Estado estado) {
        return cadastroEstadoService.salvar(estado);
    }

    @PutMapping("/{estadoId}")
    public Estado atualizar(@PathVariable Long estadoId, @RequestBody @Valid Estado estado) {
        Estado estadoEncontrado = cadastroEstadoService.obter(estadoId);
        BeanUtils.copyProperties(estado, estadoEncontrado, "id");

        return cadastroEstadoService.salvar(estadoEncontrado);
    }

    @DeleteMapping("/{estadoId}")
    @ResponseStatus(NO_CONTENT)
    public void remover(@PathVariable Long estadoId) {
        cadastroEstadoService.remover(estadoId);
    }
}
