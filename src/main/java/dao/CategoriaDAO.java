package dao;

import model.Categoria;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

public class CategoriaDAO {

    private static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("caqui");
    private static EntityManager entityManager = entityManagerFactory.createEntityManager();

    public void criarCategoria(Categoria categoria) {
        entityManager.getTransaction().begin();
        entityManager.persist(categoria);
        entityManager.getTransaction().commit();
    }

    public List<Categoria> buscaCategorias() {
        String getCategoria = "select c from Categoria c";
        TypedQuery<Categoria> typedQuery = entityManager
                .createQuery(getCategoria, Categoria.class);
        return typedQuery.getResultList();
    }
}
