package financeiroclube.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import financeiroclube.entity.Usuario;
import financeiroclube.repository.UsuarioRepository;

@Service
public class UsuarioServiceImpl implements UsuarioService {
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
		
	@Override
	public void salvar(Usuario entidade) {
		entidade.setSenha(new BCryptPasswordEncoder().encode(entidade.getSenha()));
		usuarioRepository.save(entidade);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Usuario ler(Long id) {
		return usuarioRepository.findById(id).get();
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Usuario> listar() {
			return (List<Usuario>) usuarioRepository.findAll();
	}

	@Override
	public Page<Usuario> listarPagina(Pageable pagina) {
			return usuarioRepository.findAllByOrderByLogin(pagina);
	}

	@Override
	public void editar(Usuario entidade) {
		usuarioRepository.save(entidade);
	}

	@Override
	public void excluir(Usuario entidade) {
		usuarioRepository.delete(entidade);
	}

	@Override
	public void addPUser(Usuario entidade) {
		usuarioRepository.save(entidade);
	}

	
	@Override
	public boolean userExist(long id) {
		return usuarioRepository.existsById(id);
	}
	
	@Override
	public Usuario buscarPorLogin(String login) {
		return usuarioRepository.findByLogin(login);
	}

		@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public void validar(Usuario usuario, BindingResult validacao) {
		
		if (usuario.getId() == null) {
			
			if (usuario.getLogin() != null && usuarioRepository.existsByLogin(usuario.getLogin())) {
				validacao.rejectValue("login", "Unique");
			}
		}
		
		else {
			
			if (usuario.getLogin() != null
					&& usuarioRepository.existsByLoginAndIdNot(usuario.getLogin(), usuario.getId())) {
				validacao.rejectValue("login", "Unique");
			}
		}
	
		/*if (!usuario.getAtivo()) {
			validacao.rejectValue("ativo", "AssertTrue");
		}*/
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public void padronizar(Usuario entidade) {
		
	}


}
