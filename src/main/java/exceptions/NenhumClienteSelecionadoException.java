package exceptions;

import enums.MessagesExceptions;

public class NenhumClienteSelecionadoException  extends RuntimeException {
    public NenhumClienteSelecionadoException() { super(MessagesExceptions.CLIENTE_NAO_SELECIONADO.getMessage());
    }
}
