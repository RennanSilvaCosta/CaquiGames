package dao;

import dto.FuncionarioDTO;
import model.Funcionario;
import session.UserSession;
import utils.JPAUtil;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

public class FuncionarioDAO {

    private static EntityManager entityManager = JPAUtil.getEntityManager();

    public void criaFuncionario(Funcionario funcionario) {
        entityManager.getTransaction().begin();
        entityManager.persist(funcionario);
        entityManager.getTransaction().commit();
    }

    public List<Funcionario> buscaTodosFuncionarios() {
        Funcionario func = UserSession.getFuncionario();
        String getFuncionarios = "SELECT f FROM Funcionario f WHERE f.cpf != :cpf";
        TypedQuery<Funcionario> typedQuery = entityManager
                .createQuery(getFuncionarios, Funcionario.class)
                .setParameter("cpf", func.getCpf());
        return typedQuery.getResultList();
    }

    public List<Funcionario> buscaFuncionariosPorNome(String str) {
        Funcionario func = UserSession.getFuncionario();
        String getFuncionariosPorNome = "SELECT f FROM Funcionario f WHERE (f.nome LIKE :str OR f.cpf LIKE :str) AND f.cpf != :cpfFunc";
        TypedQuery<Funcionario> typedQuery = entityManager
                .createQuery(getFuncionariosPorNome, Funcionario.class)
                .setParameter("str", "%" + str + "%")
                .setParameter("cpfFunc", func.getCpf())
                .setMaxResults(10);
        return typedQuery.getResultList();
    }

    public void deletaFuncionario(String cpf) {
        Funcionario funcionario = buscaFuncionarioPorCPF(cpf);
        entityManager.getTransaction().begin();
        entityManager.remove(funcionario);
        entityManager.getTransaction().commit();
    }

    public void editaFuncionario(Funcionario funcionario) {
        entityManager.getTransaction().begin();
        entityManager.merge(funcionario);
        entityManager.getTransaction().commit();
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
        String getClientePorCPF = "SELECT f FROM Funcionario f WHERE f.cpf = :cpf";
        TypedQuery<Funcionario> typedQuery = entityManager
                .createQuery(getClientePorCPF, Funcionario.class)
                .setParameter("cpf", cpf);
        List<Funcionario> resultList = typedQuery.getResultList();
        return !resultList.isEmpty();
    }

    public Funcionario buscaFuncionarioPorCPF(String cpf) throws NoResultException {
        String getFuncionarioPorCPF = "SELECT f FROM Funcionario f WHERE f.cpf = :cpf";
        TypedQuery<Funcionario> typedQuery = entityManager
                .createQuery(getFuncionarioPorCPF, Funcionario.class)
                .setParameter("cpf", cpf);
        return typedQuery.getSingleResult();
    }

    public boolean isEmailExiste(String email) {
        String getClientePorEmail = "SELECT f FROM Funcionario f WHERE f.email = :email";
        TypedQuery<Funcionario> typedQuery = entityManager
                .createQuery(getClientePorEmail, Funcionario.class)
                .setParameter("email", email);
        List<Funcionario> resultList = typedQuery.getResultList();
        return !resultList.isEmpty();
    }
}
