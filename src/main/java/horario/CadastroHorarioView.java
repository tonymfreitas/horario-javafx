package main.java.horario;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import main.java.PrincipalView;
import main.java.materia.CadastroMateriaView;
import main.java.model.horario.Horario;
import main.java.model.horario.HorarioController;
import main.java.model.materia.Materia;
import main.java.model.materia.MateriaController;
import main.java.model.usuario.Usuario;
import main.java.utils.DiasSemana;
import main.java.utils.SessionController;
import main.java.utils.alert.AlertUsuario;

public class CadastroHorarioView extends Application {

	private AnchorPane pane;
	private Stage stage;
	private Label lbTituloView, lbDiasSemana, lbListaMaterias;
	private ListView<DiasSemana> listaDiasSemana;
	private ListView<Materia> listaMaterias;
	private ObservableList<DiasSemana> itens;
	private ObservableList<Materia> itensMateria;
	private Button btVoltar, btNovaMateria, btCadastrar;
	
	public Materia materiaSelecionada = null;
	public DiasSemana diaSelecionado = null;

	@Override
	public void start(Stage primaryStage) throws Exception {

		listarMaterias();

		iniciarComponentes();
		iniciarEstiloNosComponentes();
		setListeners();

		Scene cena = new Scene(pane);
		primaryStage.setScene(cena);
		primaryStage.setTitle("Cadastro de horário acadêmico");
		primaryStage.setResizable(false);
		primaryStage.show();
		stage = primaryStage;
		iniciarLayoutComponentes();

	}

	private void listarMaterias() {
		MateriaController materiaCtrl = new MateriaController();
		ArrayList<Materia> materias = (ArrayList<Materia>) materiaCtrl.listarMaterias(SessionController.getUsuario());
		itensMateria = FXCollections.observableArrayList(materias);
	}

	private void iniciarComponentes() {
		pane = new AnchorPane();
		pane.setPrefSize(800, 600);
		listaDiasSemana = new ListView<DiasSemana>();
		lbTituloView = new Label("Cadastro de horários");
		itens = FXCollections.observableArrayList(DiasSemana.DOMINGO, DiasSemana.SEGUNDA, DiasSemana.TERÇA,
				DiasSemana.QUARTA, DiasSemana.QUINTA, DiasSemana.SEXTA, DiasSemana.SABADO);
		listaDiasSemana.setItems(itens);
		listaDiasSemana.setPrefSize(150, 100);
		lbDiasSemana = new Label("Dias da semana");
		btVoltar = new Button("Voltar");
		btNovaMateria = new Button("Nova matéria");
		listaMaterias = new ListView<Materia>();
		listaMaterias.setItems(itensMateria);
		lbListaMaterias = new Label("Matérias");
		listaMaterias.setPrefSize(250, 100);
		btCadastrar = new Button("Cadastrar");
		pane.getChildren().addAll(lbTituloView, btCadastrar, listaDiasSemana, lbDiasSemana, btVoltar, btNovaMateria,
				listaMaterias, lbListaMaterias);
	}

	private void iniciarLayoutComponentes() {

		lbTituloView.setLayoutX((pane.getWidth() - lbTituloView.getWidth()) / 2);
		lbTituloView.setLayoutY(100);

		listaDiasSemana.setLayoutX(((pane.getWidth() - listaDiasSemana.getWidth()) / 2 ) - listaDiasSemana.getWidth());
		listaDiasSemana.setLayoutY(250);
		lbDiasSemana.setLayoutX(((pane.getWidth() - listaDiasSemana.getWidth()) / 2 ) - listaDiasSemana.getWidth());
		lbDiasSemana.setLayoutY(220);
		lbDiasSemana.setPrefWidth(listaDiasSemana.getWidth());

		listaMaterias.setLayoutX(((pane.getWidth() - listaMaterias.getWidth()) / 2 ) + listaDiasSemana.getWidth());
		listaMaterias.setLayoutY(250);
		lbListaMaterias.setLayoutX(((pane.getWidth() - listaMaterias.getWidth()) / 2 ) + listaDiasSemana.getWidth());
		lbListaMaterias.setLayoutY(220);
		lbListaMaterias.setPrefWidth(listaMaterias.getWidth());

		btVoltar.setLayoutX((pane.getWidth() - btVoltar.getWidth() - 20));
		btNovaMateria.setLayoutX(20);
		btNovaMateria.setLayoutY(20);
		btVoltar.setLayoutY(20);
		btCadastrar.setLayoutX((pane.getWidth() - btCadastrar.getWidth()) / 2);
		btCadastrar.setLayoutY(400);
	}

	private void iniciarEstiloNosComponentes() {
		pane.getStylesheets().add("file:resources/css/styles-cadastrohorario-view.css");
		pane.setId("pane-cadastro-horario");
		lbTituloView.setId("label-titulo");
		lbDiasSemana.setId("label-dias-semana");
		btVoltar.setId("botao-voltar");
		btNovaMateria.setId("botao-nova-materia");
		lbListaMaterias.setId("label-materias");
		btCadastrar.setId("botao-cadastrar");
	}
	
	private boolean validarCamposVazios() {
		if(diaSelecionado == null) {
			Alert info = AlertUsuario.info("Informação cadastro horário", "Dia não selecionado");
			info.showAndWait();
		} else if(materiaSelecionada == null) {
			Alert info = AlertUsuario.info("Informação cadastro horário", "Matéria não selecionado");
			info.showAndWait();
		}
		return diaSelecionado == null || materiaSelecionada == null ? false: true;
	}
	
	private boolean cadastrarHorario() {
		boolean cadastrou = false;
		if(validarCamposVazios()) {
			HorarioController horarioCtrl = new HorarioController();
			Usuario usuario = SessionController.getUsuario();
			Horario horario = new Horario(materiaSelecionada, usuario, diaSelecionado);
			if(!consultarHorarioCadastro(horario)) {
				cadastrou = horarioCtrl.cadastrarHorario(horario);
			} else {
				Alert info = AlertUsuario.info("Informação cadastro horário", "Este horário já está cadastrado, tente outra combinação");
				info.showAndWait();
			}
		}
		return cadastrou;
	}
	
	private boolean consultarHorarioCadastro(Horario horario) {
		HorarioController horarioCtrl = new HorarioController();
		return horarioCtrl.consultarHorarioCadastro(horario);
	}
	
	

	private void setListeners() {

		btVoltar.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					new PrincipalView().start(stage);
				} catch (Exception e) {
					e.printStackTrace();
				}
				;
			}
		});

		btNovaMateria.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					new CadastroMateriaView().start(stage);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		listaMaterias.setCellFactory(new Callback<ListView<Materia>, ListCell<Materia>>() {

			@Override
			public ListCell<Materia> call(ListView<Materia> param) {
				ListCell<Materia> cell = new ListCell<Materia>() {

					@Override
					protected void updateItem(Materia materia, boolean bln) {
						super.updateItem(materia, bln);
						if (materia != null) {
							setText(materia.getDescricao());
						}
					}
				};
				return cell;
			}
		});
		
		listaDiasSemana.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<DiasSemana>() {

			@Override
			public void changed(ObservableValue<? extends DiasSemana> observable, DiasSemana oldValue,
					DiasSemana newValue) {
				diaSelecionado = newValue;
			}
		});
		
		listaMaterias.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Materia>() {

			@Override
			public void changed(ObservableValue<? extends Materia> observable, Materia oldValue, Materia newValue) {
				materiaSelecionada = newValue;	
			}
		});
		
		btCadastrar.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if(cadastrarHorario()) {
					Alert success = AlertUsuario.success("Cadastro horário", "Cadastro concluído com sucesso");
					success.showAndWait();
				} else {
					Alert erro = AlertUsuario.error("Erro", "Falha ao cadastrar um novo horário");
					erro.showAndWait();
				}
			}
		});

	}

}
