package main.java.utils;

public enum DiasSemana {

	DOMINGO(0),
	SEGUNDA(1),
	TERÇA(2),
	QUARTA(3),
	QUINTA(4),
	SEXTA(5),
	SABADO(6);
	
	private final int dia;
	
	DiasSemana(int diaDaSemana) {
		this.dia = diaDaSemana;
	}
	
	public int getDia() {
		return dia;
	}
	
}
