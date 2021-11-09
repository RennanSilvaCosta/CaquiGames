package enums;

public enum MessagesExceptions {

    CPF_JA_CADASTRADO("CPF já está cadastrado, verifique o CPF e tente novamente!"),
    EMAIL_INVALIDO("O email informado não é válido!"),
    SENHA_INVALIDA("Informe a senha!"),
    CARRINHO_VAZIO("O carrinho não pode está vazio, adicione ao menos um item!"),
    CLIENTE_NAO_SELECIONADO("Nenhum cliente selecionado, por favor selcione um cliente para poder finalizar a venda!"),
    OPCAO_INVALIDA("Algo deu errado, por favor tente novamente mais tarde!"),
    CPF_INVALIDO("O CPF digitado é inválido!"),
    VALOR_INVALIDO("O valor informado é inválido!"),
    CAMPO_OBRIGATORIO("Preencha os campos obrigatórios!"),
    DATA_INVALIDA("A data informada não é valida, por favor informe uma data válida!");

    private String value;

    MessagesExceptions(String value) {
        this.value = value;
    }

    public String getMessage() {
        return this.value;
    }

}