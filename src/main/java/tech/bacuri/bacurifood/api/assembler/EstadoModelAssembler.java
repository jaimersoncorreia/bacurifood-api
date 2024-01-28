package tech.bacuri.bacurifood.api.assembler;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import tech.bacuri.bacurifood.api.model.EstadoModel;
import tech.bacuri.bacurifood.domain.model.Estado;

import java.util.List;

@AllArgsConstructor
@Component
public class EstadoModelAssembler {
    private final ModelMapper modelMapper;

    public EstadoModel toModel(Estado estado) {
        return modelMapper.map(estado, EstadoModel.class);
    }

    public List<EstadoModel> toCollectionModel(List<Estado> estados) {
        return estados.stream().map(this::toModel).toList();
    }
}
