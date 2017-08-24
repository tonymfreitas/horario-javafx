package main.java.model.materia;

import java.util.List;

import main.java.model.usuario.Usuario;

public class MateriaController {
	
	MateriaDao materiaDao = new MateriaDao();
	
	public boolean cadastrarNovaMateria(Materia materia) {
		return materiaDao.cadastrarNovaMateria(materia);
	}

	public List<Materia> listarMaterias(Usuario usuario) {
		return materiaDao.listarMateriais(usuario);
	}
	
}
