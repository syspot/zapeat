package br.com.zapeat.faces;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DualListModel;

import br.com.topsys.file.TSFile;
import br.com.topsys.util.TSUtil;
import br.com.topsys.web.util.TSFacesUtil;
import br.com.zapeat.model.CarroChefe;
import br.com.zapeat.model.Categoria;
import br.com.zapeat.model.Cidade;
import br.com.zapeat.model.FormaPagamento;
import br.com.zapeat.model.Fornecedor;
import br.com.zapeat.model.FornecedorCategoria;
import br.com.zapeat.model.FornecedorFormaPagamento;
import br.com.zapeat.model.ImagemCarroChefe;
import br.com.zapeat.model.ImagemFornecedor;
import br.com.zapeat.model.UsuarioAdm;
import br.com.zapeat.util.Constantes;
import br.com.zapeat.util.UsuarioUtil;
import br.com.zapeat.util.ZapeatUtil;

@ViewScoped
@ManagedBean(name = "fornecedorFaces")
public class FornecedorFaces extends CrudFaces<Fornecedor> {

	private List<SelectItem> cidades;
	private List<Categoria> categoriasSources;
	private DualListModel<Categoria> categorias;
	private DualListModel<Categoria> targetCategorias;
	private List<FormaPagamento> formasPagamentosSources;
	private DualListModel<FormaPagamento> formasPagamentos;
	private DualListModel<FormaPagamento> targetFormasPagamentos;

	private ImagemFornecedor imagemFornecedorSelecionada;
	private ImagemCarroChefe imagemCarroChefeSelecionada;

	@PostConstruct
	protected void init() {
		this.clearFields();
		this.initCombo();
		this.isFornecedorLogado();
	}

	private void initCombo() {
		this.cidades = super.initCombo(new Cidade().findAll("nome"), "id", "nome");
	}

	@Override
	public String limpar() {
		this.setCrudModel(new Fornecedor());
		this.getCrudModel().setCidade(new Cidade());
		this.getCrudModel().setFlagAtivo(Boolean.TRUE);
		this.setFlagAlterar(Boolean.FALSE);
		this.getCrudModel().setFornecedorCategorias(new ArrayList<FornecedorCategoria>());
		this.getCrudModel().setFornecedorFormasPagamentos(new ArrayList<FornecedorFormaPagamento>());
		this.getCrudModel().setImagensFornecedores(new ArrayList<ImagemFornecedor>());
		this.getCrudModel().setCarroChefe(new CarroChefe());
		this.getCrudModel().getCarroChefe().setImagensCarrosChefes(new ArrayList<ImagemCarroChefe>());

		this.categoriasSources = new Categoria().pesquisarCategoriasAtivas();
		this.formasPagamentosSources = new FormaPagamento().findAll("descricao");

		this.targetCategorias = new DualListModel<Categoria>();
		this.targetFormasPagamentos = new DualListModel<FormaPagamento>();

		List<Categoria> categoriaTarget = new ArrayList<Categoria>();
		List<FormaPagamento> formasPagamentosTarget = new ArrayList<FormaPagamento>();

		this.categorias = new DualListModel<Categoria>(this.categoriasSources, categoriaTarget);
		this.formasPagamentos = new DualListModel<FormaPagamento>(this.formasPagamentosSources, formasPagamentosTarget);

		return null;
	}

	@Override
	public String limparPesquisa() {
		this.setFieldOrdem("nomeFantasia");
		this.setCrudPesquisaModel(new Fornecedor());
		this.getCrudPesquisaModel().setCidade(new Cidade());
		this.getCrudPesquisaModel().setFlagAtivo(Boolean.TRUE);
		this.setGrid(new ArrayList<Fornecedor>());

		return null;
	}

	private void isFornecedorLogado() {

		UsuarioAdm usuario = (UsuarioAdm) TSFacesUtil.getObjectInSession(Constantes.USUARIO_CONECTADO);

		if (!TSUtil.isEmpty(usuario) && !TSUtil.isEmpty(usuario.getId()) && !TSUtil.isEmpty(usuario.getFornecedor()) && !TSUtil.isEmpty(usuario.getFornecedor().getId())) {

			this.setCrudModel(usuario.getFornecedor());

			this.detail();

			this.setOcultarTabPesquisa(true);

		}
	}

	private boolean validaCamposCarroChefe() {

		boolean validado = true;

		if (getCrudModel().getCarroChefe().getFlagAtivo()) {

			if (TSUtil.isEmpty(getCrudModel().getCarroChefe().getTitulo())) {
				validado = false;
				ZapeatUtil.addErrorMessage("Título do Carro-Chefe: Campo obrigatório");
			}

			if (TSUtil.isEmpty(getCrudModel().getCarroChefe().getDescricao())) {
				validado = false;
				ZapeatUtil.addErrorMessage("Descrição do Carro-Chefe: Campo obrigatório");
			}

		}

		return validado;
	}

	@Override
	protected boolean validaCampos() {

		boolean validado = true;

		if (!Pattern.matches(Constantes.REGEX_LATITUDE_LONGITUDE, this.getCrudModel().getLatitude().toString())) {
			validado = false;
			ZapeatUtil.addErrorMessage("Latitude: Formato inválido");
		}

		if (!Pattern.matches(Constantes.REGEX_LATITUDE_LONGITUDE, this.getCrudModel().getLongitude().toString())) {
			validado = false;
			ZapeatUtil.addErrorMessage("Longitude: Formato inválido");
		}

		if (validado) {
			validado = this.validaCamposCarroChefe();
		}

		return validado;
	}

	@Override
	protected void prePersist() {

		FornecedorCategoria fornecedorCategoria;

		int cont = 1;

		for (Categoria categoria : categorias.getTarget()) {

			fornecedorCategoria = new FornecedorCategoria();

			fornecedorCategoria.setCategoria(categoria);
			fornecedorCategoria.setFornecedor(getCrudModel());
			fornecedorCategoria.setPrioridade(cont++);

			if (!getCrudModel().getFornecedorCategorias().contains(fornecedorCategoria)) {

				getCrudModel().getFornecedorCategorias().add(fornecedorCategoria);

			}

		}

		FornecedorFormaPagamento fornecedorFormaPagamento;

		for (FormaPagamento formaPagamento : formasPagamentos.getTarget()) {

			fornecedorFormaPagamento = new FornecedorFormaPagamento();

			fornecedorFormaPagamento.setFormaPagamento(formaPagamento);
			fornecedorFormaPagamento.setFornecedor(getCrudModel());

			if (!getCrudModel().getFornecedorFormasPagamentos().contains(fornecedorFormaPagamento)) {

				getCrudModel().getFornecedorFormasPagamentos().add(fornecedorFormaPagamento);

			}

		}

		getCrudModel().getCarroChefe().setFornecedor(getCrudModel());

	}

	@Override
	protected void posDetail() {

		List<Categoria> categoriaTarget = new ArrayList<Categoria>();

		for (FornecedorCategoria item : getCrudModel().getFornecedorCategorias()) {

			categoriaTarget.add(item.getCategoria());

		}

		this.categoriasSources.removeAll(categoriaTarget);

		this.categorias = new DualListModel<Categoria>(this.categoriasSources, categoriaTarget);

		List<FormaPagamento> formaPagamentoTarget = new ArrayList<FormaPagamento>();

		for (FornecedorFormaPagamento item : getCrudModel().getFornecedorFormasPagamentos()) {

			formaPagamentoTarget.add(item.getFormaPagamento());

		}

		this.formasPagamentosSources.removeAll(formaPagamentoTarget);

		this.formasPagamentos = new DualListModel<FormaPagamento>(this.formasPagamentosSources, formaPagamentoTarget);

		if (TSUtil.isEmpty(getCrudModel().getCarroChefe())) {
			this.getCrudModel().setCarroChefe(new CarroChefe());
			this.getCrudModel().getCarroChefe().setImagensCarrosChefes(new ArrayList<ImagemCarroChefe>());
		}

	}

	public void enviarLogoMarca(FileUploadEvent event) {
		getCrudModel().setLogoMarca(TSUtil.gerarId() + TSFile.obterExtensaoArquivo(event.getFile().getFileName()));
		ZapeatUtil.gravarImagemComRedimensionamento(event.getFile(), Constantes.PREFIXO_IMAGEM_FORNECEDOR_LOGOMARCA + getCrudModel().getLogoMarca(), Constantes.PASTA_UPLOAD, Constantes.LARGURA_FORNECEDOR_LOGOMARCA, Constantes.ALTURA_FORNECEDOR_LOGOMARCA);
	}

	public void enviarImagens(FileUploadEvent event) {

		ImagemFornecedor imagemFornecedor = new ImagemFornecedor();

		imagemFornecedor.setFornecedor(getCrudModel());
		imagemFornecedor.setImagem(TSUtil.gerarId() + TSFile.obterExtensaoArquivo(event.getFile().getFileName()));

		getCrudModel().getImagensFornecedores().add(imagemFornecedor);

		ZapeatUtil.gravarImagemComRedimensionamento(event.getFile(), Constantes.PREFIXO_IMAGEM_FORNECEDOR_FULL + imagemFornecedor.getImagem(), Constantes.PASTA_UPLOAD, Constantes.LARGURA_IMAGEM_FORNECEDOR_FULL, Constantes.ALTURA_IMAGEM_FORNECEDOR_FULL);
		ZapeatUtil.gravarImagemComRedimensionamento(event.getFile(), Constantes.PREFIXO_IMAGEM_FORNECEDOR_THUMB + imagemFornecedor.getImagem(), Constantes.PASTA_UPLOAD, Constantes.LARGURA_IMAGEM_FORNECEDOR_THUMB, Constantes.ALTURA_IMAGEM_FORNECEDOR_THUMB);
	}

	public void enviarImagensCarrosChefes(FileUploadEvent event) {

		ImagemCarroChefe imagemCarroChefe = new ImagemCarroChefe();

		imagemCarroChefe.setCarroChefe(getCrudModel().getCarroChefe());
		imagemCarroChefe.setImagem(TSUtil.gerarId() + TSFile.obterExtensaoArquivo(event.getFile().getFileName()));

		getCrudModel().getCarroChefe().getImagensCarrosChefes().add(imagemCarroChefe);

		ZapeatUtil.gravarImagemComRedimensionamento(event.getFile(), Constantes.PREFIXO_IMAGEM_CARRO_CHEFE_FULL + imagemCarroChefe.getImagem(), Constantes.PASTA_UPLOAD, Constantes.LARGURA_IMAGEM_CARRO_CHEFE_FULL, Constantes.ALTURA_IMAGEM_CARRO_CHEFE_FULL);
		ZapeatUtil.gravarImagemComRedimensionamento(event.getFile(), Constantes.PREFIXO_IMAGEM_CARRO_CHEFE_THUMB + imagemCarroChefe.getImagem(), Constantes.PASTA_UPLOAD, Constantes.LARGURA_IMAGEM_CARRO_CHEFE_THUMB, Constantes.ALTURA_IMAGEM_CARRO_CHEFE_THUMB);
	}

	public String removerImagemCarroChefe() {
		getCrudModel().getCarroChefe().getImagensCarrosChefes().remove(this.imagemCarroChefeSelecionada);
		return null;
	}

	public String removerImagemFornecedor() {
		getCrudModel().getImagensFornecedores().remove(this.imagemFornecedorSelecionada);
		return null;
	}

	public List<SelectItem> getCidades() {
		return cidades;
	}

	public void setCidades(List<SelectItem> cidades) {
		this.cidades = cidades;
	}

	public DualListModel<Categoria> getTargetCategorias() {
		return targetCategorias;
	}

	public void setTargetCategorias(DualListModel<Categoria> targetCategorias) {
		this.targetCategorias = targetCategorias;
	}

	public DualListModel<Categoria> getCategorias() {
		return categorias;
	}

	public void setCategorias(DualListModel<Categoria> categorias) {
		this.categorias = categorias;
	}

	public List<Categoria> getCategoriasSources() {
		return categoriasSources;
	}

	public void setCategoriasSources(List<Categoria> categoriasSources) {
		this.categoriasSources = categoriasSources;
	}

	public ImagemFornecedor getImagemFornecedorSelecionada() {
		return imagemFornecedorSelecionada;
	}

	public void setImagemFornecedorSelecionada(ImagemFornecedor imagemFornecedorSelecionada) {
		this.imagemFornecedorSelecionada = imagemFornecedorSelecionada;
	}

	public ImagemCarroChefe getImagemCarroChefeSelecionada() {
		return imagemCarroChefeSelecionada;
	}

	public void setImagemCarroChefeSelecionada(ImagemCarroChefe imagemCarroChefeSelecionada) {
		this.imagemCarroChefeSelecionada = imagemCarroChefeSelecionada;
	}

	public List<FormaPagamento> getFormasPagamentosSources() {
		return formasPagamentosSources;
	}

	public void setFormasPagamentosSources(List<FormaPagamento> formasPagamentosSources) {
		this.formasPagamentosSources = formasPagamentosSources;
	}

	public DualListModel<FormaPagamento> getFormasPagamentos() {
		return formasPagamentos;
	}

	public void setFormasPagamentos(DualListModel<FormaPagamento> formasPagamentos) {
		this.formasPagamentos = formasPagamentos;
	}

	public DualListModel<FormaPagamento> getTargetFormasPagamentos() {
		return targetFormasPagamentos;
	}

	public void setTargetFormasPagamentos(DualListModel<FormaPagamento> targetFormasPagamentos) {
		this.targetFormasPagamentos = targetFormasPagamentos;
	}

	@Override
	public boolean isExibirBotao() {

		if (!TSUtil.isEmpty(UsuarioUtil.obterUsuarioConectado()) && !TSUtil.isEmpty(UsuarioUtil.obterUsuarioConectado().getFornecedor())) {

			return false;
		}

		return true;

	}

}
