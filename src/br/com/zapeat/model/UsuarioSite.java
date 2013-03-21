package br.com.zapeat.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.topsys.database.hibernate.TSActiveRecordAb;
import br.com.topsys.util.TSUtil;
import br.com.zapeat.util.Utilitarios;

@Entity
@Table(name = "usuarios_site")
public class UsuarioSite extends TSActiveRecordAb<UsuarioSite> {


	/**
	 * 
	 */
	private static final long serialVersionUID = -5410280427799543796L;

	@Id
	@SequenceGenerator(name = "USUARIOS_SITE_ID_SEQ", sequenceName = "usuarios_site_id_seq", allocationSize = 1)
	@GeneratedValue(generator="USUARIOS_SITE_ID_SEQ", strategy=GenerationType.SEQUENCE)
	private Long id;
	
	private String nome;
	
	private String email;
	
	@Column(name="flag_ativo")
	private Boolean flagAtivo;	

	public UsuarioSite() {

	}
	
	public UsuarioSite(Boolean flagAtivo) {
		this.flagAtivo = flagAtivo;
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

	public Boolean getFlagAtivo() {
		return flagAtivo;
	}

	public void setFlagAtivo(Boolean flagAtivo) {
		this.flagAtivo = flagAtivo;
	}

	public String getSituacao() {
		return Utilitarios.getSituacao(flagAtivo);
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	@Override
	public List<UsuarioSite> findByModel(String... fieldsOrderBy) {
		
		StringBuilder query = new StringBuilder();
		
		query.append(" from UsuarioSite us where 1 = 1 ");
		
		query.append(!TSUtil.isEmpty(flagAtivo) ? flagAtivo ? " and us.flagAtivo = true" : " and (us.flagAtivo = false or us.flagAtivo is null) " : "");
		
		return super.find(query.toString(), "us.nome");
	}

}
