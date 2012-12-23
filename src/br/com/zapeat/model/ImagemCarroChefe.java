package br.com.zapeat.model;

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
import br.com.zapeat.util.Constantes;

@Entity
@Table(name = "imagens_carros_chefes")
public class ImagemCarroChefe extends TSActiveRecordAb<ImagemCarroChefe>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7797419767827957163L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="imagens_carros_chefes_id")
	@SequenceGenerator(name="imagens_carros_chefes_id", sequenceName="imagens_carros_chefes_id_seq")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "carro_chefe_id")
	private CarroChefe carroChefe;
	
	private String imagem;
	
	public Long getId() {
		return TSUtil.tratarLong(id);
	}

	public void setId(Long id) {
		this.id = TSUtil.tratarLong(id);
	}

	public CarroChefe getCarroChefe() {
		return carroChefe;
	}

	public void setCarroChefe(CarroChefe carroChefe) {
		this.carroChefe = carroChefe;
	}

	public String getImagem() {
		return imagem;
	}

	public void setImagem(String imagem) {
		this.imagem = imagem;
	}
	
	public String getImagemFullView(){
		return Constantes.PASTA_DOWNLOAD + Constantes.PREFIXO_IMAGEM_CARRO_CHEFE_FULL + getImagem();
	}
	
	public String getImagemThumbView(){
		return Constantes.PASTA_DOWNLOAD + Constantes.PREFIXO_IMAGEM_CARRO_CHEFE_THUMB + getImagem();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((carroChefe == null) ? 0 : carroChefe.hashCode());
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
		ImagemCarroChefe other = (ImagemCarroChefe) obj;
		if (carroChefe == null) {
			if (other.carroChefe != null)
				return false;
		} else if (!carroChefe.equals(other.carroChefe))
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
