package service;

import dao.PedidoDAO;
import model.Pedido;

public class PedidoService {

    private PedidoDAO pedidoDAO = new PedidoDAO();

    public void salvarPedido(Pedido pedido) {
        pedidoDAO.registrarVenda(pedido);
    }

}
