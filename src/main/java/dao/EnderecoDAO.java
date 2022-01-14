package dao;

import model.Endereco;
import utils.JPAUtil;

import javax.persistence.EntityManager;

public class EnderecoDAO {

    private static EntityManager entityManager = JPAUtil.getEntityManager();

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