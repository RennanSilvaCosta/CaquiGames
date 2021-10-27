package dao;

import exceptions.ValidaCPFException;
import model.Cliente;
import utils.ValidaCPF;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

public class ClienteDAO {

    ValidaCPF validaCPF = new ValidaCPF();

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
        String getClientesPorNome = "SELECT c FROM Cliente c WHERE c.nome LIKE :str";
        TypedQuery<Cliente> typedQuery = entityManager
                .createQuery(getClientesPorNome, Cliente.class)
                .setParameter("str", "%" + str + "%")
                .setMaxResults(10);
        return typedQuery.getResultList();
    }
    
    public void deletaCliente(String cpf) {
        Cliente cliente = entityManager.find(Cliente.class, validaCPF.buscaClientePorCPF(cpf));
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
        new Cliente();
        Cliente c;
        c = validaCPF.buscaClientePorCPF( cpf );
        if( Objects.isNull( c ) ) {
            return false;
        } else {
            return true;
        }
    }

}