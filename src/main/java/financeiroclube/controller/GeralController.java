package financeiroclube.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import financeiroclube.service.RelatorioService;

@Controller
@RequestMapping("/funcionario")
public class GeralController {

	@ModelAttribute("ativo")
	public String[] ativo() {
		return new String[] { "geral", "" };
	}

	@Autowired
	RelatorioService relatorioService;

	@GetMapping({ "/", "", "/geral", "/dashboard" })
	public ModelAndView funcionario(ModelMap model) {

		model.addAttribute("saldoAtual", relatorioService.saldoAtualTodosCofres());
		model.addAttribute("atraso", relatorioService.atrasoAtual());
		model.addAttribute("receitaDespesaMes", relatorioService.receitaDespesaMesAtual());
		
		model.addAttribute("conteudo", "geral");
		return new ModelAndView("fragmentos/layoutFuncionario", model);
	}

}
