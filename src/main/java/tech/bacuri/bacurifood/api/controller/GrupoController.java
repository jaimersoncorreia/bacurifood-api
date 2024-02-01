package tech.bacuri.bacurifood.api.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tech.bacuri.bacurifood.api.assembler.GrupoInputDisassembler;
import tech.bacuri.bacurifood.api.assembler.GrupoModelAssember;
import tech.bacuri.bacurifood.api.assembler.PermissaoModelAssember;
import tech.bacuri.bacurifood.api.model.GrupoModel;
import tech.bacuri.bacurifood.api.model.PermissaoModel;
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
    private final CadastroGrupoService grupoService;
    private final GrupoModelAssember grupoModelAssember;
    private final GrupoInputDisassembler grupoInputDisassembler;
    private final PermissaoModelAssember permissaoModelAssember;

    @GetMapping
    public List<GrupoModel> listar() {
        return grupoModelAssember.toCollectionModel(grupoService.listar());
    }

    @GetMapping("/{grupoId}")
    public GrupoModel obter(@PathVariable Long grupoId) {
        return grupoModelAssember.toModel(grupoService.obter(grupoId));
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public GrupoModel salvar(@RequestBody @Valid GrupoInput grupoInput) {
        Grupo grupo = grupoInputDisassembler.toEntity(grupoInput);
        return grupoModelAssember.toModel(grupoService.salvar(grupo));
    }

    @PutMapping("/{grupoId}")
    public GrupoModel atualizar(@PathVariable Long grupoId, @RequestBody @Valid GrupoInput grupoInput) {
        Grupo grupo = grupoService.obter(grupoId);
        grupoInputDisassembler.copyToEntity(grupoInput, grupo);
        return grupoModelAssember.toModel(grupoService.salvar(grupo));
    }

    @DeleteMapping("/{grupoId}")
    @ResponseStatus(NO_CONTENT)
    public void remover(@PathVariable Long grupoId) {
        grupoService.remover(grupoId);
    }

    @GetMapping("/{grupoId}/permissoes")
    public List<PermissaoModel> listar(@PathVariable Long grupoId) {
        Grupo grupo = grupoService.obter(grupoId);
        return permissaoModelAssember.toCollectionModel(grupo.getPermissoes());
    }

    @DeleteMapping("/{grupoId}/permissoes/{pemissaoId}")
    @ResponseStatus(NO_CONTENT)
    public void removerPermissao(@PathVariable Long grupoId, @PathVariable Long pemissaoId) {
        grupoService.removerPermissao(grupoId, pemissaoId);
    }

    @PutMapping("/{grupoId}/permissoes/{pemissaoId}")
    @ResponseStatus(NO_CONTENT)
    public void atribuirPermissao(@PathVariable Long grupoId, @PathVariable Long pemissaoId) {
        grupoService.atribuirPermissao(grupoId, pemissaoId);
    }
}
