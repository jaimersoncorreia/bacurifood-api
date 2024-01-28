package tech.bacuri.bacurifood.api.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tech.bacuri.bacurifood.api.assembler.EstadoInputDisassembler;
import tech.bacuri.bacurifood.api.assembler.EstadoModelAssembler;
import tech.bacuri.bacurifood.api.model.EstadoModel;
import tech.bacuri.bacurifood.api.model.input.EstadoInput;
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
    private final EstadoModelAssembler estadoModelAssembler;
    private final EstadoInputDisassembler estadoInputDisassembler;

    @GetMapping
    public List<EstadoModel> listar() {
        return estadoModelAssembler.toCollectionModel(cadastroEstadoService.listar());
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public EstadoModel salvar(@RequestBody @Valid EstadoInput estadoInput) {
        Estado estado = estadoInputDisassembler.toEntity(estadoInput);
        return estadoModelAssembler.toModel(cadastroEstadoService.salvar(estado));
    }

    @PutMapping("/{estadoId}")
    public EstadoModel atualizar(@PathVariable Long estadoId, @RequestBody @Valid EstadoInput estadoInput) {
        Estado estadoEncontrado = cadastroEstadoService.obter(estadoId);
        estadoInputDisassembler.copyToEntity(estadoInput, estadoEncontrado);
        return estadoModelAssembler.toModel(cadastroEstadoService.salvar(estadoEncontrado));
    }

    @DeleteMapping("/{estadoId}")
    @ResponseStatus(NO_CONTENT)
    public void remover(@PathVariable Long estadoId) {
        cadastroEstadoService.remover(estadoId);
    }
}
