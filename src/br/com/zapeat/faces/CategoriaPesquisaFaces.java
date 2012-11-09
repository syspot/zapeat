package br.com.zapeat.faces;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.zapeat.model.Categoria;

@ViewScoped
@ManagedBean(name = "categoriaPesquisaFaces")
public class CategoriaPesquisaFaces extends PesquisaFaces<Categoria> {

	@PostConstruct
	protected void init() {
		this.limpar();
	}
	
	@Override
	public String limpar() {
		setModel(new Categoria());
		return null;
	}
	
}
