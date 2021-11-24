package financeiroclube.controller;

import java.math.BigDecimal;
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

import financeiroclube.entity.Cofre;
import financeiroclube.service.CofreService;

@Controller
@RequestMapping("funcionario/cofres")
public class CofreController {

	@Autowired
	private CofreService cofreService;

	@ModelAttribute("ativo")
	public String[] ativo() {
		return new String[] { "financeiro", "cofres" };
	}

	@GetMapping({ "", "/", "/lista" })
	public ModelAndView getCofres(@RequestParam("pagina") Optional<Integer> pagina,
			@RequestParam("tamanho") Optional<Integer> tamanho, ModelMap model) {
		model.addAttribute("cofres",
				cofreService.listarPagina(PageRequest.of(pagina.orElse(1) - 1, tamanho.orElse(20))));
		model.addAttribute("conteudo", "cofreLista");
		return new ModelAndView("fragmentos/layoutFuncionario", model);
	}

	@GetMapping("/cadastro")
	public ModelAndView getCofreCadastro(@ModelAttribute("cofre") Cofre cofre, ModelMap model) {
		cofre.setSaldoInicial(BigDecimal.ZERO);
		model.addAttribute("conteudo", "cofreCadastro");
		return new ModelAndView("fragmentos/layoutFuncionario", model);
	}

	@GetMapping("/{idCofre}/cadastro")
	public ModelAndView getCofreEditar(@PathVariable("idCofre") Long idCofre, ModelMap model) {
		Cofre cofre = cofreService.ler(idCofre);
	 
			model.addAttribute("cofre", cofre);
					
		model.addAttribute("conteudo", "cofreCadastro");
		return new ModelAndView("fragmentos/layoutFuncionario", model);
	}

	@PostMapping(value = "/cadastro")
	public ModelAndView postCofreCadastro(@Valid @ModelAttribute("cofre") Cofre cofre, BindingResult validacao,
			ModelMap model) {
		cofreService.validar(cofre, validacao);
		if (validacao.hasErrors()) {
			cofre.setIdCofre(null);
			model.addAttribute("conteudo", "cofreCadastro");
			return new ModelAndView("fragmentos/layoutFuncionario", model);
		}
		cofreService.salvar(cofre);
		return new ModelAndView("redirect:/funcionario/cofres");
	}

	
	@PutMapping(value = "/cadastro")
	public ModelAndView putCofreCadastro(@Valid @ModelAttribute("cofre") Cofre cofre, BindingResult validacao,
			ModelMap model) {
		cofreService.validar(cofre, validacao);
		if (validacao.hasErrors()) {
			model.addAttribute("conteudo", "cofreCadastro");
			return new ModelAndView("fragmentos/layoutFuncionario", model);
		}
		cofreService.editar(cofre);
		return new ModelAndView("redirect:/funcionario/cofres");
	}

	@DeleteMapping("/excluir")
	public ModelAndView deleteCofreCadastro(@RequestParam("idObj") Long idObj) {
		cofreService.excluir(cofreService.ler(idObj));
		return new ModelAndView("redirect:/funcionario/cofres");
	}

}
