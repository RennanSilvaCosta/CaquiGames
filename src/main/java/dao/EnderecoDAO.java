package dao;

import model.Endereco;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Objects;

public class EnderecoDAO {

    private static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("caquidb");
    private static EntityManager entityManager = entityManagerFactory.createEntityManager();

    public Endereco criaEndereco(String cep, String logradouro, String bairro, Integer numero, String complemento, String referencia ) {

        Endereco endereco = new Endereco();

        endereco.setCep(cep);
        endereco.setBairro(bairro);
        endereco.setComplemento(complemento);
        endereco.setNumero(numero);
        endereco.setLogradouro(logradouro);
        endereco.setReferencia(referencia);

        entityManager.getTransaction().begin();
        entityManager.persist(endereco);
        entityManager.getTransaction().commit();

        return getEndereco();

    }

    private Endereco getEndereco() {
        return entityManager.createQuery("select * from Endereco order by id desc limit 1 ", Endereco.class).getSingleResult();
    }

    public void editaEndereco(String cep, String logradouro, String bairro, Integer numero, String complemento, String referencia, Long idEndereco ) {

        Endereco endereco = new Endereco();
        endereco.setId(idEndereco);

        if (!Objects.isNull(cep)) {
            endereco.setComplemento(cep);
        }
        if (!Objects.isNull(logradouro)) {
            endereco.setLogradouro(logradouro);
        }
        if (!Objects.isNull(bairro)) {
            endereco.setBairro(bairro);
        }
        if (!Objects.isNull(numero)) {
            endereco.setNumero(numero);
        }
        if (!Objects.isNull(complemento)) {
            endereco.setComplemento(complemento);
        }
        if (!Objects.isNull(referencia)) {
            endereco.setReferencia(referencia);
        }

        entityManager.getTransaction().begin();
        entityManager.merge(endereco);
        entityManager.getTransaction().commit();

    }

}