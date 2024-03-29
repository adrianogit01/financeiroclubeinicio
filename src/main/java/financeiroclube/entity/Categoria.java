package financeiroclube.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import financeiroclube.entity.enums.TipoCategoria;

@SuppressWarnings("serial")
@Entity
@Table(name = "categorias")
public class Categoria implements Serializable, Comparable<Categoria> {

	public static final int MAXIMO = 4;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_categoria")
	private Long idCategoria;

	@NotNull
	@Enumerated(EnumType.STRING)
	private TipoCategoria tipo;

	@Size(min = 1, max = 50)
	@NotBlank
	private String descricao;

	@Max(MAXIMO)
	private Integer nivel;

	@Size(min = 1, max = 255)
	@NotBlank
	private String ordem;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_categoriapai")
	private Categoria categoriaPai;

	@OneToMany(mappedBy = "categoriaPai", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<Categoria> categoriasFilhas = new ArrayList<>();

	@OneToMany(mappedBy = "categoriaPai", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
	@OrderBy(value = "descricao")
	private List<Subcategoria> Subcategorias = new ArrayList<>();

	public static Integer getMaximo() {
		return MAXIMO;
	}

	public Long getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(Long idCategoria) {
		this.idCategoria = idCategoria;
	}

	public TipoCategoria getTipo() {
		return tipo;
	}

	public void setTipo(TipoCategoria tipo) {
		this.tipo = tipo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Integer getNivel() {
		return nivel;
	}

	public void setNivel(Integer nivel) {
		this.nivel = nivel;
	}

	public String getOrdem() {
		return ordem;
	}

	public void setOrdem(String ordem) {
		this.ordem = ordem;
	}

	public Categoria getCategoriaPai() {
		return categoriaPai;
	}

	public void setCategoriaPai(Categoria categoriaPai) {
		this.categoriaPai = categoriaPai;
	}

	public List<Categoria> getCategoriasFilhas() {
		return categoriasFilhas;
	}

	public void setCategoriasFilhas(List<Categoria> categoriasFilhas) {
		this.categoriasFilhas = categoriasFilhas;
	}

	public List<Subcategoria> getSubcategorias() {
		return Subcategorias;
	}

	public void setSubcategorias(List<Subcategoria> subcategorias) {
		Subcategorias = subcategorias;
	}

	@Override
	public String toString() {
		return ordem + " - " + descricao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idCategoria == null) ? 0 : idCategoria.hashCode());
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
		Categoria other = (Categoria) obj;
		if (idCategoria == null) {
			if (other.idCategoria != null) {
				return false;
			}
		} else if (!idCategoria.equals(other.idCategoria)) {
			return false;
		}
		return true;
	}

	@Override
	public int compareTo(Categoria o) {
		return this.ordem.compareTo(o.getOrdem());
	}

}
