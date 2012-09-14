package br.com.zapeat.faces;

import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.zapeat.model.Estabelecimento;

@ViewScoped
@ManagedBean(name = "estabelecimentoFaces")
public class EstabelecimentoFaces extends CrudFaces<Estabelecimento> {


	@PostConstruct
	protected void init() {
		this.clearFields();
		setFieldOrdem("descricao");
	}
	
	@Override
	public String limpar() {
		setCrudModel(new Estabelecimento());
		getCrudModel().setFlagAtivo(Boolean.TRUE);
		setFlagAlterar(Boolean.FALSE);
		return null;
	}
	
	@Override
	public String limparPesquisa() {
		setCrudPesquisaModel(new Estabelecimento());
		getCrudPesquisaModel().setFlagAtivo(Boolean.TRUE);
		setGrid(new ArrayList<Estabelecimento>());
		return null;
	}

}
