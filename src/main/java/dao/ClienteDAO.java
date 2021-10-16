package dao;

import model.Cliente;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;

public class ClienteDAO {

    @PersistenceContext
    private EntityManager em;

    public void criaCliente(String nome, String cpf, String celular, String email, LocalDate dataNasc ) {

        final StringBuilder createCliente = new StringBuilder();

        createCliente.append("insert into Cliente ");
        createCliente.append("(nome, ");
        createCliente.append("cpf, ");
        createCliente.append("celular, ");
        createCliente.append("email, ");
        createCliente.append("dataNasc, ");
        createCliente.append("endereco) ");
        createCliente.append("values ");
        createCliente.append("( " + nome);
        createCliente.append("( " + cpf);
        createCliente.append("( " + celular);
        createCliente.append("( " + email);
        createCliente.append("( " + dataNasc);
        createCliente.append(")");

        this.em.createNativeQuery(createCliente.toString()).executeUpdate();

    }

    public Cliente getCliente(String cpf) {
        StringBuilder getClientePorCpf = new StringBuilder();

        getClientePorCpf.append("select * from Cliente where cpf = '" + cpf + "'" );

        return this.em.createQuery(getClientePorCpf.toString(), Cliente.class).getSingleResult();

    }
    
    public void deletaCliente( String cpf ) {
        StringBuilder deleteClientePorCpf = new StringBuilder();
        
        Cliente c = getCliente(cpf);
        Long idCliente = c.getId();
        deleteClientePorCpf.append("delete * from Cliente where id = " + idCliente);
        
    }

}
