package tech.bacuri.bacurifood.api.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class RestauranteModel {
    private Long id;

    private String nome;

    private BigDecimal taxaFrete;

    private CozinhaModel cozinha;
}
