package main.java.model.horario;

import main.java.model.materia.Materia;
import main.java.model.usuario.Usuario;
import main.java.utils.DiasSemana;

public class Horario {

	private Materia materia;
	private DiasSemana dia;
	private Usuario usuario;
	private String id;
	
	public Horario() {}
	
	public Horario(Materia materia, Usuario usuario, DiasSemana dia) {
		this.materia = materia;
		this.usuario = usuario;
		this.setDia(dia);
	}
	
	public Materia getMateria() {
		return materia;
	}
	public void setMateria(Materia materia) {
		this.materia = materia;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public DiasSemana getDia() {
		return dia;
	}

	public void setDia(DiasSemana dia) {
		this.dia = dia;
	}
	
}
