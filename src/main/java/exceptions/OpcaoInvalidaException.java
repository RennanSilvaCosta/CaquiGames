package exceptions;

import enums.MessagesExceptions;

public class OpcaoInvalidaException  extends RuntimeException {
    public OpcaoInvalidaException() { super(MessagesExceptions.OPCAO_INVALIDA.getMessage());
    }
}
