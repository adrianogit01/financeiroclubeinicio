package financeiroclube.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import financeiroclube.entity.Usuario;
import financeiroclube.service.RelatorioService;
import financeiroclube.service.UsuarioService;

@Controller
//@RequestMapping("/funcionario")
public class IndexController {

	@Autowired
	UsuarioService usuarioService;

	@ModelAttribute("ativo")
	public String[] ativo() {
		return new String[] { "index", "" };
	}

	@Autowired
	RelatorioService relatorioService;


	@RequestMapping("/")
	//@GetMapping({ "/", "", "/index", "/dashboard" })
	public ModelAndView funcionario(ModelMap model) {

	    Object auth = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails user = (UserDetails) auth;
	    Usuario logado = usuarioService.buscarPorLogin(user.getUsername());

		model.addAttribute("logado", logado);

		model.addAttribute("saldoAtual", relatorioService.saldoAtualTodosCofres());
		model.addAttribute("atraso", relatorioService.atrasoAtual());
		model.addAttribute("receitaDespesaMes", relatorioService.receitaDespesaMesAtual());
		
		model.addAttribute("conteudo", "index");
		return new ModelAndView("fragmentos/layoutFuncionario", model);
	}

}
