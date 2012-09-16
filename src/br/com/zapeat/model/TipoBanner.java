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
import br.com.zapeat.util.Constantes;

@Entity
@Table(name = "tipos_banners")
public class TipoBanner extends TSActiveRecordAb<TipoBanner> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2116048001652529251L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="tipos_banners_id")
	@SequenceGenerator(name="tipos_banners_id", sequenceName="tipos_banners_id_seq")
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
	
	public String getPrefixoImagem(){
		return Constantes.TIPO_BANNER_FULL.equals(getId()) ? Constantes.PREFIXO_IMAGEM_BANNER_FULL : 
			Constantes.TIPO_BANNER_THUMB.equals(getId()) ? Constantes.PREFIXO_IMAGEM_BANNER_THUMB : ""; 
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		TipoBanner other = (TipoBanner) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
