package br.com.zapeat.model;

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

@Entity
@Table(name = "imagens_promocoes")
public class ImagemPromocao extends TSActiveRecordAb<ImagemPromocao>{
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="imagens_promocoes_id")
	@SequenceGenerator(name="imagens_promocoes_id", sequenceName="imagens_promocoes_id_seq")
	private Long id;
	
	@ManyToOne
	private Promocao promocao;
	
	private String imagem;
	
	@Transient
	private UploadedFile uploadedFile;

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

	public UploadedFile getUploadedFile() {
		return uploadedFile;
	}

	public void setUploadedFile(UploadedFile uploadedFile) {
		this.uploadedFile = uploadedFile;
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
		return true;
	}
	
}
