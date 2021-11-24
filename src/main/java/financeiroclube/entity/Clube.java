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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@SuppressWarnings("serial")
@Entity
@Table(name = "clubes")
public class Clube implements Serializable, Comparable<Clube> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_clube")
	private Long idClube;

	@NotBlank
	@Size(min = 1, max = 50)
	private String nome;

	
	private String cnpj;

	@Email
	@Size(max = 100)
	private String email;

	@Size(max = 15)
	private String telefone;

	@Size(max = 15)
	private String celular;

	@NotBlank
	@Size(min = 1, max = 100)
	private String rua;

	@NotBlank
	@Size(min = 1, max = 6)
	@Column(name = "numero_end")
	private String numeroEnd;

	@Size(max = 30)
	@Column(name = "complemento_end")
	private String complementoEnd;

	@NotBlank
	@Size(min = 1, max = 30)
	private String bairro;

	@OneToOne(mappedBy = "clube", fetch = FetchType.LAZY)
	private Usuario funcionario;


	@OneToMany(mappedBy = "clube", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
	@OrderBy(value = "nome")
	private List<Socio> socios = new ArrayList<>();
	
	@OneToMany(mappedBy = "clube", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
	@OrderBy(value = "nome")
	private List<Fornecedor> fornecedores = new ArrayList<>();

	@OneToMany(mappedBy = "clube", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
	@OrderBy(value = "sigla")
	private List<Cofre> cofres = new ArrayList<>();

	@OneToMany(mappedBy = "clube", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
	@OrderBy(value = "ordem")
	private List<Categoria> categorias = new ArrayList<>();

	@OneToMany(mappedBy = "clube", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
	@OrderBy(value = "dataEmissao desc, socio, numero, parcela")
	private List<Pendencia> pendencias = new ArrayList<>();

	@OneToMany(mappedBy = "clube", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
	@OrderBy(value = "inicio desc")
	private List<Periodo> periodos = new ArrayList<>();

	public Long getIdClube() {
		return idClube;
	}

	public void setIdClube(Long idClube) {
		this.idClube = idClube;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
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

	public Usuario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Usuario funcionario) {
		this.funcionario = funcionario;
	}

	public List<Socio> getSocios() {
		return socios;
	}

	public void setSocios(List<Socio> socios) {
		this.socios = socios;
	}
	
	public List<Fornecedor> getFornecedores() {
		return fornecedores;
	}

	public void setFornecedores(List<Fornecedor> fornecedores) {
		this.fornecedores = fornecedores;
	}

	public List<Cofre> getCofres() {
		return cofres;
	}

	public void setCofres(List<Cofre> cofres) {
		this.cofres = cofres;
	}

	public List<Categoria> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<Categoria> categorias) {
		this.categorias = categorias;
	}

	public List<Pendencia> getPendencias() {
		return pendencias;
	}

	public void setPendencias(List<Pendencia> pendencias) {
		this.pendencias = pendencias;
	}

	public List<Periodo> getPeriodos() {
		return periodos;
	}

	public void setPeriodos(List<Periodo> periodos) {
		this.periodos = periodos;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idClube == null) ? 0 : idClube.hashCode());
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
		Clube other = (Clube) obj;
		if (idClube == null) {
			if (other.idClube != null) {
				return false;
			}
		} else if (!idClube.equals(other.idClube)) {
			return false;
		}
		return true;
	}

	@Override
	public int compareTo(Clube o) {
		return this.nome.compareTo(o.getNome());
	}

}
