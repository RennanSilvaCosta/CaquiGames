package exceptions;

import enums.MessagesExceptions;

public class CarrinhoVazioException extends RuntimeException {
    public CarrinhoVazioException() { super(MessagesExceptions.CARRINHO_VAZIO.getMessage());}
}
