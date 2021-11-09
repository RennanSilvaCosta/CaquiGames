package exceptions;

import enums.MessagesExceptions;

public class TelefoneInvalidoException extends RuntimeException {
    public TelefoneInvalidoException() { super(MessagesExceptions.TELEFONE_INVALIDO.getMessage());
    }
}
