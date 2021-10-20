package dao;

import exceptions.ClienteJaExisteException;
import model.Cliente;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Objects;

public class ClienteDAO {

    private static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("caquidb");
    private static EntityManager entityManager = entityManagerFactory.createEntityManager();

    public void criaCliente(Cliente cliente) {

        boolean isCpfExiste = isClienteExiste(cliente.getCpf());

        if (Objects.equals(isCpfExiste, false)) {
            entityManager.getTransaction().begin();
            entityManager.persist(cliente.getEndereco());
            entityManager.persist(cliente);
            entityManager.getTransaction().commit();
        }
        else throw new ClienteJaExisteException();
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
        String getClientesPorNome = "select distinct c from Cliente where nome like '%:str%' order by nome limit 20;";
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

    boolean isClienteExiste(String cpf) {
        buscaClienteCpf(cpf);
        return true;
    }

}