package financeiroclube.entity.enums;

public enum TipoClasseCategoria {

	
	C("Categoria Sintética","Categoria"),
	S("Categoria Analítica","Subcategoria");
	

	private final String nome;
	private final String classe;

	private TipoClasseCategoria(String nome, String classe) {
		this.nome = nome;
		this.classe = classe;
	}

	public String getNome() {
		return nome;
	}

	public String getClasse() {
		return classe;
	}
}
