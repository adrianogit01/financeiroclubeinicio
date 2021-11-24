package financeiroclube.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import financeiroclube.entity.Clube;
import financeiroclube.entity.Fornecedor;

public interface FornecedorRepository extends PagingAndSortingRepository<Fornecedor, Long> {

	Boolean existsByCnpjAndClube(String cnpj, Clube clube);

	Boolean existsByCnpjAndClubeAndIdFornecedorNot(String cnpj, Clube clube, Long idFornecedor);
	
	Page<Fornecedor> findAllByClubeOrderByNome(Clube clube, Pageable pagina);

}
