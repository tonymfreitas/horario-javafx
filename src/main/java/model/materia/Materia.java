package main.java.model.materia;

import java.util.ArrayList;

import main.java.model.aula.Aula;
import main.java.model.usuario.Usuario;

public class Materia {

	private String descricao;
	private String id;
	private String idaulas;
	private Usuario usuario;
	private ArrayList<Aula> aulas;
	
	public Materia() {}
	
	public Materia(String descricao) {
		this.descricao = descricao;
		criarDezAulas();
	}
	
	public void criarDezAulas() {
		aulas = new ArrayList<Aula>();
		aulas.add(new Aula(1, false));
		aulas.add(new Aula(2, false));
		aulas.add(new Aula(3, false));
		aulas.add(new Aula(4, false));
		aulas.add(new Aula(5, false));
		aulas.add(new Aula(6, false));
		aulas.add(new Aula(7, false));
		aulas.add(new Aula(8, false));
		aulas.add(new Aula(9, false));
		aulas.add(new Aula(10, false));
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
	
	public void setAulas(ArrayList<Aula> aulas) {
		this.aulas = aulas;
	}
	
	public ArrayList<Aula> getAulas() {
		return aulas;
	}

	public String getIdAulas() {
		return idaulas;
	}

	public void setIdAulas(String idaulas) {
		this.idaulas = idaulas;
	}
}
