package tech.bacuri.bacurifood.api.assembler;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import tech.bacuri.bacurifood.api.model.input.EstadoInput;
import tech.bacuri.bacurifood.domain.model.Estado;

@AllArgsConstructor
@Component
public class EstadoInputDisassembler {
    private final ModelMapper mapper;

    public Estado toEntity(EstadoInput estadoInput) {
        return mapper.map(estadoInput, Estado.class);
    }

    public void copyToEntity(EstadoInput estadoInput, Estado estado) {
        mapper.map(estadoInput, estado);
    }
}
