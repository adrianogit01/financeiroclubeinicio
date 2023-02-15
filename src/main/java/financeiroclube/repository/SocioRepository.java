package financeiroclube.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import financeiroclube.entity.Socio;

public interface SocioRepository extends PagingAndSortingRepository<Socio, Long> {

	Boolean existsByCpf(String cpf);

	Boolean existsByCpfAndIdSocioNot(String cpf, Long idSocio);
	
	List<Socio> findAll();
	
	Page<Socio> findAllByOrderByNome(Pageable pagina);

}
