package tech.bacuri.bacurifood.domain.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Cozinha {
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
}
