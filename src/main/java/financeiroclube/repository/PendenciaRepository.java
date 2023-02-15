package financeiroclube.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import financeiroclube.entity.Pendencia;
import financeiroclube.entity.Usuario;

public interface PendenciaRepository extends PagingAndSortingRepository<Pendencia, Long> {

	Boolean existsByNumeroAndParcelaAndDataEmissaoAndUsuario(String numero, String parcela,
			LocalDate dataEmissao, Usuario usuario);

	Boolean existsByNumeroAndParcelaAndDataEmissaoAndUsuarioAndIdPendenciaNot(String numero, String parcela,
			LocalDate dataEmissao, Usuario usuario, Long idPendencia);

	/*@Query("select sum(total) from #{#entityName} c where c.clube = :clube and c.dataRecebimento is null and c.dataVencimento < :data")
	BigDecimal sumTotalByClubeAndDataVencimentoBeforeAndDataRecebimentoIsNull(
			@Param("clube") Clube clube, @Param("data") LocalDate data);*/
		
	@Query("select sum(total) from #{#entityName} c where c.dataRecebimento is null and c.dataVencimento < :data")
	BigDecimal sumTotalByDataVencimentoBeforeAndDataRecebimentoIsNull(
			@Param("data") LocalDate data);

	List<Pendencia> findAllByDataVencimentoBeforeAndDataRecebimentoIsNullOrderByUsuarioAscDataVencimentoAsc(
			LocalDate data);

	Page<Pendencia> findAllByOrderByDataEmissaoDescUsuarioAscNumeroAscParcelaAsc(Pageable pagina);
	
	
}
