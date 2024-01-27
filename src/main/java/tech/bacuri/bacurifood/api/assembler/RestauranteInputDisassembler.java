package tech.bacuri.bacurifood.api.assembler;

import org.springframework.stereotype.Component;
import tech.bacuri.bacurifood.api.model.input.RestauranteInput;
import tech.bacuri.bacurifood.domain.model.Cozinha;
import tech.bacuri.bacurifood.domain.model.Restaurante;

@Component
public class RestauranteInputDisassembler {
    public Restaurante toEntity(RestauranteInput restauranteInput) {
        Cozinha cozinha = Cozinha.builder().id(restauranteInput.getCozinha().getId()).build();
        return Restaurante.builder()
                .nome(restauranteInput.getNome())
                .taxaFrete(restauranteInput.getTaxaFrete())
                .cozinha(cozinha)
                .build();
    }
}
