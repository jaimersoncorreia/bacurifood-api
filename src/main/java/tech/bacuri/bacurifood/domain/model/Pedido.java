package tech.bacuri.bacurifood.domain.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import tech.bacuri.bacurifood.domain.exception.NegocioException;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;
import static tech.bacuri.bacurifood.domain.model.StatusPedido.*;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
public class Pedido {
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private BigDecimal subtotal;

    @Column(nullable = false)
    private BigDecimal taxaFrete;

    @Column(nullable = false)
    private BigDecimal valorTotal;

    @Embedded
    private Endereco enderecoEntrega;

    @Enumerated(STRING)
    @Column(name = "status_pedido")
    private StatusPedido status = CRIADO;

    @CreationTimestamp
    @Column(nullable = false, columnDefinition = "datetime")
    private OffsetDateTime dataCriacao;

    @Column(columnDefinition = "datetime")
    private OffsetDateTime dataConfirmacao;

    @Column(columnDefinition = "datetime")
    private OffsetDateTime dataCancelamento;

    @Column(columnDefinition = "datetime")
    private OffsetDateTime dataEntrega;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "forma_pagamento_id", nullable = false)
    private FormaPagamento formaPagamento;

    @ManyToOne
    @JoinColumn(name = "restaurante_id", nullable = false)
    private Restaurante restaurante;

    @ManyToOne
    @JoinColumn(name = "usuario_cliente_id", nullable = false)
    private Usuario cliente;

    @OneToMany(mappedBy = "pedido", cascade = ALL)
    private List<ItemPedido> itens = new ArrayList<>();

    public void calcularValorTotal() {
        getItens().forEach(ItemPedido::calcularPrecoTotal);

        this.subtotal = getItens().stream()
                .map(ItemPedido::getPrecoTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        setValorTotal(getSubtotal().add(getTaxaFrete()));
    }

    public void confirmar() {
        setStatus(CONFIRMADO);
        setDataConfirmacao(OffsetDateTime.now());
    }

    public void entregar() {
        setStatus(ENTREGUE);
        setDataEntrega(OffsetDateTime.now());
    }

    public void cancelar() {
        setStatus(CANCELADO);
        setDataCancelamento(OffsetDateTime.now());
    }

    private void setStatus(StatusPedido novoStatus) {
        if (this.status.naoPodeAlterarPara(novoStatus)) {
            String message = "Status do pedido %d não pode ser alterado de %s para %s.";
            throw new NegocioException(String.format(message, getId(), getStatus().getDescricao(), novoStatus.getDescricao()));
        }
        this.status = novoStatus;
    }
}
