package enums;

public enum MessagesExceptions {

    CPF_JA_CADASTRADO("CPF j� est� cadastrado, verifique o CPF e tente novamente!"),
    EMAIL_INVALIDO("O email informado n�o � v�lido!"),
    SENHA_INVALIDA("Informe a senha!"),
    CARRINHO_VAZIO("O carrinho n�o pode est� vazio, adicione ao menos um item!"),
    CLIENTE_NAO_SELECIONADO("Nenhum cliente selecionado, por favor selcione um cliente para poder finalizar a venda!"),
    OPCAO_INVALIDA("Algo deu errado, por favor tente novamente mais tarde!"),
    CPF_INVALIDO("O CPF digitado � inv�lido!"),
    VALOR_INVALIDO("O valor informado � inv�lido!"),
    CAMPO_OBRIGATORIO("Preencha os campos obrigat�rios!"),
    DATA_INVALIDA("A data informada n�o � valida, por favor informe uma data v�lida!");

    private String value;

    MessagesExceptions(String value) {
        this.value = value;
    }

    public String getMessage() {
        return this.value;
    }

}