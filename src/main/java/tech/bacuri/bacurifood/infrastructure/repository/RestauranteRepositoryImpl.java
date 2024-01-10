package tech.bacuri.bacurifood.infrastructure.repository;

import org.springframework.stereotype.Repository;
import tech.bacuri.bacurifood.domain.model.Restaurante;
import tech.bacuri.bacurifood.domain.repository.RestauranteRepositoryQueries;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.util.List;

@Repository
public class RestauranteRepositoryImpl implements RestauranteRepositoryQueries {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<Restaurante> find(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal) {
        String jpql = "from Restaurante r " +
                "where r.nome like concat('%', :nome, '%') " +
                "and r.taxaFrete between :taxaFreteInicial and :taxaFreteFinal";

        return manager.createQuery(jpql, Restaurante.class)
                .setParameter("nome", nome)
                .setParameter("taxaFreteInicial", taxaFreteInicial)
                .setParameter("taxaFreteFinal", taxaFreteFinal)
                .getResultList();
    }
}
