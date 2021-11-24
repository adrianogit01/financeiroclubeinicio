package financeiroclube.service;

import financeiroclube.entity.Clube;

public interface ClubeService extends CrudService<Clube, Long> {

	public Clube ler();

}
