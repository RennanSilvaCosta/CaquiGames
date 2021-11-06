package dao;

import dto.DetalheItemPedidoDTO;
import dto.DetalhePedidoDTO;
import dto.PedidoRelatorioDTO;
import model.Pedido;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.*;

import javax.persistence.*;
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

    public List<DetalhePedidoDTO> detalhesPedido(LocalDate dataInicial, LocalDate dataFinal) {
        entityManager.getTransaction().begin();
        Query query = entityManager.createNativeQuery("SELECT pedido.id AS 'idPedido', pedido.data AS 'dataPedido', " +
                "pedido.desconto, pedido.forma_pagamento AS 'pagamento', pedido.sub_total AS 'subTotal', pedido.valor_total AS 'valorTotal',\n" +
                " cliente.nome AS 'nomeCliente', funcionario.nome AS 'nomeFuncionario'\n" +
                "FROM ((pedido\n" +
                "INNER JOIN cliente ON cliente.id = pedido.id_cliente) \n" +
                "INNER JOIN funcionario ON funcionario.id = pedido.id_funcionario) WHERE pedido.data BETWEEN :dataInicial AND :dataFinal");
        query.setParameter("dataInicial", dataInicial);
        query.setParameter("dataFinal", dataFinal);

        query.unwrap(SQLQuery.class)
                .addScalar("idPedido", LongType.INSTANCE)
                .addScalar("dataPedido", LocalDateType.INSTANCE)
                .addScalar("nomeCliente", StringType.INSTANCE)
                .addScalar("nomeFuncionario", StringType.INSTANCE)
                .addScalar("pagamento", StringType.INSTANCE)
                .addScalar("desconto", DoubleType.INSTANCE)
                .addScalar("subTotal", DoubleType.INSTANCE)
                .addScalar("valorTotal", DoubleType.INSTANCE)
                .setResultTransformer(Transformers.aliasToBean(DetalhePedidoDTO.class));
        List<DetalhePedidoDTO> dto = query.getResultList();
        entityManager.getTransaction().commit();
        return dto;
    }

    public List<DetalheItemPedidoDTO> detalheItensPedido(Long idPedido) {
        entityManager.getTransaction().begin();
        Query query2 = entityManager.createNativeQuery("SELECT itempedido.preco, itempedido.quantidade, produto.nome, itempedido.total " +
                "FROM itempedido INNER JOIN produto ON itempedido.id_produto = produto.id WHERE itempedido.id_pedido = :idPedido");
        query2.setParameter("idPedido", idPedido);

        query2.unwrap(SQLQuery.class)
                .addScalar("preco", DoubleType.INSTANCE)
                .addScalar("quantidade", IntegerType.INSTANCE)
                .addScalar("nome", StringType.INSTANCE)
                .addScalar("total", DoubleType.INSTANCE)
                .setResultTransformer(Transformers.aliasToBean(DetalheItemPedidoDTO.class));
        List<DetalheItemPedidoDTO> itemDTO = query2.getResultList();
        entityManager.getTransaction().commit();
        return itemDTO;
    }
}
