package financeiroclube.entity.enums;

public enum MotivoEmissao {

	
	O("Normal"),
	E("Extra"),
	A("Avulsa"),
	R("Renegociação");
	

	private final String nome;

	private MotivoEmissao(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}
}
