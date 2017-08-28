package main.java.utils.alert;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

public class AlertUsuario {

	public AlertUsuario() {}
	
	public static Alert info(String titulo, String mensagem) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(titulo);
		alert.setHeaderText(null);
		alert.setContentText(mensagem);
		return alert;
	}
	
	public static Alert error(String titulo, String mensagem) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(titulo);
		alert.setHeaderText(null);
		alert.setContentText(mensagem);
		return alert;
	}
	
	public static Alert success(String titulo, String mensagem) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(titulo);
		alert.setHeaderText(null);
		alert.setContentText(mensagem);
		return alert;
	}
	
	public static Alert confirmation(String titulo, String mensagem) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		ButtonType botaoSim = new ButtonType("Sim", ButtonBar.ButtonData.YES);
		ButtonType botaoNao = new ButtonType("Não", ButtonBar.ButtonData.NO);
		alert.getButtonTypes().clear();
		alert.getButtonTypes().add(botaoSim);
		alert.getButtonTypes().add(botaoNao);
		alert.setTitle(titulo);
		alert.setHeaderText(null);
		alert.setContentText(mensagem);
		return alert;
	}
}
