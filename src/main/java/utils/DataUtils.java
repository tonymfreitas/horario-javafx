package main.java.utils;

import javafx.collections.ObservableList;

public class DataUtils {

	public static int converterDiaSemana(String dia) {
		int diaInt = 0;
		switch (dia) {
		case "DOMINGO":
			diaInt = DiasSemana.DOMINGO.getDia();
			break;
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
		}
		return diaInt;
	}

	public static void converterDiaSemana(ObservableList<String> materias) {
		for (String materia : materias) {
			switch (materia) {
			case "DOMINGO":
				materia = String.valueOf(DiasSemana.DOMINGO.getDia());
				break;
			case "SEGUNDA-FEIRA":
				materia = String.valueOf(DiasSemana.SEGUNDA.getDia());
				break;
			case "TERÇA-FEIRA":
				materia = String.valueOf(DiasSemana.TERÇA.getDia());
				break;
			case "QUARTA-FEIRA":
				materia = String.valueOf(DiasSemana.QUARTA.getDia());
				break;
			case "QUINTA-FEIRA":
				materia = String.valueOf(DiasSemana.QUINTA.getDia());
				break;
			case "SEXTA-FEIRA":
				materia = String.valueOf(DiasSemana.SEXTA.getDia());
				break;
			case "SABADO":
				materia = String.valueOf(DiasSemana.SABADO.getDia());
				break;
			}
		}
	}

}
