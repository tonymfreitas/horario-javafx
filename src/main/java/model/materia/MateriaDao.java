package main.java.model.materia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import main.java.model.ConnectionFactory;
import main.java.model.usuario.Usuario;

public class MateriaDao {

	public boolean cadastrarNovaMateria(Materia materia) {
		Connection conn = new ConnectionFactory().obterConexao();
		int resultado = 0;
		String sql = "insert into materia(descricao, idusuario) values(?,?::uuid)";

		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, materia.getDescricao());
			pstmt.setString(2, materia.getUsuario().getId());
			resultado = pstmt.executeUpdate();
			conn.close();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultado == 1 ? true : false;
	}
	
	public List<Materia> listarMateriais(Usuario usuario) {
		
		Connection conn = new ConnectionFactory().obterConexao();
		List<Materia> materias = new ArrayList<>();
		String sql = "select * from materia where idusuario = ?::uuid";
		
		
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, usuario.getId());
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()){
				Materia materia = new Materia();
				materia.setDescricao(rs.getString("descricao"));
				materia.setId(rs.getString("id"));
				materias.add(materia);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		return materias;
	}
	
}
