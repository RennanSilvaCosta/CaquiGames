package service;

import dao.FuncionarioDAO;
import dto.FuncionarioDTO;
import exceptions.EmailInvalidoException;
import exceptions.FuncionarioJaExisteException;
import exceptions.SenhaInvalidaException;
import model.Funcionario;
import session.UserSession;
import validate.Validate;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.Objects;

import static validate.Validate.validaFormLogin;

public class FuncionarioService {

    private FuncionarioDAO funcionarioDAO = new FuncionarioDAO();

    public void cadastraFuncionario(Funcionario funcionario) {

        boolean isCPFRegistrado = funcionarioDAO.isFuncionarioExiste(funcionario.getCpf());

        if (Objects.equals(isCPFRegistrado, false)) {
            funcionarioDAO.criaFuncionario(funcionario);
        } else {
            throw new FuncionarioJaExisteException();
        }
    }

    public List<Funcionario> buscaTodosFuncionarios() {
        return funcionarioDAO.buscaTodosFuncionarios();
    }

    public List<Funcionario> buscaFuncionariosPorNome(String str) {
        return funcionarioDAO.buscaFuncionariosPorNome(str);
    }

    public void atualizaFuncionario(Funcionario funcionario) {
        funcionarioDAO.editaFuncionario(funcionario);
    }

    public void excluiFuncionario(String cpf) {
        funcionarioDAO.deletaFuncionario(cpf);
    }

    public void logarFuncionario(FuncionarioDTO dto) throws EmailInvalidoException, SenhaInvalidaException, NoResultException {
        if (validaFormLogin(dto)) {
             UserSession.getInstace(funcionarioDAO.buscaFuncionarioEmaileSenha(dto));
        }
    }

}