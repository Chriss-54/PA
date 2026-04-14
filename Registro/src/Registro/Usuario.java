package Registro;

public class Usuario {
	private String user;
    private String password;

    public Usuario(String user, String password) {
        this.user = user;
        this.password = password;
    }

	public boolean Validate() {
        return this.user.equals("admin") && this.password.equals("admin123");
    }
}
