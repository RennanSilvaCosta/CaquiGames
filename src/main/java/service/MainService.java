package service;

import dao.MainDAO;

import java.math.BigDecimal;
import java.math.BigInteger;

public class MainService {

    MainDAO mainDAO = new MainDAO();

    public Double obterTotalVendido() {
         return mainDAO.obterTotalVendido();
    }

    public BigInteger obterTotalPedidos() {
        return mainDAO.obterTotalPedidos();
    }

    public BigDecimal obterTotalProdutos() {
        return mainDAO.obterTotalProdutos();
    }



}
