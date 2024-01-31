package tech.bacuri.bacurifood.api.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tech.bacuri.bacurifood.api.assembler.FormaPagamentoModelAssembler;
import tech.bacuri.bacurifood.api.assembler.FormaPagamentoDisassembler;
import tech.bacuri.bacurifood.api.model.FormaPagamentoModel;
import tech.bacuri.bacurifood.api.model.input.FormaPagamentoInput;
import tech.bacuri.bacurifood.domain.model.FormaPagamento;
import tech.bacuri.bacurifood.domain.service.CadastroFormaPagamentoService;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@AllArgsConstructor
@RestController
@RequestMapping("/formas-pagamento")
public class FormaPagamentoController {

    private final CadastroFormaPagamentoService cadastroFormaPagamentoService;
    private final FormaPagamentoModelAssembler formaPagamentoModelAssembler;
    private final FormaPagamentoDisassembler formaPagamentoDisassembler;

    @GetMapping
    public List<FormaPagamentoModel> listar() {
        List<FormaPagamento> formasPagamento = cadastroFormaPagamentoService.listar();
        return formaPagamentoModelAssembler.toCollectionModel(formasPagamento);
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public FormaPagamentoModel salvar(@RequestBody @Valid FormaPagamentoInput formaPagamentoInput) {
        FormaPagamento entity = formaPagamentoDisassembler.toEntity(formaPagamentoInput);
        return formaPagamentoModelAssembler.toModel(cadastroFormaPagamentoService.salvar(entity));

    }

    @GetMapping("/{formaPagamentoId}")
    public FormaPagamentoModel obter(@PathVariable Long formaPagamentoId) {
        return formaPagamentoModelAssembler.toModel(cadastroFormaPagamentoService.obter(formaPagamentoId));
    }

    @PutMapping("/{formaPagamentoId}")
    public FormaPagamentoModel atualizar(@PathVariable Long formaPagamentoId,
                                         @RequestBody @Valid FormaPagamentoInput formaPagamentoInput) {
        FormaPagamento formaPagamento = cadastroFormaPagamentoService.obter(formaPagamentoId);
        formaPagamentoDisassembler.copyToEntity(formaPagamentoInput, formaPagamento);
        return formaPagamentoModelAssembler.toModel(cadastroFormaPagamentoService.atualizar(formaPagamento));
    }

    //    excluir
    @DeleteMapping("/{formaPagamentoId}")
    @ResponseStatus(NO_CONTENT)
    public void deletar(@PathVariable Long formaPagamentoId) {
        cadastroFormaPagamentoService.deletar(formaPagamentoId);
    }
}
