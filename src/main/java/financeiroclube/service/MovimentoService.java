package financeiroclube.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import financeiroclube.entity.Categoria;
import financeiroclube.entity.Cofre;
import financeiroclube.entity.Movimento;
import financeiroclube.entity.Periodo;
import financeiroclube.entity.Subcategoria;

public interface MovimentoService extends CrudService<Movimento, Long> {

	public BigDecimal somaMovimentosEntre(Collection<Cofre> cofres, LocalDate inicio, LocalDate fim, Boolean reducao);

	public BigDecimal somaMovimentosEntre(Collection<Cofre> cofres, LocalDate inicio, LocalDate fim,
			Subcategoria subcategoria);

	public BigDecimal somaMovimentosPeriodo(Collection<Cofre> cofres, Periodo periodo, Subcategoria subcategoria);

	public BigDecimal somaMovimentosPeriodo(Collection<Cofre> cofres, Periodo periodo, Categoria categoria);

	public BigDecimal somaMovimentosDesde(Collection<Cofre> cofres, LocalDate inicio, Boolean reducao);

	public List<Movimento> listarMovimentosEntre(Collection<Cofre> cofres, LocalDate inicio, LocalDate fim);

}
