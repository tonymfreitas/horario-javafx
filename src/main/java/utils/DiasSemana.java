package main.java.utils;

public enum DiasSemana {

	DOMINGO(0, "DOMINGO"),
	SEGUNDA(1, "SEGUNDA-FEIRA"),
	TERÇA(2, "TERÇA-FEIRA"),
	QUARTA(3, "QUARTA-FEIRA"),
	QUINTA(4, "QUINTA-FEIRA"),
	SEXTA(5, "SEXTA-FEIRA"),
	SABADO(6, "SABADO");
	
	private final int dia;
	private final String descricao;
	
	DiasSemana(int diaDaSemana, String descricao) {
		this.dia = diaDaSemana;
		this.descricao = descricao;
	}
	
	public int getDia() {
		return dia;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
}
