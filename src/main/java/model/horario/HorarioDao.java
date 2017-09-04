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
	
	public String consultarHorarioCadastrado(Horario horario, Usuario usuario) {
		Connection conn = new ConnectionFactory().obterConexao();
		String sql = "select * from horario where periodo = ?::numeric and idusuario = ?::uuid";
		String idhorario = "";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, horario.getPeriodo());
			pstmt.setString(2, usuario.getId());
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				idhorario = rs.getString("id");
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		return idhorario;
	}
	
	public boolean cadastrarHorario(Horario horario, Usuario usuario) {
		Connection conn = new ConnectionFactory().obterConexao();
		boolean cadastrouHorario = false;
		boolean cadastrouMateriaPeriodo = false;
		int resultado = 0;
		String sql = "insert into horario(idusuario, periodo) values(?::uuid,?)";

		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, usuario.getId());
			pstmt.setInt(2, horario.getPeriodo());
			resultado = pstmt.executeUpdate();
			conn.close();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if(resultado == 1) {
			cadastrouHorario = true;
			ArrayList<HashMap> materias = horario.getMaterias();
			HashMap<String, String> materiaPeriodo = new HashMap<>();
			String idhorario = consultarHorarioCadastrado(horario, usuario);
			materiaPeriodo.put("idhorario", idhorario);
			for(HashMap materia : materias) {
				materiaPeriodo.put("dia", String.valueOf(materia.get("dia")));
				materiaPeriodo.put("idmateria",String.valueOf( materia.get("idmateria")));
				cadastrouMateriaPeriodo = cadastrarMateriaPeriodo(materiaPeriodo);
			}	
		} else {
			cadastrouHorario = false;
		}
		return cadastrouHorario && cadastrouMateriaPeriodo;
	}
	
	public boolean editarHorario(Horario horario, Usuario usuario) {
		Connection conn = new ConnectionFactory().obterConexao();
		int resultado = 0;
		String sql = "update horario set periodo = ?::numeric where idhorario = ?::uuid";

		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, materiaPeriodo.get("idmateria"));
			pstmt.setString(2, String.valueOf(materiaPeriodo.get("idhorario")));
			pstmt.setInt(3, Integer.parseInt(materiaPeriodo.get("dia")));
			resultado = pstmt.executeUpdate();
			conn.close();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultado == 1 ? true : false;
	}
	
	public boolean cadastrarMateriaPeriodo(HashMap<String, String> materiaPeriodo) {
		Connection conn = new ConnectionFactory().obterConexao();
		int resultado = 0;
		String sql = "insert into materiaperiodo(idmateria, idhorario, dia) values(?::uuid,?::uuid,?)";

		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, materiaPeriodo.get("idmateria"));
			pstmt.setString(2, String.valueOf(materiaPeriodo.get("idhorario")));
			pstmt.setInt(3, Integer.parseInt(materiaPeriodo.get("dia")));
			resultado = pstmt.executeUpdate();
			conn.close();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultado == 1 ? true : false;
	}

	public boolean consultarHorarioCadastro(Horario horario, Usuario usuario) {

		Connection conn = new ConnectionFactory().obterConexao();
		String sql = "select * from horario where periodo = ? and idusuario = ?::uuid";
		boolean horarioEncontrado = false;
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, horario.getPeriodo());
			pstmt.setString(2, usuario.getId());
			ResultSet rs = pstmt.executeQuery();
			horarioEncontrado = rs.next();
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
				"	COUNT(MTP.ID) AS QNTMATERIAS\r\n" + 
				"FROM \r\n" + 
				"	HORARIO\r\n" + 
				"		INNER JOIN MATERIAPERIODO AS MTP ON HORARIO.ID = MTP.IDHORARIO\r\n" + 
				"		INNER JOIN USUARIO ON HORARIO.IDUSUARIO = USUARIO.ID\r\n" + 
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
	
	public List<HashMap> listarMateriasPorPeriodo(HashMap<String, String> params) {

		Connection conn = new ConnectionFactory().obterConexao();
		String sql = "SELECT\r\n" + 
				"	HORARIO.ID AS IDHORARIO,\r\n" + 
				"	MTP.DIA,\r\n" + 
				"	MTP.IDMATERIA,\r\n" + 
				"	MATERIA.DESCRICAO\r\n" + 
				"FROM\r\n" + 
				"	HORARIO INNER JOIN MATERIAPERIODO AS MTP ON HORARIO.ID = MTP.IDHORARIO\r\n" + 
				"	INNER JOIN MATERIA ON MTP.IDMATERIA = MATERIA.ID\r\n" +
				"WHERE\r\n" + 
				"	HORARIO.ID = ?::uuid\r\n" + 
				"	AND HORARIO.IDUSUARIO = ?::uuid";
		List<HashMap> listaDeMaterias = new ArrayList<HashMap>();
		
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, String.valueOf(params.get("idhorario")));
			pstmt.setString(2, String.valueOf(params.get("idusuario")));
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
	
	public boolean excluirHorario(HashMap horario) {
		Connection conn = new ConnectionFactory().obterConexao();
		int resultado = 0;
		String sql = "DELETE FROM HORARIO WHERE ID = ?::uuid";

		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, String.valueOf(horario.get("idhorario")));
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
				//horarioConsultado.setMateria(mt);
				//horarioConsultado.setUsuario(usuario);
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
			//pstmt.setString(2, horarioConsultado.getUsuario().getId());
			//pstmt.setString(3, horarioConsultado.getMateria().getId());
			resultado = pstmt.executeUpdate();
			conn.close();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultado == 1 ? true : false;
	}
	
}
