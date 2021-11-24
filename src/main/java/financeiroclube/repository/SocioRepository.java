package financeiroclube.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import financeiroclube.entity.Clube;
import financeiroclube.entity.Socio;

public interface SocioRepository extends PagingAndSortingRepository<Socio, Long> {

	Boolean existsByCpfAndClube(String cpf, Clube clube);

	Boolean existsByCpfAndClubeAndIdSocioNot(String cpf, Clube clube, Long idSocio);
	
	Page<Socio> findAllByClubeOrderByNome(Clube clube, Pageable pagina);

}
