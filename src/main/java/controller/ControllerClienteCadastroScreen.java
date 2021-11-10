package controller;

import adapter.AdapterListCliente;
import animatefx.animation.Shake;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import dto.ClienteValidaDTO;
import dto.EnderecoDTO;
import exceptions.CPFInvalidoException;
import exceptions.CPFJaExisteException;
import exceptions.CepInvalidoException;
import exceptions.EmailJaExisteException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.Cliente;
import model.Endereco;
import service.ClienteService;
import utils.Helper;
import validate.Validate;

import java.net.URL;
import java.util.*;

import static service.ViaCepService.buscaEnderecoViaCep;
import static utils.Helper.*;


public class ControllerClienteCadastroScreen implements Initializable {

    Cliente cliente = new Cliente();
    Endereco end = new Endereco();
    ClienteService clienteService = new ClienteService();

    Validate validate = new Validate();
    Map<String, String> errors = new HashMap<>();

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

    @FXML
    Label lblErrorNome;
    @FXML
    Label lblErrorCPF;
    @FXML
    Label lblErrorEmail;
    @FXML
    Label lblErrorTelefone;
    @FXML
    Label lblErrorData;
    @FXML
    Label lblErrorCEP;
    @FXML
    Label lblErrorNumero;

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
            errors = validaFormularioCadastroFuncionario();
            if (errors.isEmpty()) {
                populaEndereco();
                populaCliente();
                if (cliente.getId() != null) {
                    clienteService.atualizaCliente(this.cliente);
                } else {
                    clienteService.cadastraCliente(this.cliente);
                }
                fecharCadastroCliente();
                atualizaListaClientes();
            } else {
                setErrorMessages(errors);
            }
        } catch (CPFJaExisteException e) {
            Helper.abrirDialog("CPF já existe", e.getMessage(), Alert.AlertType.ERROR);
            lblErrorCPF.setText(e.getMessage());
            new Shake(txtCpf);
        } catch (EmailJaExisteException e) {
            Helper.abrirDialog("Email já cadastrado", e.getMessage(), Alert.AlertType.ERROR);
            lblErrorEmail.setText(e.getMessage());
            new Shake(txtEmail);
        } catch (CPFInvalidoException e) {
            Helper.abrirDialog("CPF Inválido", e.getMessage(), Alert.AlertType.ERROR);
            lblErrorCPF.setText(e.getMessage());
            new Shake(txtCpf);
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

    private Map<String, String> validaFormularioCadastroFuncionario() {
        ClienteValidaDTO dto = new ClienteValidaDTO();
        dto.setNome(txtNome.getText());
        dto.setCpf(txtCpf.getText());
        dto.setEmail(txtEmail.getText());
        dto.setCelular(txtCelular.getText());
        dto.setDataNasc(txtDataNascimento.getText());
        dto.setNumero(txtNumero.getText());
        dto.setCep(txtCep.getText());
        return validate.validaFormularioCadastroCliente(dto);
    }

    private void populaCliente() {
        this.cliente.setNome(txtNome.getText());
        this.cliente.setCpf(txtCpf.getText());
        this.cliente.setEmail(txtEmail.getText());
        this.cliente.setDataNasc(converteStringParaData(txtDataNascimento.getText()));
        this.cliente.setCelular(txtCelular.getText());
        this.cliente.setEndereco(end);
    }

    private void setErrorMessages(Map<String, String> errors) {
        Set<String> fields = errors.keySet();
        limparLabelsErro();

        if (fields.contains("nome")) {
            lblErrorNome.setText(errors.get("nome"));
            new Shake(txtNome).play();
        }
        if (fields.contains("cpf")) {
            lblErrorCPF.setText(errors.get("cpf"));
            new Shake(txtCpf).play();
        }
        if (fields.contains("email")) {
            lblErrorEmail.setText(errors.get("email"));
            new Shake(txtEmail).play();
        }
        if (fields.contains("telefone")) {
            lblErrorTelefone.setText(errors.get("telefone"));
            new Shake(txtCelular).play();
        }
        if (fields.contains("data")) {
            lblErrorData.setText(errors.get("data"));
            new Shake(txtDataNascimento).play();
        }
        if (fields.contains("numero")) {
            lblErrorNumero.setText(errors.get("numero"));
            new Shake(txtNumero).play();
        }
        if (fields.contains("cep")) {
            lblErrorCEP.setText(errors.get("cep"));
            new Shake(lblErrorCEP).play();
        }
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
            lblErrorCEP.setText("");
            EnderecoDTO dto = buscaEnderecoViaCep(txtCep.getText());
            try {
                if (dto.getLogradouro() != null) {
                    txtLogradouro.setText(dto.getLogradouro());
                    txtBairro.setText(dto.getBairro());
                    txtCidade.setText(dto.getLocalidade());
                    txtEstado.setText(dto.getUf());
                } else {
                    throw new CepInvalidoException();
                }
            } catch (CepInvalidoException e) {
                abrirDialog("Cep Invalido", e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }

    private void limparLabelsErro() {
        lblErrorNome.setText("");
        lblErrorCPF.setText("");
        lblErrorEmail.setText("");
        lblErrorTelefone.setText("");
        lblErrorData.setText("");
        lblErrorNumero.setText("");
        lblErrorCEP.setText("");
    }

    public void fecharCadastroCliente() {
        Stage stage = (Stage) btnSair.getScene().getWindow();
        stage.close();
    }

}
