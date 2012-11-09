package br.com.zapeat.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.topsys.database.hibernate.TSActiveRecordAb;
import br.com.topsys.util.TSUtil;
import br.com.zapeat.util.ZapeatUtil;

@SuppressWarnings("serial")
@Entity
@Table(name = "carros_chefes")
public class CarroChefe extends TSActiveRecordAb<CarroChefe> {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "carros_chefes_id")
	@SequenceGenerator(name = "carros_chefes_id", sequenceName = "carros_chefes_id_seq")
	private Long id;

	@ManyToOne
	private Fornecedor fornecedor;

	private String titulo;

	private String descricao;

	@Column(name = "flag_ativo")
	private Boolean flagAtivo;

	@OneToMany(mappedBy = "carroChefe", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ImagemCarroChefe> imagensCarrosChefes;

	public Long getId() {
		return TSUtil.tratarLong(id);
	}

	public void setId(Long id) {
		this.id = TSUtil.tratarLong(id);
	}

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
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

	public Boolean getFlagAtivo() {
		return flagAtivo;
	}

	public void setFlagAtivo(Boolean flagAtivo) {
		this.flagAtivo = flagAtivo;
	}

	public List<ImagemCarroChefe> getImagensCarrosChefes() {
		return imagensCarrosChefes;
	}

	public void setImagensCarrosChefes(List<ImagemCarroChefe> imagensCarrosChefes) {
		this.imagensCarrosChefes = imagensCarrosChefes;
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
		CarroChefe other = (CarroChefe) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public List<CarroChefe> findByModel(String... fieldsOrderBy) {

		StringBuilder query = new StringBuilder();

		query.append(" from CarroChefe ch where 1 = 1 ");

		if (!TSUtil.isEmpty(TSUtil.tratarString(titulo))) {
			query.append(" and ").append(ZapeatUtil.getStringParamSemAcento("ch.titulo"));
		}

		if (!TSUtil.isEmpty(TSUtil.tratarString(descricao))) {
			query.append(" and ").append(ZapeatUtil.getStringParamSemAcento("ch.descricao"));
		}

		if (!TSUtil.isEmpty(fornecedor) && !TSUtil.isEmpty(fornecedor.getId())) {
			query.append(" and ch.fornecedor.id = ? ");
		}

		if (!TSUtil.isEmpty(flagAtivo)) {
			query.append(" and ch.flagAtivo = ? ");
		}

		List<Object> params = new ArrayList<Object>();

		if (!TSUtil.isEmpty(TSUtil.tratarString(titulo))) {

			params.add(titulo);
		}

		if (!TSUtil.isEmpty(TSUtil.tratarString(descricao))) {

			params.add(descricao);
		}

		if (!TSUtil.isEmpty(fornecedor) && !TSUtil.isEmpty(fornecedor.getId())) {

			params.add(fornecedor.getId());
		}

		if (!TSUtil.isEmpty(flagAtivo)) {

			params.add(flagAtivo);
		}

		return super.find(query.toString(), "ch.titulo", params.toArray());

	}

	public List<CarroChefe> pesquisarPorFornecedor(Fornecedor fornecedor) {

		return super.find(" select ch from CarroChefe ch where ch.fornecedor.id = ? ", null, fornecedor.getId());
	}
}
