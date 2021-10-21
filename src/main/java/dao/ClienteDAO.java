package dao;

import model.Cliente;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Objects;

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

    public Cliente buscaClienteCPF(String cpf) {
        String getClientePorCPF = "select c from Cliente c where cpf = :cpf";
        TypedQuery<Cliente> typedQuery = entityManager
                .createQuery(getClientePorCPF, Cliente.class)
                .setParameter("cpf", cpf);
        List<Cliente> resultList = typedQuery.getResultList();
        if (Objects.isNull(resultList) || resultList.isEmpty()) {
            return null;
        }
        else {
            return resultList.get(0);
        }
    }

    public List<Cliente> getClientesPorNome(String str) {
        String getClientesPorNome = "SELECT c FROM Cliente c WHERE c.nome LIKE :str";
        TypedQuery<Cliente> typedQuery = entityManager
                .createQuery(getClientesPorNome, Cliente.class)
                .setParameter("str", "%" + str + "%")
                .setMaxResults(10);
        return typedQuery.getResultList();
    }
    
    public void deletaCliente(String cpf) {
        Cliente cliente = entityManager.find(Cliente.class, buscaClienteCPF(cpf));
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
        return !Objects.isNull(buscaClienteCPF(cpf));
    }

}