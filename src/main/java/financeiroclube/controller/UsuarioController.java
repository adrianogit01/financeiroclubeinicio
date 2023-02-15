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

import financeiroclube.entity.Usuario;
import financeiroclube.service.UsuarioService;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;

	
	@ModelAttribute("ativo")
	public String[] ativo() {
		return new String[] { "usuarios" };
	}

	@GetMapping({ "", "/", "/users" })
	public ModelAndView getUsuarios(@RequestParam("pagina") Optional<Integer> pagina,
			@RequestParam("tamanho") Optional<Integer> tamanho, ModelMap model) {
		model.addAttribute("usuarios",
				usuarioService.listaUsuarios());
		model.addAttribute("conteudo", "usuarioLista");
		return new ModelAndView("fragmentos/layoutFuncionario", model);
	}

	@GetMapping("/adicionar")
	public ModelAndView getUsuarioCadastro(@ModelAttribute("usuario") Usuario usuario, ModelMap model) {
		model.addAttribute("tipo", "");
		model.addAttribute("conteudo", "usuarioCadastro");
		return new ModelAndView("fragmentos/layoutFuncionario", model);
	}
	

	@GetMapping("/editusuario/{id}")
	public ModelAndView getUsuarioEditar(@PathVariable("id") Long id, ModelMap model) {
		Usuario usuario = usuarioService.retornaUser(id);
			model.addAttribute("usuario", usuario);
			model.addAttribute("conteudo", "usuarioCadastro");
		return new ModelAndView("fragmentos/layoutFuncionario", model);
	}

	@PostMapping(value = "/adicionar")
	public ModelAndView postUsuarioCadastro(@Valid @ModelAttribute("usuario") Usuario usuario,
			ModelMap model) {
				
		usuarioService.adicionarUsuario(usuario);
		return new ModelAndView("redirect:/usuario/users");
	}

	@PutMapping(value = "/adicionar")
	public ModelAndView putUsuarioCadastro(@Valid @ModelAttribute("usuario") Usuario usuario,
			ModelMap model) {
		usuarioService.atualizarUsuario(usuario);
		return new ModelAndView("redirect:/usuario/users");
	}

	
	@DeleteMapping(value="/deletar/{id}")
		public ModelAndView deletarUsuario(@RequestParam("idObj") Long idObj) {
			usuarioService.removerUsuario(idObj);
			return new ModelAndView("redirect:/usuario/users");
	}
	
	@RequestMapping("/logar" )
	public ModelAndView logar() {
		ModelAndView mv = new ModelAndView("fragmentos/layoutSite", "conteudo", "login");
		return mv;
	}

}
