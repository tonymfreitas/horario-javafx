package main.java.horario;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.controlsfx.control.CheckListView;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.java.PrincipalView;
import main.java.model.horario.Horario;
import main.java.model.horario.HorarioController;
import main.java.model.materia.Materia;
import main.java.model.materia.MateriaController;
import main.java.model.usuario.Usuario;
import main.java.utils.DiasSemana;
import main.java.utils.Periodos;
import main.java.utils.SessionController;
import main.java.utils.alert.AlertUsuario;

public class CadastroHorarioView extends Application {

	private AnchorPane pane;
	private Stage stage;
	private Label lbTituloView, lbPeriodo, lbListaMaterias;
	private CheckListView<String> listaMaterias;
	private ListView<Periodos> listaPeriodos;
	private List<String> diasSemana = new ArrayList<>();
	private ObservableList<String> itensMateria;
	private ObservableList<Periodos> itensPeriodos;
	private Button btVoltar, btCadastrar;

	public Periodos periodoSelecionado = null;
	
	ArrayList<HashMap> materiasAdicionadas = new ArrayList<>();

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
		itensMateria = FXCollections.observableArrayList();
		for(Materia materia : materias) {
			itensMateria.add(materia.getDescricao());
		}
	}

	private void listarDiasSemana() {
		diasSemana = new ArrayList<>();
		diasSemana.add(DiasSemana.DOMINGO.getDescricao());
		diasSemana.add(DiasSemana.SEGUNDA.getDescricao());
		diasSemana.add(DiasSemana.TERÇA.getDescricao());
		diasSemana.add(DiasSemana.QUARTA.getDescricao());
		diasSemana.add(DiasSemana.QUINTA.getDescricao());
		diasSemana.add(DiasSemana.SEXTA.getDescricao());
		diasSemana.add(DiasSemana.SABADO.getDescricao());
	}
	
	private void listarPeriodos() {
		itensPeriodos = FXCollections.observableArrayList(
				Periodos.PRIMEIRO, 
				Periodos.SEGUNDO, 
				Periodos.TERCEIRO,
				Periodos.QUARTO, 
				Periodos.QUINTO, 
				Periodos.SEXTO, 
				Periodos.SÉTIMO, 
				Periodos.OITAVO);
	}
	
	private void iniciarComponentes() {
		pane = new AnchorPane();
		pane.setPrefSize(800, 600);

		listaPeriodos = new ListView<Periodos>();
		listaMaterias = new CheckListView<String>();
		listarDiasSemana();
		listarPeriodos();
		listaMaterias.setItems(itensMateria);
		listaPeriodos.setItems(itensPeriodos);
		listaPeriodos.setPrefSize(150, 100);
		listaMaterias.setPrefSize(300, 100);

		lbTituloView = new Label("Cadastro de horários");
		lbPeriodo = new Label("Período semestral");
		btVoltar = new Button("Voltar");
		lbListaMaterias = new Label("Matérias");

		btCadastrar = new Button("Cadastrar");
		pane.getChildren().addAll(lbTituloView, btCadastrar, btVoltar,
				listaMaterias, lbListaMaterias, listaPeriodos, lbPeriodo);
	}

	private void iniciarLayoutComponentes() {

		lbTituloView.setLayoutX((pane.getWidth() - lbTituloView.getWidth()) / 2);
		lbTituloView.setLayoutY(100);

		listaMaterias.setLayoutX(((pane.getWidth() - listaMaterias.getWidth()) / 2) + listaPeriodos.getWidth());
		listaMaterias.setLayoutY(250);
		lbListaMaterias
				.setLayoutX(listaMaterias.getLayoutX());
		lbListaMaterias.setLayoutY(220);
		lbListaMaterias.setPrefWidth(listaMaterias.getWidth());

		listaPeriodos.setLayoutX(((pane.getWidth() - listaPeriodos.getWidth()) / 2) - listaPeriodos.getWidth());
		listaPeriodos.setLayoutY(250);
		lbPeriodo.setLayoutX(listaPeriodos.getLayoutX());
		lbPeriodo.setLayoutY(220);
		lbPeriodo.setPrefWidth(listaPeriodos.getWidth());

		btVoltar.setLayoutX((pane.getWidth() - btVoltar.getWidth() - 20));
		btVoltar.setLayoutY(20);
		btCadastrar.setLayoutX((pane.getWidth() - btCadastrar.getWidth()) / 2);
		btCadastrar.setLayoutY(400);
	}

	private void iniciarEstiloNosComponentes() {
		pane.getStylesheets().add("file:resources/css/styles-cadastrohorario-view.css");
		pane.setId("pane-cadastro-horario");
		lbTituloView.setId("label-titulo");
		btVoltar.setId("botao-voltar");
		btCadastrar.setId("botao-cadastrar");
		lbListaMaterias.getStyleClass().add("label-titulo-view");
		lbPeriodo.getStyleClass().add("label-titulo-view");
	}

	private boolean validarCamposVazios() {
		if (materiasAdicionadas.size() == 0) {
			Alert info = AlertUsuario.info("Informação cadastro horário", "Dia ou matéria não selecionado");
			info.showAndWait();
		} else if (periodoSelecionado == null) {
			Alert info = AlertUsuario.info("Informação cadastro horário", "Período semestral não selecionado");
			info.showAndWait();
		}
		return materiasAdicionadas.size() == 0 || periodoSelecionado == null ? false : true;
	}

	private boolean cadastrarHorario() {
		boolean cadastrouHorario = false;
		boolean cadastrouAula = false;
		if (validarCamposVazios()) {
			HorarioController horarioCtrl = new HorarioController();
			MateriaController materiaCtrl = new MateriaController();
			Usuario usuario = SessionController.getUsuario();
			Horario horario = new Horario(materiaSelecionada, usuario, diaSelecionado.getDia(), periodoSelecionado.getPeriodo());
			
			if (!consultarHorarioCadastro(horario)) {
				cadastrouAula = horarioCtrl.cadastrarHorario(horario);
				cadastrouHorario = materiaCtrl.cadastrarAulaMateria(horario);
			} else {
				Alert info = AlertUsuario.info("Informação cadastro horário",
						"Este horário já está cadastrado, tente outra combinação");
				info.showAndWait();
			}
		}
		return cadastrouAula && cadastrouHorario;
	}

	private boolean consultarHorarioCadastro(Horario horario) {
		HorarioController horarioCtrl = new HorarioController();
		return horarioCtrl.consultarHorarioCadastro(horario);
	}

	private void listarDiasDaSemana() {
		ChoiceDialog<String> dialog = new ChoiceDialog<>(diasSemana.get(0), diasSemana);
		
		Optional<String> resultado = dialog.showAndWait();
		
		ObservableList<Integer> indices = listaMaterias.getCheckModel().getCheckedIndices();
		int index = indices.size() - 1;
		if(resultado.isPresent()) {
			HashMap<String, String> materia = new HashMap<>();
			materia.put("materia", listaMaterias.getCheckModel().getCheckedItems().get(index));
			materia.put("dia", resultado.get());
			materiasAdicionadas.add(materia);
		} 	
	
	} 
	
	private void setListeners() {
		
		listaMaterias.getCheckModel().getCheckedItems().addListener(new ListChangeListener<String>() {

			@Override
			public void onChanged(javafx.collections.ListChangeListener.Change<? extends String> c) {
				listarDiasDaSemana();
			}
		});
		
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

	

//		listaMaterias.setCellFactory(new Callback<ListView<Materia>, ListCell<Materia>>() {
//
//			@Override
//			public ListCell<Materia> call(ListView<Materia> param) {
//				ListCell<Materia> cell = new ListCell<Materia>() {
//
//					@Override
//					protected void updateItem(Materia materia, boolean bln) {
//						super.updateItem(materia, bln);
//						if (materia != null) {
//							setText(materia.getDescricao());
//						}
//					}
//				};
//				return cell;
//			}
//		});

//		listaMaterias.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Materia>() {
//
//			@Override
//			public void changed(ObservableValue<? extends Materia> observable, Materia oldValue, Materia newValue) {
//				materiaSelecionada = newValue;
//				materiaSelecionada.criarDezAulas();
//			}
//		});
		
		listaPeriodos.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Periodos>() {

			@Override
			public void changed(ObservableValue<? extends Periodos> observable, Periodos oldValue, Periodos newValue) {
				periodoSelecionado = newValue;
			}
		});

		btCadastrar.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (cadastrarHorario()) {
					Alert success = AlertUsuario.success("Cadastro horário", "Cadastro concluído com sucesso");
					success.showAndWait();
					try {
						new PrincipalView().start(stage);
					} catch (Exception e) {
						e.printStackTrace();
					}
				} 
			}
		});
	}

}
