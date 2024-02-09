package tech.bacuri.bacurifood.api.model;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.Getter;
import lombok.Setter;
import tech.bacuri.bacurifood.domain.model.StatusPedido;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@JsonFilter("pedidoFilter")
@Getter
@Setter
public class PedidoResumoModel {
    private String codigo;
    private BigDecimal subtotal;
    private BigDecimal taxaFrete;
    private BigDecimal valorTotal;
    private StatusPedido status;
    private OffsetDateTime dataCriacao;
    private RestauranteResumoModel restaurante;
    private UsuarioModel cliente;
}
