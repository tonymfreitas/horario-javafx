package main.java.materia;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.java.model.materia.Materia;
import main.java.model.materia.MateriaController;
import main.java.utils.SessionController;
import main.java.utils.alert.AlertUsuario;

public class EditarMateriaView extends Application {

	private AnchorPane pane;
	private TextField txDescricao;
	private Button btEditar, btVoltar;
	private Label lbDescricao, lbTituloView;
	private Stage stage;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		Materia materia = (Materia) primaryStage.getUserData();
		
		iniciarComponentes();
		iniciarEstiloNosComponentes();
		
		setListeners();
		
		Scene cena = new Scene(pane);
		primaryStage.setScene(cena);
		primaryStage.setTitle("Editar matéria");
		primaryStage.setResizable(false);
		primaryStage.show();
		stage = primaryStage;
		
		iniciarLayoutComponentes();
		
		txDescricao.setText(materia.getDescricao());
	}

	private void iniciarComponentes() {
		pane = new AnchorPane();
		pane.setPrefSize(800, 600);
		txDescricao = new TextField();
		btEditar = new Button("Editar");
		btVoltar = new Button("Voltar");
		lbDescricao = new Label("Descrição");
		lbTituloView = new Label("Editar matéria");
		pane.getChildren().addAll(txDescricao, btEditar, btVoltar, lbDescricao, lbTituloView);
	}
	
	private void iniciarLayoutComponentes() {
		txDescricao.setLayoutX((pane.getWidth() - txDescricao.getWidth())/2);
		txDescricao.setLayoutY(200);
		btEditar.setLayoutX((pane.getWidth() - btEditar.getWidth())/2);
		btEditar.setLayoutY(300);
		btVoltar.setLayoutX((pane.getWidth() - btVoltar.getWidth()) - 20);
		btVoltar.setLayoutY(20);
		lbDescricao.setLayoutX((pane.getWidth() - txDescricao.getWidth())/2);
		lbDescricao.setLayoutY(180);
		lbTituloView.setLayoutX((pane.getWidth() - lbTituloView.getWidth())/2);
		lbTituloView.setLayoutY(100);
	}
	
	private void iniciarEstiloNosComponentes() {
		pane.getStylesheets().add("file:resources/css/style-cadastromateria-view.css");
		pane.setId("pane-cadastro-materia");
		btVoltar.setId("botao-voltar");
		lbTituloView.setId("label-titulo");
		txDescricao.setId("textfield-descricao");
		btEditar.setId("botao-cadastrar");
	}
	
	private boolean validarCampoVazio() {
		boolean campoVazio = txDescricao.getText().isEmpty();
		if(campoVazio) {
			Alert info = AlertUsuario.info("Informação edição de matéria", "Campo descrição está vazio, o mesmo é obrigatório");
			info.showAndWait();
		}
		return campoVazio ? false : true;
	}
	
	private boolean editarMateria(Materia materia) {
		boolean editou = false;
		if(validarCampoVazio()) {
			MateriaController materiaCtrl = new MateriaController();
			materia.setUsuario(SessionController.getUsuario());
			materia.setDescricao(txDescricao.getText());
			editou = materiaCtrl.editarMateria(materia);
			if(editou) {
				Alert success = AlertUsuario.success("Edição matéria", "Matéria editar com sucesso!");
				success.showAndWait();
			} else {
				Alert erro = AlertUsuario.error("Edição matéria", "Falha na edição da matéria");
				erro.showAndWait();
			}
		}
		return editou;
	}
	
	private void setListeners() {
		
		btVoltar.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				try {
					new MateriasView().start(stage);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		
		btEditar.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Materia materia = (Materia) stage.getUserData();
				boolean editouMateria = editarMateria(materia);
				if(editouMateria) {
					try {
						new MateriasView().start(stage);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
		
	}
	
	
}

