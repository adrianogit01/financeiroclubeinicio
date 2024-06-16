package financeiroclube.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import financeiroclube.entity.Usuario;

@Repository
public interface UsuarioRepository extends PagingAndSortingRepository <Usuario, Long>{
	Usuario findByLogin(String login);
	
	Usuario findOneByLogin(String login);

	Boolean existsByLogin(String login);

	Boolean existsByLoginAndIdNot(String login, Long id);
	
	Page<Usuario> findAllByOrderByLogin(Pageable pagina);
}
