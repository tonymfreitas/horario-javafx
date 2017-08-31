package main.java.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

	private final static String URL = "jdbc:postgresql://localhost:5432/horario";
	private final static String USUARIO = "postgres";
	private final static String SENHA = "postgres";

	public Connection obterConexao()  {
		try {
			Class.forName("org.postgresql.Driver");
			return DriverManager.getConnection(URL, USUARIO, SENHA);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
