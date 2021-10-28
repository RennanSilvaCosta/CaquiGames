package service;

import dao.ClienteDAO;
import exceptions.CPFInvalidoException;
import exceptions.CPFJaExisteException;
import model.Cliente;
import utils.ValidaCPF;

import javax.persistence.NoResultException;
import java.util.List;

public class ClienteService {

    ValidaCPF validaCPF = new ValidaCPF();

    private ClienteDAO clienteDAO = new ClienteDAO();

    public void cadastraCliente( Cliente cliente ) throws CPFJaExisteException, CPFInvalidoException {

        if( isCPFValid( cliente.getCpf() ) ) {
            if( !isCPFExiste( cliente.getCpf() ) ) {
                clienteDAO.criaCliente( cliente );
            } else {
                throw new CPFJaExisteException();
            }
        } else {
            throw new CPFInvalidoException();
        }

    }

    private boolean isCPFValid( String cpf ) throws CPFJaExisteException {
        return validaCPF.isCPFValido( cpf );
    }

    private boolean isCPFExiste( String cpf ) throws NoResultException, CPFJaExisteException {
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