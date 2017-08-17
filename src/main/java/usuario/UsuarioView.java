package main.java.usuario;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class UsuarioView extends Application {

	private AnchorPane pane;
	private TextField txLogin, txSenha;
	private Button btEntrar, btSair;
	private Label lbCadastrar, lbLogin;

	@Override
	public void start(Stage primaryStage) throws Exception {

		iniciarComponentes();
		iniciarEstiloNosComponentes();

		Scene cena = new Scene(pane);
		primaryStage.setScene(cena);
		primaryStage.setTitle("Horário acadêmico - Login");
		primaryStage.setResizable(false);
		primaryStage.show();
		iniciarLayoutComponentes();
	}

	private void iniciarComponentes() {

		pane = new AnchorPane();
		pane.setPrefSize(800, 600);
		txLogin = new TextField();
		txLogin.setPromptText("Insira aqui seu login...");
		txSenha = new TextField();
		txSenha.setPromptText("Insira aqui sua senha...");
		btEntrar = new Button("Entrar");
		btSair = new Button("Sair");
		lbCadastrar = new Label("Cadastre-se");
		lbLogin = new Label("Login");
		pane.getChildren().addAll(txLogin, txSenha, btEntrar, btSair, lbCadastrar, lbLogin);

	}

	private void iniciarLayoutComponentes() {

		lbLogin.setLayoutX((pane.getWidth() - txLogin.getWidth()) / 2);
		lbLogin.setLayoutY(160);
		txLogin.setLayoutX((pane.getWidth() - txLogin.getWidth()) / 2);
		txLogin.setLayoutY(200);
		txSenha.setLayoutX((pane.getWidth() - txSenha.getWidth()) / 2);
		txSenha.setLayoutY(250);
		btEntrar.setLayoutX((pane.getWidth() - txLogin.getWidth()) / 2 + 10);
		btEntrar.setLayoutY(280);
		btSair.setLayoutX((txLogin.getWidth() + (pane.getWidth() - txLogin.getWidth()) / 2) - btSair.getWidth());
		btSair.setLayoutY(280);
		lbCadastrar.setLayoutX((pane.getWidth() - lbCadastrar.getWidth()) / 2);
		lbCadastrar.setLayoutY(270);
	}

	private void iniciarEstiloNosComponentes() {

		pane.getStylesheets().add("file:resources/css/style.css");
		pane.setId("pane-login");
		lbLogin.setFont(Font.font(20));
		lbLogin.setId("label-login");
		txLogin.setPrefWidth(300);
		txSenha.setPrefWidth(300);
		btEntrar.setId("bt-entrar-login");
		btSair.setId("bt-sair-login");
		lbCadastrar.setId("lb-cadastrar-login");

	}

	public static void main(String[] args) {
		launch(args);
	}

}
