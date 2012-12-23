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
import br.com.topsys.util.TSUtil;

@Entity
@Table(name = "fornecedores_formas_pagamentos")
public class FornecedorFormaPagamento extends TSActiveRecordAb<FornecedorFormaPagamento> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -384645811936920747L;

	@Id
	@SequenceGenerator(name = "fornecedores_formas_pagamentos_id_seq", sequenceName = "fornecedores_formas_pagamentos_id_seq")
	@GeneratedValue(generator = "fornecedores_formas_pagamentos_id_seq", strategy = GenerationType.SEQUENCE)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "fornecedor_id")
	private Fornecedor fornecedor;

	@ManyToOne
	@JoinColumn(name = "forma_pagamento_id")
	private FormaPagamento formaPagamento;

	public FornecedorFormaPagamento() {
	}
	
	public FornecedorFormaPagamento(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

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

	public FormaPagamento getFormaPagamento() {
		return formaPagamento;
	}

	public void setFormaPagamento(FormaPagamento formaPagamento) {
		this.formaPagamento = formaPagamento;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result	+ ((formaPagamento == null) ? 0 : formaPagamento.hashCode());
		result = prime * result + ((fornecedor == null) ? 0 : fornecedor.hashCode());
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
		FornecedorFormaPagamento other = (FornecedorFormaPagamento) obj;
		if (formaPagamento == null) {
			if (other.formaPagamento != null)
				return false;
		} else if (!formaPagamento.equals(other.formaPagamento))
			return false;
		if (fornecedor == null) {
			if (other.fornecedor != null)
				return false;
		} else if (!fornecedor.equals(other.fornecedor))
			return false;
		return true;
	}

	@Override
	public List<FornecedorFormaPagamento> findByModel(String... fieldsOrderBy) {

		StringBuilder query = new StringBuilder();

		query.append(" from FornecedorCategoria fc where 1 = 1 ");

		if (!TSUtil.isEmpty(fornecedor) && !TSUtil.isEmpty(fornecedor.getId())) {
			query.append("and fc.fornecedor.id = ? ");
		}

		List<Object> params = new ArrayList<Object>();

		if (!TSUtil.isEmpty(fornecedor) && !TSUtil.isEmpty(fornecedor.getId())) {
			params.add(fornecedor.getId());
		}

		return super.find(query.toString(), null, params.toArray());

	}
	
}

