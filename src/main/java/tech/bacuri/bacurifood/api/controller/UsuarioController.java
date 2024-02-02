package tech.bacuri.bacurifood.api.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tech.bacuri.bacurifood.api.assembler.GrupoModelAssember;
import tech.bacuri.bacurifood.api.assembler.UsuarioInputDisassembler;
import tech.bacuri.bacurifood.api.assembler.UsuarioModelAssembler;
import tech.bacuri.bacurifood.api.model.GrupoModel;
import tech.bacuri.bacurifood.api.model.UsuarioModel;
import tech.bacuri.bacurifood.api.model.input.SenhaInput;
import tech.bacuri.bacurifood.api.model.input.UsuarioComSenhaInput;
import tech.bacuri.bacurifood.api.model.input.UsuarioInput;
import tech.bacuri.bacurifood.domain.exception.CidadeNaoEncontradaException;
import tech.bacuri.bacurifood.domain.exception.CozinhaNaoEncontradaException;
import tech.bacuri.bacurifood.domain.exception.NegocioException;
import tech.bacuri.bacurifood.domain.model.Usuario;
import tech.bacuri.bacurifood.domain.service.CadastroUsuarioService;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@AllArgsConstructor
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    private final CadastroUsuarioService usuarioService;
    private final UsuarioModelAssembler usuarioModelAssembler;
    private final UsuarioInputDisassembler usuarioInputDisassembler;
    private final GrupoModelAssember grupoModelAssember;

    @GetMapping
    public List<UsuarioModel> listar() {
        return usuarioModelAssembler.toCollectionModel(usuarioService.listar());
    }

    @GetMapping("/{usuarioId}")
    public UsuarioModel obter(@PathVariable Long usuarioId) {
        return usuarioModelAssembler.toModel(usuarioService.obter(usuarioId));
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public UsuarioModel salvar(@RequestBody @Valid UsuarioComSenhaInput usuarioInput) {
        try {
            Usuario usuario = usuarioInputDisassembler.toEntity(usuarioInput);
            return usuarioModelAssembler.toModel(usuarioService.salvar(usuario));
        } catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @PutMapping("/{usuarioId}")
    public UsuarioModel atualizar(@PathVariable Long usuarioId,
                                  @RequestBody @Valid UsuarioInput usuarioInput) {
        Usuario usuario = usuarioService.obter(usuarioId);
        usuarioInputDisassembler.copyToEntity(usuarioInput, usuario);
        return usuarioModelAssembler.toModel(usuarioService.atualizar(usuario));
    }

    @PutMapping("/{usuarioId}/senha")
    @ResponseStatus(NO_CONTENT)
    public void atualizarSenha(@PathVariable Long usuarioId,
                               @RequestBody @Valid SenhaInput senhaInput) {
        usuarioService.atualizarSenha(usuarioId, senhaInput.getSenhaAtual(), senhaInput.getSenhaNova());
    }

    @DeleteMapping("/{usuarioId}")
    @ResponseStatus(NO_CONTENT)
    public void remover(@PathVariable Long usuarioId) {
        usuarioService.remover(usuarioId);
    }

    @GetMapping("/{usuarioId}/grupos")
    public List<GrupoModel> listar(@PathVariable Long usuarioId) {
        return grupoModelAssember.toCollectionModel(usuarioService.obter(usuarioId).getGrupos());
    }

    @DeleteMapping("/{usuarioId}/grupos/{grupoId}")
    @ResponseStatus(NO_CONTENT)
    public void removerGrupo(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
        usuarioService.removerGrupo(usuarioId, grupoId);
    }

    @PutMapping("/{usuarioId}/grupos/{grupoId}")
    @ResponseStatus(NO_CONTENT)
    public void atribuirGrupo(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
        usuarioService.atribuirGrupo(usuarioId, grupoId);
    }
}
