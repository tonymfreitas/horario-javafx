package main.java.model.horario;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Button;

public class HorariosTableProperty {

	private final StringProperty periodo;
	private final IntegerProperty quantidadeMaterias;
	private String idhorario, idmateria;
	private ObjectProperty<Button> btExcluir;
	
	public HorariosTableProperty(String periodo, int quantidadeMaterias, Button btExcluir) {
		this.periodo = new SimpleStringProperty(periodo);
		this.quantidadeMaterias = new SimpleIntegerProperty(quantidadeMaterias);
		this.btExcluir = new SimpleObjectProperty<>(btExcluir);
	}
	
	public HorariosTableProperty(String periodo, int quantidadeMaterias, String idmateria, String idhorario) {
		this.periodo = new SimpleStringProperty(periodo);
		this.quantidadeMaterias = new SimpleIntegerProperty(quantidadeMaterias);
		this.idhorario = idhorario;
		this.idmateria = idmateria;
	}
	
	public void setPeriodo(String periodo) {
		this.periodo.set(periodo);
	}
	
	public StringProperty periodoProperty() {
		return periodo;
	}
	
	public void setQuantidadeMaterias(int quantidadeMaterias) {
		this.quantidadeMaterias.set(quantidadeMaterias);
	}
	
	public IntegerProperty quantidadeMateriasProperty() {
		return quantidadeMaterias;
	}

	public String getIdMateria() {
		return idmateria;
	}

	public String getIdHorario() {
		return idhorario;
	}
	
	public void setIdMateria(String idmateria) {
		this.idmateria = idmateria;
	}
	
	public void setIdHorario(String idhorario) {
		this.idhorario = idhorario;
	}
	
	public ObjectProperty<Button> btExcluirProperty() {
		return btExcluir;
	}
	
	public void setBtExcluir(ObjectProperty<Button> btExcluir) {
		this.btExcluir = btExcluir;
	}
	
}

