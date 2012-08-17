package br.com.zapeat.faces;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import org.primefaces.event.FileUploadEvent;

import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.file.TSFile;
import br.com.topsys.util.TSUtil;
import br.com.zapeat.model.Fornecedor;
import br.com.zapeat.model.ImagemPromocao;
import br.com.zapeat.model.Promocao;
import br.com.zapeat.model.TipoPromocao;
import br.com.zapeat.util.Constantes;
import br.com.zapeat.util.ZapeatUtil;

@ViewScoped
@ManagedBean(name = "promocaoFaces")
public class PromocaoFaces extends CrudFaces<Promocao> {

	private List<SelectItem> tiposPromocoes;
	private List<SelectItem> fornecedores;
	
	private ImagemPromocao imagemPromocaoSelecionada;
	private Integer mover;

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
		getCrudModel().setImagensPromocoes(new ArrayList<ImagemPromocao>());
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
	
	@Override
	protected void posPersist() throws TSApplicationException {
		
		for(ImagemPromocao imagem : getCrudModel().getImagensPromocoes()){
			
			if(!TSUtil.isEmpty(imagem.getUploadedFile())){
				
				ImagemPromocao img = imagem.getByModel();
				
				imagem.setId(img.getId());
				
				String nomeArquivo = imagem.getId() + TSFile.obterExtensaoArquivo(imagem.getImagem());
				imagem.setImagem(Constantes.PASTA_DOWNLOAD_PROMOCAO + nomeArquivo);
				
				ZapeatUtil.criaArquivo(imagem.getUploadedFile(), Constantes.PASTA_UPLOAD_PROMOCAO + nomeArquivo);
				
				imagem.update();
				
			}
		}
		
	}
	
	public void enviarImagem(FileUploadEvent event) {
		
		ImagemPromocao imagemPromocao = new ImagemPromocao();
		
		imagemPromocao.setPromocao(getCrudModel());
		imagemPromocao.setUploadedFile(event.getFile());
		imagemPromocao.setImagem(ZapeatUtil.criarImagemTemp(event));
		
		getCrudModel().getImagensPromocoes().add(imagemPromocao);
		
	}
	
	public void removerImagem(){
		getCrudModel().getImagensPromocoes().remove(this.imagemPromocaoSelecionada);
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

	public ImagemPromocao getImagemPromocaoSelecionada() {
		return imagemPromocaoSelecionada;
	}

	public void setImagemPromocaoSelecionada(
			ImagemPromocao imagemPromocaoSelecionada) {
		this.imagemPromocaoSelecionada = imagemPromocaoSelecionada;
	}

	public Integer getMover() {
		return mover;
	}

	public void setMover(Integer mover) {
		this.mover = mover;
	}
	

}
