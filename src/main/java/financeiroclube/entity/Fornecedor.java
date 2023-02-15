package financeiroclube.entity;

import java.io.Serializable;
import java.text.ParseException;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.swing.text.MaskFormatter;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@SuppressWarnings("serial")
@Entity
@Table(name = "fornecedores")
public class Fornecedor implements Serializable, Comparable<Fornecedor>{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_fornecedor")
	private Long idFornecedor;
	
	@NotBlank
	@Size(min = 1, max = 50)
	private String nome;

	@Email
	@Size(max = 100)
	private String email;

	@Size(max = 15)
	private String telefone;

	@Size(max = 15)
	private String celular;

	@Size(max = 100)
	private String rua;

	@Size(max = 6)
	@Column(name = "numero_end")
	private String numeroEnd;

	@Size(max = 30)
	@Column(name = "complemento_end")
	private String complementoEnd;

	@Size(max = 30)
	private String bairro;

	public Long getIdFornecedor() {
		return idFornecedor;
	}

	public void setIdFornecedor(Long idFornecedor) {
		this.idFornecedor = idFornecedor;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getRua() {
		return rua;
	}

	public void setRua(String rua) {
		this.rua = rua;
	}

	public String getNumeroEnd() {
		return numeroEnd;
	}

	public void setNumeroEnd(String numeroEnd) {
		this.numeroEnd = numeroEnd;
	}

	public String getComplementoEnd() {
		return complementoEnd;
	}

	public void setComplementoEnd(String complementoEnd) {
		this.complementoEnd = complementoEnd;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	@NotBlank
	@Size(min = 1, max = 100)
	@Column(name = "nome_empresarial")
	private String nomeEmpresarial;

	
	private String cnpj;

	@NotBlank
	@Size(min = 1, max = 100)
	@Column(name = "nome_responsavel")
	private String nomeResponsavel;

	public String getNomeEmpresarial() {
		return nomeEmpresarial;
	}

	public void setNomeEmpresarial(String nomeEmpresarial) {
		this.nomeEmpresarial = nomeEmpresarial;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getNomeResponsavel() {
		return nomeResponsavel;
	}

	public void setNomeResponsavel(String nomeResponsavel) {
		this.nomeResponsavel = nomeResponsavel;
	}

	public String cnpj() {
		if (cnpj == null) {
			return cnpj;
		} else {
			try {
				MaskFormatter formatador = new MaskFormatter("##.###.###/####-##");
				formatador.setValueContainsLiteralCharacters(false);
				return formatador.valueToString(cnpj);
			} catch (ParseException e) {
				return cnpj;
			}
		}
	}

	@Override
	public String toString() {
		if (nomeEmpresarial != null) {
			return nomeEmpresarial;
		} else {
			return super.toString();
		}
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idFornecedor == null) ? 0 : idFornecedor.hashCode());
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
		Fornecedor other = (Fornecedor) obj;
		if (idFornecedor == null) {
			if (other.idFornecedor != null) {
				return false;
			}
		} else if (!idFornecedor.equals(other.idFornecedor)) {
			return false;
		}
		return true;
	}

	@Override
	public int compareTo(Fornecedor o) {
		return this.toString().compareTo(o.toString());
	}
}
