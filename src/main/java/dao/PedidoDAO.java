package dao;

import dto.PedidoRelatorioDTO;
import model.Pedido;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.List;

public class PedidoDAO {

    private static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("caqui");
    private static EntityManager entityManager = entityManagerFactory.createEntityManager();

    public void registrarVenda(Pedido pedido) {
        entityManager.getTransaction().begin();
        entityManager.persist(pedido);
        entityManager.getTransaction().commit();
    }

    public List<PedidoRelatorioDTO> buscaPedidoRelatorio(LocalDate dataInicial, LocalDate dataFinal) {
        entityManager.getTransaction().begin();
        String jpql = "SELECT new dto.PedidoRelatorioDTO (p.id, p.data, p.valorTotal) FROM Pedido p WHERE p.data BETWEEN :dataInicial AND :dataFinal";
        TypedQuery<PedidoRelatorioDTO> typedQuery = entityManager.createQuery(jpql, PedidoRelatorioDTO.class)
                .setParameter("dataInicial", dataInicial).setParameter("dataFinal", dataFinal);
        List<PedidoRelatorioDTO> pedidos = typedQuery.getResultList();
        entityManager.getTransaction().commit();
        return pedidos;
    }

}
