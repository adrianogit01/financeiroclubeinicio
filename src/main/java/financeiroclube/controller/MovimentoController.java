package financeiroclube.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import financeiroclube.entity.Categoria;
import financeiroclube.entity.Cofre;
import financeiroclube.entity.Movimento;
import financeiroclube.entity.enums.TipoCategoria;
import financeiroclube.service.CategoriaService;
import financeiroclube.service.CofreService;
import financeiroclube.service.MovimentoService;

@Controller
@RequestMapping({ "funcionario/movimentos", "funcionario/movimentos" })
public class MovimentoController {

	@Autowired
	private MovimentoService movimentoService;

	@Autowired
	private CategoriaService categoriaService;

	@Autowired
	private CofreService cofreService;

	@ModelAttribute("ativo")
	public String[] ativo() {
		return new String[] { "cofrebilidade", "movimentos" };
	}

	@ModelAttribute("tiposMovimento")
	public TipoCategoria[] tiposMovimento() {
		return TipoCategoria.values();
	}

	@ModelAttribute("categorias")
	public List<Categoria> categorias() {
		return categoriaService.listar();
	}

	@ModelAttribute("cofres")
	public List<Cofre> cofres() {
		return cofreService.listar();
	}

	@GetMapping({ "", "/", "/lista" })
	public ModelAndView getMovimentos(@RequestParam("pagina") Optional<Integer> pagina,
			@RequestParam("tamanho") Optional<Integer> tamanho, ModelMap model) {
		model.addAttribute("movimentos",
				movimentoService.listarPagina(PageRequest.of(pagina.orElse(1) - 1, tamanho.orElse(20))));
		model.addAttribute("conteudo", "movimentoLista");
		return new ModelAndView("fragmentos/layoutFuncionario", model);
	}

	@GetMapping("/cadastro")
	public ModelAndView getMovimentoCadastro(@ModelAttribute("movimento") Movimento movimento) {
		movimento.setData(LocalDate.now());
		return new ModelAndView("fragmentos/layoutFuncionario", "conteudo", "movimentoCadastro");
	}

	@GetMapping("/{idMovimento}/cadastro")
	public ModelAndView getMovimentoEditar(@PathVariable("idMovimento") Long idMovimento, ModelMap model) {
		Movimento movimento = movimentoService.ler(idMovimento);
		model.addAttribute("movimento", movimento);
		model.addAttribute("conteudo", "movimentoCadastro");
		return new ModelAndView("fragmentos/layoutFuncionario", model);
	}

	@PostMapping(value = "/cadastro")
	public ModelAndView postMovimentoMovimentoCadastro(@Valid @ModelAttribute("movimento") Movimento movimento,
			BindingResult validacao, ModelMap model) {
		movimentoService.validar(movimento, validacao);
		if (validacao.hasErrors()) {
			movimento.setIdMovimento(null);
			model.addAttribute("conteudo", "movimentoCadastro");
			return new ModelAndView("fragmentos/layoutFuncionario", model);
		}
		movimentoService.salvar(movimento);
		return new ModelAndView("redirect:/funcionario/movimentos");
	}


	@PutMapping(value = "/cadastro")
	public ModelAndView putMovimentoMovimentoCadastro(@Valid @ModelAttribute("movimento") Movimento movimento,
			BindingResult validacao, ModelMap model) {
		movimentoService.validar(movimento, validacao);
		if (validacao.hasErrors()) {
			model.addAttribute("conteudo", "movimentoCadastro");
			return new ModelAndView("fragmentos/layoutFuncionario", "conteudo", "movimentoCadastro");
		}
		movimentoService.editar(movimento);
		return new ModelAndView("redirect:/funcionario/movimentos");
	}

	@DeleteMapping("/excluir")
	public ModelAndView deleteMovimentoCadastro(@RequestParam("idObj") Long idObj) {
		movimentoService.excluir(movimentoService.ler(idObj));
		return new ModelAndView("redirect:/funcionario/movimentos");
	}

}
