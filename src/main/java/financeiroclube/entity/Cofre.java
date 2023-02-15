package financeiroclube.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@SuppressWarnings("serial")
@Entity
@Table(name = "cofres")
@Inheritance(strategy = InheritanceType.JOINED)
public class Cofre implements Serializable, Comparable<Cofre> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_cofre")
	private Long idCofre;

	@Size(min = 1, max = 2)
	@NotBlank
	private String sigla;

	@Size(max = 30)
	private String descricao;

	@Column(name = "saldo_inicial")
	private BigDecimal saldoInicial;

	@Column(name = "saldo_atual")
	private BigDecimal saldoAtual;

	@OneToMany(mappedBy = "cofre", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<Movimento> movimentos = new ArrayList<>();


	public Long getIdCofre() {
		return idCofre;
	}

	public void setIdCofre(Long idCofre) {
		this.idCofre = idCofre;
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public BigDecimal getSaldoInicial() {
		return saldoInicial;
	}

	public void setSaldoInicial(BigDecimal saldoInicial) {
		this.saldoInicial = saldoInicial;
	}

	public BigDecimal getSaldoAtual() {
		return saldoAtual;
	}

	public void setSaldoAtual(BigDecimal saldoAtual) {
		this.saldoAtual = saldoAtual;
	}

	public List<Movimento> getMovimentos() {
		return movimentos;
	}

	public void setMovimentos(List<Movimento> movimentos) {
		this.movimentos = movimentos;
	}

	@Override
	public String toString() {
		if (descricao != null) {
			return sigla + " - " + descricao;
		} else {
			return sigla;
		}
	}

	public String numero() {
		return "";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idCofre == null) ? 0 : idCofre.hashCode());
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
		Cofre other = (Cofre) obj;
		if (idCofre == null) {
			if (other.idCofre != null) {
				return false;
			}
		} else if (!idCofre.equals(other.idCofre)) {
			return false;
		}
		return true;
	}

	@Override
	public int compareTo(Cofre o) {
		return this.sigla.compareTo(o.getSigla());
	}
}
