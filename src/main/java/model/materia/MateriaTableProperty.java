package main.java.model.materia;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class MateriaTableProperty {

	private final StringProperty descricao;
	private final StringProperty dia;
	private String idmateria, idhorario;
	
	public MateriaTableProperty(String descricao, String dia, String idmateria, String idhorario) {
		this.descricao = new SimpleStringProperty(descricao);
		this.dia = new SimpleStringProperty(dia);
		this.idmateria = idmateria;
		this.setIdHorario(idhorario);
	}
	
	public StringProperty descricaoProperty() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao.set(descricao);
	}
	
	public StringProperty diaProperty() {
		return dia;
	}
	
	public void setDia(String dia) {
		this.dia.set(dia);
	}

	public String getIdMateria() {
		return idmateria;
	}

	public void setIdMateria(String idmateria) {
		this.idmateria = idmateria;
	}

	public String getIdHorario() {
		return idhorario;
	}

	public void setIdHorario(String idhorario) {
		this.idhorario = idhorario;
	}
	
}
