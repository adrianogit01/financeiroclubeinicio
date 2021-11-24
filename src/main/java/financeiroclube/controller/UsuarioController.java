package financeiroclube.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import financeiroclube.entity.Usuario;
import financeiroclube.service.UsuarioService;

@Controller
@RequestMapping("conta")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;

	@ModelAttribute("ativo")
	public String[] ativo() {
		return new String[] { "conta", "" };
	}

	@GetMapping("/cadastrar")
	public ModelAndView getCadastrarFuncionario(@ModelAttribute("funcionario") Usuario funcionario) {
		return new ModelAndView("fragmentos/layoutSite", "conteudo", "funcionarioCadastro");
	}

	@PostMapping("/cadastrar")
	public ModelAndView postCadastrarFuncionario(@Valid @ModelAttribute("funcionario") Usuario funcionario,
			BindingResult validacao) {
		usuarioService.validar(funcionario, validacao);
		if (validacao.hasErrors()) {
			funcionario.setId(null);
			return new ModelAndView("fragmentos/layoutSite", "conteudo", "funcionarioCadastro");
		}
		usuarioService.salvarFuncionario(funcionario);
		return new ModelAndView("redirect:/login?novo");
	}

	@GetMapping("/cadastro")
	public ModelAndView getCadastroFuncionario(ModelMap model) {
		model.addAttribute("funcionario", usuarioService.lerLogado());
		model.addAttribute("conteudo", "funcionarioCadastro");
		return new ModelAndView("fragmentos/layoutSite", model);
	}

	@PutMapping("/cadastro")
	public ModelAndView putCadastroFuncionario(@Valid @ModelAttribute("funcionario") Usuario funcionario, BindingResult validacao) {
		usuarioService.validar(funcionario, validacao);
		if (validacao.hasErrors()) {
			return new ModelAndView("fragmentos/layoutSite", "conteudo", "funcionarioCadastro");
		}
		usuarioService.editar(funcionario);
		SecurityContextHolder.clearContext();
		return new ModelAndView("redirect:/entrar?alterado");
	}

	@GetMapping("/redefinir")
	public ModelAndView preRedefinir() {
		return new ModelAndView("fragmentos/layoutSite", "conteudo", "usuarioRedefinirSenha");
	}

	@PostMapping("/redefinir")
	public String postRedefinir(@RequestParam("username") String username) {
		if (usuarioService.redefinirSenha(username)) {
			return "redirect:/conta/redefinir?email&username=" + username;
		} else {
			return "redirect:/conta/redefinir?erro&username=" + username;
		}
	}

	@PutMapping("/redefinir")
	public String putRedefinir(@RequestParam("username") String username, @RequestParam("password") String password,
			@RequestParam("token") String token) {
		if (usuarioService.redefinirSenha(username, token, password)) {
			return "redirect:/login?redefinido";
		} else {
			return "redirect:/conta/redefinir?invalido";
		}
	}
}
