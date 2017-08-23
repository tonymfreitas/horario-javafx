package main.java.horario;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.java.utils.DiasSemana;

public class CadastroHorarioView extends Application {

	private AnchorPane pane;
	private Stage stage;
	private Label lbTituloView, lbDiasSemana;
	private ListView<DiasSemana> listaDiasSemana;
	private ObservableList<DiasSemana> itens;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		iniciarComponentes();
		iniciarEstiloNosComponentes();
		
		Scene cena = new Scene(pane);
		primaryStage.setScene(cena);
		primaryStage.setTitle("Cadastro de horário acadêmico");
		primaryStage.setResizable(false);
		primaryStage.show();
		stage = primaryStage;
		iniciarLayoutComponentes();

	}
	
	private void iniciarComponentes() {
		pane = new AnchorPane();
		pane.setPrefSize(800, 600);
		listaDiasSemana = new ListView<DiasSemana>();
		lbTituloView = new Label("Cadastro de horários");
		itens = FXCollections.observableArrayList(DiasSemana.DOMINGO, DiasSemana.SEGUNDA, DiasSemana.TERÇA, DiasSemana.QUARTA, DiasSemana.QUINTA, DiasSemana.SEXTA, DiasSemana.SABADO);
		listaDiasSemana.setItems(itens);
		listaDiasSemana.setPrefSize(150, 100);
		lbDiasSemana = new Label("Dias da semana");
		pane.getChildren().addAll(lbTituloView,listaDiasSemana, lbDiasSemana);
	}
	
	private void iniciarLayoutComponentes() {
		lbTituloView.setLayoutX((pane.getWidth() - lbTituloView.getWidth())/2);
		lbTituloView.setLayoutY(100);
		listaDiasSemana.setLayoutX((pane.getWidth() - listaDiasSemana.getWidth())/6);
		listaDiasSemana.setLayoutY(250);
		lbDiasSemana.setLayoutX((pane.getWidth() - listaDiasSemana.getWidth())/6);
		lbDiasSemana.setLayoutY(220);
	}
	
	private void iniciarEstiloNosComponentes() {
		pane.getStylesheets().add("file:resources/css/styles-cadastrohorario-view.css");
		pane.setId("pane-cadastro-horario");
		lbTituloView.setId("label-titulo");
		lbDiasSemana.setId("label-dias-semana");
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
}
