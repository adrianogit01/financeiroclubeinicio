package financeiroclube.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import financeiroclube.entity.Categoria;
import financeiroclube.entity.Subcategoria;

public interface SubcategoriaRepository extends PagingAndSortingRepository<Subcategoria, Long> {

	List<Subcategoria> findAllByCategoriaPaiInOrderByCategoriaPai_OrdemAscDescricao(Collection<Categoria> categoriaPai);

	int countByCategoriaPaiIn(Collection<Categoria> categoriaPai);

	Boolean existsByDescricaoAndCategoriaPai(String descricao, Categoria categoriaPai);

	Boolean existsByDescricaoAndCategoriaPaiAndIdSubcategoriaNot(String descricao, Categoria categoriaPai,
			Long idSubcategoria);

}
