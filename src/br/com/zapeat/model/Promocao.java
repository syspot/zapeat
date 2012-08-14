package br.com.zapeat.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.topsys.database.hibernate.TSActiveRecordAb;
import br.com.topsys.util.TSUtil;

@Entity
@SuppressWarnings("serial")
@Table(name = "promocoes")
public class Promocao extends TSActiveRecordAb<Promocao> {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="promocoes_id")
	@SequenceGenerator(name="promocoes_id", sequenceName="promocoes_id_seq")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "tipo_promocao_id")
	private TipoPromocao tipoPromocao;
	
	@ManyToOne
	private Fornecedor fornecedor;
	
	private String descricao;
	
	private Date inicio;
	
	private Date fim;
	
	@Column(name = "preco_original")
	private Double precoOriginal;
	
	@Column(name = "preco_promocional")
	private Double precoPromocional;

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


	public TipoPromocao getTipoPromocao() {
		return tipoPromocao;
	}

	public void setTipoPromocao(TipoPromocao tipoPromocao) {
		this.tipoPromocao = tipoPromocao;
	}

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

	public Date getInicio() {
		return inicio;
	}

	public void setInicio(Date inicio) {
		this.inicio = inicio;
	}

	public Date getFim() {
		return fim;
	}

	public void setFim(Date fim) {
		this.fim = fim;
	}

	public Double getPrecoOriginal() {
		return precoOriginal;
	}

	public void setPrecoOriginal(Double precoOriginal) {
		this.precoOriginal = precoOriginal;
	}

	public Double getPrecoPromocional() {
		return precoPromocional;
	}

	public void setPrecoPromocional(Double precoPromocional) {
		this.precoPromocional = precoPromocional;
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
		Promocao other = (Promocao) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
