package controller;

import adapter.AdapterListCliente;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import dto.EnderecoDTO;
import exceptions.CPFInvalidoException;
import exceptions.CPFJaExisteException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import model.Cliente;
import model.Endereco;
import service.ClienteService;
import utils.Helper;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import static service.ViaCepService.buscaEnderecoViaCep;
import static utils.Helper.converteDataParaString;
import static utils.Helper.converteStringParaData;

public class ControllerClienteCadastroScreen implements Initializable {

    Cliente cliente = new Cliente();
    Endereco end = new Endereco();
    ClienteService clienteService = new ClienteService();

    @FXML
    JFXButton btnSair;
    @FXML
    JFXButton btnSalvarCliente;

    @FXML
    JFXTextField txtCpf;
    @FXML
    JFXTextField txtNome;
    @FXML
    JFXTextField txtEmail;
    @FXML
    JFXTextField txtDataNascimento;
    @FXML
    JFXTextField txtCelular;
    @FXML
    JFXTextField txtCep;
    @FXML
    JFXTextField txtEstado;
    @FXML
    JFXTextField txtCidade;
    @FXML
    JFXTextField txtBairro;
    @FXML
    JFXTextField txtLogradouro;
    @FXML
    JFXTextField txtNumero;
    @FXML
    JFXTextField txtComplemento;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // TODO document why this method is empty
    }

    public void getInfoCliente(Cliente cliente) {
        this.cliente = cliente;
        this.end = cliente.getEndereco();
        txtNome.setText(cliente.getNome());
        txtCpf.setText(cliente.getCpf());
        txtEmail.setText(cliente.getEmail());
        txtDataNascimento.setText(converteDataParaString(cliente.getDataNasc()));
        txtCelular.setText(cliente.getCelular());
        txtCep.setText(cliente.getEndereco().getCep());
        txtBairro.setText(cliente.getEndereco().getBairro());
        txtEstado.setText(cliente.getEndereco().getEstado());
        txtCidade.setText(cliente.getEndereco().getCidade());
        txtLogradouro.setText(cliente.getEndereco().getLogradouro());
        txtNumero.setText(cliente.getEndereco().getNumero().toString());
        txtComplemento.setText(cliente.getEndereco().getComplemento());
    }

    public void persistirCliente() {
        try {
            populaEndereco();
            populaCliente();
            if (cliente.getId() != null) {
                clienteService.atualizaCliente(this.cliente);
            } else {
                clienteService.cadastraCliente(this.cliente);
            }
            fecharCadastroCliente();
            atualizaListaClientes();
        } catch( CPFJaExisteException | CPFInvalidoException e ) {
            Helper.abrirDialog( "Ops! Algo deu errado.", e.getMessage(), Alert.AlertType.ERROR );
        }
    }

    private void atualizaListaClientes() {
        List<Cliente> clientes = clienteService.buscaTodosClientes();
        ControllerClienteScreen.listaClienteStatic.getItems().clear();
        for (Cliente c : clientes) {
            ControllerClienteScreen.listaClienteStatic.getItems().add(c);
        }
        ControllerClienteScreen.listaClienteStatic.setCellFactory(cli -> new AdapterListCliente());
    }

    private void populaCliente() {
        this.cliente.setNome(txtNome.getText());
        this.cliente.setCpf(txtCpf.getText());
        this.cliente.setEmail(txtEmail.getText());
        this.cliente.setDataNasc(converteStringParaData(txtDataNascimento.getText()));
        this.cliente.setCelular(txtCelular.getText());
        this.cliente.setEndereco(end);
    }

    private void populaEndereco() {
        this.end.setCep(txtCep.getText());
        this.end.setLogradouro(txtLogradouro.getText());
        this.end.setBairro(txtBairro.getText());
        this.end.setEstado(txtEstado.getText());
        this.end.setCidade(txtCidade.getText());
        this.end.setNumero(Integer.parseInt(txtNumero.getText()));
        this.end.setComplemento(txtComplemento.getText());
    }

    public void buscaCep() {
        if (txtCep.getText().charAt(8) != '_') {
            EnderecoDTO dto = buscaEnderecoViaCep(txtCep.getText());
            if (dto != null) {
                txtLogradouro.setText(dto.getLogradouro());
                txtBairro.setText(dto.getBairro());
                txtCidade.setText(dto.getLocalidade());
                txtEstado.setText(dto.getUf());
            }
        }
    }

    public void fecharCadastroCliente() {
        Stage stage = (Stage) btnSair.getScene().getWindow();
        stage.close();
    }

}
