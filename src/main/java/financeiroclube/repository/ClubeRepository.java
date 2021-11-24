package financeiroclube.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import financeiroclube.entity.Clube;

public interface ClubeRepository extends PagingAndSortingRepository<Clube, Long> {

	Boolean existsByCnpj(String cnpj);

	Boolean existsByCnpjAndIdClubeNot(String cnpj, Long idClube);

}
