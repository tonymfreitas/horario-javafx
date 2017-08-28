package main.java.utils;

import java.util.ArrayList;
import java.util.HashMap;

import main.java.model.horario.Horario;
import main.java.model.horario.HorariosTableProperty;
import main.java.model.usuario.Usuario;

public class SessionController {

	private static Usuario usuario;
	private static HorariosTableProperty properts;
	private static Horario horario;
	private static ArrayList<HashMap> horarios;

	public static Usuario getUsuario() {
		return usuario;
	}

	public static void setUsuario(Usuario usuario) {
		SessionController.usuario = usuario;
	}

	public static HorariosTableProperty getProperts() {
		return properts;
	}

	public static void setProperts(HorariosTableProperty properts) {
		SessionController.properts = properts;
	}

	public static Horario getHorario() {
		return horario;
	}

	public static void setHorario(Horario horario) {
		SessionController.horario = horario;
	}

	public static ArrayList<HashMap> getHorarios() {
		return horarios;
	}

	public static void setHorarios(ArrayList<HashMap> horarios) {
		SessionController.horarios = horarios;
	}
	
}
