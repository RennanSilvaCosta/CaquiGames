package dao;

import model.Produto;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

public class ProdutoDAO {

    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("caqui");
    private static EntityManager em = emf.createEntityManager();

    public List<Produto> listarNomesProdutos() {
        em.getTransaction().begin();
        String jpql = "select p from Produto p";
        TypedQuery<Produto> typedQuery = em.createQuery(jpql, Produto.class);
        List<Produto> nomesProdutos = typedQuery.getResultList();
        em.getTransaction().commit();
        return nomesProdutos;
    }

}
