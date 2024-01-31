package tech.bacuri.bacurifood.api.assembler;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import tech.bacuri.bacurifood.api.model.FormaPagamentoModel;
import tech.bacuri.bacurifood.domain.model.FormaPagamento;

import java.util.Collection;
import java.util.List;

@AllArgsConstructor
@Component
public class FormaPagamentoModelAssembler {

    private final ModelMapper mapper;

    public List<FormaPagamentoModel> toCollectionModel(Collection<FormaPagamento> formasPagamento) {
        return formasPagamento.stream().map(this::toModel).toList();
    }

    public FormaPagamentoModel toModel(FormaPagamento formaPagamento) {
        return mapper.map(formaPagamento, FormaPagamentoModel.class);
    }
}
