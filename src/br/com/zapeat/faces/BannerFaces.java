package br.com.zapeat.faces;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import org.primefaces.event.FileUploadEvent;

import br.com.topsys.file.TSFile;
import br.com.topsys.util.TSUtil;
import br.com.zapeat.model.Banner;
import br.com.zapeat.model.Fornecedor;
import br.com.zapeat.model.TipoBanner;
import br.com.zapeat.util.Constantes;
import br.com.zapeat.util.ZapeatUtil;

@ViewScoped
@ManagedBean(name = "bannerFaces")
public class BannerFaces extends CrudFaces<Banner> {

	private List<SelectItem> tiposBanners;
	private List<SelectItem> fornecedores;

	@PostConstruct
	protected void init() {
		this.clearFields();
		this.initCombos();
		setFieldOrdem("taxaPrioridade");
	}
	
	private void initCombos(){
		this.tiposBanners = super.initCombo(new TipoBanner().findAll(), "id", "descricao");
		this.fornecedores = super.initCombo(new Fornecedor().findAll(), "id", "descricao");
	}
	
	@Override
	public String limpar() {
		setCrudModel(new Banner());
		getCrudModel().setFornecedor(new Fornecedor());
		getCrudModel().setTipoBanner(new TipoBanner());
		getCrudModel().setFlagAtivo(Boolean.TRUE);
		setFlagAlterar(Boolean.FALSE);
		return null;
	}
	
	@Override
	public String limparPesquisa() {
		setCrudPesquisaModel(new Banner());
		getCrudPesquisaModel().setFornecedor(new Fornecedor());
		getCrudPesquisaModel().setTipoBanner(new TipoBanner());
		getCrudPesquisaModel().setFlagAtivo(Boolean.TRUE);
		setGrid(new ArrayList<Banner>());
		return null;
	}
	
	@Override
	protected boolean validaCampos() {
		
		boolean valida = true;
		
		if(TSUtil.isEmpty(getCrudModel().getImagem())){
			valida = false;
			ZapeatUtil.addErrorMessage("Imagem: Campo obrigatório");
		}
		
		return valida;
	}
	
	public void enviarImagem(FileUploadEvent event) {
		getCrudModel().setImagem(TSUtil.gerarId() + TSFile.obterExtensaoArquivo(event.getFile().getFileName()));
		ZapeatUtil.gravarImagemComRedimensionamento(event.getFile(), Constantes.PREFIXO_IMAGEM_BANNER_FULL + getCrudModel().getImagem(), Constantes.PASTA_UPLOAD, Constantes.LARGURA_BANNER_FULL, Constantes.ALTURA_BANNER_FULL);
		ZapeatUtil.gravarImagemComRedimensionamento(event.getFile(), Constantes.PREFIXO_IMAGEM_BANNER_THUMB + getCrudModel().getImagem(), Constantes.PASTA_UPLOAD, Constantes.LARGURA_BANNER_THUMB, Constantes.ALTURA_BANNER_THUMB);
	}

	public List<SelectItem> getTiposBanners() {
		return tiposBanners;
	}

	public void setTiposBanners(List<SelectItem> tiposBanners) {
		this.tiposBanners = tiposBanners;
	}

	public List<SelectItem> getFornecedores() {
		return fornecedores;
	}

	public void setFornecedores(List<SelectItem> fornecedores) {
		this.fornecedores = fornecedores;
	}
	
}
