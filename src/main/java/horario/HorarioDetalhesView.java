package main.java.horario;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

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
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.java.materia.MateriaDetalhesView;
import main.java.model.horario.Horario;
import main.java.model.horario.HorarioController;
import main.java.model.horario.HorariosTableProperty;
import main.java.model.materia.MateriaController;
import main.java.model.materia.MateriaTableProperty;
import main.java.model.usuario.Usuario;
import main.java.utils.DiasSemana;
import main.java.utils.Periodos;
import main.java.utils.SessionController;
import main.java.utils.alert.AlertUsuario;

public class HorarioDetalhesView extends Application {

	private AnchorPane pane;
	private Label lbTituloView;
	private TableView<MateriaTableProperty> tbMaterias;
	private TableColumn<MateriaTableProperty, String> colunaMateria;
	private TableColumn<MateriaTableProperty, String> colunaDia;
	private Button btVoltar, btExcluirHorario;
	private ObservableList<MateriaTableProperty> itensMateria;
	private Stage stage;
	String tituloView = "";
	HorariosTableProperty properts;

	@Override
	public void start(Stage primaryStage) throws Exception {

		properts = (HorariosTableProperty) primaryStage.getUserData();
		tituloView = "MATÉRIAS - " + properts.periodoProperty().getValue() + " PERIODO";
		listarMaterias(properts);

		iniciarComponentes();
		iniciarEstiloNosComponentes();
		setListeners();
		Scene cena = new Scene(pane);
		primaryStage.setScene(cena);
		primaryStage.setTitle("Matérias");
		primaryStage.show();
		iniciarLayoutComponentes();
		stage = primaryStage;
	}

	private void iniciarComponentes() {
		pane = new AnchorPane();
		pane.setPrefSize(800, 600);

		lbTituloView = new Label(tituloView);
		btVoltar = new Button("Voltar");
		btExcluirHorario = new Button("Excluir horário");

		tbMaterias = new TableView<MateriaTableProperty>();
		tbMaterias.setPrefWidth(500);
		colunaMateria = new TableColumn<MateriaTableProperty, String>("Matéria");
		colunaMateria.setPrefWidth(250);
		colunaDia = new TableColumn<MateriaTableProperty, String>("Dia da semana");
		colunaDia.setPrefWidth(250);
		colunaMateria.setCellValueFactory(new PropertyValueFactory<MateriaTableProperty, String>("descricao"));
		colunaDia.setCellValueFactory(new PropertyValueFactory<MateriaTableProperty, String>("dia"));
		tbMaterias.getColumns().addAll(colunaMateria, colunaDia);

		tbMaterias.setItems(itensMateria);
		pane.getChildren().addAll(lbTituloView, tbMaterias, btVoltar, btExcluirHorario);

	}

	private void iniciarLayoutComponentes() {
		lbTituloView.setLayoutX((pane.getWidth() - lbTituloView.getWidth()) / 2);
		lbTituloView.setLayoutY(50);
		tbMaterias.setLayoutX((pane.getWidth() - tbMaterias.getWidth()) / 2);
		tbMaterias.setLayoutY(100);
		btVoltar.setLayoutX((pane.getWidth() - btVoltar.getWidth()) - 20);
		btVoltar.setLayoutY(20);
		btExcluirHorario.setLayoutX((pane.getWidth() - btExcluirHorario.getWidth()) - 40);
		btExcluirHorario.setLayoutY(560);
	}

	private void listarMaterias(HorariosTableProperty properts) {

		String idhorario = properts.getIdHorario();
		String idusuario = SessionController.getUsuario().getId();
		HashMap params = new HashMap<String, String>();
		params.put("idhorario", idhorario);
		params.put("idusuario", idusuario);
		
		HorarioController horarioCtrl = new HorarioController();
		ArrayList<HashMap> listaDeMaterias = (ArrayList<HashMap>) horarioCtrl.listarMateriasPorPeriodo(params);
		if (listaDeMaterias != null) {
			listarMateriasComponentes(listaDeMaterias);
		}
	}
	
	private void iniciarEstiloNosComponentes() {
		pane.getStylesheets().add("file:resources/css/style-horariodetalhes-view.css");
		pane.setId("pane-horario-detalhes");
		btVoltar.setId("botao-voltar");
		lbTituloView.setId("label-titulo-view");
		btExcluirHorario.setId("botao-excluir-horario");
	}
	
	private void excluirHorario() {
		HorarioController horarioCtrl = new HorarioController();
		Alert excluirAlert = new AlertUsuario().confirmation("Excluir horário", "Deseja excluir este horário ?");
		Optional<ButtonType> excluir = excluirAlert.showAndWait();
		if (excluir.get().getButtonData() == ButtonData.YES) {
			Horario horario = new Horario();
			horario.setId(properts.getIdHorario());
			boolean excluiuHorario = horarioCtrl.excluirHorario(horario);
			if(excluiuHorario) {
				Alert info = AlertUsuario.info("Exclusão horário", "Horário excluído com sucesso!!!");
				info.showAndWait();
				try {
					new HorariosView().start(stage);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				Alert erro = AlertUsuario.error("Exclusão horário", "Falha ao excluir horário");
				erro.showAndWait();
			}
		}
	}

	private void setListeners() {
		
		btExcluirHorario.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				excluirHorario();
			}
		});
		
		btVoltar.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				try {
					new HorariosView().start(stage);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		tbMaterias.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<MateriaTableProperty>() {

			@Override
			public void changed(ObservableValue<? extends MateriaTableProperty> observable,
					MateriaTableProperty oldValue, MateriaTableProperty newValue) {
				stage.setUserData(newValue);
				try {
					new MateriaDetalhesView().start(stage);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
	}
	
	private String converterDia(String dia) {
		int diaInt = Integer.parseInt(dia);
		String diaString = "";
		switch (diaInt) {
		case 0:
			diaString = DiasSemana.DOMINGO.getDescricao();
			break;
		case 1:
			diaString = DiasSemana.SEGUNDA.getDescricao();
			break;
		case 2:
			diaString = DiasSemana.TERÇA.getDescricao();
			break;
		case 3:
			diaString = DiasSemana.QUARTA.getDescricao();
			break;
		case 4:
			diaString = DiasSemana.QUINTA.getDescricao();
			break;
		case 5:
			diaString = DiasSemana.SEXTA.getDescricao();
			break;
		case 6:
			diaString = DiasSemana.SABADO.getDescricao();
			break;
		}
		return diaString;
	}

	private void listarMateriasComponentes(ArrayList<HashMap> listaDeMaterias) {
		itensMateria = FXCollections.observableArrayList();
		for (HashMap<String, String> materia : listaDeMaterias) {
			String descricao = materia.get("descricao");
			String dia = converterDia(materia.get("dia"));
			String idmateria = materia.get("idmateria");
			String idhorario = materia.get("idhorario");
			itensMateria.add(new MateriaTableProperty(descricao, dia, idmateria, idhorario));
		}
	}

	private int converterPeriodo(String periodo) {
		int periodoInt = 0;
		switch (periodo) {
		case "PRIMEIRO":
			periodoInt = Periodos.PRIMEIRO.getPeriodo();
			break;
		case "SEGUNDO":
			periodoInt = Periodos.SEGUNDO.getPeriodo();
			break;
		case "TERCEIRO":
			periodoInt = Periodos.TERCEIRO.getPeriodo();
			break;
		case "QUARTO":
			periodoInt = Periodos.QUARTO.getPeriodo();
			break;
		case "QUINTO":
			periodoInt = Periodos.QUINTO.getPeriodo();
			break;
		case "SEXTO":
			periodoInt = Periodos.SEXTO.getPeriodo();
			break;
		case "SETIMO":
			periodoInt = Periodos.SÉTIMO.getPeriodo();
			break;
		case "OITAVO":
			periodoInt = Periodos.OITAVO.getPeriodo();
			break;
		}
		return periodoInt;
	}

}