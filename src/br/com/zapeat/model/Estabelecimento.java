package br.com.zapeat.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.topsys.database.hibernate.TSActiveRecordAb;
import br.com.topsys.util.TSUtil;

@Entity
@Table(name = "estabelecimentos")
public class Estabelecimento extends TSActiveRecordAb<Estabelecimento> {

	private static final long serialVersionUID = -2199957366992734382L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="estabelecimentos_id")
	@SequenceGenerator(name="estabelecimentos_id", sequenceName="estabelecimentos_id_seq")
	private Long id;

	private String descricao;
	
	@Column(name = "flag_ativo")
	private Boolean flagAtivo;
	
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

	public Boolean getFlagAtivo() {
		return flagAtivo;
	}

	public void setFlagAtivo(Boolean flagAtivo) {
		this.flagAtivo = flagAtivo;
	}


}
