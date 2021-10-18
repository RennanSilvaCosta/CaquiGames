package dao;

import model.Cliente;
import model.Endereco;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class ClienteDAO {

    private static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("caquidb");
    private static EntityManager entityManager = entityManagerFactory.createEntityManager();

    public void criaCliente(String nome, String cpf, String celular, String email, LocalDate dataNasc, Endereco endereco) {

        Cliente cliente = new Cliente();
        cliente.setNome(nome);
        cliente.setCpf(cpf);
        cliente.setEmail(email);
        cliente.setDataNasc(dataNasc);
        cliente.setCelular(celular);
        cliente.setEndereco(endereco);

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
        Cliente cliente = entityManager.find(Cliente.class, getCliente(cpf).getId());
        entityManager.getTransaction().begin();
        entityManager.remove(cliente);
        entityManager.getTransaction().commit();
    }

    public void editaCliente(String nome, String cpf, String celular, String email, LocalDate dataNasc ) {

        Cliente cliente = getCliente(cpf);

        if ( !Objects.isNull(nome) ) {
            cliente.setNome(nome);
        }
        if ( !Objects.isNull(cpf) ) {
            cliente.setCpf(cpf);
        }
        if ( !Objects.isNull(celular) ) {
            cliente.setCelular(celular);
        }
        if ( !Objects.isNull(email) ) {
            cliente.setEmail(email);
        }
        if ( !Objects.isNull(dataNasc) ) {
            cliente.setDataNasc(dataNasc);
        }

        entityManager.getTransaction().begin();
        entityManager.merge(cliente);
        entityManager.getTransaction().commit();

    }

}