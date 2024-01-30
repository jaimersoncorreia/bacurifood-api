package tech.bacuri.bacurifood.api.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tech.bacuri.bacurifood.api.assembler.GrupoInputDisassembler;
import tech.bacuri.bacurifood.api.assembler.GrupoModelAssember;
import tech.bacuri.bacurifood.api.model.GrupoModel;
import tech.bacuri.bacurifood.api.model.input.GrupoInput;
import tech.bacuri.bacurifood.domain.model.Grupo;
import tech.bacuri.bacurifood.domain.service.CadastroGrupoService;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@AllArgsConstructor
@RestController
@RequestMapping("/grupos")
public class GrupoController {
    private final CadastroGrupoService cadastroGrupoService;
    private final GrupoModelAssember grupoModelAssember;
    private final GrupoInputDisassembler grupoInputDisassembler;

    @GetMapping
    public List<GrupoModel> listar() {
        return grupoModelAssember.toCollectionModel(cadastroGrupoService.listar());
    }

    @GetMapping("/{grupoId}")
    public GrupoModel obter(@PathVariable Long grupoId) {
        return grupoModelAssember.toModel(cadastroGrupoService.obter(grupoId));
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public GrupoModel salvar(@RequestBody @Valid GrupoInput grupoInput) {
        Grupo grupo = grupoInputDisassembler.toEntity(grupoInput);
        return grupoModelAssember.toModel(cadastroGrupoService.salvar(grupo));
    }

    @PutMapping("/{grupoId}")
    public GrupoModel atualizar(@PathVariable Long grupoId, @RequestBody @Valid GrupoInput grupoInput) {
        Grupo grupo = cadastroGrupoService.obter(grupoId);
        grupoInputDisassembler.copyToEntity(grupoInput, grupo);
        return grupoModelAssember.toModel(cadastroGrupoService.salvar(grupo));
    }

    @DeleteMapping("/{grupoId}")
    @ResponseStatus(NO_CONTENT)
    public void remover(@PathVariable Long grupoId) {
        cadastroGrupoService.remover(grupoId);
    }
}
