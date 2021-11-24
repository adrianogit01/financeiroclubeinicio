package financeiroclube.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import financeiroclube.entity.Clube;
import financeiroclube.entity.Periodo;
import financeiroclube.repository.PeriodoRepository;

@Service
@Transactional
public class PeriodoServiceImpl implements PeriodoService {

	@Autowired
	private PeriodoRepository periodoRepository;

	@Autowired
	private UsuarioService usuarioService;

	@Override
	public void salvar(Periodo entidade) {
		if (entidade.getIdPeriodo() == null) {
			padronizar(entidade);
			periodoRepository.save(entidade);
		}
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Periodo ler(Long id) {
		return periodoRepository.findById(id).get();
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Periodo> listar() {
		Clube clube = usuarioService.lerLogado().getClube();
		if (clube == null) {
			return new ArrayList<>();
		}
		return clube.getPeriodos();
	}

	@Override
	public Page<Periodo> listarPagina(Pageable pagina) {
		Clube clube = usuarioService.lerLogado().getClube();
		if (clube == null) {
			return Page.empty(pagina);
		}
		return periodoRepository.findAllByClubeOrderByInicioDesc(clube, pagina);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public boolean haPeriodo(LocalDate data) {
		return periodoRepository.existsByClubeAndInicioLessThanEqualAndFimGreaterThanEqual(
				usuarioService.lerLogado().getClube(), data, data);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Periodo ler(LocalDate data) {
		return periodoRepository.findOneByClubeAndInicioLessThanEqualAndFimGreaterThanEqual(
				usuarioService.lerLogado().getClube(), data, data);
	}

	@Override
	public void editar(Periodo entidade) {
		padronizar(entidade);
		periodoRepository.save(entidade);
	}

	@Override
	public void excluir(Periodo entidade) {
		periodoRepository.delete(entidade);

	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public void validar(Periodo entidade, BindingResult validacao) {
		
		if (entidade.getIdPeriodo() == null) {
			if (entidade.getInicio() != null && entidade.getFim() != null) {
				
				if (periodoRepository.existsByClubeAndInicioAfterAndFimBefore(usuarioService.lerLogado().getClube(),
						entidade.getInicio(), entidade.getFim())) {
					validacao.rejectValue("inicio", "Conflito");
					validacao.rejectValue("fim", "Conflito");
				} else {
					if (periodoRepository.existsByClubeAndInicioLessThanEqualAndFimGreaterThanEqual(
							usuarioService.lerLogado().getClube(), entidade.getInicio(), entidade.getInicio())) {
						validacao.rejectValue("inicio", "Unique");
					}
					if (periodoRepository.existsByClubeAndInicioLessThanEqualAndFimGreaterThanEqual(
							usuarioService.lerLogado().getClube(), entidade.getFim(), entidade.getFim())) {
						validacao.rejectValue("fim", "Unique");
					}
				}
			}
		}
		
		else {
			if (entidade.getInicio() != null && entidade.getFim() != null) {
				
				if (periodoRepository.existsByClubeAndInicioAfterAndFimBeforeAndIdPeriodoNot(
						usuarioService.lerLogado().getClube(), entidade.getInicio(), entidade.getFim(),
						entidade.getIdPeriodo())) {
					validacao.rejectValue("inicio", "Conflito");
					validacao.rejectValue("fim", "Conflito");
				} else {
					if (periodoRepository.existsByClubeAndInicioLessThanEqualAndFimGreaterThanEqualAndIdPeriodoNot(
							usuarioService.lerLogado().getClube(), entidade.getInicio(), entidade.getInicio(),
							entidade.getIdPeriodo())) {
						validacao.rejectValue("inicio", "Unique");
					}
					if (periodoRepository.existsByClubeAndInicioLessThanEqualAndFimGreaterThanEqualAndIdPeriodoNot(
							usuarioService.lerLogado().getClube(), entidade.getFim(), entidade.getFim(),
							entidade.getIdPeriodo())) {
						validacao.rejectValue("fim", "Unique");
					}
				}
			}
		}
		
		if (entidade.getInicio() != null && entidade.getFim() != null
				&& entidade.getFim().isBefore(entidade.getInicio())) {
			validacao.rejectValue("fim", "typeMismatch");
		}
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public void padronizar(Periodo entidade) {
		if (entidade.getClube() == null) {
			entidade.setClube(usuarioService.lerLogado().getClube());
		}
		if (entidade.getEncerrado() == null) {
			entidade.setEncerrado(Boolean.FALSE);
		}
	}

}
