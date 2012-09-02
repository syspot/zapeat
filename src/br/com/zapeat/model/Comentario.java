package br.com.zapeat.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.topsys.database.hibernate.TSActiveRecordAb;

@Entity
@SuppressWarnings("serial")
@Table(name = "comentarios")
public class Comentario extends TSActiveRecordAb<Comentario> {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="comentarios_id")
	@SequenceGenerator(name="comentarios_id", sequenceName="comentarios_id_seq")
	private Long id;

	@ManyToOne
	private Usuario usuario;

	@ManyToOne
	private Fornecedor fornecedor;
	
	private String descricao;
	
	@Column(name = "data_cadastro")
	private Date dataCadastro;
	
	@Column(name = "flag_indica_atendimento")
	private Boolean flagIndicaAtendimento;
	
	@Column(name = "flag_indica_promocao")
	private Boolean flagIndicaPromocao;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
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

	public Boolean getFlagIndicaAtendimento() {
		return flagIndicaAtendimento;
	}

	public void setFlagIndicaAtendimento(Boolean flagIndicaAtendimento) {
		this.flagIndicaAtendimento = flagIndicaAtendimento;
	}

	public Boolean getFlagIndicaPromocao() {
		return flagIndicaPromocao;
	}

	public void setFlagIndicaPromocao(Boolean flagIndicaPromocao) {
		this.flagIndicaPromocao = flagIndicaPromocao;
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
		Comentario other = (Comentario) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
