package financeiroclube.service;

import java.time.LocalDate;

import financeiroclube.entity.Periodo;

public interface PeriodoService extends CrudService<Periodo, Long> {

	public boolean haPeriodo(LocalDate data);

	public Periodo ler(LocalDate data);

}
