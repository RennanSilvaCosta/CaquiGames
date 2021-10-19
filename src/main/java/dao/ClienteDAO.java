package dao;

import model.Cliente;
import model.Endereco;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Objects;

public class ClienteDAO {

    private static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("caquidb");
    private static EntityManager entityManager = entityManagerFactory.createEntityManager();

    public void criaCliente(Cliente cliente, Endereco endereco) {

        entityManager.getTransaction().begin();
        entityManager.persist(cliente);
        entityManager.getTransaction().commit();

    }

    public Cliente getCliente(String cpf) {
        String getClientePorCpf = "select * from Cliente where cpf = ':cpf';";
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
    
    public void deletaCliente( String cpf ) {
        Cliente cliente = entityManager.find(Cliente.class, getCliente(cpf));
        entityManager.getTransaction().begin();
        entityManager.remove(cliente);
        entityManager.getTransaction().commit();
    }

    public void editaCliente( Cliente cliente, Long idCliente ) {

        Cliente clienteNew = new Cliente();
        clienteNew.setId(idCliente);

        if ( !Objects.isNull(cliente.getNome()) ) {
            clienteNew.setNome(cliente.getNome());
        }
        if ( !Objects.isNull(cliente.getCpf()) ) {
            clienteNew.setCpf(cliente.getCpf());
        }
        if ( !Objects.isNull(cliente.getCelular()) ) {
            clienteNew.setCelular(cliente.getCelular());
        }
        if ( !Objects.isNull(cliente.getEmail()) ) {
            clienteNew.setEmail(cliente.getEmail());
        }
        if ( !Objects.isNull(cliente.getDataNasc()) ) {
            clienteNew.setDataNasc(cliente.getDataNasc());
        }

        entityManager.getTransaction().begin();
        entityManager.merge(clienteNew);
        entityManager.getTransaction().commit();

    }

}