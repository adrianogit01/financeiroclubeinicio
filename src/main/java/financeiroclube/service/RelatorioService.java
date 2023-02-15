package financeiroclube.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

import financeiroclube.entity.Categoria;
import financeiroclube.entity.Movimento;
import financeiroclube.entity.Pendencia;
import financeiroclube.entity.Periodo;
import financeiroclube.entity.Usuario;
import financeiroclube.entity.Subcategoria;
import financeiroclube.entity.enums.TipoCategoria;

public interface RelatorioService {

	/**
	 * @return Retorna um BigDecimal com a soma do saldo de todos os Cofres do
	 *         Clube. Nunca retorna nulo, se não houver cofres, retorna
	 *         BigDecimal.ZERO.
	 */
	public BigDecimal saldoAtualTodosCofres();

	/**
	 * @param data
	 *            Um dia para pesquisa.
	 * @return Retorna um BigDecimal com o saldo de todos os Cofres do Clube no
	 *         início do dia passado no parâmetro. Nunca retorna nulo, se não houver
	 *         cofres, retorna BigDecimal.ZERO.
	 */
	public BigDecimal saldoInicialTodosCofresEm(LocalDate data);

	/**
	 * @param data
	 *            Um dia para pesquisa.
	 * @return Retorna um BigDecimal com o saldo de todos os Cofres do Clube no
	 *         fim do dia passado no parâmetro. Nunca retorna nulo, se não houver
	 *         cofres, retorna BigDecimal.ZERO.
	 */
	public BigDecimal saldoFinalTodosCofresEm(LocalDate data);

	/**
	 * @return Retorna um BigDecimal com o valor total da inadimplência do
	 *         Clube na data atual (considera o valor total da Cobrança, com
	 *         acréscimos e deduções). Nunca retorna nulo, se não houver
	 *         inadimplência, retorna BigDecimal.ZERO.
	 */
	public BigDecimal atrasoAtual();

	/**
	 * @return Retorna um vetor de BigDecimal[] com duas posições. A posição [0] é a
	 *         soma dos lançamentos de receitas do mês atual, e a posição [1] é a
	 *         soma dos lançamentos de despesas do mês atual. Nunca retorna nulo, se
	 *         não houver lançamentos, retorna BigDecimal.ZERO na respectiva posição
	 *         do vetor.
	 */
	public BigDecimal[] receitaDespesaMesAtual();

	/**
	 * @param inicio
	 *            Data inicial para pesquisa
	 * @param fim
	 *            Data final para pesquisa
	 * @return Retorna um vetor de BigDecimal[] com duas posições. A posição [0] é a
	 *         soma dos lançamentos de receitas dentro das datas informadas no
	 *         parâmetro, e a posição [1] é a soma dos lançamentos de despesas
	 *         dentro das datas informadas no parâmetro. Nunca retorna nulo, se não
	 *         houver lançamentos, retorna BigDecimal.ZERO na respectiva posição do
	 *         vetor.
	 */
	public BigDecimal[] receitaDespesaEntre(LocalDate inicio, LocalDate fim);

	/**
	 * @param inicio
	 *            Data inicial para pesquisa
	 * @param fim
	 *            Data final para pesquisa
	 * @return Retorna uma lista do tipo List{@literal <}Movimento{@literal >} com
	 *         Movimentos existentes em todos os Cofres dentro das datas informadas
	 *         no parâmetro. Nunca retorna nulo, se não houverem Cofres ou
	 *         Movimentos, retorna uma lista vazia.
	 */
	public List<Movimento> movimentosEntre(LocalDate inicio, LocalDate fim);

	/**
	 * @param movimentos
	 *            Uma lista do tipo List{@literal <}Movimento{@literal >}
	 * @param saldoInicial
	 *            Um BigDecimal para ser considerado como saldo inicial
	 * @return Retorna um vetor de BigDecimal[] com tamanho igual ao número de
	 *         elementos na lista passada no parâmetro, contendo em cada enésima
	 *         posição o saldo após processado o enésimo Movimento, partindo do
	 *         saldo inicial informado no parâmetro. Nunca retorna nulo, se a lista
	 *         passada no parâmetro estiver vazia, retorna um vetor com uma posição
	 *         com valor BigDecimal.ZERO.
	 */
	public BigDecimal[] saldosAposMovimentos(List<Movimento> movimentos, BigDecimal saldoInicial);

	/**
	 * @param inicio
	 *            Data inicial para pesquisa
	 * @param fim
	 *            Data final para pesquisa
	 * @param tipoCategoria
	 *            O tipo da Categoria pai da Subcategoria a ser buscada
	 * @return Retorna um mapa do tipo Map{@literal <}Subcategoria,
	 *         BigDecimal{@literal >}. Cada entrada do mapa é composta por uma
	 *         Subcategoria como chave e um BigDecimal como valor. O valor
	 *         representa a soma de todos os Movimentos existentes para a
	 *         respectiva Subcategoria dentro das datas informadas no parâmetro.
	 *         Retorna somente Subcategorias com soma diferente de zero. Nunca
	 *         retorna nulo, se não houverem entradas, retorna um mapa vazio.
	 */
	public SortedMap<Subcategoria, BigDecimal> somasPorTipoEntre(LocalDate inicio, LocalDate fim,
			TipoCategoria tipoCategoria);

	/**
	 * @return Retorna um mapa do tipo Map{@literal <}Socio,List{@literal
	 *         <}Pendencia{@literal >>}. Cada entrada do mapa é composta por um
	 *         Socio como chave e uma lista de Pendencias como valor. Esta lista
	 *         contém todas as Pendencias vencidas até a data atual do respectivo
	 *         Socio. Retorna somente Socios com uma lista não vazia. Nunca
	 *         retorna nulo, se não houverem entradas, retorna um mapa vazio.
	 */
	public SortedMap<Usuario, List<Pendencia>> atrasoAtualDetalhado();

	/**
	 * @param map
	 *            Um mapa do tipo Map{@literal <}Socio,List{@literal
	 *         <}Pendencia{@literal >>} para ser somado
	 * @return Retorna um mapa do tipo Map{@literal <}Socio,BigDecimal{@literal >}
	 *         com as mesmas chaves do mapa fornecido no parâmetro. Cada valor
	 *         corresponde à soma das Pendências do respectivo Socio. Nunca retorna
	 *         nulo, se não houverem entradas, retorna um mapa vazio.
	 */
	public Map<Usuario, BigDecimal> somaPendencias(Map<Usuario, List<Pendencia>> map);

	
}
