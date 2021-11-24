package financeiroclube.repository;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import financeiroclube.entity.Clube;
import financeiroclube.entity.Periodo;

public interface PeriodoRepository extends PagingAndSortingRepository<Periodo, Long> {

	Page<Periodo> findAllByClubeOrderByInicioDesc(Clube clube, Pageable pagina);

	boolean existsByClubeAndInicioLessThanEqualAndFimGreaterThanEqual(Clube clube, LocalDate inicio,
			LocalDate fim);

	boolean existsByClubeAndInicioLessThanEqualAndFimGreaterThanEqualAndIdPeriodoNot(Clube clube,
			LocalDate inicio, LocalDate fim, Long idPeriodo);

	boolean existsByClubeAndInicioAfterAndFimBefore(Clube clube, LocalDate inicio, LocalDate fim);

	boolean existsByClubeAndInicioAfterAndFimBeforeAndIdPeriodoNot(Clube clube, LocalDate inicio,
			LocalDate fim, Long idPeriodo);

	Periodo findOneByClubeAndInicioLessThanEqualAndFimGreaterThanEqual(Clube clube, LocalDate inicio,
			LocalDate fim);

}
