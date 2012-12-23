package br.com.zapeat.faces;

import java.util.List;

import javax.faces.model.SelectItem;

import br.com.topsys.database.hibernate.TSActiveRecordIf;
import br.com.zapeat.model.Cidade;
import br.com.zapeat.model.Estado;
import br.com.zapeat.model.Fornecedor;

public abstract class ComboCidadeEstadoFaces<T extends TSActiveRecordIf<T>> extends CrudFaces<T> {

	private List<SelectItem> estados;
	private List<SelectItem> cidades;
	private List<SelectItem> cidadesPesquisa;
	private List<SelectItem> fornecedores;
	private List<SelectItem> fornecedoresPesquisa;
	
	
	protected abstract Cidade getCidadeSelecionada();
	
	protected abstract Cidade getCidadeSelecionadaPesquisa();
	
	protected abstract Fornecedor getFornecedorSelecionado();
	
	protected abstract Fornecedor getFornecedorSelecionadoPesquisa();
	
	public ComboCidadeEstadoFaces() {
		this.estados = super.initCombo(new Estado().findAll("sigla"), "id", "sigla");
	}
	
	public String atualizarComboCidade(){
		this.cidades = super.initCombo(getCidadeSelecionada().findCombo(), "id", "nome");
		return null;
	}
	
	public String atualizarComboCidadePesquisa(){
		this.cidadesPesquisa = super.initCombo(getCidadeSelecionadaPesquisa().findCombo(), "id", "nome");
		return null;
	}
	
	public String atualizarComboFornecedor(){
		this.fornecedores = super.initCombo(getFornecedorSelecionado().pesquisarPorCidade(), "id", "nomeFantasia");
		return null;
	}
	
	public String atualizarComboFornecedorPesquisa(){
		this.fornecedoresPesquisa = super.initCombo(getFornecedorSelecionadoPesquisa().pesquisarPorCidade(), "id", "nomeFantasia");
		return null;
	}

	public List<SelectItem> getCidades() {
		return cidades;
	}

	public void setCidades(List<SelectItem> cidades) {
		this.cidades = cidades;
	}

	public List<SelectItem> getEstados() {
		return estados;
	}

	public void setEstados(List<SelectItem> estados) {
		this.estados = estados;
	}

	public List<SelectItem> getCidadesPesquisa() {
		return cidadesPesquisa;
	}

	public void setCidadesPesquisa(List<SelectItem> cidadesPesquisa) {
		this.cidadesPesquisa = cidadesPesquisa;
	}

	public List<SelectItem> getFornecedores() {
		return fornecedores;
	}

	public void setFornecedores(List<SelectItem> fornecedores) {
		this.fornecedores = fornecedores;
	}

	public List<SelectItem> getFornecedoresPesquisa() {
		return fornecedoresPesquisa;
	}

	public void setFornecedoresPesquisa(List<SelectItem> fornecedoresPesquisa) {
		this.fornecedoresPesquisa = fornecedoresPesquisa;
	}
	
}
