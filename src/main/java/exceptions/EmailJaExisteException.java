package exceptions;

import enums.MessagesExceptions;

public class EmailJaExisteException extends RuntimeException {
    public EmailJaExisteException() { super(MessagesExceptions.EMAIL_EXISTENTE.getMessage());
    }
}
