package financeiroclube.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import financeiroclube.entity.Clube;
import financeiroclube.entity.Usuario;
import financeiroclube.repository.ClubeRepository;

@Service
@Transactional
public class ClubeServiceImpl implements ClubeService {

	@Autowired
	private ClubeRepository clubeRepository;

	@Autowired
	private UsuarioService usuarioService;

	@Override
	public void salvar(Clube clube) {
		if (clube.getIdClube() == null) {
			padronizar(clube);
			clubeRepository.save(clube);

		
			Usuario funcionario = usuarioService.lerLogado();
			funcionario.setClube(clube);
			usuarioService.editar(funcionario);
		}
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Clube ler() {
		return usuarioService.lerLogado().getClube();
	}

	@Override
	public void editar(Clube clube) {
		padronizar(clube);
		clubeRepository.save(clube);
	}

	@Override
	public void excluir(Clube clube) {
		clubeRepository.delete(clube);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Clube ler(Long id) {
		// ao fazer o ADMIN
		return null;
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Clube> listar() {
		// ao fazer o ADMIN
		return null;
	}

	@Override
	public Page<Clube> listarPagina(Pageable pagina) {
		
		return null;
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public void validar(Clube entidade, BindingResult validacao) {
		
		if (entidade.getIdClube() == null) {
			
			if (entidade.getCnpj() != null && clubeRepository.existsByCnpj(entidade.getCnpj())) {
				validacao.rejectValue("cnpj", "Unique");
			}
		}
		
		else {
			
			if (entidade.getCnpj() != null
					&& clubeRepository.existsByCnpjAndIdClubeNot(entidade.getCnpj(), entidade.getIdClube())) {
				validacao.rejectValue("cnpj", "Unique");
			}
		}
	
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public void padronizar(Clube entidade) {

	}

}
