package main.java.model.materia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javafx.collections.ObservableList;
import main.java.model.ConnectionFactory;
import main.java.model.aula.Aula;
import main.java.model.horario.Horario;
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

	public boolean excluirMateria(Materia materia) {
		Connection conn = new ConnectionFactory().obterConexao();
		int resultado = 0;
		String sql = "delete from materia where id = ?::uuid and idusuario = ?::uuid";

		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, materia.getId());
			pstmt.setString(2, materia.getUsuario().getId());
			resultado = pstmt.executeUpdate();
			conn.close();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultado == 1 ? true : false;
	}

	public boolean editarMateria(Materia materia) {
		Connection conn = new ConnectionFactory().obterConexao();
		int resultado = 0;
		String sql = "update materia set descricao = ? where id = ?::uuid and idusuario = ?::uuid";

		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, materia.getDescricao());
			pstmt.setString(2, materia.getId());
			pstmt.setString(3, materia.getUsuario().getId());
			resultado = pstmt.executeUpdate();
			conn.close();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultado == 1 ? true : false;
	}

	public String consultarHorario(Horario horario, Usuario usuario) {
		Connection conn = new ConnectionFactory().obterConexao();
		String sql = "SELECT DISTINCT HORARIO.ID AS IDHORARIO " + "" + "FROM "
				+ "HORARIO INNER JOIN MATERIAPERIODO AS MTP ON HORARIO.ID = MTP.IDHORARIO " + "WHERE "
				+ "HORARIO.PERIODO = ?::numeric " + "AND HORARIO.IDUSUARIO = ?::uuid";
		String idhorario = "";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, horario.getPeriodo());
			pstmt.setString(2, usuario.getId());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				idhorario = rs.getString("idhorario");
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		return idhorario;
	}

	public boolean cadastrarAula(HashMap materia, Usuario usuario) {
		Connection conn = new ConnectionFactory().obterConexao();
		int resultado = 0;
		String sql = "insert into aula(aula1, aula2, aula3, aula4, aula5, aula6, aula7, aula8, aula9, aula10, idmateria, idusuario, idhorario) "
				+ "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?::uuid, ?::uuid, ?::uuid)";

		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setBoolean(1, false);
			pstmt.setBoolean(2, false);
			pstmt.setBoolean(3, false);
			pstmt.setBoolean(4, false);
			pstmt.setBoolean(5, false);
			pstmt.setBoolean(6, false);
			pstmt.setBoolean(7, false);
			pstmt.setBoolean(8, false);
			pstmt.setBoolean(9, false);
			pstmt.setBoolean(10, false);
			pstmt.setString(11, (String) materia.get("idmateria"));
			pstmt.setString(12, usuario.getId());
			pstmt.setString(13, (String) materia.get("idhorario"));
			resultado = pstmt.executeUpdate();
			conn.close();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultado == 1 ? true : false;
	}

	public boolean cadastrarAulaMateria(Horario horario, Usuario usuario) {

		String idhorario = consultarHorario(horario, usuario);
		boolean cadastrouAula = false;

		ArrayList<HashMap> materias = horario.getMaterias();
		for (HashMap materia : materias) {
			materia.put("idhorario", idhorario);
			cadastrouAula = cadastrarAula(materia, usuario);
		}

		return cadastrouAula;
	}

	public List<Materia> listarMateriais(Usuario usuario) {

		Connection conn = new ConnectionFactory().obterConexao();
		List<Materia> materias = new ArrayList<>();
		String sql = "select * from materia where idusuario = ?::uuid";

		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, usuario.getId());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
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

	public List<HashMap> listarMateriasPorHorario(Usuario usuario, Horario horario) {

		Connection conn = new ConnectionFactory().obterConexao();
		List<HashMap> materias = new ArrayList<>();
		String sql = "SELECT MATERIA.DESCRICAO, MATERIAPERIODO.DIA FROM HORARIO "
				+ "INNER JOIN MATERIAPERIODO ON HORARIO.ID = MATERIAPERIODO.IDHORARIO "
				+ "INNER JOIN MATERIA ON MATERIAPERIODO.IDMATERIA = MATERIA.ID WHERE HORARIO.ID = ?::uuid "
				+ "AND HORARIO.IDUSUARIO = ?::uuid";

		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, horario.getId());
			pstmt.setString(2, usuario.getId());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				HashMap<String, String> materia = new HashMap<>();
				Materia materia = new Materia();
				materia.setDescricao(rs.getString("descricao"));
				materia.ge .setId(rs.getString("dia"));
				materias.add(materia);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		return materias;
	}

	public Materia listarAulasMateria(Horario horario) {

		Connection conn = new ConnectionFactory().obterConexao();
		Materia mt = new Materia();
		String sql = "SELECT AULA.ID AS IDAULA,\r\n" + "	AULA.AULA1,\r\n" + "	AULA.AULA2,\r\n"
				+ "	AULA.AULA3,\r\n" + "	AULA.AULA4,\r\n" + "	AULA.AULA5,\r\n" + "	AULA.AULA6,\r\n"
				+ "	AULA.AULA7,\r\n" + "	AULA.AULA8,\r\n" + "	AULA.AULA9,\r\n" + "	AULA.AULA10\r\n"
				+ "FROM \r\n" + "	AULA INNER JOIN HORARIO ON AULA.IDMATERIA = HORARIO.IDMATERIA\r\n" + "WHERE\r\n"
				+ "	AULA.IDUSUARIO = ?::uuid\r\n" + "	AND AULA.IDMATERIA = ?::uuid\r\n"
				+ "	AND HORARIO.ID = ?::uuid\r\n" + "	AND HORARIO.DIA = ?::numeric";

		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, horario.getMateria().getUsuario().getId());
			pstmt.setString(2, horario.getMateria().getId());
			pstmt.setString(3, horario.getId());
			pstmt.setInt(4, horario.getDia());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				ArrayList<Aula> aulas = new ArrayList<Aula>();
				aulas.add(new Aula(1, rs.getBoolean("aula1")));
				aulas.add(new Aula(2, rs.getBoolean("aula2")));
				aulas.add(new Aula(3, rs.getBoolean("aula3")));
				aulas.add(new Aula(4, rs.getBoolean("aula4")));
				aulas.add(new Aula(5, rs.getBoolean("aula5")));
				aulas.add(new Aula(6, rs.getBoolean("aula6")));
				aulas.add(new Aula(7, rs.getBoolean("aula7")));
				aulas.add(new Aula(8, rs.getBoolean("aula8")));
				aulas.add(new Aula(9, rs.getBoolean("aula9")));
				aulas.add(new Aula(10, rs.getBoolean("aula10")));
				mt.setAulas(aulas);
				mt.setIdAulas(rs.getString("idaula"));
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		return mt;
	}

	public boolean marcarAulaConcluida(Materia materia) {
		Connection conn = new ConnectionFactory().obterConexao();
		int resultado = 0;
		String sql = "UPDATE \r\n" + "	AULA\r\n" + "SET\r\n" + "	AULA1 = ?,\r\n" + "	AULA2 = ?,\r\n"
				+ "	AULA3 = ?,\r\n" + "	AULA4 = ?,\r\n" + "	AULA5 = ?,\r\n" + "	AULA6 = ?,\r\n" + "	AULA7 = ?,\r\n"
				+ "	AULA8 = ?,\r\n" + "	AULA9 = ?,\r\n" + "	AULA10 = ?\r\n" + "WHERE\r\n" + "	IDUSUARIO = ?::uuid\r\n"
				+ "	AND ID = ?::uuid";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setBoolean(1, materia.getAulas().get(0).getConcluido());
			pstmt.setBoolean(2, materia.getAulas().get(1).getConcluido());
			pstmt.setBoolean(3, materia.getAulas().get(2).getConcluido());
			pstmt.setBoolean(4, materia.getAulas().get(3).getConcluido());
			pstmt.setBoolean(5, materia.getAulas().get(4).getConcluido());
			pstmt.setBoolean(6, materia.getAulas().get(5).getConcluido());
			pstmt.setBoolean(7, materia.getAulas().get(6).getConcluido());
			pstmt.setBoolean(8, materia.getAulas().get(7).getConcluido());
			pstmt.setBoolean(9, materia.getAulas().get(8).getConcluido());
			pstmt.setBoolean(10, materia.getAulas().get(9).getConcluido());
			pstmt.setString(11, materia.getUsuario().getId());
			pstmt.setString(12, materia.getIdAulas());
			resultado = pstmt.executeUpdate();
			conn.close();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultado == 1 ? true : false;
	}

}
