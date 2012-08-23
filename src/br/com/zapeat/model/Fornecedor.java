package br.com.zapeat.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.primefaces.model.UploadedFile;

import br.com.topsys.database.hibernate.TSActiveRecordAb;
import br.com.topsys.util.TSUtil;
import br.com.zapeat.util.Constantes;
import br.com.zapeat.util.ZapeatUtil;

@SuppressWarnings("serial")
@Entity
@Table(name = "fornecedores")
public class Fornecedor extends TSActiveRecordAb<Fornecedor> {

	@Id
	@SequenceGenerator(name = "FORNECEDORES_ID_SEQ", sequenceName = "fornecedores_id_seq", allocationSize = 1)
	@GeneratedValue(generator = "FORNECEDORES_ID_SEQ", strategy = GenerationType.SEQUENCE)
	private Long id;

	@Column(name = "razao_social")
	private String razaoSocial;

	private String cnpj;

	@Column(name = "nome_fantasia")
	private String nomeFantasia;

	private String cep;

	private String logradouro;

	private String numero;

	private String bairro;
	
	private String site;
	
	private String telefone;

	private Integer latitude;

	private Integer longitude;

	@Column(name = "logomarca")
	private String logoMarca;

	@Column(name = "flag_ativo")
	private Boolean flagAtivo;

	private String descricao;

	@Column(name = "horarios_funcionamento")
	private String horariosFuncionamento;

	private String twitter;

	private String facebook;

	@ManyToOne
	private Cidade cidade;

	@Transient
	private UploadedFile arquivo;

	@Transient
	private String caminhoLogoMarca;

	public String getCaminhoLogoMarca() {

		if (!TSUtil.isEmpty(this.logoMarca)) {

			this.caminhoLogoMarca = Constantes.PASTA_DOWNLOAD_TEMP + this.logoMarca;
		}

		return caminhoLogoMarca;
	}

	public void setCaminhoLogoMarca(String caminhoLogoMarca) {
		this.caminhoLogoMarca = caminhoLogoMarca;
	}

	public UploadedFile getArquivo() {
		return arquivo;
	}

	public void setArquivo(UploadedFile arquivo) {
		this.arquivo = arquivo;
	}

	public Long getId() {
		return TSUtil.tratarLong(id);
	}

	public void setId(Long id) {
		this.id = id;
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
		Fornecedor other = (Fornecedor) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public List<Fornecedor> findByModel(String... fieldsOrderBy) {

		StringBuilder query = new StringBuilder();

		query.append(" from Fornecedor f where 1 = 1 ");

		if (!TSUtil.isEmpty(razaoSocial)) {
			query.append("and ").append(ZapeatUtil.semAcento("f.razaoSocial")).append(" like ").append(ZapeatUtil.semAcento("?")).append(" ");
		}

		if (!TSUtil.isEmpty(cnpj)) {
			query.append("and ").append(ZapeatUtil.semAcento("f.cnpj")).append(" like ").append(ZapeatUtil.semAcento("?")).append(" ");
		}

		if (!TSUtil.isEmpty(nomeFantasia)) {
			query.append("and ").append(ZapeatUtil.semAcento("f.nomeFantasia")).append(" like ").append(ZapeatUtil.semAcento("?")).append(" ");
		}

		if (!TSUtil.isEmpty(cidade) && !TSUtil.isEmpty(cidade.getId())) {
			query.append("and f.cidade = ?").append(" ");
		}

		if (!TSUtil.isEmpty(bairro)) {
			query.append("and ").append(ZapeatUtil.semAcento("f.bairro")).append(" like ").append(ZapeatUtil.semAcento("?")).append(" ");
		}

		if (!TSUtil.isEmpty(logradouro)) {
			query.append("and ").append(ZapeatUtil.semAcento("f.logradouro")).append(" like ").append(ZapeatUtil.semAcento("?")).append(" ");
		}

		if (!TSUtil.isEmpty(numero)) {
			query.append("and ").append(ZapeatUtil.semAcento("f.numero")).append(" = ").append(ZapeatUtil.semAcento("?")).append(" ");
		}

		if (!TSUtil.isEmpty(cep)) {
			query.append("and ").append(ZapeatUtil.semAcento("f.cep")).append(" = ").append(ZapeatUtil.semAcento("?")).append(" ");
		}
		
		if (!TSUtil.isEmpty(flagAtivo)) {
			query.append("and f.flagAtivo = ?").append(" ");
		}

		List<Object> params = new ArrayList<Object>();

		if (!TSUtil.isEmpty(razaoSocial)) {
			params.add(ZapeatUtil.tratarString(razaoSocial));
		}

		if (!TSUtil.isEmpty(cnpj)) {
			params.add(ZapeatUtil.tratarString(cnpj));
		}

		if (!TSUtil.isEmpty(nomeFantasia)) {
			params.add(ZapeatUtil.tratarString(nomeFantasia));
		}

		if (!TSUtil.isEmpty(cidade) && !TSUtil.isEmpty(cidade.getId())) {
			params.add(cidade);
		}

		if (!TSUtil.isEmpty(bairro)) {
			params.add(ZapeatUtil.tratarString(bairro));
		}

		if (!TSUtil.isEmpty(logradouro)) {
			params.add(ZapeatUtil.tratarString(logradouro));
		}

		if (!TSUtil.isEmpty(numero)) {
			params.add(ZapeatUtil.tratarString(numero));
		}

		if (!TSUtil.isEmpty(cep)) {
			params.add(ZapeatUtil.tratarString(cep));
		}
		
		if (!TSUtil.isEmpty(flagAtivo)) {
			params.add(flagAtivo);
		}

		return super.find(query.toString(), params.toArray());

	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getNomeFantasia() {
		return nomeFantasia;
	}

	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public Integer getLatitude() {
		return latitude;
	}

	public void setLatitude(Integer latitude) {
		this.latitude = latitude;
	}

	public Integer getLongitude() {
		return longitude;
	}

	public void setLongitude(Integer longitude) {
		this.longitude = longitude;
	}

	public String getLogoMarca() {
		return logoMarca;
	}

	public void setLogoMarca(String logoMarca) {
		this.logoMarca = logoMarca;
	}

	public Boolean getFlagAtivo() {
		return flagAtivo;
	}

	public void setFlagAtivo(Boolean flagAtivo) {
		this.flagAtivo = flagAtivo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getHorariosFuncionamento() {
		return horariosFuncionamento;
	}

	public void setHorariosFuncionamento(String horariosFuncionamento) {
		this.horariosFuncionamento = horariosFuncionamento;
	}

	public String getTwitter() {
		return twitter;
	}

	public void setTwitter(String twitter) {
		this.twitter = twitter;
	}

	public String getFacebook() {
		return facebook;
	}

	public void setFacebook(String facebook) {
		this.facebook = facebook;
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

}
