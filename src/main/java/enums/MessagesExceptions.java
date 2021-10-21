package enums;

public enum MessagesExceptions {

    CPF_JA_CADASTRADO("CPF já está cadastrado, verifique o CPF e tente novamente!"),
    EMAIL_INVALIDO("O email informado não é válido!"),
    SENHA_INVALIDA("Informe a senha!");

    private String value;

    MessagesExceptions(String value) {
        this.value = value;
    }

    public String getMessage() {
        return this.value;
    }

}