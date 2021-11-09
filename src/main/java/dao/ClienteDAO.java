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

    public List<Cliente> getClientesPorNome(String str) {
        String getClientesPorNome = "SELECT c FROM Cliente c WHERE c.nome LIKE :str OR c.cpf LIKE :str";
        TypedQuery<Cliente> typedQuery = entityManager
                .createQuery(getClientesPorNome, Cliente.class)
                .setParameter("str", "%" + str + "%")
                .setMaxResults(10);
        return typedQuery.getResultList();
    }

    public void deletaCliente(String cpf) {
        Cliente cliente = buscaClientePorCPF(cpf);
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
        String getClientePorCPF = "SELECT c FROM Cliente c WHERE cpf = :cpf";
        TypedQuery<Cliente> typedQuery = entityManager
                .createQuery(getClientePorCPF, Cliente.class)
                .setParameter("cpf", cpf);
        List<Cliente> resultList = typedQuery.getResultList();
        return !resultList.isEmpty();
    }

    public Cliente buscaClientePorCPF(String cpf) throws NoResultException {
        String getClientePorCPF = "SELECT c FROM Cliente c WHERE cpf = :cpf";
        TypedQuery<Cliente> typedQuery = entityManager
                .createQuery(getClientePorCPF, Cliente.class)
                .setParameter("cpf", cpf);
        return typedQuery.getSingleResult();
    }

    public boolean isEmailExiste(String email) {
        String getClientePorEmail = "SELECT c FROM Cliente c WHERE c.email = :email";
        TypedQuery<Cliente> typedQuery = entityManager
                .createQuery(getClientePorEmail, Cliente.class)
                .setParameter("email", email);
        List<Cliente> resultList = typedQuery.getResultList();
        return !resultList.isEmpty();
    }
}