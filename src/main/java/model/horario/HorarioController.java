package main.java.model.horario;

import java.util.HashMap;
import java.util.List;

import main.java.model.usuario.Usuario;

public class HorarioController {

	HorarioDao horarioDao = new HorarioDao();
	
	public boolean cadastrarHorario(Horario horario, Usuario usuario) {
		return horarioDao.cadastrarHorario(horario, usuario);
	}
	
	public boolean editarHorario(Horario horario, Usuario usuario) {
		return horarioDao.editarHorario(horario, usuario);
	}
	
	public boolean consultarHorarioCadastro(Horario horario, Usuario usuario) {
		return horarioDao.consultarHorarioCadastro(horario, usuario);
	}
	
	public List<HashMap> consultarHorariosCadastrados(Usuario usuario) {
		return horarioDao.consultarHorariosCadastros(usuario);
	}
	
	public List<HashMap> listarMateriasPorPeriodo(HashMap<String, String> params) { 
		return horarioDao.listarMateriasPorPeriodo(params);
	}
	
	public boolean excluirHorario(HashMap horario) {
		return horarioDao.excluirHorario(horario);
	}

	
}

