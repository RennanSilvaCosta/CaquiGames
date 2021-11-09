package service;

import dao.ClienteDAO;
import exceptions.CPFInvalidoException;
import exceptions.CPFJaExisteException;
import exceptions.EmailJaExisteException;
import model.Cliente;
import utils.ValidaCPF;

import javax.persistence.NoResultException;
import java.util.List;

public class ClienteService {

    ValidaCPF validaCPF = new ValidaCPF();
    private ClienteDAO clienteDAO = new ClienteDAO();

    public void cadastraCliente(Cliente cliente) throws CPFJaExisteException, CPFInvalidoException {
        if (isCPFValid(cliente.getCpf())) {
            if (!isCPFExiste(cliente.getCpf())) {
                if (!isEmailExiste(cliente.getEmail())) {
                    clienteDAO.criaCliente(cliente);
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
        return clienteDAO.isClienteExiste(cpf);
    }

    private boolean isEmailExiste(String email) {
        return clienteDAO.isEmailExiste(email);
    }

    public List<Cliente> buscaTodosClientes() {
        return clienteDAO.buscaTodosClientes();
    }

    public List<Cliente> buscaClienteNome(String str) {
        return clienteDAO.getClientesPorNome(str);
    }

    public void atualizaCliente(Cliente cliente) {
        clienteDAO.editaCliente(cliente);
    }

    public void excluiCliente(String cpf) {
        clienteDAO.deletaCliente(cpf);
    }

}