package tech.bacuri.bacurifood.api.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tech.bacuri.bacurifood.api.assembler.CozinhaInputDisassembler;
import tech.bacuri.bacurifood.api.assembler.CozinhaModelAssembler;
import tech.bacuri.bacurifood.api.model.CozinhaModel;
import tech.bacuri.bacurifood.api.model.input.CozinhaInput;
import tech.bacuri.bacurifood.domain.model.Cozinha;
import tech.bacuri.bacurifood.domain.service.CadastroCozinhaService;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {

    private final CadastroCozinhaService cadastroCozinhaService;
    private final CozinhaModelAssembler cozinhaModelAssembler;
    private final CozinhaInputDisassembler cozinhaInputDisassembler;

    @GetMapping
    public List<CozinhaModel> listar() {
        return cozinhaModelAssembler.toCollectionModel(cadastroCozinhaService.listar());
    }

    @GetMapping("/{cozinhaId}")
    public CozinhaModel obter(@PathVariable Long cozinhaId) {
        return cozinhaModelAssembler.toModel(cadastroCozinhaService.obter(cozinhaId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CozinhaModel salvar(@RequestBody @Valid CozinhaInput cozinhaInput) {
        Cozinha cozinha = cozinhaInputDisassembler.toEntity(cozinhaInput);
        return cozinhaModelAssembler.toModel(cadastroCozinhaService.salvar(cozinha));
    }

    @PutMapping("/{cozinhaId}")
    public CozinhaModel atualizar(@PathVariable Long cozinhaId, @RequestBody @Valid CozinhaInput cozinhaInput) {
        Cozinha cozinhaObtida = cadastroCozinhaService.obter(cozinhaId);
        cozinhaInputDisassembler.copyToEntity(cozinhaInput, cozinhaObtida);
        return cozinhaModelAssembler.toModel(cadastroCozinhaService.salvar(cozinhaObtida));
    }

    @DeleteMapping("/{cozinhaId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long cozinhaId) {
        cadastroCozinhaService.remover(cozinhaId);
    }
}
