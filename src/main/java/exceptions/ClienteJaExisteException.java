package exceptions;

import enums.MessagesExceptions;

public class ClienteJaExisteException extends RuntimeException {
    public ClienteJaExisteException() {
        super(MessagesExceptions.CPF_JA_CADASTRADO.getMessage());
    }
}