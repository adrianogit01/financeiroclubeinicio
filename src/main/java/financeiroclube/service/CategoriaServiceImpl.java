package financeiroclube.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import financeiroclube.entity.Categoria;
import financeiroclube.entity.enums.TipoCategoria;
import financeiroclube.repository.CategoriaRepository;

@Service
@Transactional
public class CategoriaServiceImpl implements CategoriaService {

	@Autowired
	private CategoriaRepository categoriaRepository;

	@Autowired
	private UsuarioService usuarioService;

	@Override
	public void salvar(Categoria entidade) {
		if (entidade.getIdCategoria() == null) {
			padronizar(entidade);
			categoriaRepository.save(entidade);
		}
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Categoria ler(Long id) {
		return categoriaRepository.findById(id).get();
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Categoria> listar() {
		//metodo novo fiz lá em repository
		return categoriaRepository.findAll();
		
	}

	@Override
	public Page<Categoria> listarPagina(Pageable pagina) {
		// Como?
		return null;
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Categoria> listarReceitas() {
		return categoriaRepository.findAllByTipo(TipoCategoria.R);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Categoria> listarDespesas() {
		return categoriaRepository.findAllByTipo(TipoCategoria.D);
	}

	@Override
	public void editar(Categoria entidade) {
		padronizar(entidade);
		categoriaRepository.save(entidade);
	}

	@Override
	public void excluir(Categoria entidade) {
		categoriaRepository.delete(entidade);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public void validar(Categoria entidade, BindingResult validacao) {
		
		if (entidade.getIdCategoria() == null) {
			
			if (categoriaRepository.existsByOrdem(entidade.getOrdem())) {
				validacao.rejectValue("ordem", "Unique");
			}
		}
	
		else {
			
			if (categoriaRepository.existsByOrdemAndIdCategoriaNot(entidade.getOrdem(),
					entidade.getIdCategoria())) {
				validacao.rejectValue("ordem", "Unique");
			}
			
			Categoria anterior = ler(entidade.getIdCategoria());
			if (entidade.getTipo() != anterior.getTipo()) {
				validacao.rejectValue("tipo", "Final");
			}
			
			if ((entidade.getCategoriaPai() != null) && (entidade.getCategoriaPai().equals(entidade)
					|| entidade.getCategoriaPai().getOrdem().startsWith(ler(entidade.getIdCategoria()).getOrdem()))) {
				validacao.rejectValue("categoriaPai", "typeMismatch", new Object[] { 0, "é igual ou inferior a esta" },
						null);
			}
		}
		
		if (entidade.getCategoriaPai() != null) {
			
			if (entidade.getCategoriaPai().getNivel() >= Categoria.MAXIMO) {
				validacao.rejectValue("categoriaPai", "Max", new Object[] { 0, Categoria.MAXIMO }, null);
			}
			
			if (entidade.getCategoriaPai().getTipo() != entidade.getTipo()) {
				validacao.rejectValue("tipo", "typeMismatch");
			}
			
			if (!entidade.getOrdem().startsWith(entidade.getCategoriaPai().getOrdem())) {
				validacao.rejectValue("ordem", "typeMismatch");
			}
		}
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public void padronizar(Categoria entidade) {
		Categoria categoriaPai = entidade.getCategoriaPai();
		if (categoriaPai != null) {
			entidade.setNivel(categoriaPai.getNivel() + 1);
		} else {
			entidade.setNivel(1);
		}

	}

}
