package financeiroclube.entity.enums;

public enum SituacaoPendencia {

	
	N("Normal"),
	A("Notificado"),
	P("Protestado"),
	J("Ação Judicial"),
	O("Outras");


	private final String nome;

	private SituacaoPendencia(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

}
