package enums;

public enum ClienteValidation {

    CLIENTE_JA_CADASTRADO("O Cliente já está cadastrado, verifique o CPF e tente novamente!");

    private String value;

    ClienteValidation(String value) {
        this.value = value;
    }

    public String getMessage() {
        return this.value;
    }

}