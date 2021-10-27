package service;

import dao.ClienteDAO;
import enums.MessagesExceptions;
import exceptions.ClienteJaExisteException;
import exceptions.ValidaCPFException;
import model.Cliente;
import utils.ValidaCPF;

import java.util.List;
import java.util.Objects;

public class ClienteService {

    ValidaCPF validaCPF = new ValidaCPF();

    private ClienteDAO clienteDAO = new ClienteDAO();

    public void cadastraCliente(Cliente cliente) throws ValidaCPFException {

        boolean cpfValid = isCPFValid( cliente.getCpf() );
        boolean cpfDoesNotExists = isCPFExiste( cliente.getCpf() );

        if( cpfValid && cpfDoesNotExists ) {
            clienteDAO.criaCliente( cliente );
        }

    }

    private boolean isCPFValid( String cpf ) {
        boolean b = validaCPF.isCPFValido( cpf );
        if( !b ) {
            throw new ValidaCPFException( MessagesExceptions.CPF_INVALIDO );
        }
        return true;
    }

    private boolean isCPFExiste( String cpf ) {
        boolean b = clienteDAO.isClienteExiste( cpf );
        if( b ) {
            throw new ValidaCPFException( MessagesExceptions.CPF_JA_CADASTRADO );
        }
        return false;
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