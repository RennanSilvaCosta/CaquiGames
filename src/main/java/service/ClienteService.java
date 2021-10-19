package service;

import dao.ClienteDAO;
import model.Cliente;

import java.util.List;

public class ClienteService {

    private ClienteDAO clienteDAO = new ClienteDAO();

    public void cadastraCliente(Cliente cliente) {
        clienteDAO.criaCliente(cliente);
    }

    public List<Cliente> buscaTodosClientes() {
       return clienteDAO.buscaTodosClientes();
    }

    public Cliente consultaCliente(String cpf) {
        return clienteDAO.buscaClienteCpf(cpf);
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