package tech.bacuri.bacurifood.api.model;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;
import tech.bacuri.bacurifood.api.model.view.RestauranteView.ApenasNome;
import tech.bacuri.bacurifood.api.model.view.RestauranteView.Resumo;

import java.math.BigDecimal;

@Getter
@Setter
public class RestauranteModel {
    @JsonView({Resumo.class, ApenasNome.class})
    private Long id;

    @JsonView({Resumo.class, ApenasNome.class})
    private String nome;

    @JsonView(Resumo.class)
    private BigDecimal taxaFrete;

    @JsonView(Resumo.class)
    private CozinhaModel cozinha;

    private Boolean ativo;

    private EnderecoModel endereco;
}
