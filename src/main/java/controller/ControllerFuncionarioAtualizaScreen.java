package controller;

import adapter.AdapterListFuncionario;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import dto.EnderecoDTO;
import exceptions.CPFInvalidoException;
import exceptions.CPFJaExisteException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import model.Endereco;
import model.Funcionario;
import service.FuncionarioService;
import utils.Helper;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static service.ViaCepService.buscaEnderecoViaCep;
import static utils.Helper.converteDataParaString;
import static utils.Helper.converteStringParaData;

public class ControllerFuncionarioAtualizaScreen implements Initializable {

    Funcionario funcionario = new Funcionario();
    Endereco end = new Endereco();
    FuncionarioService funcionarioService = new FuncionarioService();


    @FXML
    JFXButton btnSair;
    @FXML
    JFXButton btnSalvarFuncionario;

    @FXML
    JFXComboBox<String> comboBoxPerfil;

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
        comboBoxPerfil.getItems().addAll("VENDEDOR", "ADM");
        comboBoxPerfil.getSelectionModel().selectFirst();
    }

    public void getInfoFuncionario(Funcionario func) {
        this.funcionario = func;
        this.end = func.getEndereco();
        txtNome.setText(func.getNome());
        txtCpf.setText(func.getCpf());
        txtEmail.setText(func.getEmail());
        txtDataNascimento.setText(converteDataParaString(func.getDataNasc()));
        txtCelular.setText(func.getTelefone());
        txtCep.setText(func.getEndereco().getCep());
        txtBairro.setText(func.getEndereco().getBairro());
        txtEstado.setText(func.getEndereco().getEstado());
        txtCidade.setText(func.getEndereco().getCidade());
        txtLogradouro.setText(func.getEndereco().getLogradouro());
        txtNumero.setText(func.getEndereco().getNumero().toString());
        txtComplemento.setText(func.getEndereco().getComplemento());
    }

    public void persistirFuncionario() {
        try {
            populaEndereco();
            populaFuncionario();
            funcionarioService.atualizaFuncionario(this.funcionario);
            fecharCadastroFuncionario();
            atualizaListaFuncionarios();
        } catch (CPFJaExisteException | CPFInvalidoException e) {
            Helper.abrirDialog("Ops! Algo deu errado.", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void atualizaListaFuncionarios() {
        List<Funcionario> funcionarios = funcionarioService.buscaTodosFuncionarios();
        ControllerFuncionarioScreen.listaFuncionarioStatic.getItems().clear();
        for (Funcionario f : funcionarios) {
            ControllerFuncionarioScreen.listaFuncionarioStatic.getItems().add(f);
        }
        ControllerFuncionarioScreen.listaFuncionarioStatic.setCellFactory(cli -> new AdapterListFuncionario());
    }

    private void populaFuncionario() {
        this.funcionario.setNome(txtNome.getText());
        this.funcionario.setCpf(txtCpf.getText());
        this.funcionario.setEmail(txtEmail.getText());
        this.funcionario.setDataNasc(converteStringParaData(txtDataNascimento.getText()));
        this.funcionario.setTelefone(txtCelular.getText());
        this.funcionario.setEndereco(end);
        this.funcionario.setPerfil(comboBoxPerfil.getSelectionModel().getSelectedItem());
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

    public void fecharCadastroFuncionario() {
        Stage stage = (Stage) btnSair.getScene().getWindow();
        stage.close();
    }

}
