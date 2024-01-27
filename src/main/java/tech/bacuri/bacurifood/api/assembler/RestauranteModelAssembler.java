package tech.bacuri.bacurifood.api.assembler;

import org.springframework.stereotype.Component;
import tech.bacuri.bacurifood.api.model.CozinhaModel;
import tech.bacuri.bacurifood.api.model.RestauranteModel;
import tech.bacuri.bacurifood.domain.model.Restaurante;

import java.util.List;

@Component
public class RestauranteModelAssembler {
    public RestauranteModel toModel(Restaurante restaurante) {
        CozinhaModel cozinhaModel = CozinhaModel.builder()
                .id(restaurante.getCozinha().getId())
                .nome(restaurante.getNome()).build();

        return RestauranteModel.builder()
                .id(restaurante.getId())
                .nome(restaurante.getNome())
                .taxaFrete(restaurante.getTaxaFrete())
                .cozinha(cozinhaModel)
                .build();
    }

    public List<RestauranteModel> toCollectionModel(List<Restaurante> restaurantes) {
        return restaurantes.stream().map(this::toModel).toList();
    }
}
