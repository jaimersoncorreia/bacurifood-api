package tech.bacuri.bacurifood.api.model.mixin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import tech.bacuri.bacurifood.domain.model.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PedidoMixin {
    private Long id;

    private BigDecimal subtotal;

    private BigDecimal taxaFrete;

    private BigDecimal valorTotal;

    @JsonIgnore
    private LocalDateTime dataCriacao;

    private LocalDateTime dataConfirmacao;

    private LocalDateTime dataCancelamento;

    private LocalDateTime dataEntrega;

    private FormaPagamento formaPagamento;

    private Restaurante restaurante;

    private Usuario cliente;

    private List<ItemPedido> itens = new ArrayList<>();

    @JsonIgnore
    private Endereco enderecoEntrega;

    private StatusPedido statusPedido;
}
