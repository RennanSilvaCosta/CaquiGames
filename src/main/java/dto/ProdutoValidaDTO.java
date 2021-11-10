package dto;

public class ProdutoValidaDTO {

    private String produto;
    private String descricao;
    private String marca;
    private Double valor;
    private String estoque;

    public ProdutoValidaDTO() {
    }

    public ProdutoValidaDTO(String produto, String descricao, String marca, Double valor, String estoque) {
        this.produto = produto;
        this.descricao = descricao;
        this.marca = marca;
        this.valor = valor;
        this.estoque = estoque;
    }

    public String getProduto() {
        return produto;
    }

    public void setProduto(String produto) {
        this.produto = produto;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public String getEstoque() {
        return estoque;
    }

    public void setEstoque(String estoque) {
        this.estoque = estoque;
    }
}
