package financeiroclube.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import financeiroclube.entity.Clube;
import financeiroclube.entity.Pendencia;
import financeiroclube.entity.Socio;

public interface PendenciaRepository extends PagingAndSortingRepository<Pendencia, Long> {

	Boolean existsByNumeroAndParcelaAndDataEmissaoAndSocioAndClube(String numero, String parcela,
			LocalDate dataEmissao, Socio socio, Clube socioi);

	Boolean existsByNumeroAndParcelaAndDataEmissaoAndSocioAndClubeAndIdPendenciaNot(String numero, String parcela,
			LocalDate dataEmissao, Socio socio, Clube socioi, Long idPendencia);

	@Query("select sum(total) from #{#entityName} c where c.clube = :clube and c.dataRecebimento is null and c.dataVencimento < :data")
	BigDecimal sumTotalByClubeAndDataVencimentoBeforeAndDataRecebimentoIsNull(
			@Param("clube") Clube clube, @Param("data") LocalDate data);

	List<Pendencia> findAllByClubeAndDataVencimentoBeforeAndDataRecebimentoIsNullOrderBySocioAscDataVencimentoAsc(
			Clube clube, LocalDate data);

	Page<Pendencia> findAllByClubeOrderByDataEmissaoDescSocioAscNumeroAscParcelaAsc(Clube clube,
			Pageable pagina);
}
