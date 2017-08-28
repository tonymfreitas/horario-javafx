package main.java.materia;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Button;

public class MateriaProperty {

	private StringProperty descricao;
	private ObjectProperty<Button> btExcluir;
	private ObjectProperty<Button> btEditar;
	
	public MateriaProperty(String descricao, Button excluir, Button editar) {
		this.descricao = new SimpleStringProperty(descricao);
		this.btExcluir = new SimpleObjectProperty<Button>(excluir);
		this.btEditar = new SimpleObjectProperty<Button>(editar);
	}

	public StringProperty descricaoProperty() {
		return descricao;
	}

	public void setDescricao(StringProperty descricao) {
		this.descricao = descricao;
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
	
	
}
