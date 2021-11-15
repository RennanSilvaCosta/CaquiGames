package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import exceptions.DataInvalidaException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JRException;
import relatorios.JasperReports;

import java.io.FileNotFoundException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import static utils.Helper.abrirDialog;

public class ControllerDialogPeriodo implements Initializable {

    JasperReports jr = new JasperReports();

    @FXML
    JFXButton btnCancelar;
    @FXML
    JFXButton btnSalvar;

    @FXML
    JFXDatePicker dataInicio;
    @FXML
    JFXDatePicker dataFinal;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private void geraRelatorioSintetico() {
        try {
            LocalDate dataI = dataInicio.getValue();
            LocalDate dataF = dataFinal.getValue();

            if (dataI == null || dataF == null) {
                throw new DataInvalidaException();
            }
            if (dataI.isAfter(dataF)) {
                throw new DataInvalidaException();
            }

            ControllerRelatorioScreen c = new ControllerRelatorioScreen();
            String tipoRelatorio = c.tipoRelatorio;

            if ("analitico".equals(tipoRelatorio)) {
                jr.gerarRelatorioAnalitico(dataI, dataF);
            } else {
                jr.gerarRelatorioSintetico(dataI, dataF);
            }

        } catch (FileNotFoundException | JRException | DataInvalidaException e) {
            abrirDialog("Algo deu errado", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void close() {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }

}
