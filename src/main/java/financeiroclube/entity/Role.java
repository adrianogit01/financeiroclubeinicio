package financeiroclube.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority, Serializable, Comparable<Role>{

	private static final long serialVersionUID = 1L;


	@Id
	//@Column(name = "nome_role")
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
	
	
	
	@Override
	public String toString() {
		return "Role [nomeRole=" + nomeRole + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(nomeRole);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Role other = (Role) obj;
		return Objects.equals(nomeRole, other.nomeRole);
	}

	@Override
	public int compareTo(Role arg0) {
		return this.toString().compareTo(arg0.toString());
	}

		
	
	
	
}