package br.com.zapeat.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.topsys.database.hibernate.TSActiveRecordAb;
import br.com.topsys.util.TSUtil;
import br.com.zapeat.util.ZapeatUtil;

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
	
	private String titulo;
	
	private String descricao;
	
	private Date inicio;
	
	private Date fim;
	
	@Column(name = "preco_original")
	private Double precoOriginal;
	
	@Column(name = "preco_promocional")
	private Double precoPromocional;
	
	@OneToMany(mappedBy = "promocao", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ImagemPromocao> imagensPromocoes;
	
	@Column(name = "imagem_thumb")
	private String imagemThumb;

	public Long getId() {
		return TSUtil.tratarLong(id);
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
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
		return ZapeatUtil.tratarDouble(precoOriginal);
	}

	public void setPrecoOriginal(Double precoOriginal) {
		this.precoOriginal = precoOriginal;
	}

	public Double getPrecoPromocional() {
		return ZapeatUtil.tratarDouble(precoPromocional);
	}

	public void setPrecoPromocional(Double precoPromocional) {
		this.precoPromocional = precoPromocional;
	}

	public List<ImagemPromocao> getImagensPromocoes() {
		return imagensPromocoes;
	}

	public void setImagensPromocoes(List<ImagemPromocao> imagensPromocoes) {
		this.imagensPromocoes = imagensPromocoes;
	}

	public String getImagemThumb() {
		return imagemThumb;
	}

	public void setImagemThumb(String imagemThumb) {
		this.imagemThumb = imagemThumb;
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
	
	@Override
	public List<Promocao> findByModel(String... fieldsOrderBy) {

		StringBuilder query = new StringBuilder();

		query.append(" from Promocao p where 1 = 1 ");

		if (!TSUtil.isEmpty(tipoPromocao) && !TSUtil.isEmpty(tipoPromocao.getId())) {
			query.append(" and tipoPromocao.id = ? ");
		}
		
		if (!TSUtil.isEmpty(fornecedor) && !TSUtil.isEmpty(fornecedor.getId())) {
			query.append(" and fornecedor.id = ? ");
		}

		if (!TSUtil.isEmpty(descricao)) {
			query.append(" and ").append(ZapeatUtil.getStringParamSemAcento("descricao"));
		}
		
		if (!TSUtil.isEmpty(titulo)) {
			query.append(" and ").append(ZapeatUtil.getStringParamSemAcento("titulo"));
		}

		if (!TSUtil.isEmpty(inicio)) {
			query.append(" and p.inicio = ? ");
		}

		if (!TSUtil.isEmpty(fim)) {
			query.append(" and p.fim = ? ");
		}

		if (!TSUtil.isEmpty(getPrecoOriginal())) {
			query.append(" and p.precoOriginal = ? ");
		}

		if (!TSUtil.isEmpty(getPrecoPromocional())) {
			query.append(" and p.precoPromocional = ? ");
		}

		List<Object> params = new ArrayList<Object>();
		
		if (!TSUtil.isEmpty(tipoPromocao) && !TSUtil.isEmpty(tipoPromocao.getId())) {
			params.add(tipoPromocao.getId());
		}
		
		if (!TSUtil.isEmpty(fornecedor) && !TSUtil.isEmpty(fornecedor.getId())) {
			params.add(fornecedor.getId());
		}

		if (!TSUtil.isEmpty(descricao)) {
			params.add(ZapeatUtil.tratarString(descricao));
		}
		
		if (!TSUtil.isEmpty(titulo)) {
			params.add(ZapeatUtil.tratarString(titulo));
		}

		if (!TSUtil.isEmpty(inicio)) {
			params.add(inicio);
		}

		if (!TSUtil.isEmpty(fim)) {
			params.add(fim);
		}

		if (!TSUtil.isEmpty(getPrecoOriginal())) {
			params.add(getPrecoOriginal());
		}

		if (!TSUtil.isEmpty(getPrecoPromocional())) {
			params.add(getPrecoPromocional());
		}

		return super.find(query.toString(), params.toArray());

	}

}
