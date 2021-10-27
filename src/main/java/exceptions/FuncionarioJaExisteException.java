package exceptions;

import enums.MessagesExceptions;

public class FuncionarioJaExisteException extends RuntimeException {
    public FuncionarioJaExisteException() { super(MessagesExceptions.CLIENTE_NAO_SELECIONADO.getMessage());
    }
}
