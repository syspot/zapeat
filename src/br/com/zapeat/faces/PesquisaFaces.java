package br.com.zapeat.faces;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import br.com.topsys.database.hibernate.TSActiveRecordIf;
import br.com.topsys.exception.TSSystemException;
import br.com.topsys.web.util.TSFacesUtil;

public abstract class PesquisaFaces <T extends TSActiveRecordIf<T>>{

	private T model;
	
	private List<T> grid;
	
	@SuppressWarnings("unchecked")
	private Class<T> modelClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	
	public String limpar(){
		try {
			this.model = modelClass.newInstance();
		} catch (Exception e) {
			throw new TSSystemException(e);
		}
		return null;
	}
	
	protected void preFind(){
	}
	
	protected void posFind(){
	}
	
	public String find() {
		
		this.preFind();
		
		this.grid = this.model.findByModel();
		
		TSFacesUtil.gerarResultadoLista(this.grid);
		
		this.posFind();
		
		return null;
		
	}

	public T getModel() {
		return model;
	}

	public void setModel(T model) {
		this.model = model;
	}

	public List<T> getGrid() {
		return grid;
	}

	public void setGrid(List<T> grid) {
		this.grid = grid;
	}

}
