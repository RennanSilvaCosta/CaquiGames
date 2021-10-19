package controller;

import adapter.AdapterListCliente;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import dto.EnderecoDto;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import model.Cliente;
import model.Endereco;
import service.ClienteService;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static service.ViaCepService.buscaEnderecoViaCep;

public class ControllerClienteCadastroScreen implements Initializable {

    Cliente cliente = new Cliente();
    Endereco end = new Endereco();
    ClienteService clienteService = new ClienteService();

    private List<Cliente> clientes = new ArrayList<>();

    @FXML
    JFXButton btnSair, btnSalvarCliente;

    @FXML
    JFXTextField txtNome, txtCpf, txtEmail, txtDataNascimento, txtCelular, txtCep, txtEstado, txtCidade, txtBairro, txtLogradouro, txtNumero, txtComplemento;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void getInfoCliente(Cliente cliente) {
        this.cliente = cliente;
        this.end = cliente.getEndereco();
        txtNome.setText(cliente.getNome());
        txtCpf.setText(cliente.getCpf());
        txtEmail.setText(cliente.getEmail());
        txtDataNascimento.setText(cliente.getDataNasc().toString());
        txtCelular.setText(cliente.getCelular());
        txtCep.setText(cliente.getEndereco().getCep());
        txtBairro.setText(cliente.getEndereco().getBairro());
        txtLogradouro.setText(cliente.getEndereco().getLogradouro());
        txtNumero.setText(cliente.getEndereco().getNumero().toString());
        txtComplemento.setText(cliente.getEndereco().getComplemento());
    }

    public void persistirCliente() {
        populaEndereco();
        populaCliente();
        if (cliente.getId() != null) {
            clienteService.atualizaCliente(this.cliente);
        } else {
            clienteService.cadastraCliente(this.cliente);
        }
        fecharCadastroCliente();
        atualizaListaClientes();
    }

    private void atualizaListaClientes() {
        clientes = clienteService.buscaTodosClientes();
        ControllerClienteScreen.listaClienteStatic.getItems().clear();
        for (Cliente c : clientes) {
            ControllerClienteScreen.listaClienteStatic.getItems().add(c);
        }
        ControllerClienteScreen.listaClienteStatic.setCellFactory(cliente -> new AdapterListCliente());
    }

    private void populaCliente() {
        this.cliente.setNome(txtNome.getText());
        this.cliente.setCpf(txtCpf.getText());
        this.cliente.setEmail(txtEmail.getText());
        this.cliente.setDataNasc(LocalDate.now());
        this.cliente.setCelular(txtCelular.getText());
        this.cliente.setEndereco(end);
    }

    private void populaEndereco() {
        this.end.setCep(txtCep.getText());
        this.end.setLogradouro(txtLogradouro.getText());
        this.end.setBairro(txtBairro.getText());
        this.end.setNumero(Integer.parseInt(txtNumero.getText()));
        this.end.setComplemento(txtComplemento.getText());
    }

    public void buscaCep() {
        if (txtCep.getText().charAt(8) != '_') {
            EnderecoDto end = buscaEnderecoViaCep(txtCep.getText());
            if (end != null) {
                txtLogradouro.setText(end.getLogradouro());
                txtBairro.setText(end.getBairro());
                txtCidade.setText(end.getLocalidade());
                txtEstado.setText(end.getUf());
            }
        }
    }

    public void fecharCadastroCliente() {
        Stage stage = (Stage) btnSair.getScene().getWindow();
        stage.close();
    }

}
