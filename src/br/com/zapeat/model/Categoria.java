package br.com.zapeat.model;

import java.math.BigInteger;
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
import br.com.zapeat.util.Constantes;

@Entity
@Table(name = "categorias")
public class Categoria extends TSActiveRecordAb<Categoria> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 842205665885941846L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="categorias_id")
	@SequenceGenerator(name="categorias_id", sequenceName="categoria_id_seq")
	private Long id;

	private String descricao;

	@Column(name = "flag_ativo")
	private Boolean flagAtivo;
	
	private String imagem;
	
	@Column(name = "flag_destaque")
	private Boolean flagDestaque;
	
	public Long getId() {
		return TSUtil.tratarLong(id);
	}

	public void setId(Long id) {
		this.id = TSUtil.tratarLong(id);
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

	public String getImagem() {
		return imagem;
	}

	public void setImagem(String imagem) {
		this.imagem = imagem;
	}

	public Boolean getFlagDestaque() {
		return flagDestaque;
	}

	public void setFlagDestaque(Boolean flagDestaque) {
		this.flagDestaque = flagDestaque;
	}
	
	public String getImagemView(){
		return TSUtil.isEmpty(this.imagem) ? this.imagem : Constantes.PASTA_DOWNLOAD + Constantes.PREFIXO_IMAGEM_CATEGORIA + imagem;
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
		Categoria other = (Categoria) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	public BigInteger obterTotalDestaque(){
		return ((Model) super.getBySQL(Model.class, new String[]{"qtd"}, "select count(*) as qtd from categorias c where c.flag_destaque")).getQtd();
	}
	
	public List<Categoria> pesquisarCategoriasAtivas(){
		return super.find(" from Categoria where flagAtivo = true", "descricao");
	}
	
}
