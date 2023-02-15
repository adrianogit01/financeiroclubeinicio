package financeiroclube.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import financeiroclube.entity.Usuario;
import financeiroclube.repository.UsuarioRepository;

@Service
public class UsuarioServiceImpl implements UsuarioService {
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Override
	public void adicionarUsuario(Usuario usuario) {
		usuario.setSenha(new BCryptPasswordEncoder().encode(usuario.getSenha()));
		usuarioRepository.save(usuario);
	}
	
	@Override
	public List<Usuario> listaUsuarios(){
		return usuarioRepository.findAll();
	}
	
	@Override
	public void removerUsuario(Long id) {
		usuarioRepository.deleteById(id);
	}
	
	@Override
	public void atualizarUsuario(Usuario usuario) {
		usuarioRepository.save(usuario);
	}
	
	@Override
	public void addPUser(Usuario usuario) {
		usuarioRepository.save(usuario);
	}
	
	@Override
	public int ContUser() {
		return (int) usuarioRepository.count();
		
	}
	
	@Override
	public Usuario retornaUser(Long id) {
		return usuarioRepository.getOne(id);
	}	
	
	@Override
	public boolean userExist(long id) {
		return usuarioRepository.existsById(id);
	}
	
	@Override
	public Usuario buscarPorLogin(String login) {
		return usuarioRepository.findByLogin(login);
	}

	/*@Override
	public void salvar(Usuario entidade) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Usuario ler(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Usuario> listar() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Usuario> listarPagina(Pageable pagina) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void editar(Usuario entidade) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void excluir(Usuario entidade) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void validar(Usuario entidade, BindingResult validacao) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void padronizar(Usuario entidade) {
		// TODO Auto-generated method stub
		
	}*/

}
