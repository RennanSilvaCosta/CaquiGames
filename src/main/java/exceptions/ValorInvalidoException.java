package exceptions;

import enums.MessagesExceptions;

public class ValorInvalidoException extends RuntimeException {
    public ValorInvalidoException() { super(MessagesExceptions.VALOR_INVALIDO.getMessage());}
}
