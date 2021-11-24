package financeiroclube.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import financeiroclube.entity.Clube;
import financeiroclube.service.ClubeService;

@Controller
@RequestMapping("funcionario/clube")
public class ClubeController {

	@Autowired
	private ClubeService clubeService;

	@ModelAttribute("ativo")
	public String[] ativo() {
		return new String[] { "clube", "cadastro" };
	}

	@GetMapping("/cadastro")
	public ModelAndView getClubeCadastro(ModelMap model) {
		Clube clube = clubeService.ler();
		if (clube != null) {
			model.addAttribute("clube", clube);
		} else {
			model.addAttribute("clube", new Clube());
		}
		model.addAttribute("conteudo", "clubeCadastro");
		return new ModelAndView("fragmentos/layoutFuncionario", model);
	}

	@PostMapping("/cadastro")
	public ModelAndView postClubeCadastro(@Valid @ModelAttribute("clube") Clube clube,
			BindingResult validacao) {
		clubeService.validar(clube, validacao);
		if (validacao.hasErrors()) {
			clube.setIdClube(null);
			return new ModelAndView("fragmentos/layoutFuncionario", "conteudo", "clubeCadastro");
		}
		clubeService.salvar(clube);
		return new ModelAndView("redirect:/funcionario");
	}

	@PutMapping("/cadastro")
	public ModelAndView putClubeCadastro(@Valid @ModelAttribute("clube") Clube clube,
			BindingResult validacao) {
		clubeService.validar(clube, validacao);
		if (validacao.hasErrors()) {
			return new ModelAndView("fragmentos/layoutFuncionario", "conteudo", "clubeCadastro");
		}
		clubeService.editar(clube);
		return new ModelAndView("redirect:/funcionario");
	}

}
