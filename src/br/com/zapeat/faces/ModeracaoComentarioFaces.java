package br.com.zapeat.faces;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.topsys.util.TSUtil;
import br.com.topsys.web.faces.TSMainFaces;
import br.com.zapeat.model.ComentarioCarroChefe;
import br.com.zapeat.model.ComentarioFornecedor;
import br.com.zapeat.model.ComentarioPromocao;

@ViewScoped
@ManagedBean(name = "moderacaoComentarioFaces")
public class ModeracaoComentarioFaces extends TSMainFaces {

	private String comentario;
	private Integer opcao;

	private ComentarioCarroChefe comentarioCarroChefe;
	private ComentarioFornecedor comentarioFornecedor;
	private ComentarioPromocao comentarioPromocao;

	private List<ComentarioFornecedor> comentariosFornecedor;
	private List<ComentarioCarroChefe> comentariosCarroChefe;
	private List<ComentarioPromocao> comentariosPromocao;

	public ModeracaoComentarioFaces() {

		this.clearFields();
	}

	@Override
	protected void clearFields() {

		this.setComentarioCarroChefe(new ComentarioCarroChefe());
		this.setComentarioFornecedor(new ComentarioFornecedor());
		this.setComentarioPromocao(new ComentarioPromocao());

		this.setOpcao(null);
		this.setComentario(null);

	}

	public String limpar() {

		this.clearFields();

		return null;
	}

	public String find() {

		if (!TSUtil.isEmpty(this.getOpcao())) {

			if (this.opcao.equals(1)) {

				this.comentariosCarroChefe = new ComentarioCarroChefe(this.comentario).findByModel("");

			} else if (this.opcao.equals(2)) {

				this.comentariosFornecedor = new ComentarioFornecedor(this.comentario).findByModel("");

			} else {

				this.comentariosPromocao = new ComentarioPromocao(this.comentario).findByModel("");
			}

		} else {

			super.addErrorMessage("Selecione uma das opções para realizar a pesquisa.");
		}

		return null;
	}

	public List<ComentarioFornecedor> getComentariosFornecedor() {
		return comentariosFornecedor;
	}

	public void setComentariosFornecedor(List<ComentarioFornecedor> comentariosFornecedor) {
		this.comentariosFornecedor = comentariosFornecedor;
	}

	public List<ComentarioCarroChefe> getComentariosCarroChefe() {
		return comentariosCarroChefe;
	}

	public void setComentariosCarroChefe(List<ComentarioCarroChefe> comentariosCarroChefe) {
		this.comentariosCarroChefe = comentariosCarroChefe;
	}

	public List<ComentarioPromocao> getComentariosPromocao() {
		return comentariosPromocao;
	}

	public void setComentariosPromocao(List<ComentarioPromocao> comentariosPromocao) {
		this.comentariosPromocao = comentariosPromocao;
	}

	public ComentarioCarroChefe getComentarioCarroChefe() {
		return comentarioCarroChefe;
	}

	public void setComentarioCarroChefe(ComentarioCarroChefe comentarioCarroChefe) {
		this.comentarioCarroChefe = comentarioCarroChefe;
	}

	public ComentarioFornecedor getComentarioFornecedor() {
		return comentarioFornecedor;
	}

	public void setComentarioFornecedor(ComentarioFornecedor comentarioFornecedor) {
		this.comentarioFornecedor = comentarioFornecedor;
	}

	public ComentarioPromocao getComentarioPromocao() {
		return comentarioPromocao;
	}

	public void setComentarioPromocao(ComentarioPromocao comentarioPromocao) {
		this.comentarioPromocao = comentarioPromocao;
	}

	public Integer getOpcao() {
		return TSUtil.tratarInteger(opcao);
	}

	public void setOpcao(Integer opcao) {
		this.opcao = opcao;
	}

	public String getComentario() {
		return TSUtil.tratarString(this.comentario);
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

}
