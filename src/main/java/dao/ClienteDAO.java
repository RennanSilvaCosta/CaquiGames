package dao;

import model.Cliente;

import javax.persistence.*;
import java.util.List;

public class ClienteDAO {

    private static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("caqui");
    private static EntityManager entityManager = entityManagerFactory.createEntityManager();

    public void criaCliente(Cliente cliente) {
        entityManager.getTransaction().begin();
        entityManager.persist(cliente.getEndereco());
        entityManager.persist(cliente);
        entityManager.getTransaction().commit();
    }

    public List<Cliente> buscaTodosClientes() {
        String getCliente = "select c from Cliente c";
        TypedQuery<Cliente> typedQuery = entityManager
                .createQuery(getCliente, Cliente.class);
        return typedQuery.getResultList();
    }

    public Cliente buscaClienteCpf(String cpf) throws NoResultException {
        String getClientePorCpf = "select c from Cliente c where cpf = :cpf";
        TypedQuery<?> typedQuery = entityManager
                .createQuery(getClientePorCpf, Cliente.class)
                .setParameter("cpf", cpf);
        return (Cliente) typedQuery.getSingleResult();
    }

    public List<Cliente> getClientesPorNome(String str) {
        String getClientesPorNome = "select distinct c from Cliente c where nome like '%:str%' order by nome limit 20";
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

    public boolean isClienteExiste(String cpf) {
        return buscaClienteCpf(cpf) == null;
    }

}