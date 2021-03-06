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
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.java.PrincipalView;
import main.java.model.horario.HorarioController;
import main.java.model.horario.HorariosTableProperty;
import main.java.model.usuario.Usuario;
import main.java.utils.Periodos;
import main.java.utils.SessionController;
import main.java.utils.alert.AlertUsuario;

public class HorariosView extends Application {

	private AnchorPane pane;
	private Button btVoltar;
	private TableView<HorariosTableProperty> tbHorarios;
	private TableColumn<HorariosTableProperty, String> periodo;
	private TableColumn<HorariosTableProperty, Integer> qntMaterias;
	private TableColumn<HorariosTableProperty, Button> btExcluir;
	private TableColumn<HorariosTableProperty, Button> btEditar;
	private TableColumn<HorariosTableProperty, Button> btVisualizar;
	private ObservableList<HorariosTableProperty> itensHorarios;
	private Label lbTituloView;
	private Stage stage;
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {

		
		listarHorariosCadastrados();
		iniciarComponentes();
		iniciarEstiloNosComponentes();
		setListeners();
		
		Scene cena = new Scene(pane);
		primaryStage.setScene(cena);
		primaryStage.setTitle("Hor�rio cadastrados");
		primaryStage.setResizable(false);
		primaryStage.show();
		stage = primaryStage;
		
		iniciarLayoutComponentes();
	}
	
	private String verificarPeriodo(int periodo) {
		String periodoRetorno = "";
		switch(periodo) {
		case 1 : periodoRetorno = Periodos.PRIMEIRO.getDescricao(); break;
		case 2 : periodoRetorno = Periodos.SEGUNDO.getDescricao(); break;
		case 3 : periodoRetorno = Periodos.TERCEIRO.getDescricao(); break;
		case 4 : periodoRetorno = Periodos.QUARTO.getDescricao(); break;
		case 5 : periodoRetorno = Periodos.QUINTO.getDescricao(); break;
		case 6 : periodoRetorno = Periodos.SEXTO.getDescricao(); break;
		case 7 : periodoRetorno = Periodos.S�TIMO.getDescricao(); break;
		case 8 : periodoRetorno = Periodos.OITAVO.getDescricao(); break;
		}
		return periodoRetorno;
	}
	
	private void excluirHorario(HashMap horario) {
		Alert excluirAlert = AlertUsuario.confirmation("Exclus�o hor�rio", "Deseja excluir o hor�rio do " + String.valueOf(horario.get("periodo")) + " periodo ?");
		Optional<ButtonType> excluir = excluirAlert.showAndWait();
		if(excluir.get().getButtonData() == ButtonData.YES) {
			HorarioController horarioCtrl = new HorarioController();
			boolean excluiu = horarioCtrl.excluirHorario(horario);
			if(excluiu) {
				Alert info = AlertUsuario.info("Exclus�o hor�rio", "Hor�rio exclu�do com sucesso !!");
				info.showAndWait();
				itensHorarios.clear();
				listarHorariosCadastrados();
				tbHorarios.setItems(itensHorarios);
			} else {
				Alert erro = AlertUsuario.error("Erro", "Falha ao tentar excluir o hor�rio");
				erro.showAndWait();
			}
		}
	}
	
	private void addListenerBotaoExcluir(Button btExcluir, HashMap horario) {
		btExcluir.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				excluirHorario(horario);
			}
		});
	}
	
	private void addListenerBotaoVisualizar(Button btVisualizar, HashMap horario) {
		btVisualizar.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				String periodo = String.valueOf(horario.get("periodo"));
				String idhorario = String.valueOf(horario.get("idhorario"));
				int qntMaterias = Integer.parseInt((String) horario.get("qntmaterias"));
				HorariosTableProperty properts = new HorariosTableProperty(periodo, idhorario, qntMaterias);
				try {
					stage.setUserData(properts);
					SessionController.setProperts(properts);	
					new HorarioDetalhesView().start(stage);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private void addListenerBotaoEditar(Button btEditar, HashMap horario) {
		
		btEditar.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				stage.setUserData(horario);
				try {
					new EditarHorarioView().start(stage);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		
	}
	
	private void listarHorariosCadastrados() {
		
		HorarioController horarioCtrl = new HorarioController();
		Usuario usuario = SessionController.getUsuario();
		ArrayList<HashMap> listaDeHorarios = (ArrayList<HashMap>) horarioCtrl.consultarHorariosCadastrados(usuario);
		
		if(listaDeHorarios != null) {
			itensHorarios = FXCollections.observableArrayList();
			
			for(HashMap horario : listaDeHorarios) {
				
				String periodoRetornado = (String) horario.get("periodo");
				String quantidadeRetornado = (String) horario.get("qntmaterias");
				
				String periodo = verificarPeriodo(Integer.parseInt(periodoRetornado));
				int quantidadeMaterias = Integer.parseInt(quantidadeRetornado);
				String idhorario = (String) horario.get("idhorario");
				Button btExcluir = new Button("Excluir");
				Button btEditar = new Button("Editar");
				Button btVisualizar = new Button("Visualizar");
				addListenerBotaoExcluir(btExcluir, horario);
				addListenerBotaoVisualizar(btVisualizar, horario);
				addListenerBotaoEditar(btEditar, horario);
				HorariosTableProperty horariosProperty = new HorariosTableProperty(periodo, quantidadeMaterias, btVisualizar, btEditar, btExcluir);
				horariosProperty.setIdHorario(idhorario);
				itensHorarios.add(horariosProperty);
			}
		}
		
	}
	
	private void iniciarComponentes() {
	
		pane = new AnchorPane();
		pane.setPrefSize(800, 600);
		btVoltar = new Button("Voltar");
		lbTituloView = new Label("HOR�RIOS");
		tbHorarios = new TableView<HorariosTableProperty>();
		periodo = new TableColumn<HorariosTableProperty, String>("Per�odo");
		qntMaterias = new TableColumn<HorariosTableProperty, Integer>("Quantidade de mat�rias");
		btVisualizar = new TableColumn<HorariosTableProperty, Button>("Visualizar");
		btEditar = new TableColumn<HorariosTableProperty, Button>("Editar");
		btExcluir = new TableColumn<HorariosTableProperty, Button>("Excluir");
		periodo.setCellValueFactory(new PropertyValueFactory<HorariosTableProperty, String>("periodo"));
		qntMaterias.setCellValueFactory(new PropertyValueFactory<HorariosTableProperty, Integer>("quantidadeMaterias"));
		btVisualizar.setCellValueFactory(new PropertyValueFactory<>("btVisualizar"));
		btVisualizar.setCellValueFactory(new PropertyValueFactory<>("btVisualizar"));
		btEditar.setCellValueFactory(new PropertyValueFactory<>("btEditar"));
		btExcluir.setCellValueFactory(new PropertyValueFactory<>("btExcluir"));
		
		tbHorarios.getColumns().addAll(periodo, qntMaterias, btVisualizar, btEditar, btExcluir);
		tbHorarios.setItems(itensHorarios);
		
		pane.getChildren().addAll(lbTituloView, btVoltar, tbHorarios);
	
	}
	
	private void iniciarLayoutComponentes() {
		btVoltar.setLayoutX((pane.getWidth() - btVoltar.getWidth()) - 20);
		btVoltar.setLayoutY(20);
		
		tbHorarios.setLayoutX((pane.getWidth() - tbHorarios.getWidth())/2);
		tbHorarios.setLayoutY(100);
		
		lbTituloView.setLayoutX((pane.getWidth() - lbTituloView.getWidth()) / 2);
		lbTituloView.setLayoutY(50);
	}
	
	private void iniciarEstiloNosComponentes() {
		pane.getStylesheets().add("file:resources/css/style-horarios-view.css");
		tbHorarios.setPrefWidth(500);
		pane.setId("pane-horarios");
		btVoltar.setId("botao-voltar");
		lbTituloView.setId("label-titulo-view");
	}

	private void setListeners() {
		
		btVoltar.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				try {
					new PrincipalView().start(stage);
				} catch (Exception e) {
					e.printStackTrace();
				};
			}
		});
				
	}
	
}
