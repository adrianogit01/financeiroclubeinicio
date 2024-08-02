package financeiroclube.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.SortedMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import financeiroclube.entity.Movimento;
import financeiroclube.entity.Pendencia;
import financeiroclube.entity.Periodo;
import financeiroclube.entity.Usuario;
import financeiroclube.entity.enums.TipoCategoria;
import financeiroclube.service.CategoriaService;
import financeiroclube.service.PeriodoService;
import financeiroclube.service.RelatorioService;

@Controller
@RequestMapping("funcionario/relatorios")
public class RelatorioController {

	@Autowired
	RelatorioService relatorioService;

	@Autowired
	PeriodoService periodoService;

	@Autowired
	CategoriaService categoriaService;

	@ModelAttribute("ativo")
	public String[] ativo() {
		return new String[] { "relatorios", "" };
	}

	@GetMapping({ "/", "", "/lista", "/todos" })
	public ModelAndView inicio() {
		return new ModelAndView("fragmentos/layout", "conteudo", "relatorioLista");
	}

	@GetMapping("/livroCaixa")
	public ModelAndView getLivroCaixa() {
		return new ModelAndView("fragmentos/layout", "conteudo", "relatorioLivroCaixa");
	}

	@PostMapping("/livroCaixa")
	public ModelAndView postLivroCaixa(
			@RequestParam("inicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
			@RequestParam("fim") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim, ModelMap model) {

		if (inicio.isAfter(fim)) {
			model.addAttribute("fimInvalido", "true");
			model.addAttribute("conteudo", "relatorioLivroCaixa");
			return new ModelAndView("fragmentos/layout", model);
		}

		BigDecimal saldoInicial = relatorioService.saldoInicialTodosCofresEm(inicio);
		List<Movimento> lancamentos = relatorioService.movimentosEntre(inicio, fim);

		model.addAttribute("inicio", inicio);
		model.addAttribute("fim", fim);
		model.addAttribute("saldoInicial", saldoInicial);
		model.addAttribute("lancamentos", lancamentos);
		model.addAttribute("saldos", relatorioService.saldosAposMovimentos(lancamentos, saldoInicial));
		model.addAttribute("relatorio", "relatorioLivroCaixa");
		return new ModelAndView("fragmentos/layoutRelatorio", model);
	}

	@GetMapping("/balancete")
	public ModelAndView getBalancete() {
		return new ModelAndView("fragmentos/layout", "conteudo", "relatorioBalancete");
	}

	@PostMapping("/balancete")
	public ModelAndView postBalancete(
			@RequestParam("inicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
			@RequestParam("fim") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim, ModelMap model) {

		if (inicio.isAfter(fim)) {
			model.addAttribute("fimInvalido", "true");
			model.addAttribute("conteudo", "relatorioBalancete");
			return new ModelAndView("fragmentos/layout", model);
		}

		model.addAttribute("inicio", inicio);
		model.addAttribute("fim", fim);
		model.addAttribute("receitas", relatorioService.somasPorTipoEntre(inicio, fim, TipoCategoria.R));
		model.addAttribute("despesas", relatorioService.somasPorTipoEntre(inicio, fim, TipoCategoria.D));
		model.addAttribute("totalReceitasDespesas", relatorioService.receitaDespesaEntre(inicio, fim));
		model.addAttribute("relatorio", "relatorioBalancete");
		return new ModelAndView("fragmentos/layoutRelatorio", model);
	}

	@GetMapping("/atraso")
	public ModelAndView getAtraso() {
		return new ModelAndView("fragmentos/layout", "conteudo", "relatorioAtraso");
	}

	@PostMapping("/atraso")
	public ModelAndView postAtraso(ModelMap model) {
		SortedMap<Usuario, List<Pendencia>> atraso = relatorioService.atrasoAtualDetalhado();

		model.addAttribute("fim", LocalDate.now());
		model.addAttribute("atraso", atraso);
		model.addAttribute("subtotais", relatorioService.somaPendencias(atraso));
		model.addAttribute("total", relatorioService.atrasoAtual());
		model.addAttribute("relatorio", "relatorioAtraso");
		return new ModelAndView("fragmentos/layoutRelatorio", model);
	}

}
