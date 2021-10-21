package exceptions;

import enums.MessagesExceptions;

public class SenhaInvalidaException extends RuntimeException {
    public SenhaInvalidaException() { super(MessagesExceptions.SENHA_INVALIDA.getMessage());}
}
