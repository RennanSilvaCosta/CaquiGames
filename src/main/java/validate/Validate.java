package validate;

import dto.ClienteValidaDTO;
import dto.FuncionarioDTO;
import dto.FuncionarioValidaDTO;
import exceptions.CampoObrigatorioException;
import exceptions.EmailInvalidoException;
import exceptions.SenhaInvalidaException;
import exceptions.ValidatorException;
import model.Produto;
import utils.Helper;

import java.time.format.DateTimeParseException;
import java.util.Map;

import static utils.Constante.REGEX_EMAIL;

public class Validate {

    ValidatorException exception = new ValidatorException();

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

    public Map<String, String> validaFormCadastroFuncionario(FuncionarioValidaDTO funcionarioValidaDTO) {
        exception.getErrors().clear();
        validateNome(funcionarioValidaDTO.getNome());
        validaCpf(funcionarioValidaDTO.getCpf());
        validateEmail(funcionarioValidaDTO.getEmail());
        validateSenha(funcionarioValidaDTO.getSenha());
        validaTelefone(funcionarioValidaDTO.getTelefone());
        validaDataNascimento(funcionarioValidaDTO.getData());
        validaNumeroEndereco(funcionarioValidaDTO.getNumero());
        validaCepEdenreco(funcionarioValidaDTO.getCep());
        return exception.getErrors();
    }

    public Map<String, String> validaFormAtualizaFuncionario(FuncionarioValidaDTO funcionarioValidaDTO) {
        exception.getErrors().clear();
        validateNome(funcionarioValidaDTO.getNome());
        validaCpf(funcionarioValidaDTO.getCpf());
        validateEmail(funcionarioValidaDTO.getEmail());
        validaTelefone(funcionarioValidaDTO.getTelefone());
        validaDataNascimento(funcionarioValidaDTO.getData());
        validaNumeroEndereco(funcionarioValidaDTO.getNumero());
        validaCepEdenreco(funcionarioValidaDTO.getCep());
        return exception.getErrors();
    }

    public Map<String, String> validaFormularioCadastroCliente(ClienteValidaDTO cliente) {
        exception.getErrors().clear();
        validateNome(cliente.getNome());
        validaCpf(cliente.getCpf());
        validateEmail(cliente.getEmail());
        validaTelefone(cliente.getCelular());
        validaDataNascimento(cliente.getDataNasc());
        validaNumeroEndereco(cliente.getNumero());
        validaCepEdenreco(cliente.getCep());
        return exception.getErrors();
    }

    private void validateNome(String nome) {
        if (nome == null || nome.trim().isBlank()) {
            exception.addError("nome", "O nome de Usuario não pode ser nulo!");
        } else if (nome.length() < 10) {
            exception.addError("nome", "O nome deve converter no minimo 10 caracteres");
        } else if (nome.matches("^[0-9]+$")) {
            exception.addError("nome", "O nome não pode conter números!");
        }
    }

    private void validateEmail(String email) {
        if (email == null || email.trim().isBlank()) {
            exception.addError("email", "O email não pode ser nulo!");
        } else if (!email.matches(REGEX_EMAIL)) {
            exception.addError("email", "O email informado é inválido!");
        }
    }

    private void validateSenha(String senha) {
        if (senha == null || senha.trim().isBlank()) {
            exception.addError("senha", "A senha não pode ser nula!");
        } else if (senha.length() < 8) {
            exception.addError("senha", "A senha tem que ter no minimo 8 caracteres");
        }
    }

    private void validaTelefone(String telefone) {
        if (telefone == null || telefone.trim().isBlank()) {
            exception.addError("telefone", "O telefone não pode ser nulo!");
        } else if (telefone.contains("_")) {
            exception.addError("telefone", "O telefone informado é inválido!");
        }
    }

    private void validaDataNascimento(String data) {
        try {
            Helper.converteStringParaData(data);
        } catch (DateTimeParseException e) {
            exception.addError("data", "A data de nascimetno é inválida!");
        }
    }

    private void validaCpf(String cpf) {
        if (cpf == null || cpf.trim().isBlank()) {
            exception.addError("cpf", "O CPF não pode ser nulo!");
        } else if (cpf.contains("_")) {
            exception.addError("cpf", "O CPF informado é inválido");
        }
    }

    private void validaNumeroEndereco(String numero) {
        try {
            int num = Integer.parseInt(numero);
            if (num <= 0) {
                exception.addError("numero", "O número não poder menor ou igual a 0");
            }
        } catch (NumberFormatException e) {
            exception.addError("numero", "O número informado é inválido!");
        }
    }

    private void validaCepEdenreco(String cep) {
        if (cep == null || cep.trim().isBlank()) {
            exception.addError("cep", "O CEP não pode ser nulo!");
        } else if (cep.contains("_")) {
            exception.addError("cep", "O CEP informado é inválido");
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
        if (!senha.trim().isBlank()) {
            return true;
        } else {
            throw new SenhaInvalidaException();
        }
    }

}
