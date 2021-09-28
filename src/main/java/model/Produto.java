package model;

import com.sun.istack.NotNull;

import javax.persistence.*;

@Entity
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(length = 40)
    private String descricao;

    @NotNull
    private Double valor;

    @NotNull
    @Column(length = 40)
    private String marca;

    @NotNull
    private Integer qtdEstoque;

    public Produto() {}

    public Produto(Long id, String descricao, Double valor, String marca, Integer qtdEstoque) {
        this.id = id;
        this.descricao = descricao;
        this.valor = valor;
        this.marca = marca;
        this.qtdEstoque = qtdEstoque;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public Integer getQtdEstoque() {
        return qtdEstoque;
    }

    public void setQtdEstoque(Integer qtdEstoque) {
        this.qtdEstoque = qtdEstoque;
    }
}
