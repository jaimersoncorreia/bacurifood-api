package tech.bacuri.bacurifood.infrastructure.repository.spec;

import org.springframework.data.jpa.domain.Specification;
import tech.bacuri.bacurifood.domain.model.Restaurante;

import java.math.BigDecimal;

public class RestauranteSpecs {

    public static Specification<Restaurante> comFreteGratis() {
        return (root, query, builder) -> builder.equal(root.get("taxaFrete"), BigDecimal.ZERO);
    }

    public static Specification<Restaurante> comNomeSemelhante(String nome) {
        return (root, query, builder) -> builder.like(root.get("nome"), "%" + nome + "%");
    }
}
