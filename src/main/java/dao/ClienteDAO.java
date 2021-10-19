package dao;

import model.Cliente;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

public class ClienteDAO {

    private static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("caqui");
    private static EntityManager entityManager = entityManagerFactory.createEntityManager();

    public void criaCliente(Cliente cliente) {
        entityManager.getTransaction().begin();
        entityManager.persist(cliente);
        entityManager.persist(cliente.getEndereco());
        entityManager.getTransaction().commit();
    }

    public List<Cliente> buscaTodosClientes() {
        String getCliente = "select c from Cliente c";
        TypedQuery<Cliente> typedQuery = entityManager
                .createQuery(getCliente, Cliente.class);
        return typedQuery.getResultList();
    }

    public Cliente buscaClienteCpf(String cpf) {
        String getClientePorCpf = "select c from Cliente where cpf = ':cpf';";
        TypedQuery<Cliente> typedQuery = entityManager
                .createQuery(getClientePorCpf, Cliente.class)
                .setParameter("cpf", cpf);
        return typedQuery.getSingleResult();
    }

    public List<Cliente> getClientesPorNome(String str) {
        String getClientesPorNome = "select distinct * from Cliente where nome like '%:str%' order by nome limit 10;";
        TypedQuery<Cliente> typedQuery = entityManager
                .createQuery(getClientesPorNome, Cliente.class)
                .setParameter("str", str);
        return typedQuery.getResultList();
    }
    
    public void deletaCliente(String cpf) {
        Cliente cliente = entityManager.find(Cliente.class, buscaClienteCpf(cpf));
        entityManager.getTransaction().begin();
        entityManager.remove(cliente);
        entityManager.getTransaction().commit();
    }

    public void editaCliente(Cliente cliente) {
        entityManager.getTransaction().begin();
        entityManager.merge(cliente);
        entityManager.getTransaction().commit();
    }

}