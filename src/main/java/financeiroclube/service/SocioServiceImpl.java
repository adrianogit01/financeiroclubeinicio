package financeiroclube.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import financeiroclube.entity.Clube;
import financeiroclube.entity.Socio;
import financeiroclube.repository.SocioRepository;


@Service
@Transactional
public class SocioServiceImpl implements SocioService {


	@Autowired
	private SocioRepository socioRepository;

	@Autowired
	private UsuarioService usuarioService;

	@Override
	public void salvar(Socio entidade) {
		if (entidade.getIdSocio() == null) {
			padronizar(entidade);
			socioRepository.save(entidade);
		}
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Socio ler(Long id) {
		return socioRepository.findById(id).get();
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Socio> listar() {
		Clube clube = usuarioService.lerLogado().getClube();
		if (clube == null) {
			return new ArrayList<>();
		}
		return clube.getSocios();
	}

	@Override
	public Page<Socio> listarPagina(Pageable pagina) {
		Clube clube = usuarioService.lerLogado().getClube();
		if (clube == null) {
			return Page.empty(pagina);
		}
		return socioRepository.findAllByClubeOrderByNome(clube, pagina);
	}

	@Override
	public void editar(Socio entidade) {
		padronizar(entidade);
		socioRepository.save(entidade);
	}

	@Override
	public void excluir(Socio entidade) {
		socioRepository.delete(entidade);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public void validar(Socio entidade, BindingResult validacao) {
		

			 if (entidade.getCpf() != null && socioRepository.existsByCpfAndClube(
						 entidade.getCpf(), usuarioService.lerLogado().getClube())) {
					validacao.rejectValue("cpf", "Unique");
				}
 
		
	
		else {
				if ( entidade.getCpf() != null
						&& socioRepository.existsByCpfAndClubeAndIdSocioNot(entidade.getCpf(),
								usuarioService.lerLogado().getClube(), entidade.getIdSocio())) {
					validacao.rejectValue("cpf", "Unique");
				} 
		}

		Set<Socio> socios = new HashSet<>();
	

	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public void padronizar(Socio entidade) {
		if (entidade.getClube() == null) {
			entidade.setClube(usuarioService.lerLogado().getClube());
		}


	}

}
