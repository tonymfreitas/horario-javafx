package main.java.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

	private final static String URL = "jdbc:postgresql://localhost:5432/horario";
	private final static String USUARIO = "postgres";
	private final static String SENHA = "aula321";

	public Connection obterConexao()  {
		try {
			return DriverManager.getConnection(URL, USUARIO, SENHA);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
