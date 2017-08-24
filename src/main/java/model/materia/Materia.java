package main.java.model.materia;

import main.java.model.usuario.Usuario;

public class Materia {

	private String descricao;
	private String id;
	private Usuario usuario;
	
	public Materia() {}
	
	public Materia(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	
}
