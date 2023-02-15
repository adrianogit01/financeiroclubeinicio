package financeiroclube.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import financeiroclube.entity.Periodo;

public interface PeriodoRepository extends PagingAndSortingRepository<Periodo, Long> {

	List<Periodo> findAll();
	
	Page<Periodo> findAllByOrderByInicioDesc(Pageable pagina);

	boolean existsByInicioLessThanEqualAndFimGreaterThanEqual(LocalDate inicio,
			LocalDate fim);

	boolean existsByInicioLessThanEqualAndFimGreaterThanEqualAndIdPeriodoNot(LocalDate inicio, 
			LocalDate fim, Long idPeriodo);

	boolean existsByInicioAfterAndFimBefore(LocalDate inicio, LocalDate fim);

	boolean existsByInicioAfterAndFimBeforeAndIdPeriodoNot(LocalDate inicio,
			LocalDate fim, Long idPeriodo);

	Periodo findOneByInicioLessThanEqualAndFimGreaterThanEqual(LocalDate inicio,
			LocalDate fim);

}
