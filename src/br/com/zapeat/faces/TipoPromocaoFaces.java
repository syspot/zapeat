package br.com.zapeat.faces;

import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.zapeat.model.TipoPromocao;

@ViewScoped
@ManagedBean(name = "tipoPromocaoFaces")
public class TipoPromocaoFaces extends CrudFaces<TipoPromocao> {


	@PostConstruct
	protected void init() {
		this.clearFields();
		setFieldOrdem("descricao");
	}
	
	@Override
	public String limpar() {
		setCrudModel(new TipoPromocao());
		getCrudModel().setFlagAtivo(Boolean.TRUE);
		setFlagAlterar(Boolean.FALSE);
		return null;
	}
	
	@Override
	public String limparPesquisa() {
		setCrudPesquisaModel(new TipoPromocao());
		getCrudPesquisaModel().setFlagAtivo(Boolean.TRUE);
		setGrid(new ArrayList<TipoPromocao>());
		return null;
	}

}
