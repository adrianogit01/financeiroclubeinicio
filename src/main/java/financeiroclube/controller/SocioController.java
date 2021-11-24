package financeiroclube.controller;

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

import financeiroclube.entity.Socio;
import financeiroclube.service.SocioService;

@Controller
@RequestMapping("funcionario/socios")
public class SocioController {

	@Autowired
	private SocioService socioService;

	
	@ModelAttribute("ativo")
	public String[] ativo() {
		return new String[] { "clube", "socios" };
	}

	@GetMapping({ "", "/", "/lista" })
	public ModelAndView getSocios(@RequestParam("pagina") Optional<Integer> pagina,
			@RequestParam("tamanho") Optional<Integer> tamanho, ModelMap model) {
		model.addAttribute("socios",
				socioService.listarPagina(PageRequest.of(pagina.orElse(1) - 1, tamanho.orElse(20))));
		model.addAttribute("conteudo", "socioLista");
		return new ModelAndView("fragmentos/layoutFuncionario", model);
	}

	@GetMapping("/cadastro")
	public ModelAndView getSocioCadastro(@ModelAttribute("socio") Socio socio, ModelMap model) {
		model.addAttribute("tipo", "");
		model.addAttribute("conteudo", "socioCadastro");
		return new ModelAndView("fragmentos/layoutFuncionario", model);
	}

	@GetMapping("/{idSocio}/cadastro")
	public ModelAndView getSocioEditar(@PathVariable("idSocio") Long idSocio, ModelMap model) {
		Socio socio = socioService.ler(idSocio);

			model.addAttribute("socio", socio);
			
	model.addAttribute("conteudo", "socioCadastro");
		return new ModelAndView("fragmentos/layoutFuncionario", model);
	}

	@PostMapping(value = "/cadastro")
	public ModelAndView postSocioCadastro(@Valid @ModelAttribute("socio") Socio socio,
			BindingResult validacao, ModelMap model) {
		socioService.validar(socio, validacao);
		if (validacao.hasErrors()) {
			socio.setIdSocio(null);
			model.addAttribute("conteudo", "socioCadastro");
			return new ModelAndView("fragmentos/layoutFuncionario", model);
		}
		socioService.salvar(socio);
		return new ModelAndView("redirect:/funcionario/socios");
	}

	@PutMapping(value = "/cadastro")
	public ModelAndView putSocioCadastro(@Valid @ModelAttribute("socio") Socio socio,
			BindingResult validacao, ModelMap model) {
		socioService.validar(socio, validacao);
		if (validacao.hasErrors()) {
			model.addAttribute("conteudo", "socioCadastro");
			return new ModelAndView("fragmentos/layoutFuncionario", model);
		}
		socioService.editar(socio);
		return new ModelAndView("redirect:/funcionario/socios");
	}

	@DeleteMapping("/excluir")
	public ModelAndView deleteSocioCadastro(@RequestParam("idObj") Long idObj) {
		socioService.excluir(socioService.ler(idObj));
		return new ModelAndView("redirect:/funcionario/socios");
	}

}
