package dao;

import dto.FuncionarioDTO;
import model.Funcionario;
import utils.ValidaCPF;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

public class FuncionarioDAO {

    private static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("caqui");
    private static EntityManager entityManager = entityManagerFactory.createEntityManager();

    public void criaFuncionario(Funcionario funcionario) {
        entityManager.getTransaction().begin();
        entityManager.persist(funcionario.getEndereco());
        entityManager.persist(funcionario);
        entityManager.getTransaction().begin();
    }

    public List<Funcionario> buscaTodosFuncionarios() {
        String getFuncionarios = "SELECT f FROM Funcionario f";
        TypedQuery<Funcionario> typedQuery = entityManager
                .createQuery(getFuncionarios, Funcionario.class);
        return typedQuery.getResultList();
    }

    public List<Funcionario> buscaFuncionariosPorNome(String str) {
        String getFuncionariosPorNome = "SELECT f FROM Funcionario f WHERE f.nome LIKE :str";
        TypedQuery<Funcionario> typedQuery = entityManager
                .createQuery(getFuncionariosPorNome, Funcionario.class)
                .setParameter("str", "%" + str + "%")
                .setMaxResults(10);
        return typedQuery.getResultList();
    }

    public void deletaFuncionario(String cpf) {
        Funcionario funcionario = entityManager.find(Funcionario.class, buscaFuncionarioPorCPF(cpf));
        entityManager.getTransaction().begin();
        entityManager.remove(funcionario);
        entityManager.getTransaction().commit();
    }

    public void editaFuncionario(Funcionario funcionario) {
        entityManager.getTransaction().begin();
        entityManager.merge(funcionario);
        entityManager.getTransaction();
    }

    public Funcionario buscaFuncionarioEmaileSenha(FuncionarioDTO dto) throws NoResultException {
        String getFuncionarioEmailSenha = "SELECT f FROM Funcionario f WHERE f.email = :email and f.senha = :senha";
        TypedQuery<Funcionario> typedQuery = entityManager
                .createQuery(getFuncionarioEmailSenha, Funcionario.class)
                .setParameter("email", dto.getEmail())
                .setParameter("senha", dto.getSenha());
        return typedQuery.getSingleResult();
    }

    public boolean isFuncionarioExiste(String cpf) {
        new Funcionario();
        Funcionario f;
        f = buscaFuncionarioPorCPF(cpf);
        return Objects.isNull( f );
    }

    public Funcionario buscaFuncionarioPorCPF( String cpf ) throws NoResultException {
        String getFuncionarioPorCPF = "SELECT f FROM Funcionario f WHERE cpf = :cpf";
        TypedQuery< Funcionario > typedQuery = entityManager
                .createQuery( getFuncionarioPorCPF, Funcionario.class )
                .setParameter( "cpf", cpf );
        return typedQuery.getSingleResult();
    }

}
