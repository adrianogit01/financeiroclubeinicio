package financeiroclube.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import financeiroclube.entity.Cofre;
import financeiroclube.entity.Movimento;
import financeiroclube.entity.Periodo;
import financeiroclube.entity.Subcategoria;

public interface MovimentoRepository extends PagingAndSortingRepository<Movimento, Long> {

	List<Movimento> findAllByCofreInOrderByDataDesc(Collection<Cofre> cofre);

	Page<Movimento> findAllByCofreInOrderByDataDesc(Collection<Cofre> cofre, Pageable pageable);

	List<Movimento> findAllByCofreInAndDataBetweenOrderByDataAsc(Collection<Cofre> cofre, LocalDate inicio,
			LocalDate fim);

	@Query("select sum(valor) from #{#entityName} l where l.cofre in :cofres and l.data between :dataInicial and :dataFinal and l.reducao = :reducao")
	BigDecimal sumValorByCofreInAndDataBetweenAndReducao(@Param("cofres") Collection<Cofre> cofres,
			@Param("dataInicial") LocalDate dataInicial, @Param("dataFinal") LocalDate dataFinal,
			@Param("reducao") Boolean reducao);

	@Query("select sum(valor) from #{#entityName} l where l.cofre in :cofres and l.data >= :data and l.reducao = :reducao")
	BigDecimal sumValorByCofreInAndDataGreaterThanEqualAndReducao(@Param("cofres") Collection<Cofre> cofres,
			@Param("data") LocalDate data, @Param("reducao") Boolean reducao);

	@Query("select sum(valor) from #{#entityName} l where l.cofre in :cofres and l.data between :dataInicial and :dataFinal and l.subcategoria = :subcategoria ")
	BigDecimal sumValorByCofreInAndDataBetweenAndSubcategoria(@Param("cofres") Collection<Cofre> cofres,
			@Param("dataInicial") LocalDate dataInicial, @Param("dataFinal") LocalDate dataFinal,
			@Param("subcategoria") Subcategoria subcategoria);

	@Query("select sum(valor) from #{#entityName} l where l.cofre in :cofres and l.periodo = :periodo and l.subcategoria = :subcategoria ")
	BigDecimal sumValorByCofreInAndPeriodoAndSubcategoria(@Param("cofres") Collection<Cofre> cofres,
			@Param("periodo") Periodo periodo, @Param("subcategoria") Subcategoria subcategoria);

	@Query("select sum(valor) from #{#entityName} l where l.cofre in :cofres and l.periodo = :periodo and l.subcategoria.categoriaPai.ordem like :ordem%")
	BigDecimal sumValorByCofreInAndPeriodoAndSubcategoria_CategoriaPai_OrdemStartingWith(
			@Param("cofres") Collection<Cofre> cofres, @Param("periodo") Periodo periodo, @Param("ordem") String ordem);

	
}
