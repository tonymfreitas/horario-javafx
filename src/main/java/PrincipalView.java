package main.java;

import java.util.Optional;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.java.horario.CadastroHorarioView;
import main.java.usuario.UsuarioView;
import main.java.utils.SessionController;

public class PrincipalView extends Application {

	private AnchorPane pane;
	private Button btHorarios, btCadastrar, btVoltar, btNovaMateria;
	private Label lbUsuario;
	private Stage stage;
	
	@Override
	public void start(Stage primaryStage) throws Exception {

		iniciarComponentes();
		iniciarEstiloNosComponentes();
		setListeners();
		
		Scene cena = new Scene(pane);
		primaryStage.setScene(cena);
		primaryStage.setTitle("Horário acadêmido - JAVA FX");
		primaryStage.setResizable(false);
		primaryStage.show();
		stage = primaryStage;
		iniciarLayoutComponentes();
	}

	private void iniciarComponentes() {
		pane = new AnchorPane();
		pane.setPrefSize(800, 600);
		btHorarios = new Button("HORÁRIOS");
		btCadastrar = new Button("NOVO HORÁRIO");
		btNovaMateria = new Button("NOVA MATÉRIA");
		lbUsuario = new Label("BEM VINDO " + SessionController.getUsuario().getUsuario().toUpperCase());
		btVoltar = new Button("Sair");
		pane.getChildren().addAll(btHorarios, btCadastrar, lbUsuario, btVoltar, btNovaMateria);
	}
	
	private void iniciarLayoutComponentes() {
		btHorarios.setLayoutX((pane.getWidth() - btHorarios.getWidth())/2);
		btHorarios.setLayoutY(100);
		btCadastrar.setLayoutX((pane.getWidth() - btCadastrar.getWidth())/2);
		btCadastrar.setLayoutY(200);
		btNovaMateria.setLayoutX((pane.getWidth() - btNovaMateria.getWidth())/2);
		btNovaMateria.setLayoutY(300);
		lbUsuario.setLayoutX(20);
		lbUsuario.setLayoutY(20);
		btVoltar.setLayoutX((pane.getWidth() - btVoltar.getWidth() - 20));
		btVoltar.setLayoutY(20);
	}
	
	private void iniciarEstiloNosComponentes() {
		pane.getStylesheets().add("file:resources/css/style-principal-view.css");
		pane.setId("pane-principal");
		btHorarios.setId("botao-horarios");
		btCadastrar.setId("botao-cadastrar");
		lbUsuario.setId("label-usuario");
		btVoltar.setId("botao-voltar");
		btNovaMateria.setId("botao-");
	}
	
	private void deslogar() {
		SessionController.setUsuario(null);
		try {
			new UsuarioView().start(stage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void setListeners() {
		
		btCadastrar.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				try {
					new CadastroHorarioView().start(stage);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		btVoltar.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Alert sair = new Alert(AlertType.CONFIRMATION);
				sair.setContentText("Deseja relamente sair ?");
				Optional<ButtonType> saindo = sair.showAndWait();
				if(saindo.get() == ButtonType.OK) {
					deslogar();
				}
			}
		});
		
	}
	
}
