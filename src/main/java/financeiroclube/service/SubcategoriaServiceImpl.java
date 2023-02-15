package financeiroclube.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import financeiroclube.entity.Subcategoria;
import financeiroclube.repository.SubcategoriaRepository;

@Service
@Transactional
public class SubcategoriaServiceImpl implements SubcategoriaService {

	@Autowired
	private SubcategoriaRepository subcategoriaRepository;

	@Autowired
	private CategoriaService categoriaService;

	@Override
	public void salvar(Subcategoria entidade) {
		if (entidade.getIdSubcategoria() == null) {
			subcategoriaRepository.save(entidade);
		}
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Subcategoria ler(Long id) {
		return subcategoriaRepository.findById(id).get();
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Subcategoria> listar() {
		return subcategoriaRepository.findAllByCategoriaPaiInOrderByCategoriaPai_OrdemAscDescricao(categoriaService.listar());
	}

	@Override
	public Page<Subcategoria> listarPagina(Pageable pagina) {
		//Como?
		return null;
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Subcategoria> listarReceitas() {
		return subcategoriaRepository
				.findAllByCategoriaPaiInOrderByCategoriaPai_OrdemAscDescricao(categoriaService.listarReceitas());
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Subcategoria> listarDespesas() {
		return subcategoriaRepository
				.findAllByCategoriaPaiInOrderByCategoriaPai_OrdemAscDescricao(categoriaService.listarDespesas());
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public int contagem() {
		return subcategoriaRepository.countByCategoriaPaiIn(categoriaService.listar());
	}

	@Override
	public void editar(Subcategoria entidade) {
		subcategoriaRepository.save(entidade);

	}

	@Override
	public void excluir(Subcategoria entidade) {
		subcategoriaRepository.delete(entidade);

	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public void validar(Subcategoria entidade, BindingResult validacao) {
		
		if (entidade.getIdSubcategoria() == null) {
			
			if (entidade.getCategoriaPai() != null && subcategoriaRepository
					.existsByDescricaoAndCategoriaPai(entidade.getDescricao(), entidade.getCategoriaPai())) {
				validacao.rejectValue("descricao", "Unique");
			}
		}
		
		else {
			
			if (entidade.getCategoriaPai() != null
					&& subcategoriaRepository.existsByDescricaoAndCategoriaPaiAndIdSubcategoriaNot(entidade.getDescricao(),
							entidade.getCategoriaPai(), entidade.getIdSubcategoria())) {
				validacao.rejectValue("descricao", "Unique");
			}
			
			Subcategoria anterior = ler(entidade.getIdSubcategoria());
			if (anterior.getCategoriaPai().getTipo() != entidade.getCategoriaPai().getTipo()) {
				validacao.rejectValue("categoriaPai", "typeMismatch",
						new Object[] { 0, "não é do mesmo tipo da anterior" }, null);
			}
		}
		
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public void padronizar(Subcategoria entidade) {
		

	}

}
