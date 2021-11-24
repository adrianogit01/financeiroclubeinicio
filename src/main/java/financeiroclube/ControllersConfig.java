package financeiroclube;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.context.request.WebRequest;

import financeiroclube.entity.Usuario;
import financeiroclube.service.UsuarioService;

@ControllerAdvice
public class ControllersConfig {

	@Autowired
	UsuarioService usuarioService;

	@ModelAttribute("haClube")
	public boolean haClube() {
		Usuario usuario = usuarioService.lerLogado();
		if (usuario == null) {
			return false;
		}
		return usuario.getClube() != null;
	}

	@InitBinder
	public void registerCustomEditors(WebDataBinder binder, WebRequest request) {
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
}
