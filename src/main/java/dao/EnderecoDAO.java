package dao;

import model.Endereco;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

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

    public void editaEndereco(Endereco endereco) {
        entityManager.getTransaction().begin();
        entityManager.merge(endereco);
        entityManager.getTransaction().commit();

    }

}