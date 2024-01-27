package tech.bacuri.bacurifood.api.assembler;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import tech.bacuri.bacurifood.api.model.input.RestauranteInput;
import tech.bacuri.bacurifood.domain.model.Restaurante;


@AllArgsConstructor
@Component
public class RestauranteInputDisassembler {

    private final ModelMapper mapper;

    public Restaurante toEntity(RestauranteInput restauranteInput) {
        return mapper.map(restauranteInput, Restaurante.class);
    }
}
