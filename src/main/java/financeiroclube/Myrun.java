package financeiroclube;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import financeiroclube.entity.Role;
import financeiroclube.entity.Usuario;
import financeiroclube.repository.RoleRepository;
import financeiroclube.repository.UsuarioRepository;
import financeiroclube.service.RoleService;
import financeiroclube.service.UsuarioService;

@Component
public class Myrun implements CommandLineRunner{
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired 
	UsuarioService usuarioService;
	
	@Autowired
	RoleService roleService;

	@Override
	public void run(String... args) throws Exception {
		Usuario user = new Usuario(1,"admin","Usuário Padrão","(00) 00000-0000","admin@admin.com","$2a$10$wo7WLU7ZU9axJP3kzgs6B.Cd61WbsuDwD4Fv4uTG0JUHbkW5llONG", "ADMINISTRADOR");
		boolean existe = usuarioService.userExist(1);
		boolean roleAdm = roleService.roleExist("ROLE_ADMIN");
		boolean roleUser = roleService.roleExist("ROLE_USER");
		if(existe == false && roleAdm == false && roleUser == false) {
			roleRepository.save(new Role("ROLE_ADMIN"));
			roleRepository.save(new Role("ROLE_USER"));
			Role ADM = new Role("ROLE_ADMIN");
			List<Usuario> usuarios = new ArrayList<Usuario>();
			List<Role> roles = new ArrayList<Role>();
			usuarios.add(user);
			roles.add(ADM);
			ADM.setUsuarios(usuarios);
			user.setRoles(roles);
			usuarioService.addPUser(user);
			
			

		}
		
	
	}

}

