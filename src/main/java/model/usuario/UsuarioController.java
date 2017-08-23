package main.java.model.usuario;

public class UsuarioController {

	public UsuarioDao usuarioDao = new UsuarioDao();
	
	public boolean cadastrarUsuario(Usuario usuario) {
		return usuarioDao.cadastrarUsuario(usuario);
	}
	
	public Usuario autenticar(Usuario usuario) {
		return usuarioDao.autenticarUsuario(usuario);
	}
	
}
