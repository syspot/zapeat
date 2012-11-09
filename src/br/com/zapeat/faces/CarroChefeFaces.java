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
import br.com.topsys.web.util.TSFacesUtil;
import br.com.zapeat.model.CarroChefe;
import br.com.zapeat.model.Fornecedor;
import br.com.zapeat.model.ImagemCarroChefe;
import br.com.zapeat.model.UsuarioAdm;
import br.com.zapeat.util.Constantes;
import br.com.zapeat.util.UsuarioUtil;
import br.com.zapeat.util.ZapeatUtil;

@ViewScoped
@ManagedBean(name = "carroChefeFaces")
public class CarroChefeFaces extends CrudFaces<CarroChefe> {

	private List<SelectItem> fornecedores;
	private ImagemCarroChefe imagemCarroChefeSelecionada;

	@PostConstruct
	protected void init() {

		this.clearFields();
		this.initCombo();
		this.isFornecedorLogado();
	}

	private void initCombo() {
		this.fornecedores = super.initCombo(new Fornecedor().findAll("nomeFantasia"), "id", "nomeFantasia");
	}

	private void isFornecedorLogado() {

		UsuarioAdm usuario = (UsuarioAdm) TSFacesUtil.getObjectInSession(Constantes.USUARIO_CONECTADO);

		if (!TSUtil.isEmpty(usuario) && !TSUtil.isEmpty(usuario.getId()) && !TSUtil.isEmpty(usuario.getFornecedor()) && !TSUtil.isEmpty(usuario.getFornecedor().getId())) {

			this.getCrudModel().setFornecedor(usuario.getFornecedor());

			this.getCrudPesquisaModel().setFornecedor(usuario.getFornecedor());

		}
	}

	@Override
	public String limpar() {

		this.setCrudModel(new CarroChefe());
		this.getCrudModel().setFornecedor(new Fornecedor());
		this.getCrudModel().setFlagAtivo(Boolean.TRUE);
		this.setFlagAlterar(Boolean.FALSE);
		this.getCrudModel().setImagensCarrosChefes(new ArrayList<ImagemCarroChefe>());

		return null;
	}

	@Override
	public String limparPesquisa() {

		this.setFieldOrdem("titulo");
		this.setCrudPesquisaModel(new CarroChefe());
		this.getCrudPesquisaModel().setFornecedor(new Fornecedor());
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
	public boolean isExibirBotao() {

		UsuarioAdm usuario = UsuarioUtil.obterUsuarioConectado();

		if (!TSUtil.isEmpty(usuario) && !TSUtil.isEmpty(usuario.getFornecedor())) {

			return false;
		}

		return true;

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
