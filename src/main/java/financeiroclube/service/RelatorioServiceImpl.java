package financeiroclube.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import financeiroclube.entity.Categoria;
import financeiroclube.entity.Cofre;
import financeiroclube.entity.Movimento;
import financeiroclube.entity.Pendencia;
import financeiroclube.entity.Periodo;
import financeiroclube.entity.Usuario;
import financeiroclube.entity.Subcategoria;
import financeiroclube.entity.enums.TipoCategoria;

@Service
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class RelatorioServiceImpl implements RelatorioService {

	@Autowired
	CofreService cofreService;

	@Autowired
	MovimentoService movimentoService;

	@Autowired
	PendenciaService pendenciaService;

	@Autowired
	PeriodoService periodoService;

	@Autowired
	SubcategoriaService subcategoriaService;

	@Autowired
	CategoriaService categoriaService;

	@Override
	public BigDecimal saldoAtualTodosCofres() {
		return cofreService.saldoAtual();
	}

	@Override
	public BigDecimal saldoInicialTodosCofresEm(LocalDate data) {
		BigDecimal saldo = cofreService.saldoAtual();
		BigDecimal[] movimentos = receitaDespesaDesde(cofreService.listar(), data);
		return saldo.subtract(movimentos[0]).add(movimentos[1]);
	}

	@Override
	public BigDecimal saldoFinalTodosCofresEm(LocalDate data) {
		return saldoInicialTodosCofresEm(data.plusDays(1));
	}

	@Override
	public BigDecimal atrasoAtual() {
		return pendenciaService.atraso();
	}

	private BigDecimal[] receitaDespesaEntre(Collection<Cofre> cofres, LocalDate inicio, LocalDate fim) {
		BigDecimal[] resultado = new BigDecimal[2];
		if (!cofres.isEmpty()) {
			resultado[0] = movimentoService.somaMovimentosEntre(cofres, inicio, fim, Boolean.FALSE);
			resultado[1] = movimentoService.somaMovimentosEntre(cofres, inicio, fim, Boolean.TRUE);
		}
		if (resultado[0] == null) {
			resultado[0] = BigDecimal.ZERO.setScale(2);
		}
		if (resultado[1] == null) {
			resultado[1] = BigDecimal.ZERO.setScale(2);
		}
		return resultado;
	}

	private BigDecimal[] receitaDespesaDesde(Collection<Cofre> cofres, LocalDate inicio) {
		BigDecimal[] resultado = new BigDecimal[2];
		if (!cofres.isEmpty()) {
			resultado[0] = movimentoService.somaMovimentosDesde(cofres, inicio, Boolean.FALSE);
			resultado[1] = movimentoService.somaMovimentosDesde(cofres, inicio, Boolean.TRUE);
		}
		if (resultado[0] == null) {
			resultado[0] = BigDecimal.ZERO.setScale(2);
		}
		if (resultado[1] == null) {
			resultado[1] = BigDecimal.ZERO.setScale(2);
		}
		return resultado;
	}

	@Override
	public BigDecimal[] receitaDespesaMesAtual() {
		List<Cofre> cofres = cofreService.listar();
		YearMonth mesAtual = YearMonth.from(LocalDate.now());
		
		return receitaDespesaEntre(cofres, mesAtual.atDay(1), mesAtual.atEndOfMonth());
	}

	@Override
	public BigDecimal[] receitaDespesaEntre(LocalDate inicio, LocalDate fim) {
		List<Cofre> cofres = cofreService.listar();
		return receitaDespesaEntre(cofres, inicio, fim);
	}

	@Override
	public List<Movimento> movimentosEntre(LocalDate inicio, LocalDate fim) {
		List<Cofre> cofres = cofreService.listar();
		if (!cofres.isEmpty()) {
			List<Movimento> movimentos = new ArrayList<>();
			movimentos.addAll(movimentoService.listarMovimentosEntre(cofres, inicio, fim));
			return movimentos;
		}
		return new ArrayList<>();
	}

	@Override
	public BigDecimal[] saldosAposMovimentos(List<Movimento> movimentos, BigDecimal saldoInicial) {
		if (saldoInicial == null) {
			saldoInicial = BigDecimal.ZERO.setScale(2);
		}
		if (!movimentos.isEmpty()) {
			BigDecimal[] saldos = new BigDecimal[movimentos.size()];
			Movimento movimento = movimentos.get(0);
			
			if (movimento.getReducao()) {
				saldos[0] = saldoInicial.subtract(movimento.getValor());
			} else {
				saldos[0] = saldoInicial.add(movimento.getValor());
			}
			
			for (int i = 1; i < saldos.length; i++) {
				movimento = movimentos.get(i);
				if (movimento.getReducao()) {
					saldos[i] = saldos[i - 1].subtract(movimento.getValor());
				} else {
					saldos[i] = saldos[i - 1].add(movimento.getValor());
				}
			}
			return saldos;
		} else {
			BigDecimal[] vazio = new BigDecimal[1];
			vazio[0] = saldoInicial;
			return vazio;
		}
	}

	@Override
	public SortedMap<Subcategoria, BigDecimal> somasPorTipoEntre(LocalDate inicio, LocalDate fim,
			TipoCategoria tipoCategoria) {
		SortedMap<Subcategoria, BigDecimal> map = new TreeMap<>();
		List<Cofre> cofres = cofreService.listar();
		if (!cofres.isEmpty()) {
			List<Subcategoria> subcategorias;
			if (TipoCategoria.R.equals(tipoCategoria)) {
				subcategorias = subcategoriaService.listarReceitas();
			} else if (TipoCategoria.D.equals(tipoCategoria)) {
				subcategorias = subcategoriaService.listarDespesas();
			} else {
				return map;
			}
			for (Subcategoria subcategoria : subcategorias) {
				BigDecimal soma = movimentoService.somaMovimentosEntre(cofres, inicio, fim, subcategoria);
				if (soma != null && soma.compareTo(BigDecimal.ZERO) != 0) {
					map.put(subcategoria, soma);
				}
			}
		}
		return map;
	}

	@Override
	public SortedMap<Usuario, List<Pendencia>> atrasoAtualDetalhado() {
		SortedMap<Usuario, List<Pendencia>> map = new TreeMap<>();
		List<Pendencia> atraso = pendenciaService.listarAtraso();
		for (Pendencia pendencia : atraso) {
			List<Pendencia> lista;
			if (map.containsKey(pendencia.getUsuario())) {
				lista = map.get(pendencia.getUsuario());
			} else {
				lista = new ArrayList<>();
			}
			lista.add(pendencia);
			map.put(pendencia.getUsuario(), lista);
		}
		return map;
	}

	@Override
	public Map<Usuario, BigDecimal> somaPendencias(Map<Usuario, List<Pendencia>> map) {
		Map<Usuario, BigDecimal> mapa = new HashMap<>();
		if (!map.isEmpty()) {
			for (Map.Entry<Usuario, List<Pendencia>> entrada : map.entrySet()) {
				BigDecimal soma = BigDecimal.ZERO;
				for (Pendencia pendencia : entrada.getValue()) {
					soma = soma.add(pendencia.getTotal());
				}
				mapa.put(entrada.getKey(), soma);
			}
		}
		return mapa;
	}

}
