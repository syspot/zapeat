package br.com.zapeat.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.topsys.database.hibernate.TSActiveRecordAb;
import br.com.topsys.util.TSUtil;
import br.com.zapeat.util.ZapeatUtil;
import br.com.zapeat.util.Utilitarios;

@Entity
@Table(name = "usuarios")
public class Usuario extends TSActiveRecordAb<Usuario> {

	@Id
	@SequenceGenerator(name = "USUARIOS_ID_SEQ", sequenceName = "usuarios_id_seq", allocationSize = 1)
	@GeneratedValue(generator="USUARIOS_ID_SEQ", strategy=GenerationType.SEQUENCE)
	private Long id;
	
	private String nome;
	
	private String login;
	
	private String senha;	
	
	private String email;
	
	@Transient
	private String confirmaSenha;
	
	@Column(name="flag_ativo")
	private Boolean flagAtivo;	
	
	@ManyToOne
	@JoinColumn(name = "grupo_id")
	private Grupo grupo;

	public Usuario() {

	}

	public Long getId() {
		return TSUtil.tratarLong(id);
	}

	public void setId(Long id) {
		this.id = TSUtil.tratarLong(id);
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		
		return senha;
		
	}

	public void setSenha(String senha) {
		
		this.senha = senha;
		
	}

	public Boolean getFlagAtivo() {
		return flagAtivo;
	}

	public void setFlagAtivo(Boolean flagAtivo) {
		this.flagAtivo = flagAtivo;
	}

	public String getSituacao() {
		return Utilitarios.getSituacao(flagAtivo);
	}
	
	

	public String getConfirmaSenha() {
		return confirmaSenha;
	}

	public void setConfirmaSenha(String confirmaSenha) {
		this.confirmaSenha = confirmaSenha;
	}
	
	@Override
	public List<Usuario> findByModel(String... fieldsOrderBy) {
		
		StringBuilder query = new StringBuilder();
		
		query.append(" from Usuario u where lower(u.nome) like coalesce(?, lower(u.login)) and lower(u.login) like coalesce(?, lower(u.login))");
		
		List<Object> params = new ArrayList<Object>();
		
		params.add(ZapeatUtil.tratarString(nome));
		params.add(ZapeatUtil.tratarString(login));
		
		return super.find(query.toString(), params.toArray());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (login == null) {
			if (other.login != null)
				return false;
		} else if (!login.equals(other.login))
			return false;
		return true;
	}

	public Grupo getGrupo() {
		return grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	

}
