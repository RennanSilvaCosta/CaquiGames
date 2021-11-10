package service;

import dao.FuncionarioDAO;
import dto.FuncionarioDTO;
import exceptions.*;
import model.Funcionario;
import session.UserSession;
import utils.ValidaCPF;

import javax.persistence.NoResultException;
import java.util.List;

public class FuncionarioService {

    private FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
    ValidaCPF validaCPF = new ValidaCPF();

    public void cadastraFuncionario(Funcionario funcionario) throws CPFJaExisteException, CPFInvalidoException {
        if (isCPFValid(funcionario.getCpf())) {
            if (!isCPFExiste(funcionario.getCpf())) {
                if (!isEmailExiste(funcionario.getEmail())) {
                    funcionarioDAO.criaFuncionario(funcionario);
                } else {
                    throw new EmailJaExisteException();
                }
            } else {
                throw new CPFJaExisteException();
            }
        } else {
            throw new CPFInvalidoException();
        }
    }

    private boolean isCPFValid(String cpf) throws CPFJaExisteException {
        return validaCPF.isCPFValido(cpf);
    }

    private boolean isCPFExiste(String cpf) throws NoResultException, CPFJaExisteException {
        return funcionarioDAO.isFuncionarioExiste(cpf);
    }

    private boolean isEmailExiste(String email) {
        return funcionarioDAO.isEmailExiste(email);
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
        UserSession.getInstace(funcionarioDAO.buscaFuncionarioEmaileSenha(dto));
    }

}