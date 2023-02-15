package financeiroclube.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@SuppressWarnings("serial")
@Entity
@Table(name = "subcategorias")
public class Subcategoria implements Serializable, Comparable<Subcategoria> {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_subcategoria")
	private Long idSubcategoria;

	@Size(min = 1, max = 50)
	@NotBlank
	private String descricao;

	@NotNull
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_categoria")
	private Categoria categoriaPai;

	@OneToMany(mappedBy = "subcategoria", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
	@OrderBy(value = "data")
	private List<Movimento> movimentos = new ArrayList<>();

	public Long getIdSubcategoria() {
		return idSubcategoria;
	}

	public void setIdSubcategoria(Long idSubcategoria) {
		this.idSubcategoria = idSubcategoria;
	}

	
	public void setIdCategoria(Long idSubcategoria) {
		setIdSubcategoria(idSubcategoria);
	}

	
	public Long getIdCategoria() {
		return getIdSubcategoria();
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Categoria getCategoriaPai() {
		return categoriaPai;
	}

	public void setCategoriaPai(Categoria categoriaPai) {
		this.categoriaPai = categoriaPai;
	}

	public List<Movimento> getMovimentos() {
		return movimentos;
	}

	public void setMovimentos(List<Movimento> movimentos) {
		this.movimentos = movimentos;
	}

	@Override
	public String toString() {
		return categoriaPai.getOrdem() + " - " + descricao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idSubcategoria == null) ? 0 : idSubcategoria.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Subcategoria other = (Subcategoria) obj;
		if (idSubcategoria == null) {
			if (other.idSubcategoria != null) {
				return false;
			}
		} else if (!idSubcategoria.equals(other.idSubcategoria)) {
			return false;
		}
		return true;
	}

	@Override
	public int compareTo(Subcategoria o) {
		int comparacao = this.categoriaPai.getOrdem().compareTo(o.getCategoriaPai().getOrdem());
		if (comparacao != 0) {
			return comparacao;
		} else {
			return this.descricao.compareTo(o.getDescricao());
		}

	}
}
