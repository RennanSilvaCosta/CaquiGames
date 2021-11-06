package dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DetalhePedidoDTO implements Serializable {

    private Long idPedido;
    private LocalDate dataPedido;
    private String nomeCliente;
    private String nomeFuncionario;
    private String pagamento;
    private Double desconto;
    private Double subTotal;
    private Double valorTotal;
    private List<DetalheItemPedidoDTO> itens = new ArrayList<>();

    public DetalhePedidoDTO() {
    }

    public DetalhePedidoDTO(Long idPedido, LocalDate dataPedido, String nomecliente, String nomeFuncionario, String pagamento, Double desconto, Double subTotal, Double valorTotal) {
        this.idPedido = idPedido;
        this.dataPedido = dataPedido;
        this.nomeCliente = nomecliente;
        this.nomeFuncionario = nomeFuncionario;
        this.pagamento = pagamento;
        this.desconto = desconto;
        this.subTotal = subTotal;
        this.valorTotal = valorTotal;
    }

    public Long getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(Long idPedido) {
        this.idPedido = idPedido;
    }

    public LocalDate getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(LocalDate dataPedido) {
        this.dataPedido = dataPedido;
    }

    public String getNomecliente() {
        return nomeCliente;
    }

    public void setNomecliente(String nomecliente) {
        this.nomeCliente = nomecliente;
    }

    public String getNomeFuncionario() {
        return nomeFuncionario;
    }

    public void setNomeFuncionario(String nomeFuncionario) {
        this.nomeFuncionario = nomeFuncionario;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public String getPagamento() {
        return pagamento;
    }

    public void setPagamento(String pagamento) {
        this.pagamento = pagamento;
    }

    public Double getDesconto() {
        return desconto;
    }

    public void setDesconto(Double desconto) {
        this.desconto = desconto;
    }

    public Double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(Double subTotal) {
        this.subTotal = subTotal;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public List<DetalheItemPedidoDTO> getItens() {
        return itens;
    }
}
