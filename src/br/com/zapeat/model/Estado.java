package br.com.zapeat.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.topsys.database.hibernate.TSActiveRecordAb;
import br.com.topsys.util.TSUtil;
import br.com.zapeat.util.ZapeatUtil;

@Entity
@Table(name = "estados")
public class Estado extends TSActiveRecordAb<Estado> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7693009400955345338L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	private String sigla;

	public Long getId() {
		return TSUtil.tratarLong(id);
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
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
		Estado other = (Estado) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public List<Estado> findByModel(String... fieldsOrderBy) {

		StringBuilder query = new StringBuilder();

		query.append(" from Estado e where 1 = 1 ");

		if (!TSUtil.isEmpty(sigla)) {
			query.append("and ").append(ZapeatUtil.semAcento("e.sigla")).append(" like ").append(ZapeatUtil.semAcento("?")).append(" ");
		}

		List<Object> params = new ArrayList<Object>();

		if (!TSUtil.isEmpty(sigla)) {
			params.add(ZapeatUtil.tratarString(sigla));
		}

		return super.find(query.toString(), params.toArray());
	}

}
