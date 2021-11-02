package dto;

import java.io.Serializable;
import java.time.LocalDate;

public class PedidoRelatorioDTO implements Serializable {

    private Long id;
    private LocalDate data;
    private Double valorTotal;

    public PedidoRelatorioDTO() {
    }

    public PedidoRelatorioDTO(Long id, LocalDate data, Double valorTotal) {
        this.id = id;
        this.data = data;
        this.valorTotal = valorTotal;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }
}
