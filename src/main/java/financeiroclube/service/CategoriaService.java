package financeiroclube.service;

import java.util.List;

import financeiroclube.entity.Categoria;

public interface CategoriaService extends CrudService<Categoria, Long> {

	public List<Categoria> listarReceitas();

	public List<Categoria> listarDespesas();

}
