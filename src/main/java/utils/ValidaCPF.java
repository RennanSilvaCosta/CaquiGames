package utils;

import br.com.caelum.stella.ValidationMessage;
import br.com.caelum.stella.validation.CPFValidator;
import exceptions.ValidaCPFException;
import model.Cliente;
import model.Funcionario;

import javax.persistence.*;
import java.util.List;

public class ValidaCPF {

    public boolean isCPFValido( String cpf ) {
        CPFValidator cpfValidator = new CPFValidator();
        List< ValidationMessage > erros = cpfValidator.invalidMessagesFor( cpf );
        return erros.isEmpty();
    }
}



