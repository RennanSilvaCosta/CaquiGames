package model;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "valor_total")
    private Double valorTotal;

    @NotNull
    @Column(name = "sub_total")
    private double subTotal;

    @NotNull
    private double desconto;

    @NotNull
    @Column(name = "qtd_parcelas")
    private int qtdParcelas;

    @NotNull
    @Column(name = "forma_pagamento")
    private String formaPagamento;

    @NotNull
    @Column(name = "quantidade_itens")
    private Integer quantidadeItens;

    @NotNull
    private LocalDate data;

    @OneToMany(mappedBy = "id.pedido")
    private Set<ItemPedido> itens = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "id_funcionario")
    private Funcionario funcionario;

    public Pedido() {
    }

    public Pedido(Long id, LocalDate data, Double valorTotal) {
        this.id = id;
        this.data = data;
        this.valorTotal = valorTotal;
    }

    public Pedido(Long id, Double valorTotal, String formaPagamento, Integer quantidadeItens, LocalDate data, Cliente cliente, Funcionario funcionario) {
        this.id = id;
        this.valorTotal = valorTotal;
        this.formaPagamento = formaPagamento;
        this.quantidadeItens = quantidadeItens;
        this.data = data;
        this.cliente = cliente;
        this.funcionario = funcionario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    public double getDesconto() {
        return desconto;
    }

    public void setDesconto(double desconto) {
        this.desconto = desconto;
    }

    public int getQtdParcelas() {
        return qtdParcelas;
    }

    public void setQtdParcelas(int qtdParcelas) {
        this.qtdParcelas = qtdParcelas;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public void setItens(Set<ItemPedido> itens) {
        this.itens = itens;
    }

    public Integer getQuantidadeItens() {
        return quantidadeItens;
    }

    public void setQuantidadeItens(Integer quantidadeItens) {
        this.quantidadeItens = quantidadeItens;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public Set<ItemPedido> getItens() {
        return itens;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}
