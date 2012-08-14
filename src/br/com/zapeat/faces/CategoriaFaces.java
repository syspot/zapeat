package br.com.zapeat.faces;

import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.FileUploadEvent;

import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.file.TSFile;
import br.com.topsys.util.TSUtil;
import br.com.zapeat.model.Categoria;
import br.com.zapeat.util.Constantes;
import br.com.zapeat.util.ZapeatUtil;

@ViewScoped
@ManagedBean(name = "categoriaFaces")
public class CategoriaFaces extends CrudFaces<Categoria> {


	@PostConstruct
	protected void init() {
		this.clearFields();
		setFieldOrdem("descricao");
	}
	
	@Override
	public String limpar() {
		setCrudModel(new Categoria());
		getCrudModel().setFlagAtivo(Boolean.TRUE);
		setFlagAlterar(Boolean.FALSE);
		return null;
	}
	
	@Override
	public String limparPesquisa() {
		setCrudPesquisaModel(new Categoria());
		getCrudPesquisaModel().setFlagAtivo(Boolean.TRUE);
		setGrid(new ArrayList<Categoria>());
		return null;
	}
	
	@Override
	protected boolean validaCampos() {
		
		boolean valida = true;
		
		if(TSUtil.isEmpty(getCrudModel().getId()) && TSUtil.isEmpty(getCrudModel().getUploadedFile())){
			valida = false;
			ZapeatUtil.addErrorMessage("Imagem: Campo obrigat�rio");
		}
		
		return valida;
	}
	
	@Override
	protected void posPersist() throws TSApplicationException {
		
		if(!TSUtil.isEmpty(getCrudModel().getUploadedFile())){
			
			String nomeArquivo = getCrudModel().getId() + TSFile.obterExtensaoArquivo(getCrudModel().getImagem());
			getCrudModel().setImagem(Constantes.PASTA_DOWNLOAD_TEMP + nomeArquivo);
			ZapeatUtil.criaArquivo(getCrudModel().getUploadedFile(), Constantes.PASTA_UPLOAD + nomeArquivo);
			
			getCrudModel().update();
			
		}
		
	}
	
	public void enviarImagem(FileUploadEvent event) {
		getCrudModel().setUploadedFile(event.getFile());
		getCrudModel().setImagem(ZapeatUtil.criarImagemTemp(event));
	}

}
