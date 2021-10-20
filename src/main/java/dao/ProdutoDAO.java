package dao;

import model.Produto;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.lang.reflect.Type;
import java.util.List;

public class ProdutoDAO {

    private static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("caqui");
    private static EntityManager entityManager = entityManagerFactory.createEntityManager();

    public void cadastraProduto( Produto produto ) {
        entityManager.getTransaction().begin();
        entityManager.persist(produto);
        entityManager.getTransaction().begin();
    }

    public Produto buscaProduto(String descricao) {
        String findProdutoUnicoPorNome = "select p from Produto where descricao = ':descricao';";
        TypedQuery<Produto> typedQuery = entityManager
                .createQuery(findProdutoUnicoPorNome, Produto.class)
                .setParameter("descricao", descricao);
        return typedQuery.getSingleResult();
    }

    public List<Produto> getProdutosPorNome(String str) {
        String findProdutosPorNome = "select distinct p from Produto where descricao like '%:str%' order by nome limit 10;";
        TypedQuery<Produto> typedQuery = entityManager
                .createQuery(findProdutosPorNome, Produto.class)
                .setParameter("str", str);
        return typedQuery.getResultList();
    }

    public List<Produto> getProdutosPorMarca(String str) {
        String findProdutosPorMarca = "select distinct p from Produto where marca like '%:str' order by marca limit 10;";
        TypedQuery<Produto> typedQuery = entityManager
                .createQuery(findProdutosPorMarca, Produto.class)
                .setParameter("str", str);
        return typedQuery.getResultList();
    }

    public List<Produto> listarNomesProdutos() {
        entityManager.getTransaction().begin();
        String jpql = "select p from Produto p limit 20;";
        TypedQuery<Produto> typedQuery = entityManager.createQuery(jpql, Produto.class);
        List<Produto> nomesProdutos = typedQuery.getResultList();
        entityManager.getTransaction().commit();
        return nomesProdutos;
    }

    public void editaProduto(Produto produto) {
        entityManager.getTransaction().begin();
        entityManager.merge(produto);
        entityManager.getTransaction().commit();
    }

    public void deletaProduto(String descricao) {
        Produto produto = entityManager.find(Produto.class, buscaProduto(descricao));
        entityManager.getTransaction().begin();
        entityManager.remove(produto);
        entityManager.getTransaction().commit();
    }
}
