package utils;

import br.com.caelum.stella.ValidationMessage;
import br.com.caelum.stella.validation.CPFValidator;
import exceptions.ValidaCPFException;
import model.Cliente;
import model.Funcionario;

import javax.persistence.*;
import java.util.List;

public class ValidaCPF {

    private static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory( "caqui" );
    private EntityManager entityManager = entityManagerFactory.createEntityManager();

    public boolean isCPFValido( String cpf ) {
        CPFValidator cpfValidator = new CPFValidator();
        List< ValidationMessage > erros = cpfValidator.invalidMessagesFor( cpf );
        if( erros.size() > 0 ) {
            return false;
        } else {
            return true;
        }

    }

    public Funcionario buscaFuncionarioPorCPF( String cpf ) throws NoResultException {
        String getFuncionarioPorCPF = "SELECT f FROM Funcionario f WHERE cpf = :cpf";
        TypedQuery< Funcionario > typedQuery = entityManager
                .createQuery( getFuncionarioPorCPF, Funcionario.class )
                .setParameter( "cpf", cpf );
        return typedQuery.getSingleResult();
    }

    public Cliente buscaClientePorCPF( String cpf ) throws NoResultException {
        String getClientePorCPF = "SELECT c FROM Cliente c WHERE cpf = :cpf";
        TypedQuery< Cliente > typedQuery = entityManager
                .createQuery( getClientePorCPF, Cliente.class )
                .setParameter( "cpf", cpf );
        return typedQuery.getSingleResult();
    }

}



