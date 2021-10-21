package dao;

import model.Endereco;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Objects;

public class EnderecoDAO {

    private static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("caqui");
    private static EntityManager entityManager = entityManagerFactory.createEntityManager();

    public Endereco criaEndereco(Endereco endereco) {

        entityManager.getTransaction().begin();
        entityManager.persist(endereco);
        entityManager.getTransaction().commit();

        return getEndereco();

    }

    private Endereco getEndereco() {
        return entityManager.createQuery("select * from Endereco order by id desc limit 1 ", Endereco.class).getSingleResult();
    }

    public void editaEndereco(Endereco endereco, Long idEndereco) {

        Endereco enderecoNew = new Endereco();
        enderecoNew.setId(idEndereco);

        if (!Objects.isNull(endereco.getCep())) {
            enderecoNew.setComplemento(endereco.getCep());
        }
        if (!Objects.isNull(endereco.getLogradouro())) {
            enderecoNew.setLogradouro(endereco.getLogradouro());
        }
        if (!Objects.isNull(endereco.getBairro())) {
            enderecoNew.setBairro(endereco.getBairro());
        }
        if (!Objects.isNull(endereco.getNumero())) {
            enderecoNew.setNumero(endereco.getNumero());
        }
        if (!Objects.isNull(endereco.getComplemento())) {
            enderecoNew.setComplemento(endereco.getComplemento());
        }
        if (!Objects.isNull(endereco.getReferencia())) {
            enderecoNew.setReferencia(endereco.getReferencia());
        }

        entityManager.getTransaction().begin();
        entityManager.merge(enderecoNew);
        entityManager.getTransaction().commit();

    }

}