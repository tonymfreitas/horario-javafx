package main.java.utils;

import main.java.model.usuario.Usuario;

public class SessionController {

	private static Usuario usuario;

	public static Usuario getUsuario() {
		return usuario;
	}

	public static void setUsuario(Usuario usuario) {
		SessionController.usuario = usuario;
	}
	
}
