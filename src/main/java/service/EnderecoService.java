package service;

import dao.ClienteDAO;
import dao.EnderecoDAO;
import model.Cliente;
import model.Endereco;

public class EnderecoService {

    private EnderecoDAO enderecoDAO = new EnderecoDAO();

    private ClienteDAO clienteDAO;

    public Endereco cadastraEndereco(String cep, String logradouro, String bairro, Integer numero, String complemento, String referencia) {

        return enderecoDAO.criaEndereco(
                cep,
                logradouro,
                bairro,
                numero,
                complemento,
                referencia
        );

    }

    public void atualizaEndereco(String cep, String logradouro, String bairro, Integer numero, String complemento, String referencia, String cpf) {

        Cliente cliente = clienteDAO.getCliente(cpf);
        enderecoDAO.editaEndereco(
                cep,
                logradouro,
                bairro,
                numero,
                complemento,
                referencia,
                cliente
                        .getEndereco()
                        .getId()
        );
    }

}
