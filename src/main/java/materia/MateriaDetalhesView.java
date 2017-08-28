package main.java.materia;

import java.util.Optional;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.java.horario.HorarioDetalhesView;
import main.java.model.aula.Aula;
import main.java.model.horario.Horario;
import main.java.model.materia.Materia;
import main.java.model.materia.MateriaController;
import main.java.model.materia.MateriaTableProperty;
import main.java.model.usuario.Usuario;
import main.java.utils.DiasSemana;
import main.java.utils.SessionController;
import main.java.utils.alert.AlertUsuario;

public class MateriaDetalhesView extends Application {

	private AnchorPane pane;
	private Stage stage;
	private Button btAula1, btAula2, btAula3, btAula4, btAula5, btAula6, btAula7, btAula8, btAula9, btAula10;
	private Button btVoltar;
	private MateriaTableProperty materiaProperty;

	private Materia materiaDetalhe;

	@Override
	public void start(Stage primaryStage) throws Exception {

		materiaProperty = (MateriaTableProperty) primaryStage.getUserData();
		listarAulas();

		iniciarComponentes();
		iniciarEstiloNosComponentes();
		setListeners();

		Scene cena = new Scene(pane);
		primaryStage.setScene(cena);
		primaryStage.setTitle("Detalhes matéria");
		primaryStage.show();
		iniciarLayotComponentes();
		stage = primaryStage;

		for (Aula aula : materiaDetalhe.getAulas()) {
			iniciarLayoutBotoes(aula.getNumero());
		}
	}

	private void listarAulas() {

		MateriaController materiaCtrl = new MateriaController();
		Horario horario = new Horario();
		Materia materia = new Materia();

		Usuario usuario = SessionController.getUsuario();
		int dia = converterDia(materiaProperty.diaProperty().getValue());
		String idhorario = materiaProperty.getIdHorario();

		materia.setId(materiaProperty.getIdMateria());
		materia.setUsuario(usuario);
		horario.setMateria(materia);
		horario.setDia(dia);
		horario.setId(idhorario);

		materiaDetalhe = materiaCtrl.listarAulasMateria(horario);
	}

	private void adicionarAulas(Materia materia) {
		for (Aula aula : materia.getAulas()) {
			Button botaoAula = criarBotaoAula(aula.getNumero());
			if (!aula.getConcluido()) {
				botaoAula.getStyleClass().add("botao-aula-naoconcluida");
			} else {
				botaoAula.getStyleClass().add("botao-aula-concluida");
				botaoAula.setText(botaoAula.getText() + "\nConcluída");
			}
			pane.getChildren().addAll(botaoAula);
		}
	}

	private int converterDia(String diaString) {
		int diaInt = 0;
		switch (diaString) {
		case "SEGUNDA-FEIRA":
			diaInt = DiasSemana.SEGUNDA.getDia();
			break;
		case "TERÇA-FEIRA":
			diaInt = DiasSemana.TERÇA.getDia();
			break;
		case "QUARTA-FEIRA":
			diaInt = DiasSemana.QUARTA.getDia();
			break;
		case "QUINTA-FEIRA":
			diaInt = DiasSemana.QUINTA.getDia();
			break;
		case "SEXTA-FEIRA":
			diaInt = DiasSemana.SEXTA.getDia();
			break;
		case "SABADO":
			diaInt = DiasSemana.SABADO.getDia();
			break;
		case "DOMINGO":
			diaInt = DiasSemana.DOMINGO.getDia();
			break;
		}
		return diaInt;
	}

	private void iniciarComponentes() {

		pane = new AnchorPane();
		pane.setPrefSize(800, 600);
		btVoltar = new Button("Voltar");
		pane.getChildren().addAll(btVoltar);
		adicionarAulas(materiaDetalhe);

	}

	private void desmarcarAulaConcluida(int numeroAula) {
		switch (numeroAula) {
		case 1:
			btAula1.getStyleClass().clear();
			btAula1.getStyleClass().add("botao-aula-naoconcluida");
			btAula1.setText("Aula 1");
			break;
		case 2:
			btAula2.getStyleClass().clear();
			btAula2.getStyleClass().add("botao-aula-naoconcluida");
			btAula2.setText("Aula 2");
			break;
		case 3:
			btAula3.getStyleClass().clear();
			btAula3.getStyleClass().add("botao-aula-naoconcluida");
			btAula3.setText("Aula 3");
			break;
		case 4:
			btAula4.getStyleClass().clear();
			btAula4.getStyleClass().add("botao-aula-naoconcluida");
			btAula4.setText("Aula 4");
			break;
		case 5:
			btAula5.getStyleClass().clear();
			btAula5.getStyleClass().add("botao-aula-naoconcluida");
			btAula5.setText("Aula 5");
			break;
		case 6:
			btAula6.getStyleClass().clear();
			btAula6.getStyleClass().add("botao-aula-naoconcluida");
			btAula6.setText("Aula 6");
			break;
		case 7:
			btAula7.getStyleClass().clear();
			btAula7.getStyleClass().add("botao-aula-naoconcluida");
			btAula7.setText("Aula 7");
			break;
		case 8:
			btAula8.getStyleClass().clear();
			btAula8.getStyleClass().add("botao-aula-naoconcluida");
			btAula1.setText("Aula 8");
			break;
		case 9:
			btAula9.getStyleClass().clear();
			btAula9.getStyleClass().add("botao-aula-naoconcluida");
			btAula8.setText("Aula 9");
			break;
		case 10:
			btAula10.getStyleClass().clear();
			btAula10.getStyleClass().add("botao-aula-naoconcluida");
			btAula10.setText("Aula 10");
			break;
		}
	}

	private void marcarAulaConcluida(int numeroAula) {
		switch (numeroAula) {
		case 1:
			btAula1.getStyleClass().clear();
			btAula1.getStyleClass().add("botao-aula-concluida");
			btAula1.setText(btAula1.getText() + "\nConcluída");
			break;
		case 2:
			btAula2.getStyleClass().clear();
			btAula2.getStyleClass().add("botao-aula-concluida");
			btAula2.setText(btAula2.getText() + "\nConcluída");
			break;
		case 3:
			btAula3.getStyleClass().clear();
			btAula3.getStyleClass().add("botao-aula-concluida");
			btAula3.setText(btAula3.getText() + "\nConcluída");
			break;
		case 4:
			btAula4.getStyleClass().clear();
			btAula4.getStyleClass().add("botao-aula-concluida");
			btAula4.setText(btAula4.getText() + "\nConcluída");
			break;
		case 5:
			btAula5.getStyleClass().clear();
			btAula5.getStyleClass().add("botao-aula-concluida");
			btAula5.setText(btAula5.getText() + "\nConcluída");
			break;
		case 6:
			btAula6.getStyleClass().clear();
			btAula6.getStyleClass().add("botao-aula-concluida");
			btAula6.setText(btAula6.getText() + "\nConcluída");
			break;
		case 7:
			btAula7.getStyleClass().clear();
			btAula7.getStyleClass().add("botao-aula-concluida");
			btAula7.setText(btAula7.getText() + "\nConcluída");
			break;
		case 8:
			btAula8.getStyleClass().clear();
			btAula8.getStyleClass().add("botao-aula-concluida");
			btAula1.setText(btAula1.getText() + "\nConcluída");
			break;
		case 9:
			btAula9.getStyleClass().clear();
			btAula9.getStyleClass().add("botao-aula-concluida");
			btAula8.setText(btAula8.getText() + "\nConcluída");
			break;
		case 10:
			btAula10.getStyleClass().clear();
			btAula10.getStyleClass().add("botao-aula-concluida");
			btAula10.setText(btAula10.getText() + "\nConcluída");
			break;
		}
	}

	private Button criarBotaoAula(int numeroAula) {
		switch (numeroAula) {
		case 1:
			btAula1 = new Button("Aula 1");
			return btAula1;
		case 2:
			btAula2 = new Button("Aula 2");
			return btAula2;
		case 3:
			btAula3 = new Button("Aula 3");
			return btAula3;
		case 4:
			btAula4 = new Button("Aula 4");
			return btAula4;
		case 5:
			btAula5 = new Button("Aula 5");
			return btAula5;
		case 6:
			btAula6 = new Button("Aula 6");
			return btAula6;
		case 7:
			btAula7 = new Button("Aula 7");
			return btAula7;
		case 8:
			btAula8 = new Button("Aula 8");
			return btAula8;
		case 9:
			btAula9 = new Button("Aula 9");
			return btAula9;
		case 10:
			btAula10 = new Button("Aula 10");
			return btAula10;
		}
		return null;
	}

	private void iniciarLayoutBotoes(int numeroAula) {
		switch (numeroAula) {
		case 1:
			aplicarLayoutBotao(btAula1, 0, 10, 100);
			break;
		case 2:
			aplicarLayoutBotao(btAula2, 1, 25, 100);
			break;
		case 3:
			aplicarLayoutBotao(btAula3, 2, 45, 100);
			break;
		case 4:
			aplicarLayoutBotao(btAula4, 3, 65, 100);
			break;
		case 5:
			aplicarLayoutBotao(btAula5, 4, 85, 100);
			break;
		case 6:
			aplicarLayoutBotao(btAula6, 0, 10, 240);
			break;
		case 7:
			aplicarLayoutBotao(btAula7, 1, 25, 240);
			break;
		case 8:
			aplicarLayoutBotao(btAula8, 2, 45, 240);
			break;
		case 9:
			aplicarLayoutBotao(btAula9, 3, 65, 240);
			break;
		case 10:
			aplicarLayoutBotao(btAula10, 4, 85, 240);
			break;
		}
	}

	private void aplicarLayoutBotao(Button botao, int mult, int layouX, int layoutY) {
		botao.setLayoutX((botao.getWidth() * mult) + layouX);
		botao.setLayoutY(layoutY);
	}

	private void iniciarLayotComponentes() {

		btVoltar.setLayoutX((pane.getWidth() - btVoltar.getWidth()) - 20);
		btVoltar.setLayoutY(20);

	}

	private void iniciarEstiloNosComponentes() {

		pane.getStylesheets().add("file:resources/css/style-materiadetalhes-view.css");
		pane.setId("pane-materia-detalhes");
		btVoltar.setId("botao-voltar");
	}

	private void concluirAula(int numeroAula) {
		Alert concluir = new AlertUsuario().confirmation("Concluir aula",
				"Deseja marca a aula " + numeroAula + " como concluída?");
		Optional<ButtonType> marcar = concluir.showAndWait();
		if (marcar.get().getButtonData() == ButtonData.YES) {
			MateriaController materiaCtrl = new MateriaController();
			materiaDetalhe.setUsuario(SessionController.getUsuario());
			for (Aula aula : materiaDetalhe.getAulas()) {
				if (aula.getNumero() == numeroAula) {
					aula.setConcluido(true);
				}
			}
			if (materiaCtrl.marcarAulaConcluida(materiaDetalhe)) {
				marcarAulaConcluida(numeroAula);
			} else {
				Alert erro = AlertUsuario.error("Erro", "Falha ao marcar aula como concluída");
				erro.showAndWait();
			}
		}
	}

	private void desmarcarAulaComoConcluida(int numeroAula) {
		Alert concluir = new AlertUsuario().confirmation("Desmarcar aula concluída",
				"Deseja desmarcar a aula " + numeroAula + " como concluída?");
		Optional<ButtonType> marcar = concluir.showAndWait();
		if (marcar.get().getButtonData() == ButtonData.YES) {
			MateriaController materiaCtrl = new MateriaController();
			materiaDetalhe.setUsuario(SessionController.getUsuario());
			for (Aula aula : materiaDetalhe.getAulas()) {
				if (aula.getNumero() == numeroAula) {
					aula.setConcluido(false);
				}
			}
			if (materiaCtrl.desmarcarAulaConcluida(materiaDetalhe)) {
				desmarcarAulaConcluida(numeroAula);
			} else {
				Alert erro = AlertUsuario.error("Erro", "Falha ao marcar aula como concluída");
				erro.showAndWait();
			}
		}
	}

	private boolean verificarAulaMarcada(int numeroAula) {
		boolean aulaConcluida = false;
		for (Aula aula : materiaDetalhe.getAulas()) {
			if (aula.getNumero() == numeroAula) {
				aulaConcluida = aula.getConcluido();
			}
		}
		return aulaConcluida;
	}

	private void setListeners() {

		btVoltar.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				stage.setUserData(SessionController.getProperts());
				try {
					new HorarioDetalhesView().start(stage);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		btAula1.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (!verificarAulaMarcada(1)) {
					concluirAula(1);
				} else {
					desmarcarAulaComoConcluida(1);
				}
			}
		});

		btAula2.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (!verificarAulaMarcada(2)) {
					concluirAula(2);
				} else {
					desmarcarAulaComoConcluida(2);
				}
			}
		});

		btAula3.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (!verificarAulaMarcada(3)) {
					concluirAula(3);
				} else {
					desmarcarAulaComoConcluida(3);
				}
			}
		});

		btAula4.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (!verificarAulaMarcada(4)) {
					concluirAula(4);
				} else {
					desmarcarAulaComoConcluida(4);
				}
			}
		});

		btAula5.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (!verificarAulaMarcada(5)) {
					concluirAula(5);
				} else {
					desmarcarAulaComoConcluida(5);
				}
			}
		});

		btAula6.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (!verificarAulaMarcada(6)) {
					concluirAula(6);
				} else {
					desmarcarAulaComoConcluida(6);
				}
			}
		});

		btAula7.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (!verificarAulaMarcada(7)) {
					concluirAula(7);
				} else {
					desmarcarAulaComoConcluida(7);
				}
			}
		});

		btAula8.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (!verificarAulaMarcada(8)) {
					concluirAula(8);
				} else {
					desmarcarAulaComoConcluida(8);
				}
			}
		});

		btAula9.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (!verificarAulaMarcada(9)) {
					concluirAula(9);
				} else {
					desmarcarAulaComoConcluida(9);
				}
			}
		});

		btAula10.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (!verificarAulaMarcada(10)) {
					concluirAula(10);
				} else {
					desmarcarAulaComoConcluida(10);
				}
			}
		});
	}

}
