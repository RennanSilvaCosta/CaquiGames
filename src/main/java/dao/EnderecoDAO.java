package dao;

import model.Endereco;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class EnderecoDAO {

    @PersistenceContext
    private EntityManager em;

    public Endereco criaEndereco(String cep, String logradouro, String bairro, Integer numero, String complemento, String referencia ) {

        final StringBuilder createEndereco = new StringBuilder();

        createEndereco.append("insert into Endereco ");
        createEndereco.append("(cep, ");
        createEndereco.append("logradouro, ");
        createEndereco.append("bairro, ");
        createEndereco.append("numero, ");
        createEndereco.append("complemento, ");
        createEndereco.append("referencia) ");
        createEndereco.append("values ");
        createEndereco.append("( '" + cep + "'");
        createEndereco.append(", '" + logradouro + "'");
        createEndereco.append(", '" + bairro + "'");
        createEndereco.append(", " + numero);
        createEndereco.append(", '" + complemento + "'");
        createEndereco.append(", '" + referencia + "'");
        createEndereco.append(")");

        this.em.createNativeQuery(createEndereco.toString()).executeUpdate();

        return getIdEndereco();

    }

    private Endereco getIdEndereco() {
        StringBuilder queryBuilder = new StringBuilder();

        queryBuilder.append("select * from Endereco order by id desc limit 1 ");
        return this.em.createQuery(queryBuilder.toString(), Endereco.class).getSingleResult();
    }
}
