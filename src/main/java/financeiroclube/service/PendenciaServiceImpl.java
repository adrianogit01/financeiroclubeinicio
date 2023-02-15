package financeiroclube.service;

import java.math.BigDecimal;
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

import financeiroclube.entity.Pendencia;
import financeiroclube.entity.enums.MotivoEmissao;
import financeiroclube.entity.enums.SituacaoPendencia;
import financeiroclube.repository.PendenciaRepository;

@Service
@Transactional
public class PendenciaServiceImpl implements PendenciaService {

	@Autowired
	private PendenciaRepository pendenciaRepository;

	@Autowired
	private UsuarioService usuarioService;

	@Override
	public void salvar(Pendencia entidade) {
		if (entidade.getIdPendencia() == null) {
			padronizar(entidade);
			pendenciaRepository.save(entidade);
		}
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Pendencia ler(Long id) {
		return pendenciaRepository.findById(id).get();
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Pendencia> listar() {
		//modificado
		return pendenciaRepository.findAllByDataVencimentoBeforeAndDataRecebimentoIsNullOrderByUsuarioAscDataVencimentoAsc(LocalDate.now());
	}

	@Override
	public Page<Pendencia> listarPagina(Pageable pagina) {
		return pendenciaRepository.findAllByOrderByDataEmissaoDescUsuarioAscNumeroAscParcelaAsc(pagina);
	}

	@Override
	public void editar(Pendencia entidade) {
		padronizar(entidade);
		pendenciaRepository.save(entidade);
	}

	@Override
	public void excluir(Pendencia entidade) {
		pendenciaRepository.delete(entidade);

	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public void validar(Pendencia entidade, BindingResult validacao) {
		
		if (entidade.getIdPendencia() == null) {
			if (entidade.getDataEmissao() != null && entidade.getUsuario() != null
					&& pendenciaRepository.existsByNumeroAndParcelaAndDataEmissaoAndUsuario(entidade.getNumero(),
							entidade.getParcela(), entidade.getDataEmissao(), entidade.getUsuario())) {
				validacao.rejectValue("usuario", "Unique", new Object[] { 0, entidade.toString() }, null);
			}
		}
		
		else {
			if (entidade.getDataEmissao() != null && entidade.getUsuario() != null
					&& pendenciaRepository.existsByNumeroAndParcelaAndDataEmissaoAndUsuarioAndIdPendenciaNot(
							entidade.getNumero(), entidade.getParcela(), entidade.getDataEmissao(),
							entidade.getUsuario(),
							entidade.getIdPendencia())) {
				validacao.rejectValue("usuario", "Unique", new Object[] { 0, entidade.toString() }, null);
			}
			if (entidade.getDataRecebimento() != null) {
				
				if (entidade.getDataRecebimento().isBefore(entidade.getDataEmissao())) {
					validacao.rejectValue("dataRecebimento", "typeMismatch");
				}
				
				if (entidade.getMotivoBaixa() == null) {
					validacao.rejectValue("motivoBaixa", "NotNull");
				}
				
			} else if (entidade.getMotivoBaixa() != null) {
				validacao.rejectValue("motivoBaixa", "Null");
			}
			
			if (entidade.getSituacao() == null) {
				validacao.rejectValue("situacao", "NotNull");
			}
		}
		
		if (entidade.getDataVencimento() != null && entidade.getDataVencimento().isBefore(entidade.getDataEmissao())) {
			validacao.rejectValue("dataVencimento", "typeMismatch");
		}
		if (entidade.getValor() != null && entidade.getTotal() != null) {
			
			if (entidade.getValor().compareTo(BigDecimal.ZERO) == 0) {
				validacao.rejectValue("valor", "NotNull");
			} else {
				
				BigDecimal teste = entidade.getValor();
				if (entidade.getDesconto() != null) {
					teste = teste.subtract(entidade.getDesconto());
				}

				if (entidade.getJuros() != null) {
					teste = teste.add(entidade.getJuros());
				}

				if (entidade.getTotal().compareTo(teste) != 0) {
					validacao.rejectValue("total", "typeMismatch");
				}
			}

		}

	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public void padronizar(Pendencia entidade) {
		if (entidade.getDataEmissao() == null) {
			entidade.setDataEmissao(LocalDate.now());
		}
		if (entidade.getMotivoEmissao() == null) {
			entidade.setMotivoEmissao(MotivoEmissao.O);
		}
		if (entidade.getSituacao() == null) {
			entidade.setSituacao(SituacaoPendencia.N);
		}
		if (entidade.getDesconto() == null) {
			entidade.setDesconto(BigDecimal.ZERO);
		}

		if (entidade.getJuros() == null) {
			entidade.setJuros(BigDecimal.ZERO);
		}

		if (entidade.getPercentualJurosMes() == null) {
			entidade.setPercentualJurosMes(new Float(0));
		}

	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public BigDecimal atraso() {
		BigDecimal resultado;
		//if (clube == null) {
			//resultado = BigDecimal.ZERO.setScale(2);
		//} else {
			resultado = pendenciaRepository.sumTotalByDataVencimentoBeforeAndDataRecebimentoIsNull(LocalDate.now());
			if (resultado == null) {
				resultado = BigDecimal.ZERO.setScale(2);
			}
		//}
		return resultado;
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Pendencia> listarAtraso() {
		List<Pendencia> lista = new ArrayList<>();
		//if (clube != null && !clube.getPendencias().isEmpty()) {
			lista.addAll(pendenciaRepository
					.findAllByDataVencimentoBeforeAndDataRecebimentoIsNullOrderByUsuarioAscDataVencimentoAsc(
							LocalDate.now()));
		//}
		return lista;
	}

}
