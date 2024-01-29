package tech.bacuri.bacurifood.api.model;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
public class RestauranteModel {
    private Long id;
    private String nome;
    private BigDecimal precoFrete;
    private CozinhaModel cozinha;
    private Boolean ativo;
    private EnderecoModel endereco;
}
