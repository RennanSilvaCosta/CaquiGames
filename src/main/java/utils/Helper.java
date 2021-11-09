package utils;

import com.jfoenix.controls.JFXButton;
import exceptions.DataInvalidaException;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

public class Helper {

    public static LocalDate converteStringParaData(String data) throws DateTimeParseException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return LocalDate.parse(data, formatter);
    }

    public static String converteDataParaString(LocalDate data) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return data.format(formatter);
    }

    public static String formataValor(Double valor) {
        if (null != valor){
            NumberFormat n = NumberFormat.getCurrencyInstance(Locale.getDefault());
            return n.format(valor);
        }
        return "0";
    }

    public static void fecharJanela(JFXButton componente) {
        Stage stage = (Stage) componente.getScene().getWindow();
        stage.close();
    }

    public static void abrirDialog(String titulo, String mensagem, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setHeaderText(titulo);
        alert.setTitle(titulo);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
