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

import financeiroclube.entity.Fornecedor;
import financeiroclube.service.FornecedorService;

@Controller
@RequestMapping("funcionario/fornecedores")
public class FornecedorController {

	@Autowired
	private FornecedorService fornecedorService;

	
	@ModelAttribute("ativo")
	public String[] ativo() {
		return new String[] { "clube", "fornecedores" };
	}

	@GetMapping({ "", "/", "/lista" })
	public ModelAndView getFornecedores(@RequestParam("pagina") Optional<Integer> pagina,
			@RequestParam("tamanho") Optional<Integer> tamanho, ModelMap model) {
		model.addAttribute("fornecedores",
				fornecedorService.listarPagina(PageRequest.of(pagina.orElse(1) - 1, tamanho.orElse(20))));
		model.addAttribute("conteudo", "fornecedorLista");
		return new ModelAndView("fragmentos/layoutFuncionario", model);
	}

	@GetMapping("/cadastro")
	public ModelAndView getFornecedorCadastro(@ModelAttribute("fornecedor") Fornecedor fornecedor, ModelMap model) {
		model.addAttribute("tipo", "");
		model.addAttribute("conteudo", "fornecedorCadastro");
		return new ModelAndView("fragmentos/layoutFuncionario", model);
	}

	@GetMapping("/{idFornecedor}/cadastro")
	public ModelAndView getFornecedorEditar(@PathVariable("idFornecedor") Long idFornecedor, ModelMap model) {
		Fornecedor fornecedor = fornecedorService.ler(idFornecedor);

			model.addAttribute("fornecedor", fornecedor);
			
	model.addAttribute("conteudo", "fornecedorCadastro");
		return new ModelAndView("fragmentos/layoutFuncionario", model);
	}

	@PostMapping(value = "/cadastro")
	public ModelAndView postFornecedorCadastro(@Valid @ModelAttribute("fornecedor") Fornecedor fornecedor,
			BindingResult validacao, ModelMap model) {
		fornecedorService.validar(fornecedor, validacao);
		if (validacao.hasErrors()) {
			fornecedor.setIdFornecedor(null);
			model.addAttribute("conteudo", "fornecedorCadastro");
			return new ModelAndView("fragmentos/layoutFuncionario", model);
		}
		fornecedorService.salvar(fornecedor);
		return new ModelAndView("redirect:/funcionario/fornecedores");
	}

	@PutMapping(value = "/cadastro")
	public ModelAndView putFornecedorCadastro(@Valid @ModelAttribute("fornecedor") Fornecedor fornecedor,
			BindingResult validacao, ModelMap model) {
		fornecedorService.validar(fornecedor, validacao);
		if (validacao.hasErrors()) {
			model.addAttribute("conteudo", "fornecedorCadastro");
			return new ModelAndView("fragmentos/layoutFuncionario", model);
		}
		fornecedorService.editar(fornecedor);
		return new ModelAndView("redirect:/funcionario/fornecedores");
	}

	@DeleteMapping("/excluir")
	public ModelAndView deleteFornecedorCadastro(@RequestParam("idObj") Long idObj) {
		fornecedorService.excluir(fornecedorService.ler(idObj));
		return new ModelAndView("redirect:/funcionario/fornecedores");
	}

}
