package tech.bacuri.bacurifood.api.assembler;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import tech.bacuri.bacurifood.api.model.CidadeModel;
import tech.bacuri.bacurifood.domain.model.Cidade;

import java.util.List;

@AllArgsConstructor
@Component
public class CidadeModelAssembler {
    private final ModelMapper mapper;

    public CidadeModel toModel(Cidade cidade) {
        return mapper.map(cidade, CidadeModel.class);
    }

    public List<CidadeModel> toCollectionModel(List<Cidade> cidades) {
        return cidades.stream().map(this::toModel).toList();
    }
}
