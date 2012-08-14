package br.com.zapeat.faces;

import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.zapeat.model.TipoBanner;

@ViewScoped
@ManagedBean(name = "tipoBannerFaces")
public class TipoBannerFaces extends CrudFaces<TipoBanner> {


	@PostConstruct
	protected void init() {
		this.clearFields();
		setFieldOrdem("descricao");
	}
	
	@Override
	public String limpar() {
		setCrudModel(new TipoBanner());
		getCrudModel().setFlagAtivo(Boolean.TRUE);
		setFlagAlterar(Boolean.FALSE);
		return null;
	}
	
	@Override
	public String limparPesquisa() {
		setCrudPesquisaModel(new TipoBanner());
		getCrudPesquisaModel().setFlagAtivo(Boolean.TRUE);
		setGrid(new ArrayList<TipoBanner>());
		return null;
	}

}
