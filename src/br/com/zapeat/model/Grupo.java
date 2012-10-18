package br.com.zapeat.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.topsys.database.hibernate.TSActiveRecordAb;
import br.com.topsys.util.TSUtil;

@Entity
@Table(name = "grupos")
public class Grupo extends TSActiveRecordAb<Grupo>  {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4967258305777024288L;

	@Id
	@SequenceGenerator(name = "GRUPOS_ID_SEQ", sequenceName = "grupos_id_seq", allocationSize = 1)
	@GeneratedValue(generator="GRUPOS_ID_SEQ", strategy=GenerationType.SEQUENCE)
	private Long id;
	
	private String descricao;
	
	@OneToMany(mappedBy = "grupo", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Permissao> permissoes;	
	
	public Long getId() {
		return TSUtil.tratarLong(id);
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<Permissao> getPermissoes() {
		return permissoes;
	}

	public void setPermissoes(List<Permissao> permissoes) {
		this.permissoes = permissoes;
	}
	
}
