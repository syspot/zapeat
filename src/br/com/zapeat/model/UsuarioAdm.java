package br.com.zapeat.model;

import java.util.ArrayList;
import java.util.List;

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
@Table(name = "usuarios_adm")
public class UsuarioAdm extends TSActiveRecordAb<UsuarioAdm> {

	@Id
	@SequenceGenerator(name = "USUARIOS_ADM_ID_SEQ", sequenceName = "usuarios_adm_id_seq", allocationSize = 1)
	@GeneratedValue(generator = "USUARIOS_ADM_ID_SEQ", strategy = GenerationType.SEQUENCE)
	private Long id;

	private String login;

	private String senha;

	@ManyToOne
	private Fornecedor fornecedor;

	@Override
	public List<UsuarioAdm> findByModel(String... fieldsOrderBy) {

		StringBuilder query = new StringBuilder();

		query.append(" from UsuarioAdm u where 1 = 1 ");

		if (!TSUtil.isEmpty(login)) {
			query.append("and u.login like ?");
		}

		if (!TSUtil.isEmpty(fornecedor) && !TSUtil.isEmpty(fornecedor.getId())) {
			query.append("and u.fornecedor = ?").append(" ");
		}

		List<Object> params = new ArrayList<Object>();

		if (!TSUtil.isEmpty(login)) {
			params.add("%" + login + "%");
		}

		if (!TSUtil.isEmpty(fornecedor) && !TSUtil.isEmpty(fornecedor.getId())) {
			params.add(fornecedor);
		}

		return super.find(query.toString(), params.toArray());
	}

	public Long getId() {
		return TSUtil.tratarLong(id);
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

}
