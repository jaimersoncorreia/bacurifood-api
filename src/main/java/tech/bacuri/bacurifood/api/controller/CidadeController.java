package tech.bacuri.bacurifood.api.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tech.bacuri.bacurifood.api.assembler.CidadeInputDisassembler;
import tech.bacuri.bacurifood.api.assembler.CidadeModelAssembler;
import tech.bacuri.bacurifood.api.model.CidadeModel;
import tech.bacuri.bacurifood.api.model.input.CidadeInput;
import tech.bacuri.bacurifood.domain.exception.EstadoNaoEncontradoException;
import tech.bacuri.bacurifood.domain.exception.NegocioException;
import tech.bacuri.bacurifood.domain.model.Cidade;
import tech.bacuri.bacurifood.domain.service.CadastroCidadeService;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@AllArgsConstructor
@RestController
@RequestMapping("/cidades")
public class CidadeController {

    private final CadastroCidadeService cadastroCidadeService;
    private final CidadeModelAssembler cidadeModelAssembler;
    private final CidadeInputDisassembler cidadeInputDisassembler;

    @GetMapping
    public List<CidadeModel> listar() {
        return cidadeModelAssembler.toCollectionModel(cadastroCidadeService.listar());
    }

    @GetMapping("/{cidadeId}")
    public CidadeModel obter(@PathVariable Long cidadeId) {
        return cidadeModelAssembler.toModel(cadastroCidadeService.obter(cidadeId));
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public CidadeModel salvar(@RequestBody @Valid CidadeInput cidadeInput) {
        try {
            Cidade cidade = cidadeInputDisassembler.toEntity(cidadeInput);
            return cidadeModelAssembler.toModel(cadastroCidadeService.salvar(cidade));
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @PutMapping("/{cidadeId}")
    public CidadeModel atualizar(@PathVariable Long cidadeId, @RequestBody @Valid CidadeInput cidadeInput) {
        try {
            Cidade cidadeEncontrado = cadastroCidadeService.obter(cidadeId);
            cidadeInputDisassembler.copyToEntity(cidadeInput, cidadeEncontrado);
            return cidadeModelAssembler.toModel(cadastroCidadeService.salvar(cidadeEncontrado));
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @DeleteMapping("/{cidadeId}")
    @ResponseStatus(NO_CONTENT)
    public void remover(@PathVariable Long cidadeId) {
        cadastroCidadeService.remover(cidadeId);
    }
}
