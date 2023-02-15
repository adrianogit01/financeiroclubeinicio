package financeiroclube.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import financeiroclube.entity.Usuario;
import financeiroclube.repository.UsuarioRepository;


public interface UsuarioService /*extends CrudService<Usuario, Long>*/{
	
	public void adicionarUsuario(Usuario usuario);
	
	public List<Usuario> listaUsuarios();
	
	public void removerUsuario(Long id);
	
	public void atualizarUsuario(Usuario usuario);
	
	public void addPUser(Usuario usuario);
	
	public int ContUser();
	
	public Usuario retornaUser(Long id);
	
	public boolean userExist(long id);
	
	public Usuario buscarPorLogin(String login);

}
