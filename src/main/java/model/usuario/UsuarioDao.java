package main.java.model.usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

import main.java.model.ConnectionFactory;

public class UsuarioDao {

	public boolean cadastrarUsuario(Usuario usuario) {
		Connection conn = new ConnectionFactory().obterConexao();
		int resultado = 0;
		String sql = "insert into usuario(id,usuario,senha) values(?,?,?)";
		
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, UUID.randomUUID().toString());
			pstmt.setString(2, usuario.getUsuario());
			pstmt.setString(3, usuario.getSenha());
			resultado = pstmt.executeUpdate();
			conn.close();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	
		return resultado == 1 ? true : false;
	}
	
}
