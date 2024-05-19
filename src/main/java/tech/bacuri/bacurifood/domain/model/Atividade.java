package tech.bacuri.bacurifood.domain.model;

import lombok.*;
import tech.bacuri.bacurifood.core.validation.DataFimDeveEstahDepoisDaDataInicio;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

import static javax.persistence.EnumType.STRING;

@DataFimDeveEstahDepoisDaDataInicio(dataInicioField="dataInicio", dataFimField="dataFim")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
@ToString
@Entity
public class Atividade {
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nome;

    private String descricao;

    @Enumerated(STRING)
    @Column(name = "status_atividade")
    private StatusAtividade atividade;

    @NotNull
    private LocalDate dataInicio;

    private LocalDate dataFim;
}
