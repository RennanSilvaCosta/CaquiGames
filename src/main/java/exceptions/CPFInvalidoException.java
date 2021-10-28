package exceptions;

import enums.MessagesExceptions;

public class CPFInvalidoException extends RuntimeException {
    public CPFInvalidoException() { super( MessagesExceptions.CPF_INVALIDO.getMessage() );
    }
}
