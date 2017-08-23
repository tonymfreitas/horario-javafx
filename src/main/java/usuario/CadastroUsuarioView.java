package main.java.usuario;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import main.java.model.usuario.Usuario;
import main.java.model.usuario.UsuarioController;
import main.java.utils.Security;
import main.java.utils.alert.AlertUsuario;

public class CadastroUsuarioView extends Application {

	private AnchorPane pane;
	private Label lbUsuario, lbSenha, lbSenhaConfirmacao, lbCadastro;
	private TextField txUsuario;
	private PasswordField psSenha, psSenhaConfirmacao;
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
		psSenha = new PasswordField();
		psSenhaConfirmacao = new PasswordField();
		btSalvar = new Button("Salvar");
		btVoltar = new Button("Voltar");
		lbCadastro = new Label("Cadastro de usuário");
		pane.getChildren().addAll(lbUsuario, lbSenha, lbSenhaConfirmacao, txUsuario, psSenha, psSenhaConfirmacao,
				btSalvar, btVoltar, lbCadastro);

	}

	private void iniciarLayoutCompoenentes() {

		lbUsuario.setLayoutX((pane.getWidth() - txUsuario.getWidth()) / 2);
		lbUsuario.setLayoutY(150);
		txUsuario.setLayoutX((pane.getWidth() - txUsuario.getWidth()) / 2);
		txUsuario.setLayoutY(170);

		lbSenha.setLayoutX((pane.getWidth() - txUsuario.getWidth()) / 2);
		lbSenha.setLayoutY(200);
		psSenha.setLayoutX((pane.getWidth() - psSenha.getWidth()) / 2);
		psSenha.setLayoutY(220);

		lbSenhaConfirmacao.setLayoutX((pane.getWidth() - txUsuario.getWidth()) / 2);
		lbSenhaConfirmacao.setLayoutY(250);
		psSenhaConfirmacao.setLayoutX((pane.getWidth() - psSenhaConfirmacao.getWidth()) / 2);
		psSenhaConfirmacao.setLayoutY(270);

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
		psSenha.setPrefWidth(300);
		psSenhaConfirmacao.setPrefWidth(300);
		btSalvar.setPrefWidth(200);
		btSalvar.setFont(Font.font(15));
		btVoltar.setFont(Font.font(12));

	}

	private boolean cadastrarUsuario() {
		String usuario = txUsuario.getText();
		String senha = Security.gerarMD5(psSenha.getText());
		Usuario usuarioCadastro = new Usuario(usuario, senha);
		UsuarioController usuarioCtrl = new UsuarioController();
		return usuarioCtrl.cadastrarUsuario(usuarioCadastro);
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
				if (validarCamposVazios() && validarSenhasIguais()) {
					if (cadastrarUsuario()) {
						try {
							new UsuarioView().start(stage);
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else {
						Alert erro = AlertUsuario.error("Erro", "Erro ao cadastrar o usuário: " + txUsuario.getText());
						erro.showAndWait();
					}
				}
			}
		});
	}

	private boolean validarCamposVazios() {
		String campoVazio = "";
		if (txUsuario.getText().isEmpty()) {
			campoVazio = "usuario";
		} else if (psSenha.getText().isEmpty()) {
			campoVazio = "senha";
		} else if (psSenhaConfirmacao.getText().isEmpty()) {
			campoVazio = "senha de confirmação";
		}
		if (!campoVazio.isEmpty()) {
			Alert info = AlertUsuario.info("Info", "O campo " + campoVazio + " é obrigatório");
			info.showAndWait();
			return false;
		} else {
			return true;
		}
	}
	
	private boolean validarSenhasIguais() {
		if(psSenha.getText().equals(psSenhaConfirmacao.getText())) {
			return true;
		} else {
			Alert info = AlertUsuario.info("Erro", "Senhas diferentes, por favor verifique e tente novamente");
			info.showAndWait();
			return false;
		}
	}

}
