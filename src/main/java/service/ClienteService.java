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

        Cliente cliente = new Cliente();

        cliente.setNome(nome);
        cliente.setCpf(cpf);
        cliente.setCelular(celular);
        cliente.setEmail(email);
        cliente.setDataNasc(dataNasc);

        clienteDAO.criaCliente(
                cliente,
                enderecoService.cadastraEndereco(
                        cep,
                        logradouro,
                        bairro,
                        numero,
                        complemento,
                        referencia
                ));

    }

    public Cliente consultaCliente(String cpf) {
        return clienteDAO.getCliente(cpf);
    }

    public List<Cliente> consultaListaClientes(String str) {
        return clienteDAO.getClientesPorNome(str);
    }

    public void atualizaCliente(String nome, String cpf, String celular, String email, LocalDate dataNasc) {

        Cliente cliente = new Cliente();
        Long idCliente = clienteDAO.getCliente(cpf).getId();

        cliente.setNome(nome);
        cliente.setCpf(cpf);
        cliente.setCelular(celular);
        cliente.setEmail(email);
        cliente.setDataNasc(dataNasc);

        clienteDAO.editaCliente(cliente, idCliente);
    }

    public void excluiCliente(String cpf) {
        clienteDAO.deletaCliente(cpf);
    }

}