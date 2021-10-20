package exceptions;

import enums.ClienteValidation;

public class ClienteJaExisteException extends RuntimeException {
    public ClienteJaExisteException() {
        super(ClienteValidation.CLIENTE_JA_CADASTRADO.getMessage());
    }
}