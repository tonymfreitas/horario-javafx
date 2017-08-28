package main.java.model.aula;

public class Aula {

	private int numero;
	private boolean concluido;

	public Aula() {}
	
	public Aula(int numero, boolean concluido) {
		this.numero = numero;
		this.concluido = concluido;
	}

	public Aula(boolean concluido) {
		this.concluido = concluido;
	}
	
	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public boolean getConcluido() {
		return concluido;
	}

	public void setConcluido(boolean concluido) {
		this.concluido = concluido;
	}
	
}
