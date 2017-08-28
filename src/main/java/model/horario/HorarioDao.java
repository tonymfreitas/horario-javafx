package main.java.model.horario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import main.java.model.ConnectionFactory;
import main.java.model.materia.Materia;
import main.java.model.usuario.Usuario;

public class HorarioDao {

	public boolean cadastrarHorario(Horario horario) {
		Connection conn = new ConnectionFactory().obterConexao();
		int resultado = 0;
		String sql = "insert into horario(idmateria, idusuario, dia, periodo) values(?::uuid,?::uuid,?,?)";

		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, horario.getMateria().getId());
			pstmt.setString(2, horario.getUsuario().getId());
			pstmt.setInt(3, horario.getDia());
			pstmt.setInt(4, horario.getPeriodo());
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
		String sql = "select * from horario where idmateria = ?::uuid and idusuario = ?::uuid and dia = ? and periodo = ?";
		boolean horarioEncontrado = false;
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, horario.getMateria().getId());
			pstmt.setString(2, horario.getUsuario().getId());
			pstmt.setInt(3, horario.getDia());
			pstmt.setInt(4, horario.getPeriodo());
			ResultSet rs = pstmt.executeQuery();
			horarioEncontrado = rs.next();
			System.out.println(horarioEncontrado+"");
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		return horarioEncontrado;
	}

	public List<HashMap> consultarHorariosCadastros(Usuario usuario) {

		Connection conn = new ConnectionFactory().obterConexao();
		String sql = "SELECT DISTINCT\r\n" + 
				"	HORARIO.PERIODO,\r\n" +
				"	HORARIO.ID AS IDHORARIO,\r\n" +
				"	COUNT(DESCRICAO) AS QNTMATERIAS\r\n" + 
				"FROM \r\n" + 
				"	HORARIO\r\n" + 
				"		INNER JOIN MATERIA ON HORARIO.IDMATERIA = MATERIA.ID\r\n" + 
				"		INNER JOIN USUARIO ON MATERIA.IDUSUARIO = USUARIO.ID\r\n" + 
				"WHERE\r\n" + 
				"	USUARIO.ID = ?::uuid\r\n" + 
				"GROUP BY\r\n" + 
				"	HORARIO.PERIODO,\r\n" +
				"	HORARIO.ID";
		List<HashMap> listaDeHorarios = new ArrayList<HashMap>();
		
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, usuario.getId());
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				HashMap<String, String> horario = new HashMap<String, String>();
				horario.put("periodo", rs.getString("periodo"));
				horario.put("qntmaterias", rs.getString("qntmaterias"));
				horario.put("idhorario", rs.getString("idhorario"));
				listaDeHorarios.add(horario);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		return listaDeHorarios;
	}
	
	public List<HashMap> listarMateriasPorPeriodo(Usuario usuario, int periodo) {

		Connection conn = new ConnectionFactory().obterConexao();
		String sql = "SELECT\r\n" + 
				"	HORARIO.ID AS IDHORARIO,\r\n" + 
				"	HORARIO.DIA,\r\n" + 
				"	HORARIO.IDMATERIA,\r\n" + 
				"	MATERIA.DESCRICAO\r\n" + 
				"FROM\r\n" + 
				"	HORARIO INNER JOIN MATERIA ON HORARIO.IDMATERIA = MATERIA.ID\r\n" + 
				"WHERE\r\n" + 
				"	HORARIO.PERIODO = ?::numeric\r\n" + 
				"	AND HORARIO.IDUSUARIO = ?::uuid";
		List<HashMap> listaDeMaterias = new ArrayList<HashMap>();
		
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, periodo);
			pstmt.setString(2, usuario.getId());
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				HashMap<String, String> materia = new HashMap<String, String>();
				materia.put("idhorario", rs.getString("idhorario"));
				materia.put("dia", rs.getString("dia"));
				materia.put("idmateria", rs.getString("idmateria"));
				materia.put("descricao", rs.getString("descricao"));
				listaDeMaterias.add(materia);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		return listaDeMaterias;
	}
	
	public boolean excluirHorario(Horario horario) {
		Connection conn = new ConnectionFactory().obterConexao();
		int resultado = 0;
		String sql = "DELETE FROM HORARIO WHERE ID = ?::uuid";

		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, horario.getId());
			resultado = pstmt.executeUpdate();
			conn.close();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultado == 1 ? true : false;
	}
	
	public Horario consultarHorarioPorId(Horario horario) { 
		Connection conn = new ConnectionFactory().obterConexao();
		String sql = "SELECT ID AS IDHORARIO, IDUSUARIO,IDMATERIA FROM HORARIO WHERE ID = ?::uuid";
		Horario horarioConsultado = new Horario();
		
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, horario.getId());
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				Materia mt = new Materia();
				mt.setId(rs.getString("idmateria"));
				Usuario usuario = new Usuario();
				usuario.setId(rs.getString("idusuario"));
				horarioConsultado.setId(rs.getString("idhorario"));
				horarioConsultado.setMateria(mt);
				horarioConsultado.setUsuario(usuario);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		return horarioConsultado;
	}
	
	public boolean excluirAulasHorario(Horario horario) {

		Horario horarioConsultado = consultarHorarioPorId(horario);
		
		Connection conn = new ConnectionFactory().obterConexao();
		int resultado = 0;
		String sql = "DELETE FROM AULA WHERE IDHORARIO = ?::uuid AND IDUSUARIO = ?::uuid AND IDMATERIA = ?::uuid";

		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, horarioConsultado.getId());
			pstmt.setString(2, horarioConsultado.getUsuario().getId());
			pstmt.setString(3, horarioConsultado.getMateria().getId());
			resultado = pstmt.executeUpdate();
			conn.close();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultado == 1 ? true : false;
	}
	
}
