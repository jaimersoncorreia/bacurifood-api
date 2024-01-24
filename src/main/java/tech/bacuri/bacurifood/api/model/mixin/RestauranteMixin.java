package tech.bacuri.bacurifood.api.model.mixin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import tech.bacuri.bacurifood.core.validation.Groups;
import tech.bacuri.bacurifood.core.validation.Multiplo;
import tech.bacuri.bacurifood.domain.model.Cozinha;
import tech.bacuri.bacurifood.domain.model.Endereco;
import tech.bacuri.bacurifood.domain.model.FormaPagamento;
import tech.bacuri.bacurifood.domain.model.Produto;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.groups.ConvertGroup;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RestauranteMixin {
    private Long id;

    @NotBlank
    private String nome;

    @NotNull
    @Multiplo(numero = 5)
    private BigDecimal taxaFrete;

    @JsonIgnoreProperties(value = "nome", allowGetters = true)
    @Valid
    @ConvertGroup(to = Groups.CozinhaId.class)
    @NotNull
    private Cozinha cozinha;

    @JsonIgnore
    @Embedded
    private Endereco endereco;

    @JsonIgnore
    private LocalDateTime dataCadastro;

    @JsonIgnore
    private LocalDateTime dataAtualizacao;

    @JsonIgnore
    private List<FormaPagamento> formasPagamento = new ArrayList<>();

    @JsonIgnore
    private List<Produto> produtos = new ArrayList<>();
}
