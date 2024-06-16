package financeiroclube.controller;

import java.util.Optional;

import javax.servlet.http.HttpSession;
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

import financeiroclube.entity.Usuario;
import financeiroclube.service.UsuarioService;

@Controller
@RequestMapping("funcionario/usuario")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;

	
	@ModelAttribute("ativo")
	public String[] ativo() {
		return new String[] { "usuarios" };
	}

	@GetMapping({ "", "/", "/lista" })
	public ModelAndView getUsuarios(@RequestParam("pagina") Optional<Integer> pagina,
			@RequestParam("tamanho") Optional<Integer> tamanho, ModelMap model) {
		model.addAttribute("usuarios",
				usuarioService.listarPagina(PageRequest.of(pagina.orElse(1) - 1, tamanho.orElse(20))));
				model.addAttribute("conteudo", "usuarioLista");
				return new ModelAndView("fragmentos/layoutFuncionario", model);
	}

	//Responsável pelo modal de cadastrar.
	@GetMapping("/adicionar")
	public ModelAndView getUsuarioCadastro(@ModelAttribute("usuario") Usuario usuario, ModelMap model) {
		model.addAttribute("tipo", "");
		model.addAttribute("conteudo", "usuarioCadastro");
		return new ModelAndView("fragmentos/layoutFuncionario", model);
	}
	

	@GetMapping("/editusuario/{id}")
	public ModelAndView getUsuarioEditar(@PathVariable("id") Long id, ModelMap model) {
		Usuario usuario = usuarioService.ler(id);
		//usuarioService.buscarPorLogin(usuario.getLogin());
			model.addAttribute("usuario", usuario);
			
	model.addAttribute("conteudo", "usuarioCadastro");
		return new ModelAndView("fragmentos/layoutFuncionario", model);
	}

	@PostMapping(value = "/adicionar")
	public ModelAndView postUsuarioCadastro(@Valid @ModelAttribute("usuario") Usuario usuario,
			ModelMap model) {
				
		usuarioService.salvar(usuario);
		return new ModelAndView("redirect:/funcionario/usuario");
	}

	@PutMapping(value = "/adicionar")
	public ModelAndView putUsuarioCadastro(@Valid @ModelAttribute("usuario") Usuario usuario,
			ModelMap model) {
		usuarioService.editar(usuario);
		return new ModelAndView("redirect:/funcionario/usuario");
	}
	
	/*@PostMapping(value = "/adicionar")
	public ModelAndView postUsuarioCadastro(@Valid @ModelAttribute("usuario") Usuario usuario,
			ModelMap model, BindingResult validacao) {
		usuarioService.validar(usuario, validacao);		
		usuarioService.salvar(usuario);
		if (validacao.hasErrors()) {
			usuario.setId(null);
			return new ModelAndView("fragmentos/layoutFuncionario", "conteudo", "usuarioCadastro");
		}
		return new ModelAndView("redirect:/funcionario/usuario");
	}

	@PutMapping(value = "/adicionar")
	public ModelAndView putUsuarioCadastro(@Valid @ModelAttribute("usuario") Usuario usuario,
			ModelMap model, BindingResult validacao) {
		if (validacao.hasErrors()) {
			return new ModelAndView("fragmentos/layoutFuncionario", "conteudo", "usuarioCadastro");
		}
		usuarioService.editar(usuario);
		return new ModelAndView("redirect:/funcionario/usuario");
	}*/
	
	

	
	   //também compatibilizado
	@DeleteMapping(value="/deletar/{id}")
		public ModelAndView deletarUsuario(@RequestParam("idObj") Long idObj) {
			usuarioService.excluir(usuarioService.ler(idObj));
			return new ModelAndView("redirect:/funcionario/usuario");
	}
	
	
	@RequestMapping("/logar")
	public ModelAndView logar() {
		ModelAndView mv = new ModelAndView("login");
		return mv;
	}
	

}
