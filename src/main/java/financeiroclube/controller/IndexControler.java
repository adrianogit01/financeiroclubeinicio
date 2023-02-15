package financeiroclube.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import financeiroclube.entity.Usuario;
import financeiroclube.service.UsuarioService;

@Controller
public class IndexControler {
	
	@Autowired
	UsuarioService usuarioService;
	
		@RequestMapping("/")
	    public ModelAndView index(){
			Object auth = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			UserDetails user = (UserDetails) auth;
			
			Usuario logado = usuarioService.buscarPorLogin(user.getUsername());
	        
			ModelAndView mv = new ModelAndView("fragmentos/layoutSite", "conteudo", "index");
			mv.addObject("logado", logado);
			return mv;
	    }

}
