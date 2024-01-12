package tech.bacuri.bacurifood.infrastructure.repository;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import tech.bacuri.bacurifood.domain.repository.CustomJpaRepository;

import javax.persistence.EntityManager;
import java.util.Optional;

public class CustomJpaRepositoryImpl<T, ID> extends SimpleJpaRepository<T, ID> implements CustomJpaRepository<T, ID> {
    private final EntityManager manager;

    public CustomJpaRepositoryImpl(JpaEntityInformation<T, ?> information, EntityManager manager) {
        super(information, manager);

        this.manager = manager;
    }

    @Override
    public Optional<T> buscarPrimeiro() {
        String jpql = "from " + getDomainClass().getName();

        T entity = manager.createQuery(jpql, getDomainClass())
                .setMaxResults(1)
                .getSingleResult();

        return Optional.ofNullable(entity);
    }
}
