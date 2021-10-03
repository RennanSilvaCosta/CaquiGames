package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.util.Arrays;

public class Main extends Application {

    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void start(Stage primaryStage) throws Exception {
       /* EntityManagerFactory emf = Persistence.createEntityManagerFactory("caqui");

        EntityManager entityManager = emf.createEntityManager();

        Categoria cat = new Categoria(null, "Controles");

        entityManager.getTransaction().begin();
        entityManager.persist(cat);
        entityManager.getTransaction().commit();

        Endereco end = new Endereco();

        end.setCep("69310060");
        end.setLogradouro("Rua Detson Mendes");
        end.setBairro("Aeroporto");
        end.setNumero(556);
        end.setComplemento("CASA 1");
        end.setReferencia("Esquina");

        entityManager.getTransaction().begin();
        entityManager.persist(end);
        entityManager.getTransaction().commit();

        Cliente cliente = new Cliente();

        cliente.setNome("Rennan Silva Costa");
        cliente.setCpf("20949013005");
        cliente.setEmail("rennan@hotmail.com");
        cliente.setCelular("11958331269");
        cliente.setDataNasc(LocalDate.now());
        //setando endereco no cliente
        cliente.setEndereco(end);

        entityManager.getTransaction().begin();
        entityManager.persist(cliente);
        entityManager.getTransaction().commit();

        Funcionario funcionario = new Funcionario();

        funcionario.setNome("Gabriel Santos Silva");
        funcionario.setCpf("11122233355");
        funcionario.setEmail("funcionario@hotmail.com");
        funcionario.setTelefone("11958584769");
        funcionario.setDataNasc(LocalDate.now());
        funcionario.setSenha("123456");
        funcionario.setEndereco(end);

        entityManager.getTransaction().begin();
        entityManager.persist(funcionario);
        entityManager.getTransaction().commit();

        Produto produto = new Produto();
        produto.setValor(200.00);
        produto.setMarca("Sony");
        produto.setDescricao("Controle PS4 Dual shock");
        produto.setQtdEstoque(50);
        produto.setCategoria(cat);

        entityManager.getTransaction().begin();
        entityManager.persist(produto);
        entityManager.getTransaction().commit();

        Pedido pedido = new Pedido();

        pedido.setData(LocalDate.now());

        //setando cliente no pedido
        pedido.setCliente(cliente);

        //setando funcionario no pedido
        pedido.setFuncionario(funcionario);

        entityManager.getTransaction().begin();
        entityManager.persist(pedido);
        entityManager.getTransaction().commit();

        ItemPedido ip1 = new ItemPedido(pedido, produto, 0.00, 2, 400.00);

        pedido.getItens().addAll(Arrays.asList(ip1));

        entityManager.getTransaction().begin();
        entityManager.persist(ip1);
        entityManager.getTransaction().commit();*/

        Parent root = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setScene(scene);
        primaryStage.show();
        scene.getRoot().requestFocus();

        scene.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        scene.setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() - xOffset);
            primaryStage.setY(event.getScreenY() - yOffset);
            primaryStage.setOpacity(0.7);
        });
        scene.setOnMouseReleased(mouseEvent -> primaryStage.setOpacity(1));
    }

    public static void main(String[] args) {
        launch(args);
    }

}
