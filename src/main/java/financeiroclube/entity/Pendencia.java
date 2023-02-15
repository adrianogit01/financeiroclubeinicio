package financeiroclube.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

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
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import financeiroclube.entity.enums.MotivoBaixa;
import financeiroclube.entity.enums.MotivoEmissao;
import financeiroclube.entity.enums.SituacaoPendencia;

@SuppressWarnings("serial")
@Entity
@Table(name = "pendencias")
public class Pendencia implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_pendencia")
	private Long idPendencia;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "motivo_emissao")
	private MotivoEmissao motivoEmissao;

	@Size(max = 10)
	@NotBlank
	private String numero;

	@Size(max = 3)
	private String parcela;

	@NotNull
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	@Column(name = "data_emissao")
	private LocalDate dataEmissao;

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	@Column(name = "data_vencimento")
	private LocalDate dataVencimento;

	@NotNull
	@Min(0)
	private BigDecimal valor;

	@Min(0)
	private BigDecimal desconto;

	@Min(0)
	@Column(name = "juros")
	private BigDecimal juros;
	
	@Min(0)
	@NotNull
	private BigDecimal total;

	@Min(0)
	@Column(name = "percentual_juros_mes")
	private Float percentualJurosMes;

	@Enumerated(EnumType.STRING)
	private SituacaoPendencia situacao;

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	@Column(name = "data_recebimento")
	private LocalDate dataRecebimento;

	@Enumerated(EnumType.STRING)
	@Column(name = "motivo_baixa")
	private MotivoBaixa motivoBaixa;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id")
	private Usuario usuario;

	public Long getIdPendencia() {
		return idPendencia;
	}

	public void setIdPendencia(Long idPendencia) {
		this.idPendencia = idPendencia;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public MotivoEmissao getMotivoEmissao() {
		return motivoEmissao;
	}

	public void setMotivoEmissao(MotivoEmissao motivoEmissao) {
		this.motivoEmissao = motivoEmissao;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getParcela() {
		return parcela;
	}

	public void setParcela(String parcela) {
		this.parcela = parcela;
	}

	public LocalDate getDataEmissao() {
		return dataEmissao;
	}

	public void setDataEmissao(LocalDate dataEmissao) {
		this.dataEmissao = dataEmissao;
	}

	public LocalDate getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(LocalDate dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public BigDecimal getDesconto() {
		return desconto;
	}

	public void setDesconto(BigDecimal desconto) {
		this.desconto = desconto;
	}

	public BigDecimal getJuros() {
		return juros;
	}

	public void setJuros(BigDecimal juros) {
		this.juros = juros;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public Float getPercentualJurosMes() {
		return percentualJurosMes;
	}

	public void setPercentualJurosMes(Float percentualJurosMes) {
		this.percentualJurosMes = percentualJurosMes;
	}

	public SituacaoPendencia getSituacao() {
		return situacao;
	}

	public void setSituacao(SituacaoPendencia situacao) {
		this.situacao = situacao;
	}

	public LocalDate getDataRecebimento() {
		return dataRecebimento;
	}

	public void setDataRecebimento(LocalDate dataRecebimento) {
		this.dataRecebimento = dataRecebimento;
	}

	public MotivoBaixa getMotivoBaixa() {
		return motivoBaixa;
	}

	public void setMotivoBaixa(MotivoBaixa motivoBaixa) {
		this.motivoBaixa = motivoBaixa;
	}

	@Override
	public String toString() {
		String s = numero;
		if (parcela != null) {
			s += " - " + parcela;
		}
		return s;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idPendencia == null) ? 0 : idPendencia.hashCode());
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
		Pendencia other = (Pendencia) obj;
		if (idPendencia == null) {
			if (other.idPendencia != null) {
				return false;
			}
		} else if (!idPendencia.equals(other.idPendencia)) {
			return false;
		}
		return true;
	}

}
