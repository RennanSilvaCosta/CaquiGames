package exceptions;

import enums.MessagesExceptions;

public class FuncionarioJaExisteException extends RuntimeException {
    public FuncionarioJaExisteException() { super(MessagesExceptions.CPF_JA_CADASTRADO.getMessage());
    }
}
