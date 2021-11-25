package financeiroclube.service;

import financeiroclube.entity.Usuario;

public interface UsuarioService extends CrudService<Usuario, Long> {

	public void salvarFuncionario(Usuario usuario);

	public void salvarSocio(Usuario usuario);

	public void salvarAdmin(Usuario usuario);

	public Usuario ler(String username);

	public Usuario lerLogado();

	//public boolean redefinirSenha(String username);

	//public boolean redefinirSenha(String username, String token, String password);

}
