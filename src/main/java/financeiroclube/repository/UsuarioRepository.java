package financeiroclube.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import financeiroclube.entity.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository <Usuario, Long>{
	Usuario findByLogin(String login);
}
