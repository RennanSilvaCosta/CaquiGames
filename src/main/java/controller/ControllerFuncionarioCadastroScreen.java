package controller;

import adapter.AdapterListFuncionario;
import animatefx.animation.Shake;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import dto.EnderecoDTO;
import dto.FuncionarioValidaDTO;
import exceptions.CPFInvalidoException;
import exceptions.CPFJaExisteException;
import exceptions.CepInvalidoException;
import exceptions.EmailJaExisteException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Endereco;
import model.Funcionario;
import service.FuncionarioService;
import utils.Helper;
import validate.Validate;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.*;

import static service.ViaCepService.buscaEnderecoViaCep;
import static utils.Helper.*;

public class ControllerFuncionarioCadastroScreen implements Initializable {

    Funcionario funcionario = new Funcionario();
    Endereco end = new Endereco();
    FuncionarioService funcionarioService = new FuncionarioService();

    Validate validate = new Validate();
    Map<String, String> errors = new HashMap<>();

    @FXML
    JFXPasswordField txtSenha;

    @FXML
    JFXComboBox<String> comboBoxPerfil;

    @FXML
    JFXButton btnSair;
    @FXML
    JFXButton btnSalvarFuncionario;
    @FXML
    JFXButton btnAddFotoPerfil;

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
    Label lblErrorSenha;
    @FXML
    Label lblErrorPerfil;
    @FXML
    Label lblErrorTelefone;
    @FXML
    Label lblErrorData;
    @FXML
    Label lblErrorCEP;
    @FXML
    Label lblErrorNumero;

    @FXML
    Circle fotoPerfil;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        comboBoxPerfil.getItems().addAll("VENDEDOR", "ADM");
        comboBoxPerfil.getSelectionModel().selectFirst();
        btnAddFotoPerfil.setGraphic(new ImageView(new Image("/icons/icon-add-foto.png")));
        Image img = new Image("/icons/perfil/icon-perfil-padrao.png");
        fotoPerfil.setFill(new ImagePattern(img));
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
            errors = validaFormularioCadastroFuncionario();
            if (errors.isEmpty()) {
                populaEndereco();
                populaFuncionario();
                if (funcionario.getId() != null) {
                    funcionarioService.atualizaFuncionario(this.funcionario);
                } else {
                    funcionarioService.cadastraFuncionario(this.funcionario);
                }
                fecharCadastroFuncionario();
                atualizaListaFuncionarios();
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

    private void atualizaListaFuncionarios() {
        List<Funcionario> funcionarios = funcionarioService.buscaTodosFuncionarios();
        ControllerFuncionarioScreen.listaFuncionarioStatic.getItems().clear();
        for (Funcionario f : funcionarios) {
            ControllerFuncionarioScreen.listaFuncionarioStatic.getItems().add(f);
        }
        ControllerFuncionarioScreen.listaFuncionarioStatic.setCellFactory(cli -> new AdapterListFuncionario());
    }

    private Map<String, String> validaFormularioCadastroFuncionario() {
        FuncionarioValidaDTO dto = new FuncionarioValidaDTO();
        dto.setNome(txtNome.getText());
        dto.setCpf(txtCpf.getText());
        dto.setEmail(txtEmail.getText());
        dto.setSenha(txtSenha.getText());
        dto.setTelefone(txtCelular.getText());
        dto.setData(txtDataNascimento.getText());
        dto.setNumero(txtNumero.getText());
        dto.setCep(txtCep.getText());
        return validate.validaFormCadastroFuncionario(dto);
    }

    private void populaFuncionario() {
        this.funcionario.setNome(txtNome.getText());
        this.funcionario.setCpf(txtCpf.getText());
        this.funcionario.setEmail(txtEmail.getText());
        this.funcionario.setSenha(txtSenha.getText());
        this.funcionario.setTelefone(txtCelular.getText());
        this.funcionario.setEndereco(end);
        this.funcionario.setPerfil(comboBoxPerfil.getSelectionModel().getSelectedItem());
        this.funcionario.setDataNasc(converteStringParaData(txtDataNascimento.getText()));
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
        if (fields.contains("senha")) {
            lblErrorSenha.setText(errors.get("senha"));
            new Shake(txtSenha).play();
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

    private void limparLabelsErro() {
        lblErrorNome.setText("");
        lblErrorCPF.setText("");
        lblErrorEmail.setText("");
        lblErrorSenha.setText("");
        lblErrorTelefone.setText("");
        lblErrorData.setText("");
        lblErrorNumero.setText("");
        lblErrorCEP.setText("");
    }

    @FXML
    private void escolheFoto() {
        FileChooser fl = new FileChooser();
        fl.setTitle("Selecione uma foto");
        fl.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("png Files", "*.png"), new FileChooser.ExtensionFilter("jpg Files", "*.jpg"));
        File foto = fl.showOpenDialog(null);

        if (foto != null) {
            Image img = new Image(foto.toURI().toString());
            fotoPerfil.setFill(new ImagePattern(img));
            byte[] bFile = new byte[(int) foto.length()];
            try {
                FileInputStream fileInputStream = new FileInputStream(foto);
                fileInputStream.read(bFile);
                fileInputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            funcionario.setImage(bFile);
        }
    }

    public void fecharCadastroFuncionario() {
        Stage stage = (Stage) btnSair.getScene().getWindow();
        stage.close();
    }

}
