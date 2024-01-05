package tech.bacuri.bacurifood.jpa;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tech.bacuri.bacurifood.domain.model.Cozinha;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Component
public class CadastroCozinha {

    @PersistenceContext
    private EntityManager manager;

    public List<Cozinha> listar() {
        TypedQuery<Cozinha> query = manager.createQuery("from Cozinha", Cozinha.class);
        return query.getResultList();
    }

    @Transactional
    public Cozinha salvar(Cozinha cozinha) {
        return manager.merge(cozinha);
    }

    public Cozinha obter(Long id) {
        return manager.find(Cozinha.class, id);
    }

    @Transactional
    public void remover(Cozinha cozinha) {
        manager.remove(obter(cozinha.getId()));
    }
}
