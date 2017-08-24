package main.java.model.horario;

public class HorarioController {

	HorarioDao horarioDao = new HorarioDao();
	
	public boolean cadastrarHorario(Horario horario) {
		return horarioDao.cadastrarHorario(horario);
	}
	
	public boolean consultarHorarioCadastro(Horario horario) {
		return horarioDao.consultarHorarioCadastro(horario);
	}
	
}
