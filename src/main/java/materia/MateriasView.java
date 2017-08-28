package main.java.materia;

import java.util.ArrayList;
import java.util.Optional;

import javafx.application.Application;
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
import main.java.model.materia.Materia;
import main.java.model.materia.MateriaController;
import main.java.model.usuario.Usuario;
import main.java.utils.SessionController;
import main.java.utils.alert.AlertUsuario;

public class MateriasView extends Application {

	private AnchorPane pane;
	private Button btVoltar,btNovaMateria;
	
	private TableView<MateriaProperty> tbMaterias;
	private TableColumn<MateriaProperty, String> colunaDescricao;
	private TableColumn<MateriaProperty, Button> colunaBotaoExcluir;
	private TableColumn<MateriaProperty, Button> colunaBotaoEditar;
	
	private ObservableList<MateriaProperty> itensMateria;
	private Label lbTituloView;
	private Stage stage;
 	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		listarMaterias();
		
		iniciarComponentes();
		iniciarEstiloNosComponentes();
		setListeners();
		
		Scene cena = new Scene(pane);
		primaryStage.setScene(cena);
		primaryStage.setTitle("Matérias cadastradas");
		primaryStage.setResizable(false);
		primaryStage.show();
		stage = primaryStage;
		iniciarLayoutComponentes();
	}
	
	private void excluirMateria(Button btExcluir, Materia materia, int index) {
		Alert excluirAlert = AlertUsuario.confirmation("Exclusão matéria", "Deseja excluir a matéria " + materia.getDescricao());
		Optional<ButtonType> excluir = excluirAlert.showAndWait();
		if(excluir.get().getButtonData() == ButtonData.YES) {
			MateriaController materiaCtrl = new MateriaController();
			materia.setUsuario(SessionController.getUsuario());
			if(materiaCtrl.excluirMateria(materia)) {
				tbMaterias.getItems().clear();
				listarMaterias();
				tbMaterias.setItems(itensMateria);
				Alert info = AlertUsuario.info("Exclusão matéria", "Matéria excluída com sucesso!!!");
				info.showAndWait();
			} else {
				Alert erro = AlertUsuario.error("Exclusão matéria", "Falha na exclusão da matéria");
				erro.showAndWait();
			}
		}
	}
	
	private void setListenerBotaoExcluir(Button btExcluir, Materia materia, int index) {
		btExcluir.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				excluirMateria(btExcluir, materia, index);
			}
		});
	}
	
	private void setListenerBotaoEditar(Button btEditar, Materia materia) {
		btEditar.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				stage.setUserData(materia);
				try {
					new EditarMateriaView().start(stage);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private void listarMaterias() {
		
		MateriaController materiaCtrl = new MateriaController();
		Usuario usuario = SessionController.getUsuario();
		ArrayList<Materia> listaDeMaterias = (ArrayList<Materia>) materiaCtrl.listarMaterias(usuario);
		if(listaDeMaterias != null) {
			itensMateria = FXCollections.observableArrayList();
			int index = 0;
			for(Materia materia : listaDeMaterias) {
				Button btExcluir = new Button("Excluir");
				Button btEditar = new Button("Editar");
				setListenerBotaoExcluir(btExcluir, materia, index);
				setListenerBotaoEditar(btEditar, materia);
				MateriaProperty materiaProperty = new MateriaProperty(materia.getDescricao(), btExcluir, btEditar);
				itensMateria.add(materiaProperty);
				index ++;
			}
		}
	}
	
	private void iniciarComponentes() {
		
		pane = new AnchorPane();
		pane.setPrefSize(800, 600);
		btVoltar = new Button("Voltar");
		lbTituloView = new Label("MATÉRIAS CADASTRADAS");
		btNovaMateria = new Button("NOVA MATÉRIA");
		tbMaterias = new TableView<MateriaProperty>();
		colunaDescricao = new TableColumn<MateriaProperty, String>("Descrição");
		colunaDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
		colunaBotaoEditar = new TableColumn<MateriaProperty, Button>("Editar");
		colunaBotaoEditar.setCellValueFactory(new PropertyValueFactory<>("btEditar"));
		colunaBotaoExcluir = new TableColumn<MateriaProperty, Button>("Excluir");
		colunaBotaoExcluir.setCellValueFactory(new PropertyValueFactory<>("btExcluir"));
		
		tbMaterias.getColumns().addAll(colunaDescricao, colunaBotaoEditar, colunaBotaoExcluir);
		tbMaterias.setItems(itensMateria);
		tbMaterias.setPrefWidth(300);
		pane.getChildren().addAll(btVoltar, tbMaterias,lbTituloView, btNovaMateria);
		
	}
	

	
	private void iniciarLayoutComponentes() {
		
		btVoltar.setLayoutX((pane.getWidth() - btVoltar.getWidth()) - 20);
		btVoltar.setLayoutY(20);
		
		tbMaterias.setLayoutX((pane.getWidth() - tbMaterias.getWidth())/2);
		tbMaterias.setLayoutY(150);
		
		lbTituloView.setLayoutX((pane.getWidth() - lbTituloView.getWidth())/2);
		lbTituloView.setLayoutY(70);
		
		btNovaMateria.setLayoutX(20);
		btNovaMateria.setLayoutY(20);

	}
	
	private void iniciarEstiloNosComponentes() {
		
		pane.getStylesheets().add("file:resources/css/style-materias-view.css");
		pane.setId("pane-materias");
		btVoltar.setId("botao-voltar");
		lbTituloView.setId("label-titulo-view");
		btNovaMateria.setId("botao-nova-materia");
		
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
		
	}

}
