package br.com.zapeat.faces;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import br.com.zapeat.model.Fornecedor;
import br.com.zapeat.model.Promocao;
import br.com.zapeat.model.TipoPromocao;

@ViewScoped
@ManagedBean(name = "promocaoFaces")
public class PromocaoFaces extends CrudFaces<Promocao> {

	private List<SelectItem> tiposPromocoes;
	private List<SelectItem> fornecedores;

	@PostConstruct
	protected void init() {
		this.clearFields();
		this.initCombos();
		setFieldOrdem("descricao");
	}
	
	private void initCombos(){
		this.tiposPromocoes = super.initCombo(new TipoPromocao().findAll(), "id", "descricao");
		this.fornecedores = super.initCombo(new Fornecedor().findAll(), "id", "descricao");
	}
	
	@Override
	public String limpar() {
		setCrudModel(new Promocao());
		getCrudModel().setFornecedor(new Fornecedor());
		getCrudModel().setTipoPromocao(new TipoPromocao());
		setFlagAlterar(Boolean.FALSE);
		return null;
	}
	
	@Override
	public String limparPesquisa() {
		setCrudPesquisaModel(new Promocao());
		getCrudPesquisaModel().setFornecedor(new Fornecedor());
		getCrudPesquisaModel().setTipoPromocao(new TipoPromocao());
		setGrid(new ArrayList<Promocao>());
		return null;
	}

	public List<SelectItem> getTiposPromocoes() {
		return tiposPromocoes;
	}

	public void setTiposPromocoes(List<SelectItem> tiposPromocoes) {
		this.tiposPromocoes = tiposPromocoes;
	}

	public List<SelectItem> getFornecedores() {
		return fornecedores;
	}

	public void setFornecedores(List<SelectItem> fornecedores) {
		this.fornecedores = fornecedores;
	}
	

}
