package financeiroclube.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import financeiroclube.entity.Categoria;
import financeiroclube.entity.enums.TipoCategoria;

public interface CategoriaRepository extends PagingAndSortingRepository<Categoria, Long> {

	Boolean existsByOrdem(String ordem);

	Boolean existsByOrdemAndIdCategoriaNot(String ordem, Long idCategoria);

	List<Categoria> findAll();
	
	List<Categoria> findAllByTipo(TipoCategoria tipo);

}
