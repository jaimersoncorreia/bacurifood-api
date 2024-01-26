package tech.bacuri.bacurifood.api.model.mixin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import tech.bacuri.bacurifood.domain.model.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public class PedidoMixin {
    private Long id;

    private BigDecimal subtotal;

    private BigDecimal taxaFrete;

    private BigDecimal valorTotal;

    @JsonIgnore
    private OffsetDateTime dataCriacao;

    private OffsetDateTime dataConfirmacao;

    private OffsetDateTime dataCancelamento;

    private OffsetDateTime dataEntrega;

    private FormaPagamento formaPagamento;

    private Restaurante restaurante;

    private Usuario cliente;

    private List<ItemPedido> itens = new ArrayList<>();

    @JsonIgnore
    private Endereco enderecoEntrega;

    private StatusPedido statusPedido;
}
