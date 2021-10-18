package service;

import dao.ClienteDAO;
import model.Cliente;

import java.time.LocalDate;
import java.util.List;

public class ClienteService {

    EnderecoService enderecoService;

    private ClienteDAO clienteDAO = new ClienteDAO();

    public void cadastraCliente(String nome, String cpf, String celular, String email, LocalDate dataNasc,
                                String cep, String logradouro, String bairro, Integer numero, String complemento, String referencia) {

        clienteDAO.criaCliente(
                nome,
                cpf,
                celular,
                email,
                dataNasc,
                enderecoService.cadastraEndereco(
                        cep,
                        logradouro,
                        bairro,
                        numero,
                        complemento,
                        referencia
                ));

    }

    public Cliente consultaCliente(String cpf ) {
        return clienteDAO.getCliente(cpf);
    }

    public List<Cliente> consultaListaClientes( String str ) {
        return clienteDAO.getClientesPorNome( str );
    }

    public void atualizaCliente(String nome, String cpf, String celular, String email, LocalDate dataNasc) {
        clienteDAO.editaCliente(
                nome,
                cpf,
                celular,
                email,
                dataNasc
        );
    }

    public void excluiCliente( String cpf ) {
        clienteDAO.deletaCliente(cpf);
    }

}
