package exceptions;

import enums.MessagesExceptions;

public class CampoObrigatorioException extends RuntimeException {
    public CampoObrigatorioException() { super(MessagesExceptions.CAMPO_OBRIGATORIO.getMessage());}
}
