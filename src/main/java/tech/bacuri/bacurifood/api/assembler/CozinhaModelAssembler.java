package tech.bacuri.bacurifood.api.assembler;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import tech.bacuri.bacurifood.api.model.CozinhaModel;
import tech.bacuri.bacurifood.domain.model.Cozinha;

import java.util.List;

@AllArgsConstructor
@Component
public class CozinhaModelAssembler {

    private final ModelMapper mapper;

    public CozinhaModel toModel(Cozinha cozinha) {
        return mapper.map(cozinha, CozinhaModel.class);
    }

    public List<CozinhaModel> toCollectionModel(List<Cozinha> cozinhas) {
        return cozinhas.stream().map(this::toModel).toList();
    }
}
