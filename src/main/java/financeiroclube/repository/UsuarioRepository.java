package financeiroclube.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import financeiroclube.entity.Usuario;

public interface UsuarioRepository extends PagingAndSortingRepository<Usuario, Long> {

	Usuario findOneByUsername(String username);

	Boolean existsByUsername(String username);

	Boolean existsByUsernameAndIdNot(String username, Long id);

}
