package financeiroclube.service;

import java.math.BigDecimal;
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
import financeiroclube.entity.Cofre;
import financeiroclube.repository.CofreRepository;

@Service
@Transactional
public class CofreServiceImpl implements CofreService {

	@Autowired
	private CofreRepository cofreRepository;

	@Autowired
	private UsuarioService usuarioService;

	@Override
	public void salvar(Cofre entidade) {
		if (entidade.getIdCofre() == null) {
			padronizar(entidade);
			
			entidade.setSaldoAtual(entidade.getSaldoInicial());
			cofreRepository.save(entidade);
		}
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Cofre ler(Long id) {
		return cofreRepository.findById(id).get();
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Cofre> listar() {
		Clube clube = usuarioService.lerLogado().getClube();
		if (clube == null) {
			return new ArrayList<>();
		}
		return clube.getCofres();
	}

	@Override
	public Page<Cofre> listarPagina(Pageable pagina) {
		Clube clube = usuarioService.lerLogado().getClube();
		if (clube == null) {
			return Page.empty(pagina);
		}
		return cofreRepository.findAllByClubeOrderBySiglaAsc(clube, pagina);
	}

	@Override
	public void editar(Cofre entidade) {
		padronizar(entidade);
		
		Cofre antiga = ler(entidade.getIdCofre());
		entidade.setSaldoAtual(
				antiga.getSaldoAtual().subtract(antiga.getSaldoInicial()).add(entidade.getSaldoInicial()));
		cofreRepository.save(entidade);
	}

	@Override
	public void excluir(Cofre entidade) {
		cofreRepository.delete(entidade);

	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public void validar(Cofre entidade, BindingResult validacao) {
		
		if (entidade.getIdCofre() == null) {
			
			if (cofreRepository.existsBySiglaAndClube(entidade.getSigla(), usuarioService.lerLogado().getClube())) {
				validacao.rejectValue("sigla", "Unique");
			}
		}
	
		else {
		
			if (cofreRepository.existsBySiglaAndClubeAndIdCofreNot(entidade.getSigla(),
					usuarioService.lerLogado().getClube(), entidade.getIdCofre())) {
				validacao.rejectValue("sigla", "Unique");
			}
		}
		

	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public void padronizar(Cofre entidade) {
		if (entidade.getClube() == null) {
			entidade.setClube(usuarioService.lerLogado().getClube());
		}
		if (entidade.getSaldoInicial() == null) {
			entidade.setSaldoInicial(BigDecimal.ZERO);
		}
		if (entidade.getSaldoAtual() == null) {
			entidade.setSaldoAtual(BigDecimal.ZERO);
		}
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public BigDecimal saldoAtual() {
		Clube clube = usuarioService.lerLogado().getClube();
		if (clube == null || clube.getCofres().isEmpty()) {
			return BigDecimal.ZERO.setScale(2);
		} else {
			return cofreRepository.sumSaldoAtualByClube(clube);
		}
	}

}
