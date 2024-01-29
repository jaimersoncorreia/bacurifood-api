package tech.bacuri.bacurifood.api.assembler;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import tech.bacuri.bacurifood.api.model.input.RestauranteInput;
import tech.bacuri.bacurifood.domain.model.Cidade;
import tech.bacuri.bacurifood.domain.model.Cozinha;
import tech.bacuri.bacurifood.domain.model.Restaurante;

import java.util.Objects;


@AllArgsConstructor
@Component
public class RestauranteInputDisassembler {

    private final ModelMapper mapper;

    public Restaurante toEntity(RestauranteInput restauranteInput) {
        return mapper.map(restauranteInput, Restaurante.class);
    }

    public void copyToEntity(RestauranteInput restauranteInput, Restaurante restaurante) {
        /* Pra evitar org.hibernate.HibernateException: identifier of an instance of
           tech.bacuri.bacurifood.domain.model.Cozinha was altered from 1 to 2 */
        restaurante.setCozinha(new Cozinha());

        if (Objects.nonNull(restaurante.getEndereco()))
            restaurante.getEndereco().setCidade(new Cidade());

        mapper.map(restauranteInput, restaurante);
    }
}
