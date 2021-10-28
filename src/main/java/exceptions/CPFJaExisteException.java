package exceptions;

import enums.MessagesExceptions;

public class CPFJaExisteException extends RuntimeException {
    public CPFJaExisteException() { super(MessagesExceptions.CPF_JA_CADASTRADO.getMessage());
    }
}
