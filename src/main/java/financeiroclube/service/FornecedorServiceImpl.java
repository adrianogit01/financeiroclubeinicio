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

import financeiroclube.entity.Fornecedor;
import financeiroclube.repository.FornecedorRepository;


@Service
@Transactional
public class FornecedorServiceImpl implements FornecedorService {

	@Autowired
	private FornecedorRepository fornecedorRepository;

	@Autowired
	private UsuarioService usuarioService;

	@Override
	public void salvar(Fornecedor entidade) {
		if (entidade.getIdFornecedor() == null) {
			padronizar(entidade);
			fornecedorRepository.save(entidade);
		}
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Fornecedor ler(Long id) {
		return fornecedorRepository.findById(id).get();
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Fornecedor> listar() {
			return fornecedorRepository.findAll();

	}

	@Override
	public Page<Fornecedor> listarPagina(Pageable pagina) {
		return fornecedorRepository.findAllByOrderByNome(pagina);
	}

	@Override
	public void editar(Fornecedor entidade) {
		padronizar(entidade);
		fornecedorRepository.save(entidade);
	}

	@Override
	public void excluir(Fornecedor entidade) {
		fornecedorRepository.delete(entidade);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public void validar(Fornecedor entidade, BindingResult validacao) {
		
				if (entidade.getCnpj() != null && fornecedorRepository
						.existsByCnpjAndIdFornecedorNot(entidade.getCnpj(),
								entidade.getIdFornecedor())) {
					validacao.rejectValue("cnpj", "Unique");
				}
					

		Set<Fornecedor> fornecedores = new HashSet<>();
	

	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public void padronizar(Fornecedor entidade) {


	}


}
