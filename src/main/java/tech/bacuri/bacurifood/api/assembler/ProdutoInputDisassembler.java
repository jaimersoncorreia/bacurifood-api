package tech.bacuri.bacurifood.api.assembler;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import tech.bacuri.bacurifood.api.model.input.ProdutoInput;
import tech.bacuri.bacurifood.domain.model.Produto;

@AllArgsConstructor
@Component
public class ProdutoInputDisassembler {

    private final ModelMapper mapper;

    public Produto toEntity(ProdutoInput produtoInput) {
        return mapper.map(produtoInput, Produto.class);
    }

    public void copyToEntity(ProdutoInput produtoInput, Produto produto) {
        mapper.map(produtoInput, produto);
    }
}
