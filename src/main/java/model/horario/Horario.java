package main.java.model.horario;

import java.util.ArrayList;
import java.util.HashMap;

import main.java.model.usuario.Usuario;

public class Horario {

	private ArrayList<HashMap> materias;
	private int  periodo;
	private String id;
	
	public Horario() {}
	
	public Horario(ArrayList<HashMap> materias, int periodo) {
		this.periodo = periodo;
		this.setMaterias(materias);
	}
	

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public int getPeriodo() {
		return periodo;
	}

	public void setPeriodo(int periodo) {
		this.periodo = periodo;
	}

	public ArrayList<HashMap> getMaterias() {
		return materias;
	}

	public void setMaterias(ArrayList<HashMap> materias) {
		this.materias = materias;
	}
	
}
