package main.java.model.materia;

import java.util.List;

import main.java.model.horario.Horario;
import main.java.model.usuario.Usuario;

public class MateriaController {
	
	MateriaDao materiaDao = new MateriaDao();
	
	public boolean cadastrarNovaMateria(Materia materia) {
		return materiaDao.cadastrarNovaMateria(materia);
	}

	public List<Materia> listarMaterias(Usuario usuario) {
		return materiaDao.listarMateriais(usuario);
	}
	
	public List<Materia> listarMaterias(Usuario usuario, Horario horario) {
		return materiaDao.listarMateriasPorHorario(usuario, horario);
	}
	
	public boolean cadastrarAulaMateria(Horario horario, Usuario usuario) {
		return materiaDao.cadastrarAulaMateria(horario, usuario);
	}
	
	public Materia listarAulasMateria(Horario horario) { 
		return materiaDao.listarAulasMateria(horario);
	}
	
	public boolean marcarAulaConcluida(Materia materia) {
		return materiaDao.marcarAulaConcluida(materia);
	}
	
	public boolean desmarcarAulaConcluida(Materia materia) {
		return materiaDao.marcarAulaConcluida(materia);
	}
	
	public boolean excluirMateria(Materia materia) {
		return materiaDao.excluirMateria(materia);
	}
	
	public boolean editarMateria(Materia materia) {
		return materiaDao.editarMateria(materia);
	}
	
}
