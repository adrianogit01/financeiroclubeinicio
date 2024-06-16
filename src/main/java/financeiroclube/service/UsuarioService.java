package financeiroclube.service;


import financeiroclube.entity.Usuario;



public interface UsuarioService extends CrudService<Usuario, Long>{
	
	public void addPUser(Usuario usuario);
	
	public boolean userExist(long id);
	
	public Usuario buscarPorLogin(String login);
	
	
}
