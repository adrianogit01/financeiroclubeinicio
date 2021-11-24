package financeiroclube.service;

import java.math.BigDecimal;
import java.util.List;

import financeiroclube.entity.Pendencia;

public interface PendenciaService extends CrudService<Pendencia, Long> {

	/**
	 * @return Retorna um BigDecimal com o valor total dos atrasos do
	 *         Clube na data atual (considera o valor total da Pendencia, com
	 *         acréscimos e deduções). Nunca retorna nulo, se não houver
	 *         atrasos, retorna BigDecimal.ZERO.
	 */
	public BigDecimal atraso();

	/**
	 * @return Retorna uma lista do tipo List{@literal <}Pendencia{@literal >} com
	 *         todas as Pendencias do Clube vencidas na data atual (considera o
	 *         valor total da Cobrança, com acréscimos e deduções). Nunca retorna
	 *         nulo, se não houver atrasos, retorna uma lista vazia.
	 */
	public List<Pendencia> listarAtraso();

}
