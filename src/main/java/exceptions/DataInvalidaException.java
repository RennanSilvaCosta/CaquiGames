package exceptions;

import enums.MessagesExceptions;

public class DataInvalidaException extends RuntimeException {
    public DataInvalidaException() { super(MessagesExceptions.DATA_INVALIDA.getMessage());
    }
}
