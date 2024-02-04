package tech.bacuri.bacurifood.domain.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "item_pedido")
public class ItemPedido {
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "quantidades", nullable = false)
    private Integer quantidade;

    @Column(nullable = false)
    private BigDecimal precoUnitario;

    @Column(nullable = false)
    private BigDecimal precoTotal;

    private String observacao;

    @ManyToOne
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;

    public void calcularPrecoTotal() {
        BigDecimal precoUnitario = getPrecoUnitario();
        Integer qtde = getQuantidade();

        if (Objects.isNull(precoUnitario))
            precoUnitario = BigDecimal.ZERO;

        if (Objects.isNull(qtde))
            qtde = 0;

        this.setPrecoTotal(precoUnitario.multiply(new BigDecimal(qtde)));
    }
}
