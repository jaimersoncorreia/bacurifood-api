package tech.bacuri.bacurifood.domain.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
public class Restaurante {
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(name = "taxa_frete", nullable = false)
    private BigDecimal taxaFrete;

    @ManyToOne
    @JoinColumn(name = "cozinha_id", nullable = false)
    private Cozinha cozinha;

    @Embedded
    private Endereco endereco;

    private Boolean ativo = TRUE;

    private Boolean aberto = TRUE;

    @CreationTimestamp
    @Column(nullable = false, columnDefinition = "datetime")
    private OffsetDateTime dataCadastro;

    @UpdateTimestamp
    @Column(nullable = false, columnDefinition = "datetime")
    private OffsetDateTime dataAtualizacao;

    @ManyToMany
    @JoinTable(name = "restaurante_forma_pagamento",
            joinColumns = @JoinColumn(name = "restaurante_id"),
            inverseJoinColumns = @JoinColumn(name = "forma_pagamento_id"))
    private Set<FormaPagamento> formasPagamento = new HashSet<>();

    @OneToMany(mappedBy = "restaurante")
    private List<Produto> produtos = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "restaurante_usuario_responsavel",
            joinColumns = @JoinColumn(name = "restaurante_id"),
            inverseJoinColumns = @JoinColumn(name = "usuario_id"))
    private Set<Usuario> responsaveis = new HashSet<>();

    public void ativar() {
        this.setAtivo(true);
    }

    public void inativar() {
        this.setAtivo(false);
    }

    public boolean adicionarFormaPagamento(FormaPagamento formaPagamento) {
        return getFormasPagamento().add(formaPagamento);
    }

    public boolean removerFormaPagamento(FormaPagamento formaPagamento) {
        return getFormasPagamento().remove(formaPagamento);
    }

    public boolean atribuirResponsavel(Usuario usuario) {
        return responsaveis.add(usuario);
    }

    public boolean removerUsuario(Usuario usuario) {
        return responsaveis.remove(usuario);
    }

    public void fechar() {
        setAberto(FALSE);
    }

    public void abrir() {
        setAberto(TRUE);
    }

    public boolean naoAceitaFormaPagamento(FormaPagamento formaPagamento) {
        return !aceitaFormaPagamento(formaPagamento);
    }

    public boolean aceitaFormaPagamento(FormaPagamento formaPagamento) {
        return formasPagamento.contains(formaPagamento);
    }
}
