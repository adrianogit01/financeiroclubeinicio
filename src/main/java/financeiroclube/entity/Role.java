package financeiroclube.entity;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import org.springframework.security.core.GrantedAuthority;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Role implements GrantedAuthority{

	private static final long serialVersionUID = 1L;


	@Id
	private String nomeRole;
	
	public Role() {
		
	}
	
	public Role(String nomeRole) {
		this.nomeRole = nomeRole;
	}

	@ManyToMany(mappedBy = "roles") 
	@JsonBackReference
	private List<Usuario>usuarios;
	
	
	@Override
	public String getAuthority() {
		return this.nomeRole;
	}

	public String getNomeRole() {
		return nomeRole;
	}

	public void setNomeRole(String nomeRole) {
		this.nomeRole = nomeRole;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}
	
	
}
