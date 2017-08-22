package main.java.model.usuario;

public class Usuario {

	private String usuario;
	private String senha;
	private String id;
	
	public Usuario(String usuario, String senha) {
		this.senha = senha;
		this.usuario = usuario;
	}
	
	public Usuario(String usuario, String senha, String id) {
		this.senha = senha;
		this.usuario = usuario;
		this.id = id;
	}
	
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
}
