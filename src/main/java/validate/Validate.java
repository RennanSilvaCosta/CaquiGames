package validate;

import dto.FuncionarioDTO;
import exceptions.EmailInvalidoException;
import exceptions.SenhaInvalidaException;

import static util.Constante.REGEX_EMAIL;

public class Validate {

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
