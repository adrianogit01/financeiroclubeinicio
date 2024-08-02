package financeiroclube.controller;

import java.time.LocalDate;
import java.util.List;
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

import financeiroclube.entity.Pendencia;
import financeiroclube.entity.Usuario;
import financeiroclube.entity.enums.MotivoBaixa;
import financeiroclube.entity.enums.MotivoEmissao;
import financeiroclube.entity.enums.SituacaoPendencia;
import financeiroclube.service.PendenciaService;
import financeiroclube.service.UsuarioService;

@Controller
@RequestMapping("funcionario/pendencias")
public class PendenciaController {

	@Autowired
	private PendenciaService pendenciaService;

	@Autowired
	UsuarioService usuarioService;

	@ModelAttribute("ativo")
	public String[] ativo() {
		return new String[] { "financeiro", "pendencias" };
	}

	@ModelAttribute("motivosEmissao")
	public MotivoEmissao[] motivosEmissao() {
		return MotivoEmissao.values();
	}

	@ModelAttribute("motivosBaixa")
	public MotivoBaixa[] motivosBaixa() {
		return MotivoBaixa.values();
	}

	@ModelAttribute("situacoes")
	public SituacaoPendencia[] situacoes() {
		return SituacaoPendencia.values();
	}

	@ModelAttribute("usuarios")
	public List<Usuario> usuarios() {
		//return usuarioService.listar();
		return usuarioService.listar();
	}

	@GetMapping({ "", "/", "/lista" })
	public ModelAndView getPendencias(@RequestParam("pagina") Optional<Integer> pagina,
			@RequestParam("tamanho") Optional<Integer> tamanho, ModelMap model) {
		model.addAttribute("pendencias",
				pendenciaService.listarPagina(PageRequest.of(pagina.orElse(1) - 1, tamanho.orElse(20))));
		model.addAttribute("conteudo", "pendenciaLista");
		return new ModelAndView("fragmentos/layout", model);
	}

	@GetMapping("/cadastro")
	public ModelAndView getPendenciaCadastro(@ModelAttribute("pendencia") Pendencia pendencia) {
		pendencia.setDataEmissao(LocalDate.now());
		pendencia.setMotivoEmissao(MotivoEmissao.O);
		return new ModelAndView("fragmentos/layout", "conteudo", "pendenciaCadastro");
	}

	@GetMapping("/{idPendencia}/cadastro")
	public ModelAndView getPendenciaEditar(@PathVariable("idPendencia") Long idPendencia, ModelMap model) {
		model.addAttribute("pendencia", pendenciaService.ler(idPendencia));
		model.addAttribute("conteudo", "pendenciaCadastro");
		return new ModelAndView("fragmentos/layout", model);
	}

	@PostMapping("/cadastro")
	public ModelAndView postPendenciaCadastro(@Valid @ModelAttribute("pendencia") Pendencia pendencia,
			BindingResult validacao) {
		pendenciaService.validar(pendencia, validacao);
		if (validacao.hasErrors()) {
			pendencia.setIdPendencia(null);
			return new ModelAndView("fragmentos/layout", "conteudo", "pendenciaCadastro");
		}
		pendenciaService.salvar(pendencia);
		return new ModelAndView("redirect:/funcionario/pendencias");
	}

	@PutMapping("/cadastro")
	public ModelAndView putPendenciaCadastro(@Valid @ModelAttribute("pendencia") Pendencia pendencia,
			BindingResult validacao) {
		pendenciaService.validar(pendencia, validacao);
		if (validacao.hasErrors()) {
			return new ModelAndView("fragmentos/layout", "conteudo", "pendenciaCadastro");
		}
		pendenciaService.editar(pendencia);
		return new ModelAndView("redirect:/funcionario/pendencias");
	}

	@DeleteMapping("/excluir")
	public ModelAndView deletePendenciaCadastro(@RequestParam("idObj") Long idObj) {
		pendenciaService.excluir(pendenciaService.ler(idObj));
		return new ModelAndView("redirect:/funcionario/pendencias");
	}
}
