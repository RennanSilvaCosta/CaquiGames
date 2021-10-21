package service;

import dao.ClienteDAO;
import dao.EnderecoDAO;
import model.Endereco;

public class EnderecoService {

    private EnderecoDAO enderecoDAO = new EnderecoDAO();

    public Endereco cadastraEndereco(String cep, String logradouro, String bairro, Integer numero, String complemento, String referencia) {

        Endereco endereco = new Endereco();

        endereco.setCep(cep);
        endereco.setLogradouro(logradouro);
        endereco.setBairro(bairro);
        endereco.setNumero(numero);
        endereco.setComplemento(complemento);
        endereco.setReferencia(referencia);

        return enderecoDAO.criaEndereco(endereco);

    }

    /*public void atualizaEndereco(String cep, String logradouro, String bairro, Integer numero, String complemento, String referencia, String cpf) {

       // Cliente cliente = clienteDAO.buscaClienteCpf(cpf);

        Endereco endereco = new Endereco();

        endereco.setCep(cep);
        endereco.setLogradouro(logradouro);
        endereco.setBairro(bairro);
        endereco.setNumero(numero);
        endereco.setComplemento(complemento);
        endereco.setReferencia(referencia);

        enderecoDAO.editaEndereco(
                endereco,
                cliente
                        .getEndereco()
                        .getId()
        );
    }*/

}