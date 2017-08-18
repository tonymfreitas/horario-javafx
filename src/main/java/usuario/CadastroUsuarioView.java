package main.java.usuario;

import javax.swing.JOptionPane;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import main.java.model.usuario.Usuario;
import main.java.model.usuario.UsuarioController;

public class CadastroUsuarioView extends Application {

	private AnchorPane pane;
	private Label lbUsuario, lbSenha, lbSenhaConfirmacao, lbCadastro;
	private TextField txUsuario, txSenha, txSenhaConfirmacao;
	private Button btSalvar, btVoltar;
	private Stage stage;

	@Override
	public void start(Stage primaryStage) throws Exception {

		iniciarComponentes();
		iniciarEstiloNosComponentes();
		
		setListeners();

		Scene cena = new Scene(pane);
		primaryStage.setScene(cena);
		primaryStage.setResizable(false);
		primaryStage.setTitle("Cadastro de usuário");
		primaryStage.show();
		stage = primaryStage;
		iniciarLayoutCompoenentes();

		
	}

	private void iniciarComponentes() {

		pane = new AnchorPane();
		pane.setPrefSize(800, 600);
		lbUsuario = new Label("Usuario");
		lbSenha = new Label("Senha");
		lbSenhaConfirmacao = new Label("Senha de confirmação");
		txUsuario = new TextField();
		txSenha = new TextField();
		txSenhaConfirmacao = new TextField();
		btSalvar = new Button("Salvar");
		btVoltar = new Button("Voltar");
		lbCadastro = new Label("Cadastro de usuário");
		pane.getChildren().addAll(lbUsuario, lbSenha, lbSenhaConfirmacao, txUsuario, txSenha, txSenhaConfirmacao,
				btSalvar, btVoltar, lbCadastro);

	}

	private void iniciarLayoutCompoenentes() {

		lbUsuario.setLayoutX((pane.getWidth() - txUsuario.getWidth()) / 2);
		lbUsuario.setLayoutY(150);
		txUsuario.setLayoutX((pane.getWidth() - txUsuario.getWidth()) / 2);
		txUsuario.setLayoutY(170);

		lbSenha.setLayoutX((pane.getWidth() - txUsuario.getWidth()) / 2);
		lbSenha.setLayoutY(200);
		txSenha.setLayoutX((pane.getWidth() - txSenha.getWidth()) / 2);
		txSenha.setLayoutY(220);

		lbSenhaConfirmacao.setLayoutX((pane.getWidth() - txUsuario.getWidth()) / 2);
		lbSenhaConfirmacao.setLayoutY(250);
		txSenhaConfirmacao.setLayoutX((pane.getWidth() - txSenhaConfirmacao.getWidth()) / 2);
		txSenhaConfirmacao.setLayoutY(270);

		btVoltar.setLayoutX(pane.getWidth() - btVoltar.getWidth() - 10);
		btVoltar.setLayoutY(10);

		btSalvar.setLayoutX((pane.getWidth() - btSalvar.getWidth()) / 2);
		btSalvar.setLayoutY(320);

		lbCadastro.setLayoutX((pane.getWidth() - lbCadastro.getWidth()) / 2);
		lbCadastro.setLayoutY(60);
	}

	private void iniciarEstiloNosComponentes() {
		pane.getStylesheets().add("file:resources/css/style-cadastrousuario-view.css");
		pane.setId("pane");
		btSalvar.setId("botao-salvar");
		btVoltar.setId("botao-voltar");
		lbCadastro.setId("label-cadastro");
		txUsuario.setPrefWidth(300);
		txSenha.setPrefWidth(300);
		txSenhaConfirmacao.setPrefWidth(300);
		btSalvar.setPrefWidth(200);
		btSalvar.setFont(Font.font(15));
		btVoltar.setFont(Font.font(12));

	}
	
	private void cadastrarUsuario() {
		Usuario usuario = new Usuario(txUsuario.getText(), txSenha.getText());
		UsuarioController usuarioCtrl = new UsuarioController();
		boolean abc = usuarioCtrl.cadastrarUsuario(usuario);
	}

	public void setListeners() {

		btVoltar.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					new UsuarioView().start(stage);
				} catch (Exception e) {
					e.printStackTrace();
				}
				;
			}
		});

		btSalvar.setOnMouseClicked(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				cadastrarUsuario();
				try {
					new UsuarioView().start(stage);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		});
	}

	public static void main(String[] args) {
		launch(args);
	}
	
}
