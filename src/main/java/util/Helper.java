package util;

import com.jfoenix.controls.JFXButton;
import javafx.stage.Stage;

import java.text.NumberFormat;
import java.util.Locale;

public class Helper {

    public static String formataValor(double valor) {
        NumberFormat n = NumberFormat.getCurrencyInstance(Locale.getDefault());
        return n.format(valor);
    }

    public static void fecharJanela(JFXButton componente) {
        Stage stage = (Stage) componente.getScene().getWindow();
        stage.close();
    }

}
