package tech.bacuri.bacurifood.api.assembler;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import tech.bacuri.bacurifood.api.model.ProdutoModel;
import tech.bacuri.bacurifood.domain.model.Produto;

import java.util.List;

@AllArgsConstructor
@Component
public class ProdutoModelAssembler {

    private final ModelMapper modelMapper;

    public ProdutoModel toModel(Produto produto) {
        return modelMapper.map(produto, ProdutoModel.class);
    }

    public List<ProdutoModel> toCollectionModel(List<Produto> produtos) {
        return produtos.stream().map(this::toModel).toList();
    }
}
