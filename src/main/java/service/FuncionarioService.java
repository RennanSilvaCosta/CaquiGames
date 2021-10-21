package service;

import dao.FuncionarioDAO;
import exceptions.FuncionarioJaExisteException;
import model.Funcionario;

import java.util.List;
import java.util.Objects;

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

}