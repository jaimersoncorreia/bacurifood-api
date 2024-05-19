package tech.bacuri.bacurifood.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tech.bacuri.bacurifood.domain.model.Atividade;
import tech.bacuri.bacurifood.domain.service.CadastroAtividadeService;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/atividades")
public class AtividadeController {
    private final CadastroAtividadeService cadastroAtividadeService;

    @GetMapping
    public List<Atividade> listar() {
        return cadastroAtividadeService.listar();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Atividade salvar(@RequestBody @Valid Atividade atividade) {
        return cadastroAtividadeService.salvar(atividade);
    }
}
