package br.com.zapeat.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.topsys.database.hibernate.TSActiveRecordAb;
import br.com.zapeat.util.Constantes;

@Entity
@Table(name = "imagens_promocoes")
public class ImagemPromocao extends TSActiveRecordAb<ImagemPromocao>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7797419767827957163L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="imagens_promocoes_id")
	@SequenceGenerator(name="imagens_promocoes_id", sequenceName="imagens_promocoes_id_seq")
	private Long id;
	
	@ManyToOne
	private Promocao promocao;
	
	private String imagem;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Promocao getPromocao() {
		return promocao;
	}

	public void setPromocao(Promocao promocao) {
		this.promocao = promocao;
	}

	public String getImagem() {
		return imagem;
	}

	public void setImagem(String imagem) {
		this.imagem = imagem;
	}

	public String getImagemFullView(){
		return Constantes.PASTA_DOWNLOAD + Constantes.PREFIXO_IMAGEM_PROMOCAO_FULL + getImagem();
	}
	
	public String getImagemThumbView(){
		return Constantes.PASTA_DOWNLOAD + Constantes.PREFIXO_IMAGEM_PROMOCAO_THUMB + getImagem();
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
		ImagemPromocao other = (ImagemPromocao) obj;
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
		if (promocao == null) {
			if (other.promocao != null)
				return false;
		} else if (!promocao.equals(other.promocao))
			return false;
		return true;
	}

}
