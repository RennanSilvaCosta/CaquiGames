package relatorios;

import dto.PedidoRelatorioDTO;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import service.PedidoService;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JasperReports {

    public void gerarRelatorioSintetico(LocalDate dataInicial, LocalDate dataFinal) throws FileNotFoundException, JRException {
        PedidoService  pedidoService = new PedidoService();
        InputStream inputStream = new FileInputStream("src/main/resources/model_relatorios/relatorio_sintetico.jrxml");

        List<PedidoRelatorioDTO> listaPedidos = pedidoService.consultarPedidos(dataInicial, dataFinal);
        double totalPedidos = 0;
        for (PedidoRelatorioDTO p: listaPedidos) {
            totalPedidos += p.getValorTotal();
        }

        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(listaPedidos);

        Map parameters = new HashMap();
        parameters.put("Parameter1", totalPedidos);

        //carrega o layout do relatorio
        JasperDesign jasperDesign = JRXmlLoader.load(inputStream);

        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, beanColDataSource);

        JasperViewer viewer = new JasperViewer(jasperPrint);
        viewer.setVisible(true);
    }

}
