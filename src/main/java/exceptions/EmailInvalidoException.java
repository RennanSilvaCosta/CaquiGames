package exceptions;

import enums.MessagesExceptions;

public class EmailInvalidoException extends RuntimeException {
    public EmailInvalidoException() { super(MessagesExceptions.EMAIL_INVALIDO.getMessage());
    }
}
