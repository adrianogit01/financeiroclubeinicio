package financeiroclube.service;

import java.util.List;

import financeiroclube.entity.Subcategoria;

public interface SubcategoriaService extends CrudService<Subcategoria, Long> {

	public int contagem();

	public List<Subcategoria> listarReceitas();

	public List<Subcategoria> listarDespesas();

}
