package utils;

import com.jfoenix.controls.JFXButton;
import javafx.animation.PathTransition;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.image.BufferedImage;
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

    public static Image convertToFxImage(BufferedImage image) {
        WritableImage wr = null;
        if (image != null) {
            wr = new WritableImage(image.getWidth(), image.getHeight());
            PixelWriter pw = wr.getPixelWriter();
            for (int x = 0; x < image.getWidth(); x++) {
                for (int y = 0; y < image.getHeight(); y++) {
                    pw.setArgb(x, y, image.getRGB(x, y));
                }
            }
        }
        return new ImageView(wr).getImage();
    }

    public static void deslizaPainelCima(Pane painel) {
        Path path = new Path();
        path.getElements().add(new MoveTo(110, 75));
        path.getElements().add(new LineTo(110, 65));

        PathTransition transition = new PathTransition();
        transition.setNode(painel);
        transition.setDuration(Duration.millis(300));
        transition.setPath(path);
        transition.play();
    }

    public static void deslizaPainelBaixo(Pane painel) {
        Path path = new Path();
        path.getElements().add(new MoveTo(110, 65));
        path.getElements().add(new LineTo(110, 75));

        PathTransition transition = new PathTransition();
        transition.setNode(painel);
        transition.setDuration(Duration.millis(300));
        transition.setPath(path);
        transition.play();
    }
}
