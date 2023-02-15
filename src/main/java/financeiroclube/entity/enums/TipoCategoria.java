package financeiroclube.entity.enums;

public enum TipoCategoria {

	
	R("Receita","Entrada"),
	D("Despesa","Saída");
	

	private final String nome;
	private final String movimento;

	private TipoCategoria(String nome, String movimento) {
		this.nome = nome;
		this.movimento = movimento;
	}

	public String getNome() {
		return nome;
	}

	public String getMovimento() {
		return movimento;
	}
}
