package financeiroclube.entity.enums;

public enum MotivoBaixa {

	
	N("Recebimento normal"),
	R("Renegociação de dívida"),
	P("Prescrição"),
	O("Outras");
	

	private final String nome;

	private MotivoBaixa(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}
}
