package session;

import model.Funcionario;

public final class UserSession {

    private static UserSession instance;

    private Funcionario funcionario;

    private UserSession(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public static UserSession getInstace(Funcionario funcionario) {
        if(instance == null) {
            instance = new UserSession(funcionario);
        }
        return instance;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void cleanUserSession() {
        funcionario = new Funcionario();// or null
    }

}
