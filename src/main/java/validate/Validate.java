package validate;

import dto.ClienteValidaDTO;
import dto.FuncionarioDTO;
import dto.FuncionarioValidaDTO;
import dto.ProdutoValidaDTO;
import exceptions.ValidatorException;
import utils.Helper;

import java.time.format.DateTimeParseException;
import java.util.Map;

import static utils.Constante.REGEX_EMAIL;

public class Validate {

    ValidatorException exception = new ValidatorException();

    public Map<String, String> validaFormLogin(FuncionarioDTO dto) {
        exception.getErrors().clear();
        validateEmail(dto.getEmail());
        validateSenha(dto.getSenha());
        return exception.getErrors();
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

    public Map<String, String> validaFormularioCadastroProduto(ProdutoValidaDTO produto) {
        exception.getErrors().clear();
        validaNomeProduto(produto.getProduto());
        validaDescricaoProduto(produto.getDescricao());
        validaMarcaProduto(produto.getMarca());
        validaValorProduto(produto.getValor());
        validaQtdEstoque(produto.getEstoque());
        return exception.getErrors();
    }

    private void validaQtdEstoque(String estoque) {
        try {
            int num = Integer.parseInt(estoque);
            if (num <= 0) {
                exception.addError("estoque", "A quantidade não poder ser menor ou igual a 0");
            }
        } catch (NumberFormatException e) {
            exception.addError("estoque", "A quantidade informado é inválido!");
        }
    }

    private void validaValorProduto(Double valor) {
        if (valor <= 0) {
            exception.addError("valor", "O valor não poder ser menor ou igual a 0");
        }
    }

    private void validaMarcaProduto(String marcaProduto) {
        if (marcaProduto == null || marcaProduto.trim().isBlank()) {
            exception.addError("marcaProduto", "A marca do produto não pode ser nula!");
        } else if (marcaProduto.length() < 3) {
            exception.addError("marcaProduto", "A marca deve conter no minimo 3 caracteres");
        }
    }

    private void validaDescricaoProduto(String descProduto) {
        if (descProduto == null || descProduto.trim().isBlank()) {
            exception.addError("descProduto", "A descrição do produto não pode ser nula!");
        } else if (descProduto.length() < 10) {
            exception.addError("descProduto", "A descrição do produto deve conter no minimo 10 caracteres");
        }
    }

    private void validaNomeProduto(String nomeProduto) {
        if (nomeProduto == null || nomeProduto.trim().isBlank()) {
            exception.addError("nomeProduto", "O nome do produto não pode ser nulo!");
        } else if (nomeProduto.length() < 3) {
            exception.addError("nomeProduto", "O nome do produto deve conter no minimo 3 caracteres");
        }
    }

    private void validateNome(String nome) {
        if (nome == null || nome.trim().isBlank()) {
            exception.addError("nome", "O nome de Usuario não pode ser nulo!");
        } else if (nome.length() < 10) {
            exception.addError("nome", "O nome deve conter no minimo 10 caracteres");
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
            exception.addError("senha", "A senha deve conter no minimo 8 caracteres");
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
            exception.addError("data", "A data de nascimento é inválida!");
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
                exception.addError("numero", "O número não poder ser menor ou igual a 0");
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
}
