package tech.bacuri.bacurifood.infrastructure.repository.spec;

import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import tech.bacuri.bacurifood.domain.model.Restaurante;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@AllArgsConstructor
public class RestauranteComNomeSemelhateSpec implements Specification<Restaurante> {

    private String nome;

    @Override
    public Predicate toPredicate(Root<Restaurante> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        return builder.like(root.get("nome"), "%" + this.nome + "%");
    }
}
