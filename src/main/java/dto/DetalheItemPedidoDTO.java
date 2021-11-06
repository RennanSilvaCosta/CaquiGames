package dto;

public class DetalheItemPedidoDTO {

    private Double preco;
    private Integer quantidade;
    private String nome;
    private Double total;

    public DetalheItemPedidoDTO() {
    }

    public DetalheItemPedidoDTO(Double preco, Integer quantidade, String nome, Double total) {
        this.preco = preco;
        this.quantidade = quantidade;
        this.nome = nome;
        this.total = total;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}
