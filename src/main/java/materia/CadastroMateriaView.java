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
import main.java.horario.CadastroHorarioView;
import main.java.model.materia.Materia;
import main.java.model.materia.MateriaController;
import main.java.model.usuario.Usuario;
import main.java.utils.SessionController;
import main.java.utils.alert.AlertUsuario;

public class CadastroMateriaView extends Application {

	private AnchorPane pane;
	private TextField txDescricao;
	private Button btCadastrar, btVoltar;
	private Label lbDescricao, lbTituloView;
	private Stage stage;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		iniciarComponentes();
		iniciarEstiloNosComponentes();
		
		setListeners();
		
		Scene cena = new Scene(pane);
		primaryStage.setScene(cena);
		primaryStage.setTitle("Nova matéria");
		primaryStage.setResizable(false);
		primaryStage.show();
		stage = primaryStage;
		
		iniciarLayoutComponentes();
	}

	private void iniciarComponentes() {
		pane = new AnchorPane();
		pane.setPrefSize(800, 600);
		txDescricao = new TextField();
		btCadastrar = new Button("Cadastrar");
		btVoltar = new Button("Voltar");
		lbDescricao = new Label("Descrição");
		lbTituloView = new Label("Cadastro de matéria");
		pane.getChildren().addAll(txDescricao, btCadastrar, btVoltar, lbDescricao, lbTituloView);
	}
	
	private void iniciarLayoutComponentes() {
		txDescricao.setLayoutX((pane.getWidth() - txDescricao.getWidth())/2);
		txDescricao.setLayoutY(200);
		btCadastrar.setLayoutX((pane.getWidth() - btCadastrar.getWidth())/2);
		btCadastrar.setLayoutY(300);
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
		btCadastrar.setId("botao-cadastrar");
	}
	
	private boolean validarCampoVazio() {
		boolean campoVazio = txDescricao.getText().isEmpty();
		if(campoVazio) {
			Alert info = AlertUsuario.info("Informação cadastro de matéria", "Campo descrição está vazio, o mesmo é obrigatório");
			info.showAndWait();
		}
		return campoVazio ? false : true;
	}
	
	private boolean cadastrarNovaMateria() {
		MateriaController materiaCtrl = new MateriaController();
		Materia materia = new Materia(txDescricao.getText());
		Usuario usuario = SessionController.getUsuario();
		materia.setUsuario(usuario);
		return materiaCtrl.cadastrarNovaMateria(materia);
	}
	

	
	private void setListeners() {
		
		btVoltar.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				try {
					new CadastroHorarioView().start(stage);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		btCadastrar.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if(validarCampoVazio()) {
					if(cadastrarNovaMateria()) {
						try {
							new CadastroHorarioView().start(stage);
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else {
						Alert erro = AlertUsuario.error("Erro", "Falha ao cadastrar nova matéria");
						erro.showAndWait();
					}
				}
			}
		});
		
	}
	
	
}
