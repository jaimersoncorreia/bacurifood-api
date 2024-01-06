package tech.bacuri.bacurifood.domain.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
@ToString
@Entity
public class Cidade {
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "estado_id", nullable = false)
    private Estado estado;
}
