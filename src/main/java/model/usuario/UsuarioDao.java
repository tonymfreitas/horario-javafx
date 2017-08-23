package main.java.model.usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import main.java.model.ConnectionFactory;

public class UsuarioDao {

	public boolean cadastrarUsuario(Usuario usuario) {
		Connection conn = new ConnectionFactory().obterConexao();
		int resultado = 0;
		String sql = "insert into usuario(usuario,senha) values(?,?)";

		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, usuario.getUsuario());
			pstmt.setString(2, usuario.getSenha());
			resultado = pstmt.executeUpdate();
			conn.close();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultado == 1 ? true : false;
	}

	public Usuario autenticarUsuario(Usuario usuario) {
		Connection conn = new ConnectionFactory().obterConexao();
		String sql = "select * from usuario where usuario = ? and senha = ?";
		ResultSet usuarioConsultado = null;
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, usuario.getUsuario());
			pstmt.setString(2, usuario.getSenha());
			usuarioConsultado = pstmt.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			while (usuarioConsultado.next()) {
				String id = usuarioConsultado.getString("id");
				String user = usuarioConsultado.getString("usuario");
				String senha = usuarioConsultado.getString("senha");
				Usuario usuarioObtido = new Usuario(user, senha, id);
				return usuarioObtido;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

}
