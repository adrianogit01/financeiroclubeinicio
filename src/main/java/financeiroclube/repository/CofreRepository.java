package financeiroclube.repository;

import java.math.BigDecimal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import financeiroclube.entity.Clube;
import financeiroclube.entity.Cofre;

public interface CofreRepository extends PagingAndSortingRepository<Cofre, Long> {

	Page<Cofre> findAllByClubeOrderBySiglaAsc(Clube clube, Pageable pagina);

	Boolean existsBySiglaAndClube(String sigla, Clube clube);

	Boolean existsBySiglaAndClubeAndIdCofreNot(String sigla, Clube clube, Long idCofre);

	@Query("select sum(saldoAtual) from #{#entityName} c where c.clube = :clube")
	BigDecimal sumSaldoAtualByClube(@Param("clube") Clube clube);

}
