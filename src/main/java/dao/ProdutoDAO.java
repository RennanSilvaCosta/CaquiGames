package dao;

import model.Produto;
import utils.JPAUtil;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class ProdutoDAO {

    private static EntityManager entityManager = JPAUtil.getEntityManager();

    public void cadastraProduto(Produto produto) {
        entityManager.getTransaction().begin();
        entityManager.persist(produto);
        entityManager.getTransaction().commit();
    }

    public Produto buscaProduto(String nome) {
        String findProdutoUnicoPorNome = "SELECT p FROM Produto p WHERE nome = :nome";
        TypedQuery<Produto> typedQuery = entityManager
                .createQuery(findProdutoUnicoPorNome, Produto.class)
                .setParameter("nome", nome);
        return typedQuery.getSingleResult();
    }

    public List<Produto> getProdutosPorNome(String str) {
        String findProdutosPorNome = "SELECT p FROM Produto p WHERE p.nome LIKE :str OR p.marca LIKE :str";
        TypedQuery<Produto> typedQuery = entityManager
                .createQuery(findProdutosPorNome, Produto.class)
                .setParameter("str", "%" + str + "%");
        return typedQuery.getResultList();
    }

    public List<Produto> getProdutosPorMarca(String str) {
        String findProdutosPorMarca = "select distinct p from Produto p where marca like % :str order by marca";
        TypedQuery<Produto> typedQuery = entityManager
                .createQuery(findProdutosPorMarca, Produto.class)
                .setParameter("str", str);
        return typedQuery.getResultList();
    }

    public List<Produto> listarNomesProdutos() {
        entityManager.getTransaction().begin();
        String jpql = "select p from Produto p";
        TypedQuery<Produto> typedQuery = entityManager.createQuery(jpql, Produto.class);
        List<Produto> nomesProdutos = typedQuery.getResultList();
        entityManager.getTransaction().commit();
        return nomesProdutos;
    }

    public List<Produto> litarProdutosParaVenda() {
        entityManager.getTransaction().begin();
        String jpql = "select p from Produto p where p.qtdEstoque > 0";
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

    public void deletaProduto(String nome) {
        Produto produto = buscaProduto(nome);
        entityManager.getTransaction().begin();
        entityManager.remove(produto);
        entityManager.getTransaction().commit();
    }

    public void subtraiEstoque(Produto produto) {
        entityManager.getTransaction().begin();
        entityManager.merge(produto);
        entityManager.getTransaction().commit();
    }
}
