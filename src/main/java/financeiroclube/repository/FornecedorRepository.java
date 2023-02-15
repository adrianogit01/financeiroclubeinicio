package financeiroclube.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import financeiroclube.entity.Fornecedor;

public interface FornecedorRepository extends PagingAndSortingRepository<Fornecedor, Long> {

	Boolean existsByCnpj(String cnpj);

	Boolean existsByCnpjAndIdFornecedorNot(String cnpj, Long idFornecedor);
	
	List<Fornecedor> findAll();
	
	Page<Fornecedor> findAllByOrderByNome(Pageable pagina);

}
