package main.java.model.horario;

import main.java.model.materia.Materia;
import main.java.model.usuario.Usuario;
import main.java.utils.DiasSemana;
import main.java.utils.Periodos;

public class Horario {

	private Materia materia;
	private int dia;
	private Usuario usuario;
	private int  periodo;
	private String id;
	
	public Horario() {}
	
	public Horario(Materia materia, Usuario usuario, int dia, int periodo) {
		this.materia = materia;
		this.usuario = usuario;
		this.dia = dia;
		this.periodo = periodo;
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

	public int getDia() {
		return dia;
	}

	public void setDia(int dia) {
		this.dia = dia;
	}

	public int getPeriodo() {
		return periodo;
	}

	public void setPeriodo(int periodo) {
		this.periodo = periodo;
	}
	
}
