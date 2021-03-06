package br.com.zapeat.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import br.com.topsys.util.TSDateUtil;
import br.com.topsys.util.TSParseUtil;
import br.com.topsys.util.TSUtil;

@Entity
@Table(name = "comentarios_carros_chefes")
public class ComentarioCarroChefe extends TSActiveRecordAb<ComentarioCarroChefe> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4673210545372700603L;

	@Id
	@SequenceGenerator(name = "COMENTARIOS_CARROS_CHEFES_ID_SEQ", sequenceName = "comentarios_carros_chefes_id_seq", allocationSize = 1)
	@GeneratedValue(generator = "COMENTARIOS_CARROS_CHEFES_ID_SEQ", strategy = GenerationType.SEQUENCE)
	private Long id;

	@ManyToOne
	private UsuarioSite usuario;

	@ManyToOne
	@JoinColumn(name = "carro_chefe_id")
	private CarroChefe carroChefe;

	private String descricao;

	@Column(name = "data_cadastro")
	private Date dataCadastro;

	@Column(name = "flag_ativo")
	private Boolean flagAtivo;

	public ComentarioCarroChefe() {

	}

	public ComentarioCarroChefe(String descricao) {
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

	public CarroChefe getCarroChefe() {
		return carroChefe;
	}

	public void setCarroChefe(CarroChefe carroChefe) {
		this.carroChefe = carroChefe;
	}

	public Boolean getFlagAtivo() {
		return flagAtivo;
	}

	public void setFlagAtivo(Boolean flagAtivo) {
		this.flagAtivo = flagAtivo;
	}

	public UsuarioSite getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioSite usuario) {
		this.usuario = usuario;
	}
	
	public String getImagemTable(){
		return getFlagAtivo() ? "checkmark.png" : "delete.png";
	}

	public String getImagemTitle(){
		return getFlagAtivo() ? "Inativar" : "Ativar";
	}
	
	public String getDataCadastroFormatada(){
		return TSParseUtil.dateToString(getDataCadastro(), TSDateUtil.DD_MM_YYYY_HH_MM);
	}
	
	public String getResumoDescricao(){
		return getDescricao().length() > 100 ? getDescricao().substring(0, 100) : getDescricao();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((usuario == null) ? 0 : usuario.hashCode());
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
		ComentarioCarroChefe other = (ComentarioCarroChefe) obj;
		if (usuario == null) {
			if (other.usuario != null)
				return false;
		} else if (!usuario.equals(other.usuario))
			return false;
		return true;
	}

	
	@Override
	public List<ComentarioCarroChefe> findByModel(String... fieldsOrderBy) {

		StringBuilder query = new StringBuilder();

		query.append(" from ComentarioCarroChefe c where 1 = 1 ");

		if (!TSUtil.isEmpty(descricao)) {
			query.append("and c.descricao like ?");
		}

		List<Object> params = new ArrayList<Object>();

		if (!TSUtil.isEmpty(descricao)) {
			params.add("%" + descricao + "%");
		}

		return super.find(query.toString(), "c.dataCadastro", params.toArray());
	}

}
