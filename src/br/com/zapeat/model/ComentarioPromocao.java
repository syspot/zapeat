package br.com.zapeat.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.topsys.database.hibernate.TSActiveRecordAb;
import br.com.topsys.util.TSUtil;

@SuppressWarnings("serial")
@Entity
@Table(name = "comentarios_promocoes")
public class ComentarioPromocao extends TSActiveRecordAb<ComentarioPromocao> {

	@Id
	@SequenceGenerator(name = "COMENTARIOS_PROMOCOES_ID_SEQ", sequenceName = "comentarios_promocoes_id_seq", allocationSize = 1)
	@GeneratedValue(generator = "COMENTARIOS_PROMOCOES_ID_SEQ", strategy = GenerationType.SEQUENCE)
	private Long id;

	@ManyToOne
	private UsuarioAdm usuario;

	@ManyToOne
	private Promocao promocao;

	private String descricao;

	@Column(name = "data_cadastro")
	private Date dataCadastro;

	@Column(name = "flag_ativo")
	private Boolean flagAtivo;

	public ComentarioPromocao() {

	}

	public ComentarioPromocao(String descricao) {

		this.descricao = descricao;
	}

	public Long getId() {
		return id;
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

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
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
		ComentarioPromocao other = (ComentarioPromocao) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public Promocao getPromocao() {
		return promocao;
	}

	public void setPromocao(Promocao promocao) {
		this.promocao = promocao;
	}

	public Boolean getFlagAtivo() {
		return flagAtivo;
	}

	public void setFlagAtivo(Boolean flagAtivo) {
		this.flagAtivo = flagAtivo;
	}

	@Override
	public List<ComentarioPromocao> findByModel(String... fieldsOrderBy) {

		StringBuilder query = new StringBuilder();

		query.append(" from ComentarioPromocao c where 1 = 1 ");

		if (!TSUtil.isEmpty(descricao)) {
			query.append("and c.descricao like ?");
		}

		List<Object> params = new ArrayList<Object>();

		if (!TSUtil.isEmpty(descricao)) {
			params.add("%" + descricao + "%");
		}

		return super.find(query.toString(), null, params.toArray());
	}

	public UsuarioAdm getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioAdm usuario) {
		this.usuario = usuario;
	}

}
