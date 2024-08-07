package financeiroclube.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
import financeiroclube.entity.enums.TipoCategoria;
import financeiroclube.entity.enums.TipoClasseCategoria;
import financeiroclube.service.CategoriaService;
import financeiroclube.service.SubcategoriaService;

@Controller
@RequestMapping({ "funcionario/categorias", "funcionario/plano-de-contas" })
public class CategoriaController {

	@Autowired
	private CategoriaService categoriaService;

	@Autowired
	private SubcategoriaService subcategoriaService;

	@ModelAttribute("ativo")
	public String[] ativo() {
		return new String[] { "contabilidade", "categorias" };
	}

	@ModelAttribute("tiposCategoria")
	public TipoCategoria[] tiposCategoria() {
		return TipoCategoria.values();
	}

	@ModelAttribute("tiposClasseCategoria")
	public TipoClasseCategoria[] tiposClasseCategoria() {
		return TipoClasseCategoria.values();
	}

	@ModelAttribute("categorias")
	public List<Categoria> categorias() {
		return categoriaService.listar();
	}

	@GetMapping({ "", "/", "/lista", "/todos" })
	public ModelAndView getCategorias(ModelMap model) {
		model.addAttribute("contagemSubcategorias", subcategoriaService.contagem());
		model.addAttribute("conteudo", "categoriaLista");
		return new ModelAndView("fragmentos/layout", model);
	}

	@GetMapping("/cadastro")
	public ModelAndView getCategoriaCadastro(@ModelAttribute("categoria") Categoria categoria, ModelMap model) {
		model.addAttribute("classe", "");
		model.addAttribute("conteudo", "categoriaCadastro");
		return new ModelAndView("fragmentos/layout", model);
	}

	@GetMapping("/{idCategoria}/cadastro")
	public ModelAndView getCategoriaEditar(@PathVariable("idCategoria") Long idCategoria, ModelMap model) {
		model.addAttribute("classe", TipoClasseCategoria.C);
		model.addAttribute("categoria", categoriaService.ler(idCategoria));
		model.addAttribute("conteudo", "categoriaCadastro");
		return new ModelAndView("fragmentos/layout", model);
	}

	@PostMapping("/cadastro")
	public ModelAndView postCategoriaCadastro(@Valid @ModelAttribute("categoria") Categoria categoria,
			BindingResult validacao, ModelMap model) {
		categoriaService.validar(categoria, validacao);
		if (validacao.hasErrors()) {
			categoria.setIdCategoria(null);
			model.addAttribute("classe", TipoClasseCategoria.C);
			model.addAttribute("conteudo", "categoriaCadastro");
			return new ModelAndView("fragmentos/layout", model);
		}
		categoriaService.salvar(categoria);
		return new ModelAndView("redirect:/funcionario/categorias");
	}

	@PutMapping("/cadastro")
	public ModelAndView putCategoriaCadastro(@Valid @ModelAttribute("categoria") Categoria categoria,
			BindingResult validacao, ModelMap model) {
		categoriaService.validar(categoria, validacao);
		if (validacao.hasErrors()) {
			model.addAttribute("classe", TipoClasseCategoria.C);
			model.addAttribute("conteudo", "categoriaCadastro");
			return new ModelAndView("fragmentos/layout", model);
		}
		categoriaService.editar(categoria);
		return new ModelAndView("redirect:/funcionario/categorias");
	}

	@DeleteMapping("/excluir")
	public ModelAndView deleteCategoriaCadastro(@RequestParam("idObj") Long idObj) {
		categoriaService.excluir(categoriaService.ler(idObj));
		return new ModelAndView("redirect:/funcionario/categorias");
	}

}
