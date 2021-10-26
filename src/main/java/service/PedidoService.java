package service;

import dao.PedidoDAO;
import model.ItemPedido;
import model.Pedido;

import java.util.Set;

public class PedidoService {

    private PedidoDAO pedidoDAO = new PedidoDAO();
    private ProdutoService ps = new ProdutoService();

    public void salvarPedido(Pedido pedido) {
        pedidoDAO.registrarVenda(pedido);
        int qtdSelecionada = 0;
        int qtdEstoque = 0;

        Set<ItemPedido> itemPedidos = pedido.getItens();

        for (ItemPedido it: itemPedidos) {
            qtdSelecionada = it.getQuantidade();
            qtdEstoque = it.getProduto().getQtdEstoque();
            it.getProduto().setQtdEstoque(qtdEstoque - qtdSelecionada);
            ps.subtraiEstoque(it.getProduto());
        }
    }

}
