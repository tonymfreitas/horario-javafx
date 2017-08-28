package main.java.utils;

public enum Periodos {

	PRIMEIRO(1, "PRIMEIRO"),
	SEGUNDO(2, "SEGUNDO"),
	TERCEIRO(3, "TERCEIRO"),
	QUARTO(4, "QUARTO"),
	QUINTO(5, "QUINTO"),
	SEXTO(6 ,"SEXTO"),
	SÉTIMO(7, "SÉTIMO"),
	OITAVO(8, "OITAVO");
	
	private final int periodo;
	private final String descricao;
	
	Periodos(int periodo, String descricao) {
		this.periodo = periodo;
		this.descricao = descricao;
	}
	
	public int getPeriodo() {
		return periodo;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
}
