package dao;

import model.Funcionario;
import session.UserSession;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.math.BigInteger;

public class MainDAO {

    private static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("caqui");
    private static EntityManager entityManager = entityManagerFactory.createEntityManager();


    public Double obterTotalVendido() {
        Funcionario func = UserSession.getFuncionario();
        entityManager.getTransaction().begin();
        Query query = entityManager.createNativeQuery("SELECT SUM(pedido.valor_total) AS 'totalVendido' FROM pedido WHERE pedido.id_funcionario = :idFunc");
        query.setParameter("idFunc", func.getId());
        entityManager.getTransaction().commit();
        return (Double) query.getSingleResult();
    }

    public BigDecimal obterTotalProdutos() {
        entityManager.getTransaction().begin();
        Query query = entityManager.createNativeQuery("SELECT SUM(produto.quantidade_estoque) AS 'totalProdutos' FROM produto");
        entityManager.getTransaction().commit();
        return (BigDecimal) query.getSingleResult();
    }

    public BigInteger obterTotalPedidos() {
        entityManager.getTransaction().begin();
        Query query = entityManager.createNativeQuery("SELECT COUNT(pedido.id) AS 'totalPedidos' FROM pedido");
        entityManager.getTransaction().commit();
        return (BigInteger) query.getSingleResult();
    }
}
