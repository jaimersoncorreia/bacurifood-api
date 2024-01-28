package tech.bacuri.bacurifood.api.assembler;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import tech.bacuri.bacurifood.api.model.input.FormaPagamentoInput;
import tech.bacuri.bacurifood.domain.model.FormaPagamento;

@AllArgsConstructor
@Component
public class FormaPagamentoDisassembler {

    private final ModelMapper mapper;

    public FormaPagamento toEntity(FormaPagamentoInput pagamentoInput) {
        return mapper.map(pagamentoInput, FormaPagamento.class);
    }

    public void copyToEntity(FormaPagamentoInput formaPagamentoInput, FormaPagamento formaPagamento) {
        mapper.map(formaPagamentoInput, formaPagamento);
    }
}
