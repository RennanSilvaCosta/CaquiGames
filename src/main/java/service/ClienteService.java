package service;

import dao.ClienteDAO;
import exceptions.ValidaCPFException;
import model.Cliente;
import utils.ValidaCPF;

import java.util.List;

public class ClienteService {

    ValidaCPF validaCPF = new ValidaCPF();

    private ClienteDAO clienteDAO = new ClienteDAO();

    public void cadastraCliente(Cliente cliente) throws ValidaCPFException {

        boolean cpfValid = isCPFValid( cliente.getCpf() );
        boolean cpfDoesNotExists = isCPFExiste( cliente.getCpf() );

        if( cpfValid && !cpfDoesNotExists ) {
            clienteDAO.criaCliente( cliente );
        } //Falta um else ??? Rever..

    }

    private boolean isCPFValid( String cpf ) throws ValidaCPFException {
        return validaCPF.isCPFValido( cpf );
    }

    private boolean isCPFExiste( String cpf ) throws ValidaCPFException {
        return clienteDAO.isClienteExiste( cpf );
    }

    public List<Cliente> buscaTodosClientes() {
       return clienteDAO.buscaTodosClientes();
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