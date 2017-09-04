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
	private ObjectProperty<Button> btEditar;
	private ObjectProperty<Button> btVisualizar;
	
	public HorariosTableProperty(String periodo, String idhorario, int quantidadeMaterias) {
		this.periodo = new SimpleStringProperty(periodo);
		this.quantidadeMaterias = new SimpleIntegerProperty(quantidadeMaterias);
		this.idhorario = idhorario;
	}
	
	public HorariosTableProperty(String periodo, int quantidadeMaterias, Button btVisualizar, Button btEditar, Button btExcluir) {
		this.periodo = new SimpleStringProperty(periodo);
		this.quantidadeMaterias = new SimpleIntegerProperty(quantidadeMaterias);
		this.btEditar = new SimpleObjectProperty<>(btEditar);
		this.btExcluir = new SimpleObjectProperty<>(btExcluir);
		this.btVisualizar = new SimpleObjectProperty<>(btVisualizar);
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

	public ObjectProperty<Button> btEditarProperty() {
		return btEditar;
	}

	public void setBtEditar(ObjectProperty<Button> btEditar) {
		this.btEditar = btEditar;
	}

	public ObjectProperty<Button> btVisualizarProperty() {
		return btVisualizar;
	}

	public void setBtVisualizar(ObjectProperty<Button> btVisualizar) {
		this.btVisualizar = btVisualizar;
	}
	
}

