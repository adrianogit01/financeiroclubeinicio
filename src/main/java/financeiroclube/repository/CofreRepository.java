package financeiroclube.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import financeiroclube.entity.Cofre;

public interface CofreRepository extends PagingAndSortingRepository<Cofre, Long> {

	List<Cofre> findAll();
	
	//Page<Cofre> findAllBySiglaAsc(Pageable pagina);
	Page<Cofre> findAllByOrderBySiglaAsc(Pageable pagina);

	Boolean existsBySigla(String sigla);

	Boolean existsBySiglaAndIdCofreNot(String sigla, Long idCofre);

	//@Query("select sum(saldoAtual) from #{#entityName} c where c.clube = :clube")
	@Query("select sum(saldoAtual) from #{#entityName}")
	//BigDecimal sumSaldoAtualByClube(@Param("clube") Clube clube);
	BigDecimal sumSaldoAtual();

}
