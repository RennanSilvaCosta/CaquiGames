package exceptions;

import enums.MessagesExceptions;

public class CepInvalidoException extends RuntimeException {
    public CepInvalidoException() { super(MessagesExceptions.CEP_INVALIDO.getMessage());
    }
}
