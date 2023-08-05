package financeiroclube.service;

import java.math.BigDecimal;

import financeiroclube.entity.Cofre;

public interface CofreService extends CrudService<Cofre, Long> {

	public BigDecimal saldoAtual();

    public void decreaseSaldo(Cofre entidade, BigDecimal valor);

	public void increaseSaldo(Cofre entidade, BigDecimal valor);

}
