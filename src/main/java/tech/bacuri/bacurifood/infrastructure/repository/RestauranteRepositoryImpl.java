package tech.bacuri.bacurifood.infrastructure.repository;

import org.springframework.stereotype.Repository;
import tech.bacuri.bacurifood.domain.model.Restaurante;
import tech.bacuri.bacurifood.domain.repository.RestauranteRepositoryQueries;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.List;

@Repository
public class RestauranteRepositoryImpl implements RestauranteRepositoryQueries {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<Restaurante> find(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal) {

        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Restaurante> criteria = builder.createQuery(Restaurante.class);
        Root<Restaurante> root = criteria.from(Restaurante.class);

        Predicate predicateNome = builder.like(root.get("nome"), "%" + nome + "%");
        Predicate predicateInicial = builder.greaterThanOrEqualTo(root.get("taxaFrete"), taxaFreteInicial);
        Predicate predicateFinal = builder.lessThanOrEqualTo(root.get("taxaFrete"), taxaFreteFinal);

        criteria.where(predicateNome, predicateInicial, predicateFinal);

        return manager.createQuery(criteria).getResultList();
    }
}
