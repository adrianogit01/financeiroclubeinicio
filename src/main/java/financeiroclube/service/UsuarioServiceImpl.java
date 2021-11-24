package financeiroclube.service;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import financeiroclube.entity.Usuario;
import financeiroclube.entity.enums.Autorizacao;
import financeiroclube.repository.UsuarioRepository;

@Service
@Transactional
public class UsuarioServiceImpl implements UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private EmailService emailService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public void salvar(Usuario usuario) {
		if (usuario.getId() == null) {
			usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
			usuarioRepository.save(usuario);
		}
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Usuario ler(String username) {
		return usuarioRepository.findOneByUsername(username);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Usuario ler(Long id) {
		return usuarioRepository.findById(id).get();
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Usuario lerLogado() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth == null || auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ANONYMOUS"))) {
			return null;
		}
		return usuarioRepository.findOneByUsername(auth.getName());
	
	}

	@Override
	public void editar(Usuario usuario) {
		if (!usuario.getPassword().startsWith("{bcrypt}")) {
			usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
		}
		if (usuario.getAutorizacoes().isEmpty()) {
			usuario.setAutorizacoes(ler(usuario.getId()).getAutorizacoes());
		}
		usuarioRepository.save(usuario);
	}

	@Override
	public void excluir(Usuario usuario) {
		usuarioRepository.delete(usuario);
	}

	@Override
	public void salvarFuncionario(Usuario usuario) {
		usuario.getAutorizacoes().add(Autorizacao.FUNCIONARIO);
		salvar(usuario);
	}

	@Override
	public void salvarSocio(Usuario usuario) {
		usuario.getAutorizacoes().add(Autorizacao.SOCIO);
		salvar(usuario);
	}

	@Override
	public void salvarAdmin(Usuario usuario) {
		usuario.getAutorizacoes().add(Autorizacao.ADMIN);
		salvar(usuario);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public boolean redefinirSenha(String username) {
		Usuario usuario = ler(username);
		if (usuario != null) {
			String para = usuario.getEmail();
			String assunto = "Financeiro Clube - Redefinição de Senha";
			String mensagem = "Acesse o endereço abaixo para redefinir sua senha:\n\nhttp://localhost:8080/conta/redefinir?username="
					+ usuario.getUsername() + "&token=" + getToken(usuario.getPassword())
					+ "\n\nCaso não consiga clicar no link acima, copie-o e cole em seu navegador."
					+ "\n\nPor segurança este link só é válido até o final do dia.";
			emailService.enviarEmail(para, assunto, mensagem);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean redefinirSenha(String username, String token, String password) {
		Usuario usuario = usuarioRepository.findOneByUsername(username);
		if (usuario != null && getToken(usuario.getPassword()).equals(token)) {
			usuario.setPassword(password);
			editar(usuario);
			return true;
		} else {
			return false;
		}
	}

	private String getToken(String texto) {
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo"));
		String d = "" + calendar.get(Calendar.DAY_OF_YEAR);
		String a = "" + (calendar.get(Calendar.YEAR) - 2000);
		String regex = "\\\\|/|\\?|\\.|&|\\$"; 

		return texto.substring(8).replaceAll(regex, d) + a;
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public void validar(Usuario usuario, BindingResult validacao) {
		
		if (usuario.getId() == null) {
			
			if (usuario.getUsername() != null && usuarioRepository.existsByUsername(usuario.getUsername())) {
				validacao.rejectValue("username", "Unique");
			}
		}
		
		else {
			
			if (usuario.getUsername() != null
					&& usuarioRepository.existsByUsernameAndIdNot(usuario.getUsername(), usuario.getId())) {
				validacao.rejectValue("username", "Unique");
			}
		}
	
		if (!usuario.getAtivo()) {
			validacao.rejectValue("ativo", "AssertTrue");
		}
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Usuario> listar() {
		// para o ADMIN
		return null;
	}

	@Override
	public Page<Usuario> listarPagina(Pageable pagina) {
		// para o admin
		return null;
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public void padronizar(Usuario entidade) {
	
	}

}