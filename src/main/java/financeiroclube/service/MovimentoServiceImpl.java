package financeiroclube.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import financeiroclube.entity.Categoria;
import financeiroclube.entity.Cofre;
import financeiroclube.entity.Movimento;
import financeiroclube.entity.Periodo;
import financeiroclube.entity.Subcategoria;
import financeiroclube.entity.enums.TipoCategoria;
import financeiroclube.repository.MovimentoRepository;

@Service
@Transactional
public class MovimentoServiceImpl implements MovimentoService {

	@Autowired
	private MovimentoRepository movimentoRepository;

	@Autowired
	private CofreService cofreService;

	@Autowired
	private PeriodoService periodoService;

	@Override
	public void salvar(Movimento entidade) {
		if (entidade.getIdMovimento() == null) {
			padronizar(entidade);
			List<Movimento> listaSalvar = new ArrayList<>();
		
			if (entidade instanceof Movimento) {
				((Movimento) entidade).setPeriodo(periodoService.ler(entidade.getData()));
				if (((Movimento) entidade).getSubcategoria().getCategoriaPai().getTipo().equals(TipoCategoria.D)) {
					entidade.setReducao(Boolean.TRUE);
					cofreService.decreaseSaldo(entidade.getCofre(), entidade.getValor());
				} else {
					entidade.setReducao(Boolean.FALSE);
					cofreService.increaseSaldo(entidade.getCofre(), entidade.getValor());
				}
			} 
			listaSalvar.add(entidade);
			movimentoRepository.saveAll(listaSalvar);
		}
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Movimento ler(Long id) {
		return movimentoRepository.findById(id).get();
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Movimento> listar() {
		return movimentoRepository.findAllByCofreInOrderByDataDesc(cofreService.listar());
	}

	@Override
	public Page<Movimento> listarPagina(Pageable pagina) {
		return movimentoRepository.findAllByCofreInOrderByDataDesc(cofreService.listar(), pagina);
	}

	@Override
	public void editar(Movimento entidade) {
		padronizar(entidade);
		List<Movimento> listaSalvar = new ArrayList<>();
		if (entidade instanceof Movimento) {
			((Movimento) entidade).setPeriodo(periodoService.ler(entidade.getData()));
			if (((Movimento) entidade).getSubcategoria().getCategoriaPai().getTipo().equals(TipoCategoria.D)) {
				entidade.setReducao(Boolean.TRUE);
			} else {
				entidade.setReducao(Boolean.FALSE);
			}
		}
		listaSalvar.add(entidade);
		movimentoRepository.saveAll(listaSalvar);
	}

	@Override
	public void excluir(Movimento entidade) {
		List<Movimento> listaDeletar = new ArrayList<>();
		if (entidade instanceof Movimento) {
			((Movimento) entidade).setPeriodo(periodoService.ler(entidade.getData()));
			if (((Movimento) entidade).getSubcategoria().getCategoriaPai().getTipo().equals(TipoCategoria.D)) {
				entidade.setReducao(Boolean.TRUE);
				cofreService.increaseSaldo(entidade.getCofre(), entidade.getValor());
			} else {
				entidade.setReducao(Boolean.FALSE);
				cofreService.decreaseSaldo(entidade.getCofre(), entidade.getValor());
			}
		}		  	
		listaDeletar.add(entidade);
		movimentoRepository.deleteAll(listaDeletar);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public void validar(Movimento entidade, BindingResult validacao) {
		
		if (entidade.getData() != null && entidade instanceof Movimento) {
			if (!periodoService.haPeriodo(entidade.getData())) {
				validacao.rejectValue("data", "Inexistente");
			} else if (periodoService.ler(entidade.getData()).getEncerrado()) {
				validacao.rejectValue("data", "Final");
			}
		}
	
	
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public void padronizar(Movimento entidade) {
		if (entidade.getData() == null) {
			entidade.setData(LocalDate.now());
		}
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public BigDecimal somaMovimentosEntre(Collection<Cofre> cofres, LocalDate inicio, LocalDate fim, Boolean reducao) {
		if (!cofres.isEmpty()) {
			return movimentoRepository.sumValorByCofreInAndDataBetweenAndReducao(cofres, inicio, fim, reducao);
		} else {
			return BigDecimal.ZERO.setScale(2);
		}
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public BigDecimal somaMovimentosEntre(Collection<Cofre> cofres, LocalDate inicio, LocalDate fim,
			Subcategoria subcategoria) {
		if (!cofres.isEmpty()) {
			return movimentoRepository.sumValorByCofreInAndDataBetweenAndSubcategoria(cofres, inicio, fim, subcategoria);
		} else {
			return BigDecimal.ZERO.setScale(2);
		}
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public BigDecimal somaMovimentosDesde(Collection<Cofre> cofres, LocalDate inicio, Boolean reducao) {
		if (!cofres.isEmpty()) {
			return movimentoRepository.sumValorByCofreInAndDataGreaterThanEqualAndReducao(cofres, inicio, reducao);
		} else {
			return BigDecimal.ZERO.setScale(2);
		}
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Movimento> listarMovimentosEntre(Collection<Cofre> cofres, LocalDate inicio, LocalDate fim) {
		if (!cofres.isEmpty()) {
			return movimentoRepository.findAllByCofreInAndDataBetweenOrderByDataAsc(cofres, inicio, fim);
		}
		return new ArrayList<>();
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public BigDecimal somaMovimentosPeriodo(Collection<Cofre> cofres, Periodo periodo, Subcategoria subcategoria) {
		if (!cofres.isEmpty() && periodo != null && subcategoria != null) {
			return movimentoRepository.sumValorByCofreInAndPeriodoAndSubcategoria(cofres, periodo, subcategoria);
		} else {
			return BigDecimal.ZERO.setScale(2);
		}
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public BigDecimal somaMovimentosPeriodo(Collection<Cofre> cofres, Periodo periodo, Categoria categoria) {
		if (!cofres.isEmpty() && periodo != null && categoria != null) {
			return movimentoRepository.sumValorByCofreInAndPeriodoAndSubcategoria_CategoriaPai_OrdemStartingWith(cofres,
					periodo, categoria.getOrdem());
		} else {
			return BigDecimal.ZERO.setScale(2);
		}
	}

}
