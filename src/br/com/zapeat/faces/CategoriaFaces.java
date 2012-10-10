package br.com.zapeat.faces;

import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.FileUploadEvent;

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
		
		if(TSUtil.isEmpty(getCrudModel().getImagem())){
			valida = false;
			ZapeatUtil.addErrorMessage("Imagem: Campo obrigatório");
		}
		
		if(!TSUtil.isEmpty(getCrudModel().getId())){
			
			Categoria categoriaTemp = getCrudModel().getById();
			
			int qtdCategoriasDestaques = new Categoria().obterTotalDestaque().intValue();
			
			if(categoriaTemp.getFlagDestaque() && !getCrudModel().getFlagDestaque() &&
					qtdCategoriasDestaques <= Constantes.QTD_MINIMA_CATEGORIAS_DESTAQUE){
				
				valida = false;
				ZapeatUtil.addErrorMessage("Não é possível alterar o registro, pois o número mínimo de categorias destaque não foi atingido");
				
			}
			
		}
		
		return valida;
	}
	
	@Override
	protected boolean validaExclusao() {
		
		boolean valida = true;
		
		Categoria categoriaTemp = getCrudModel().getById();
		
		if(categoriaTemp.getFlagDestaque()){
			
			int qtdCategoriasDestaques = new Categoria().obterTotalDestaque().intValue();
			
			if(qtdCategoriasDestaques <= Constantes.QTD_MINIMA_CATEGORIAS_DESTAQUE){
				
				valida = false;
				ZapeatUtil.addErrorMessage("Não é possível excluir o registro, pois o número mínimo de categorias destaque não foi atingido");
				
			}
		
		}
		
		return valida;
	}
	
	public void enviarImagem(FileUploadEvent event) {
		
		getCrudModel().setImagem(TSUtil.gerarId() + TSFile.obterExtensaoArquivo(event.getFile().getFileName()));
		ZapeatUtil.gravarImagemComRedimensionamento(event.getFile(), Constantes.PREFIXO_IMAGEM_CATEGORIA + getCrudModel().getImagem(), Constantes.PASTA_UPLOAD, Constantes.LARGURA_CATEGORIA, Constantes.ALTURA_CATEGORIA);
		
	}
	
	
}
