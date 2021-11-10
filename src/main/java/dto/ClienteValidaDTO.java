package dto;

public class ClienteValidaDTO {

    private String nome;
    private String cpf;
    private String email;
    private String celular;
    private String dataNasc;
    private String cep;
    private String numero;

    public ClienteValidaDTO() {
    }

    public ClienteValidaDTO(String nome, String cpf, String email, String celular, String dataNasc, String cep, String numero) {
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.celular = celular;
        this.dataNasc = dataNasc;
        this.cep = cep;
        this.numero = numero;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getDataNasc() {
        return dataNasc;
    }

    public void setDataNasc(String dataNasc) {
        this.dataNasc = dataNasc;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }
}
