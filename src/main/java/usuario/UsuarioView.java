package main.java.usuario;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import main.java.PrincipalView;
import main.java.model.usuario.Usuario;
import main.java.model.usuario.UsuarioController;
import main.java.utils.Security;
import main.java.utils.SessionController;
import main.java.utils.alert.AlertUsuario;

public class UsuarioView extends Application {

	private AnchorPane pane;
	private TextField txLogin, txSenha;
	private Button btEntrar, btSair;
	private Label lbCadastrar, lbLogin;
	private Stage stage;

	@Override
	public void start(Stage primaryStage) throws Exception {

		iniciarComponentes();
		iniciarEstiloNosComponentes();

		setListeners();

		Scene cena = new Scene(pane);
		primaryStage.setScene(cena);
		primaryStage.setTitle("Horário acadêmico - Login");
		primaryStage.setResizable(false);
		primaryStage.show();

		stage = primaryStage;

		iniciarLayoutComponentes();
	}

	private void iniciarComponentes() {

		pane = new AnchorPane();
		pane.setPrefSize(800, 600);
		txLogin = new TextField();
		txLogin.setPromptText("Insira aqui seu login...");
		txLogin.setText("tony");
		txSenha = new TextField();
		txSenha.setPromptText("Insira aqui sua senha...");
		txSenha.setText("1");
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
		btSair.setLayoutX((txLogin.getWidth() + (pane.getWidth() - txLogin.getWidth()) / 2) - btSair.getWidth() - 10);
		btSair.setLayoutY(280);
		lbCadastrar.setLayoutX((pane.getWidth() - lbCadastrar.getWidth()) / 2);
		lbCadastrar.setLayoutY(320);
	}

	private void iniciarEstiloNosComponentes() {

		pane.getStylesheets().add("file:resources/css/style-usuario-view.css");
		pane.setId("pane-login");
		lbLogin.setFont(Font.font(20));
		lbLogin.setId("label-login");
		txLogin.setPrefWidth(300);
		txSenha.setPrefWidth(300);
		btEntrar.setPrefWidth(100);
		btSair.setPrefWidth(100);
		btEntrar.setId("bt-entrar-login");
		btSair.setId("bt-sair-login");
		lbCadastrar.setPrefWidth(100);
		lbCadastrar.setId("lb-cadastrar-login");

	}

	private Usuario autenticarUsuario() {
		UsuarioController usuarioCtrl = new UsuarioController();
		String usuario = txLogin.getText();
		String senha = Security.gerarMD5(txSenha.getText());
		Usuario usuarioAutenticacao = new Usuario(usuario, senha);
		return usuarioCtrl.autenticar(usuarioAutenticacao);
	}

	private boolean validarCamposVazios() {
		boolean usuarioVazio = txLogin.getText().isEmpty();
		boolean senhaVazio = txSenha.getText().isEmpty();
		if (usuarioVazio) {
			Alert info = AlertUsuario.info("Informação login", "Campo usuário esta vazio");
			info.showAndWait();
		} else if (senhaVazio) {
			Alert info = AlertUsuario.info("Informação login", "Campo senha esta vazio");
			info.showAndWait();
		}
		return usuarioVazio || senhaVazio ? false : true;
	}

	private void setListeners() {
		lbCadastrar.setOnMouseClicked(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				try {
					new CadastroUsuarioView().start(stage);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		
		btEntrar.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (validarCamposVazios()) {
					try {
						Usuario usuarioAutenticado = autenticarUsuario();
						if(usuarioAutenticado != null) {
							SessionController.setUsuario(usuarioAutenticado);
							new PrincipalView().start(stage);
						} else {
							Alert info = AlertUsuario.info("Erro ao tentar logar", "Usuário ou senha inválidos");
							info.showAndWait();
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
	}

	public static void main(String[] args) {
		launch(args);
	}

}
