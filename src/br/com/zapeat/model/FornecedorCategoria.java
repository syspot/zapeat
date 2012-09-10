package br.com.zapeat.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.topsys.database.hibernate.TSActiveRecordAb;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.util.TSUtil;

@SuppressWarnings("serial")
@Entity
@Table(name = "fornecedores_categorias")
public class FornecedorCategoria extends TSActiveRecordAb<FornecedorCategoria> {

	@Id
	@SequenceGenerator(name = "FORNECEDORES_CATEGORIAS_ID_SEQ", sequenceName = "fornecedores_categorias_id_seq", allocationSize = 1)
	@GeneratedValue(generator = "FORNECEDORES_CATEGORIAS_ID_SEQ", strategy = GenerationType.SEQUENCE)
	private Long id;

	@ManyToOne()
	@JoinColumn(name = "fornecedor_id")
	private Fornecedor fornecedor;

	@ManyToOne()
	@JoinColumn(name = "categoria_id")
	private Categoria categoria;

	private Integer prioridade;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Integer getPrioridade() {
		return prioridade;
	}

	public void setPrioridade(Integer prioridade) {
		this.prioridade = prioridade;
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
		FornecedorCategoria other = (FornecedorCategoria) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public List<FornecedorCategoria> findByModel(String... fieldsOrderBy) {

		StringBuilder query = new StringBuilder();

		query.append(" from FornecedorCategoria fc where 1 = 1 ");

		if (!TSUtil.isEmpty(fornecedor) && !TSUtil.isEmpty(fornecedor.getId())) {
			query.append("and fc.fornecedor = ?").append(" ");
		}

		List<Object> params = new ArrayList<Object>();

		if (!TSUtil.isEmpty(fornecedor) && !TSUtil.isEmpty(fornecedor.getId())) {
			params.add(fornecedor);
		}

		return super.find(query.toString(), params.toArray());

	}

	public int delete(String hql, Object... objects) throws TSApplicationException {

		return super.delete(hql, objects);
	}

}
