package service;

import dao.ClienteDAO;
import exceptions.ClienteJaExisteException;
import model.Cliente;

import java.util.List;
import java.util.Objects;

public class ClienteService {

    private ClienteDAO clienteDAO = new ClienteDAO();

    public void cadastraCliente(Cliente cliente) {

        boolean isCPFRegistrado = clienteDAO.isClienteExiste(cliente.getCpf());

        if (Objects.equals(isCPFRegistrado, false)) {
            clienteDAO.criaCliente(cliente);
        } else {
            throw new ClienteJaExisteException();
        }

    }

    public List<Cliente> buscaTodosClientes() {
       return clienteDAO.buscaTodosClientes();
    }

    public Cliente consultaCliente(String cpf) {
        return clienteDAO.buscaClienteCPF(cpf);
    }

    public List<Cliente> consultaListaClientes(String str) {
        return clienteDAO.getClientesPorNome(str);
    }

    public void atualizaCliente(Cliente cliente) {
        clienteDAO.editaCliente(cliente);
    }

    public void excluiCliente(String cpf) {
        clienteDAO.deletaCliente(cpf);
    }

}