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
import br.com.zapeat.model.Cidade;
import br.com.zapeat.model.Estado;
import br.com.zapeat.model.Fornecedor;
import br.com.zapeat.model.TipoBanner;
import br.com.zapeat.util.Constantes;
import br.com.zapeat.util.ZapeatUtil;

@ViewScoped
@ManagedBean(name = "bannerFaces")
public class BannerFaces extends ComboCidadeEstadoFaces<Banner> {

	private List<SelectItem> tiposBanners;

	@PostConstruct
	protected void init() {
		this.clearFields();
		this.initCombos();
		setFieldOrdem("taxaPrioridade");
	}
	
	private void initCombos(){
		this.tiposBanners = super.initCombo(new TipoBanner().findAll(), "id", "descricao");
	}
	
	@Override
	public String limpar() {
		setCrudModel(new Banner());
		getCrudModel().setFornecedor(new Fornecedor());
		getCrudModel().setTipoBanner(new TipoBanner());
		getCrudModel().setFlagAtivo(Boolean.TRUE);
		getCrudModel().getFornecedor().setCidade(new Cidade());
		getCrudModel().getFornecedor().getCidade().setEstado(new Estado());
		setFlagAlterar(Boolean.FALSE);
		return null;
	}
	
	@Override
	public String limparPesquisa() {
		setCrudPesquisaModel(new Banner());
		getCrudPesquisaModel().setFornecedor(new Fornecedor());
		getCrudPesquisaModel().setTipoBanner(new TipoBanner());
		getCrudPesquisaModel().setFlagAtivo(Boolean.TRUE);
		getCrudPesquisaModel().getFornecedor().setCidade(new Cidade());
		getCrudPesquisaModel().getFornecedor().getCidade().setEstado(new Estado());
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
	
	@Override
	protected void posDetail() {
		
		super.atualizarComboCidade();
		
		super.atualizarComboFornecedor();
		
	}
	
	public void enviarImagem(FileUploadEvent event) {

		if(TSUtil.isEmpty(getCrudModel().getTipoBanner().getId())){
			ZapeatUtil.addErrorMessage("Tipo de Banner: Campo obrigatório");
			return;
		}
		
		getCrudModel().setImagem(TSUtil.gerarId() + TSFile.obterExtensaoArquivo(event.getFile().getFileName()));

		if(Constantes.TIPO_BANNER_SUPERIOR_GRANDE.equals(getCrudModel().getTipoBanner().getId())){
			
			ZapeatUtil.gravarImagemComRedimensionamento(event.getFile(), Constantes.PREFIXO_IMAGEM_BANNER_SUPERIOR_GRANDE + getCrudModel().getImagem(), Constantes.PASTA_UPLOAD, Constantes.LARGURA_BANNER_SUPERIOR_GRANDE, Constantes.ALTURA_BANNER_SUPERIOR_GRANDE);
			
		} else if(Constantes.TIPO_BANNER_SUPERIOR_LATERAL.equals(getCrudModel().getTipoBanner().getId())){
			
			ZapeatUtil.gravarImagemComRedimensionamento(event.getFile(), Constantes.PREFIXO_IMAGEM_BANNER_LATERAL + getCrudModel().getImagem(), Constantes.PASTA_UPLOAD, Constantes.LARGURA_BANNER_LATERAL, Constantes.ALTURA_BANNER_LATERAL);
			
		}
		
	}
	
	public String limparImagem(){
		getCrudModel().setImagem(null);
		return null;
	}
	
	@Override
	protected Cidade getCidadeSelecionada() {
		return getCrudModel().getFornecedor().getCidade();
	}

	@Override
	protected Cidade getCidadeSelecionadaPesquisa() {
		return getCrudPesquisaModel().getFornecedor().getCidade();
	}

	@Override
	protected Fornecedor getFornecedorSelecionado() {
		return getCrudModel().getFornecedor();
	}

	@Override
	protected Fornecedor getFornecedorSelecionadoPesquisa() {
		return getCrudPesquisaModel().getFornecedor();
	}

	public List<SelectItem> getTiposBanners() {
		return tiposBanners;
	}

	public void setTiposBanners(List<SelectItem> tiposBanners) {
		this.tiposBanners = tiposBanners;
	}

}
