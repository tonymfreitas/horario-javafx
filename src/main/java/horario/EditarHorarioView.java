package main.java.horario;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.controlsfx.control.CheckListView;

import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
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
import main.java.utils.DataUtils;
import main.java.utils.DiasSemana;
import main.java.utils.Periodos;
import main.java.utils.SessionController;
import main.java.utils.alert.AlertUsuario;

public class EditarHorarioView extends Application {

	private AnchorPane pane;
	private Stage stage;
	private Label lbTituloView, lbPeriodo, lbListaMaterias;
	private CheckListView<String> listaMaterias;
	private ListView<Periodos> listaPeriodos;
	private List<String> diasSemana = new ArrayList<>();
	private ObservableList<String> itensMateria;
	private ObservableList<Periodos> itensPeriodos;
	private Button btVoltar, btEditar;
	private boolean cadastroMateria;

	public Periodos periodoSelecionado = null;
	ArrayList<Materia> materiasListadas;
	ArrayList<HashMap> materiasAdicionadas = new ArrayList<>();
	HashMap horarioStage;
	boolean edit = false;
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {

		horarioStage = (HashMap) primaryStage.getUserData();
		listarMaterias();

		iniciarComponentes();
		marcarPeriodo(Integer.parseInt((String) horarioStage.get("periodo")) - 1);
		iniciarEstiloNosComponentes();
		setListeners();
		marcarMaterias();

		Scene cena = new Scene(pane);
		primaryStage.setScene(cena);
		primaryStage.setTitle("Cadastro de horário acadêmico");
		primaryStage.setResizable(false);
		primaryStage.show();
		stage = primaryStage;
		iniciarLayoutComponentes();

	}
	
	private void marcarMaterias() {
		edit = true;
		MateriaController materiaCtrl = new MateriaController();
		Usuario usuario = SessionController.getUsuario();
		Horario horario = new Horario();
		horario.setId(String.valueOf(horarioStage.get("idhorario")));
		ArrayList<Materia> materias = (ArrayList<Materia>) materiaCtrl.listarMaterias(usuario, horario);
		for(Materia materia : materias) {
			HashMap<String,String> mt = new HashMap<>();
			int dia = DataUtils.converterDiaSemana(materia.getDescricao());
			mt.put("dia", String.valueOf(dia));
			mt.put("materia", materia.getDescricao());
			listaMaterias.getCheckModel().check(dia);
			listaMaterias.scrollTo(dia);
			materiasAdicionadas.add(mt);
		}
		edit = false;
	}

	private void marcarPeriodo(int periodo) {
		listaPeriodos.getSelectionModel().select(periodo);
		listaPeriodos.getFocusModel().focus(periodo);
		listaPeriodos.scrollTo(periodo);
		periodoSelecionado = listaPeriodos.getSelectionModel().getSelectedItem();
	}

	
	private void listarMaterias() {
		MateriaController materiaCtrl = new MateriaController();
		materiasListadas = (ArrayList<Materia>) materiaCtrl.listarMaterias(SessionController.getUsuario());
		itensMateria = FXCollections.observableArrayList();
		for(Materia materia : materiasListadas) {
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

		btEditar = new Button("Cadastrar");
		pane.getChildren().addAll(lbTituloView, btEditar, btVoltar,
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
		btEditar.setLayoutX((pane.getWidth() - btEditar.getWidth()) / 2);
		btEditar.setLayoutY(400);
	}

	private void iniciarEstiloNosComponentes() {
		pane.getStylesheets().add("file:resources/css/styles-cadastrohorario-view.css");
		pane.setId("pane-cadastro-horario");
		lbTituloView.setId("label-titulo");
		btVoltar.setId("botao-voltar");
		btEditar.setId("botao-cadastrar");
		lbListaMaterias.getStyleClass().add("label-titulo-view");
		lbPeriodo.getStyleClass().add("label-titulo-view");
	}

	private boolean validarCamposVazios() {
		if (materiasAdicionadas.size() == 0) {
			listaMaterias.getCheckModel().clearChecks();
			Alert info = AlertUsuario.info("Informação cadastro horário", "Dia ou matéria não selecionado");
			info.showAndWait();
		} else if (periodoSelecionado == null) {
			Alert info = AlertUsuario.info("Informação cadastro horário", "Período semestral não selecionado");
			info.showAndWait();
		}
		return materiasAdicionadas.size() == 0 || periodoSelecionado == null ? false : true;
	}

	private boolean verificarMateriaInvalida() {
		boolean materiaInvalida = false;
		ObservableList<String> materias = listaMaterias.getCheckModel().getCheckedItems();
		DataUtils.converterDiaSemana(materias);
		for(String materia : materias) {
			for(HashMap materiaAdicionada : materiasAdicionadas) {
				if(materia.equals(materiaAdicionada.get("materia"))) {
					materiaInvalida = false;
					break;
				} else {
					materiaInvalida = true;
				}
			}
			if(materiaInvalida) {
				Alert erro = AlertUsuario.error("Erro", "Selecione um dia da seman para a matéria : " + materia);
				erro.showAndWait();
			}
		}
		
		return materiaInvalida;
	}
	
	private void setIdsMateria() {
		for(HashMap materiaAdicionada : materiasAdicionadas) {
			for(Materia materia : materiasListadas) {
				if(materia.getDescricao().equals(materiaAdicionada.get("materia"))) {
					materiaAdicionada.put("idmateria", materia.getId());
				}
			}
		}	
	}
	
	private boolean alterarHorario() {
		boolean cadastrouHorario = false;
		if (validarCamposVazios() && !verificarMateriaInvalida()) {
			HorarioController horarioCtrl = new HorarioController();
			Usuario usuario = SessionController.getUsuario();
			setIdsMateria();
			Horario horario = new Horario(materiasAdicionadas, periodoSelecionado.getPeriodo());
			
			if (!consultarHorarioCadastro(horario, usuario)) {
				cadastrouHorario = horarioCtrl.cadastrarHorario(horario, usuario);
			} else {
				Alert info = AlertUsuario.info("Informação cadastro horário",
						"Um horário ja foi cadastrado para o período selecionado");
				info.showAndWait();
			}
		}
		return cadastrouHorario;
	}

	private boolean consultarHorarioCadastro(Horario horario, Usuario usuario) {
		HorarioController horarioCtrl = new HorarioController();
		return horarioCtrl.consultarHorarioCadastro(horario, usuario);
	}
	
	private void listarDiasDaSemana() {
		ChoiceDialog<String> dialog = new ChoiceDialog<>(diasSemana.get(0), diasSemana);
		
		Optional<String> resultado = dialog.showAndWait();
		
		ObservableList<Integer> indices = listaMaterias.getCheckModel().getCheckedIndices();
		int index = indices.size() - 1;
		if(resultado.isPresent()) {
			HashMap<String, String> materia = new HashMap<>();
			materia.put("materia", listaMaterias.getCheckModel().getCheckedItems().get(index));
			int dia = DataUtils.converterDiaSemana(resultado.get());
			materia.put("dia", String.valueOf(dia));
			materiasAdicionadas.add(materia);
		} 		
	} 
	
	private void setListeners() {
		
		listaMaterias.getCheckModel().getCheckedItems().addListener(new ListChangeListener<String>() {

			@Override
			public void onChanged(javafx.collections.ListChangeListener.Change<? extends String> c) {
				if(c.next()) {
					if(c.wasAdded() && !edit) {
						listarDiasDaSemana();
					} else if(c.wasRemoved()) {
						
					}
				}
			}
		});
		
		listaMaterias.getCheckModel().getCheckedItems().addListener(new InvalidationListener() {
			
			@Override
			public void invalidated(Observable observable) {
				System.out.println("aaa");
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

	

		
		listaPeriodos.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Periodos>() {

			@Override
			public void changed(ObservableValue<? extends Periodos> observable, Periodos oldValue, Periodos newValue) {
				periodoSelecionado = newValue;
			}
		});

		btEditar.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (cadastrarHorario()) {
					Alert success = AlertUsuario.success("Edição horário", "Horário alterado com sucesso");
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
