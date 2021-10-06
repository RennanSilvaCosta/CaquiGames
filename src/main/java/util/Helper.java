package util;

import java.text.NumberFormat;
import java.util.Locale;

public class Helper {

    public static String formataValor(double valor) {
        NumberFormat n = NumberFormat.getCurrencyInstance(Locale.getDefault());
        return n.format(valor);
    }

}
