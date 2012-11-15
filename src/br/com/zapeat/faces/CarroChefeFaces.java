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
import br.com.zapeat.model.CarroChefe;
import br.com.zapeat.model.Fornecedor;
import br.com.zapeat.model.ImagemCarroChefe;
import br.com.zapeat.util.Constantes;
import br.com.zapeat.util.UsuarioUtil;
import br.com.zapeat.util.ZapeatUtil;

@ViewScoped
@ManagedBean(name = "carroChefeFaces")
public class CarroChefeFaces extends CrudFaces<CarroChefe> {

	private List<SelectItem> fornecedores;
	private ImagemCarroChefe imagemCarroChefeSelecionada;
	
	private boolean usuarioFornecedor;

	@PostConstruct
	protected void init() {

		this.clearFields();
		this.initCombo();
		this.verificarUsuarioFornecedor();
	}

	private void initCombo() {
		this.fornecedores = super.initCombo(new Fornecedor().findAll("nomeFantasia"), "id", "nomeFantasia");
	}

	@Override
	protected boolean validaCampos() {

		boolean validado = true;

		if (!isFlagAlterar() && !TSUtil.isEmpty(new CarroChefe().pesquisarPorFornecedor(this.getCrudModel().getFornecedor()))) {

			super.addErrorMessage("J� existe Carro-Chefe cadastrado para esse fornecedor.");

			return false;
		}

		return validado;
	}

	private void verificarUsuarioFornecedor() {

		if (!TSUtil.isEmpty(UsuarioUtil.obterUsuarioConectado()) && !TSUtil.isEmpty(UsuarioUtil.obterUsuarioConectado().getFornecedor())) {

			this.getCrudModel().setFornecedor(UsuarioUtil.obterUsuarioConectado().getFornecedor());

			List<CarroChefe> list = new CarroChefe().pesquisarPorFornecedor(this.getCrudModel().getFornecedor());

			if (!TSUtil.isEmpty(list)) {

				this.setCrudModel(list.get(0));

				this.detail();
			}

			this.usuarioFornecedor = true;

		}
	}

	@Override
	public String limpar() {

		this.setCrudModel(new CarroChefe());

		if (!TSUtil.isEmpty(UsuarioUtil.obterUsuarioConectado()) && !TSUtil.isEmpty(UsuarioUtil.obterUsuarioConectado().getFornecedor())) {

			this.getCrudModel().setFornecedor(UsuarioUtil.obterUsuarioConectado().getFornecedor());

		} else {

			this.getCrudModel().setFornecedor(new Fornecedor());
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

			this.getCrudPesquisaModel().setFornecedor(new Fornecedor());
		}

		this.getCrudPesquisaModel().setFlagAtivo(Boolean.TRUE);
		this.setGrid(new ArrayList<CarroChefe>());

		return null;
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

	public List<SelectItem> getFornecedores() {
		return fornecedores;
	}

	public void setFornecedores(List<SelectItem> fornecedores) {
		this.fornecedores = fornecedores;
	}

	public ImagemCarroChefe getImagemCarroChefeSelecionada() {
		return imagemCarroChefeSelecionada;
	}

	public void setImagemCarroChefeSelecionada(ImagemCarroChefe imagemCarroChefeSelecionada) {
		this.imagemCarroChefeSelecionada = imagemCarroChefeSelecionada;
	}

}
