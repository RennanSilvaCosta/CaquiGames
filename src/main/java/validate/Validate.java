package validate;

import dto.FuncionarioDTO;
import exceptions.CampoObrigatorioException;
import exceptions.EmailInvalidoException;
import exceptions.SenhaInvalidaException;
import exceptions.ValorInvalidoException;
import model.Produto;

import static utils.Constante.REGEX_EMAIL;

public class Validate {

    public static void validaFormCadastroProduto(Produto produto) throws CampoObrigatorioException {
        if (produto.getNome().isBlank()) {
            throw new CampoObrigatorioException();
        }
        if (produto.getMarca().isBlank()) {
            throw new CampoObrigatorioException();
        }
        if (produto.getDescricao().isBlank()) {
            throw new CampoObrigatorioException();
        }
        if (produto.getValor() <= 0) {
            throw new CampoObrigatorioException();
        }
        if (produto.getQtdEstoque() <= 0) {
            throw new CampoObrigatorioException();
        }
        if (produto.getCategoria() == null) {
            throw new CampoObrigatorioException();
        }
    }

    public static boolean validaFormLogin(FuncionarioDTO dto) throws EmailInvalidoException, SenhaInvalidaException {
        return validaEmail(dto.getEmail()) && validaSenha(dto.getSenha());
    }

    private static boolean validaEmail(String email) throws EmailInvalidoException {
        if (email.matches(REGEX_EMAIL)) {
            return true;
        } else {
            throw new EmailInvalidoException();
        }
    }

    private static boolean validaSenha(String senha) throws SenhaInvalidaException {
        if (!senha.trim().equals("")) {
            return true;
        } else {
            throw new SenhaInvalidaException();
        }
    }

}
