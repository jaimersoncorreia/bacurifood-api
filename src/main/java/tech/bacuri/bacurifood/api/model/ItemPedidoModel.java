package tech.bacuri.bacurifood.api.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ItemPedidoModel {
    private Long id;
    private Long produtoId;
    private String produtoNome;
    private Integer quantidades;
    private BigDecimal precoUnitario;
    private BigDecimal precoTotal;
    private String observacao;
}
