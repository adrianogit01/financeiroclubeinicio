package financeiroclube.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import financeiroclube.entity.Categoria;
import financeiroclube.entity.Clube;
import financeiroclube.entity.enums.TipoCategoria;

public interface CategoriaRepository extends PagingAndSortingRepository<Categoria, Long> {

	Boolean existsByOrdemAndClube(String ordem, Clube clube);

	Boolean existsByOrdemAndClubeAndIdCategoriaNot(String ordem, Clube clube, Long idCategoria);

	List<Categoria> findAllByClubeAndTipo(Clube clube, TipoCategoria tipo);

}
