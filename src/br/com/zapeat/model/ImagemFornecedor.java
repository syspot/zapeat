package br.com.zapeat.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.topsys.database.hibernate.TSActiveRecordAb;
import br.com.topsys.util.TSUtil;
import br.com.zapeat.util.Constantes;

@Entity
@Table(name = "imagens_fornecedores")
public class ImagemFornecedor extends TSActiveRecordAb<ImagemFornecedor>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7797419767827957163L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="imagens_fornecedores_id")
	@SequenceGenerator(name="imagens_fornecedores_id", sequenceName="imagens_fornecedores_id_seq")
	private Long id;
	
	@ManyToOne
	private Fornecedor fornecedor;
	
	private String imagem;
	
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

	public String getImagem() {
		return imagem;
	}

	public void setImagem(String imagem) {
		this.imagem = imagem;
	}
	
	public String getImagemFullView(){
		return Constantes.PASTA_DOWNLOAD + Constantes.PREFIXO_IMAGEM_FORNECEDOR_FULL + getImagem();
	}
	
	public String getImagemThumbView(){
		return Constantes.PASTA_DOWNLOAD + Constantes.PREFIXO_IMAGEM_FORNECEDOR_THUMB + getImagem();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((fornecedor == null) ? 0 : fornecedor.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((imagem == null) ? 0 : imagem.hashCode());
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
		ImagemFornecedor other = (ImagemFornecedor) obj;
		if (fornecedor == null) {
			if (other.fornecedor != null)
				return false;
		} else if (!fornecedor.equals(other.fornecedor))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (imagem == null) {
			if (other.imagem != null)
				return false;
		} else if (!imagem.equals(other.imagem))
			return false;
		return true;
	}

}
