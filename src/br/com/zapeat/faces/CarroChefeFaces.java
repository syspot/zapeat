package br.com.zapeat.faces;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.FileUploadEvent;

import br.com.topsys.file.TSFile;
import br.com.topsys.util.TSUtil;
import br.com.zapeat.model.CarroChefe;
import br.com.zapeat.model.Cidade;
import br.com.zapeat.model.Estado;
import br.com.zapeat.model.Fornecedor;
import br.com.zapeat.model.ImagemCarroChefe;
import br.com.zapeat.util.Constantes;
import br.com.zapeat.util.UsuarioUtil;
import br.com.zapeat.util.ZapeatUtil;

@ViewScoped
@ManagedBean(name = "carroChefeFaces")
public class CarroChefeFaces extends ComboCidadeEstadoFaces<CarroChefe> {

	private ImagemCarroChefe imagemCarroChefeSelecionada;
	
	private boolean usuarioFornecedor;

	@PostConstruct
	protected void init() {
		this.clearFields();
		this.verificarUsuarioFornecedor();
	}
	
	@Override
	public String limpar() {

		this.setCrudModel(new CarroChefe());

		if (!TSUtil.isEmpty(UsuarioUtil.obterUsuarioConectado()) && !TSUtil.isEmpty(UsuarioUtil.obterUsuarioConectado().getFornecedor())) {

			this.getCrudModel().setFornecedor(UsuarioUtil.obterUsuarioConectado().getFornecedor());

		} else {

			getCrudModel().setFornecedor(new Fornecedor());
			getCrudModel().getFornecedor().setCidade(new Cidade());
			getCrudModel().getFornecedor().getCidade().setEstado(new Estado());
			
		}

		this.getCrudModel().setFlagAtivo(Boolean.TRUE);
		this.setFlagAlterar(Boolean.FALSE);
		this.getCrudModel().setImagensCarrosChefes(new ArrayList<ImagemCarroChefe>());

		return null;
	}

	@Override
	public String limparPesquisa() {

		this.setFieldOrdem("titulo");
		this.setCrudPesquisaModel(new CarroChefe());

		if (!TSUtil.isEmpty(UsuarioUtil.obterUsuarioConectado()) && !TSUtil.isEmpty(UsuarioUtil.obterUsuarioConectado().getFornecedor())) {

			this.getCrudPesquisaModel().setFornecedor(UsuarioUtil.obterUsuarioConectado().getFornecedor());

		} else {

			getCrudPesquisaModel().setFornecedor(new Fornecedor());
			getCrudPesquisaModel().getFornecedor().setCidade(new Cidade());
			getCrudPesquisaModel().getFornecedor().getCidade().setEstado(new Estado());
			
		}

		this.getCrudPesquisaModel().setFlagAtivo(Boolean.TRUE);
		this.setGrid(new ArrayList<CarroChefe>());

		return null;
	}

	@Override
	protected boolean validaCampos() {

		boolean validado = true;

		if (!isFlagAlterar() && !TSUtil.isEmpty(new CarroChefe().pesquisarPorFornecedor(this.getCrudModel().getFornecedor()))) {

			super.addErrorMessage("Já existe Carro-Chefe cadastrado para esse fornecedor.");

			return false;
		}

		return validado;
	}

	@Override
	protected void posDetail() {
		
		super.atualizarComboCidade();
		
		super.atualizarComboFornecedor();
	}
	
	private void verificarUsuarioFornecedor() {

		if (!TSUtil.isEmpty(UsuarioUtil.obterUsuarioConectado()) && !TSUtil.isEmpty(UsuarioUtil.obterUsuarioConectado().getFornecedor())) {

			this.getCrudModel().setFornecedor(UsuarioUtil.obterUsuarioConectado().getFornecedor());
			
			this.atualizarComboCidade();
			this.atualizarComboFornecedor();

			List<CarroChefe> list = new CarroChefe().pesquisarPorFornecedor(this.getCrudModel().getFornecedor());

			if (!TSUtil.isEmpty(list)) {

				this.setCrudModel(list.get(0));

				this.detail();
			}

			this.usuarioFornecedor = true;

		}
	}

	public void enviarImagensCarrosChefes(FileUploadEvent event) {

		ImagemCarroChefe imagemCarroChefe = new ImagemCarroChefe();

		imagemCarroChefe.setCarroChefe(this.getCrudModel());
		imagemCarroChefe.setImagem(TSUtil.gerarId() + TSFile.obterExtensaoArquivo(event.getFile().getFileName()));

		this.getCrudModel().getImagensCarrosChefes().add(imagemCarroChefe);

		ZapeatUtil.gravarImagemComRedimensionamento(event.getFile(), Constantes.PREFIXO_IMAGEM_CARRO_CHEFE_FULL + imagemCarroChefe.getImagem(), Constantes.PASTA_UPLOAD, Constantes.LARGURA_IMAGEM_CARRO_CHEFE_FULL, Constantes.ALTURA_IMAGEM_CARRO_CHEFE_FULL);
		ZapeatUtil.gravarImagemComRedimensionamento(event.getFile(), Constantes.PREFIXO_IMAGEM_CARRO_CHEFE_THUMB + imagemCarroChefe.getImagem(), Constantes.PASTA_UPLOAD, Constantes.LARGURA_IMAGEM_CARRO_CHEFE_THUMB, Constantes.ALTURA_IMAGEM_CARRO_CHEFE_THUMB);
	}

	public String removerImagemCarroChefe() {

		this.getCrudModel().getImagensCarrosChefes().remove(this.imagemCarroChefeSelecionada);

		return null;
	}
	
	@Override
	public boolean isUsuarioFornecedor() {
		return usuarioFornecedor;
	}
	
	@Override
	public boolean isOcultarTabPesquisa() {
		return usuarioFornecedor;
	}

	public ImagemCarroChefe getImagemCarroChefeSelecionada() {
		return imagemCarroChefeSelecionada;
	}

	public void setImagemCarroChefeSelecionada(ImagemCarroChefe imagemCarroChefeSelecionada) {
		this.imagemCarroChefeSelecionada = imagemCarroChefeSelecionada;
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

}
