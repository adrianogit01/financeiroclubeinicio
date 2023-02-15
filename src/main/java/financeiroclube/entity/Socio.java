package financeiroclube.entity;

import java.io.Serializable;
import java.text.ParseException;
import java.time.LocalDate;

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

import org.springframework.format.annotation.DateTimeFormat;

@SuppressWarnings("serial")
@Entity
@Table(name = "socios")
public class Socio implements Serializable, Comparable<Socio> {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_socio")
	private Long idSocio;
	
	@NotBlank
	@Size(min = 1, max = 50)
	private String username;

	@NotBlank
	@Size(min = 4, max = 100)
	private String password;

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

	@NotBlank
	@Size(min = 1, max = 100)
	private String sobrenome;

	private String cpf;

	@Size(max = 45)
	@Column(name="nome_mae")
	private String nomeMae;

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate nascimento;

	public Long getIdSocio() {
		return idSocio;
	}

	public void setIdSocio(Long idSocio) {
		this.idSocio = idSocio;
	}
	
		
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public String getSobrenome() {
		return sobrenome;
	}

	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getNomeMae() {
		return nomeMae;
	}

	public void setNomeMae(String nomeMae) {
		this.nomeMae = nomeMae;
	}

	public LocalDate getNascimento() {
		return nascimento;
	}

	public void setNascimento(LocalDate nascimento) {
		this.nascimento = nascimento;
	}

	public String cpf() {
		if (cpf == null) {
			return null;
		} else {
			try {
				MaskFormatter formatador = new MaskFormatter("###.###.###-##");
				formatador.setValueContainsLiteralCharacters(false);
				return formatador.valueToString(cpf);
			} catch (ParseException e) {
				return cpf;
			}
		}
	}

	public String toString() {
		if (sobrenome != null) {
			return getNome() + " " + sobrenome;
		} else {
			return super.toString();
		}

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idSocio == null) ? 0 : idSocio.hashCode());
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
		Socio other = (Socio) obj;
		if (idSocio == null) {
			if (other.idSocio != null) {
				return false;
			}
		} else if (!idSocio.equals(other.idSocio)) {
			return false;
		}
		return true;
	}

	@Override
	public int compareTo(Socio o) {
		return this.toString().compareTo(o.toString());
	}

}
