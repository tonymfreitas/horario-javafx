package main.java.model.horario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import main.java.model.ConnectionFactory;
import main.java.model.materia.Materia;
import main.java.model.usuario.Usuario;

public class HorarioDao {

	public boolean cadastrarHorario(Horario horario) {
		Connection conn = new ConnectionFactory().obterConexao();
		int resultado = 0;
		String sql = "insert into horario(idmateria, idusuario, dia) values(?::uuid,?::uuid,?)";

		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, horario.getMateria().getId());
			pstmt.setString(2, horario.getUsuario().getId());
			pstmt.setInt(3, horario.getDia().getDia());
			resultado = pstmt.executeUpdate();
			conn.close();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultado == 1 ? true : false;
	}

	public boolean consultarHorarioCadastro(Horario horario) {

		Connection conn = new ConnectionFactory().obterConexao();
		String sql = "select * from materia where idmateria = ?::uuid and idusuario = ?::uuid and dia = ?";
		boolean horarioEncontrado = false;
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, horario.getMateria().getId());
			pstmt.setString(2, horario.getUsuario().getId());
			pstmt.setInt(3, horario.getDia().getDia());
			ResultSet rs = pstmt.executeQuery();
			horarioEncontrado = rs.next();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		return horarioEncontrado;
	}

}
