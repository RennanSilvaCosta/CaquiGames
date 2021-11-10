package service;

import dao.MainDAO;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;

public class MainService {

    MainDAO mainDAO = new MainDAO();

    public Double obterTotalVendido() {
         return mainDAO.obterTotalVendido(LocalDate.now());
    }

    public BigInteger obterTotalPedidos() {
        return mainDAO.obterTotalPedidos(LocalDate.now());
    }

    public BigDecimal obterTotalProdutos() {
        return mainDAO.obterTotalProdutos();
    }



}
