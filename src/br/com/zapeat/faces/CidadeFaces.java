package br.com.zapeat.faces;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import br.com.zapeat.model.Cidade;
import br.com.zapeat.model.Estado;

@ViewScoped
@ManagedBean(name = "cidadeFaces")
public class CidadeFaces extends CrudFaces<Cidade> {
	
	private List<SelectItem> estados;

	@PostConstruct
	protected void init() {
		this.clearFields();
		this.setFieldOrdem("nome");
	}
	
	@Override
	protected void clearFields() {
		this.limpar();
		this.limparPesquisa();
		this.initCombo();
	}
	
	private void initCombo() {
		this.estados = super.initCombo(new Estado().findAll(), "id", "nome");
	}

	@Override
	public String limpar() {

		this.setCrudModel(new Cidade());
		this.getCrudModel().setEstado(new Estado());
		this.getCrudModel().setFlagAtivo(Boolean.TRUE);
		this.setFlagAlterar(Boolean.FALSE);

		return null;
	}

	@Override
	public String limparPesquisa() {

		this.setCrudPesquisaModel(new Cidade());
		this.getCrudPesquisaModel().setEstado(new Estado());
		this.getCrudPesquisaModel().setFlagAtivo(Boolean.TRUE);
		this.setGrid(new ArrayList<Cidade>());

		return null;
	}

	public List<SelectItem> getEstados() {
		return estados;
	}

	public void setEstados(List<SelectItem> estados) {
		this.estados = estados;
	}

}
