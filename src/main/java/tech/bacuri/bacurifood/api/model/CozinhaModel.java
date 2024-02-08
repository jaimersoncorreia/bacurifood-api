package tech.bacuri.bacurifood.api.model;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;

import static tech.bacuri.bacurifood.api.model.view.RestauranteView.Resumo;

@Getter
@Setter
public class CozinhaModel {
    @JsonView(Resumo.class)
    private Long id;

    @JsonView(Resumo.class)
    private String nome;
}
