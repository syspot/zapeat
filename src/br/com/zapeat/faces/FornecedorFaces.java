package br.com.zapeat.faces;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DualListModel;
import org.primefaces.model.UploadedFile;

import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.exception.TSSystemException;
import br.com.topsys.file.TSFile;
import br.com.topsys.util.TSUtil;
import br.com.zapeat.model.Categoria;
import br.com.zapeat.model.Cidade;
import br.com.zapeat.model.Fornecedor;
import br.com.zapeat.model.FornecedorCategoria;
import br.com.zapeat.model.ImagemFornecedor;
import br.com.zapeat.util.Constantes;
import br.com.zapeat.util.ZapeatUtil;

@ManagedBean(name = "fornecedorFaces")
@ViewScoped
public class FornecedorFaces extends CrudFaces<Fornecedor> {

	private List<SelectItem> cidades;
	private UploadedFile arquivo;
	private DualListModel<Categoria> categorias;
	private DualListModel<Categoria> targetCategorias;
	private List<Categoria> categoriasSources;
	private List<UploadedFile> imagens;

	@PostConstruct
	protected void init() {
		this.clearFields();
		this.initCombo();
	}

	private void initCombo() {
		this.cidades = super.initCombo(new Cidade().findAll("nome"), "id", "nome");
	}

	@Override
	public String limpar() {
		this.setCrudModel(new Fornecedor());
		this.getCrudModel().setCidade(new Cidade());
		this.getCrudModel().setFlagAtivo(Boolean.TRUE);
		this.setArquivo(null);
		this.setFlagAlterar(Boolean.FALSE);
		this.getCrudModel().setFornecedorCategorias(new ArrayList<FornecedorCategoria>());
		this.getCrudModel().setImagensAmbiente(new ArrayList<ImagemFornecedor>());

		this.categoriasSources = new Categoria().findAll("descricao");

		this.targetCategorias = new DualListModel<Categoria>();

		List<Categoria> categoriaTarget = new ArrayList<Categoria>();

		this.categorias = new DualListModel<Categoria>(this.categoriasSources, categoriaTarget);

		this.setImagens(new ArrayList<UploadedFile>());

		return SUCESSO;
	}

	@Override
	public String limparPesquisa() {
		this.setFieldOrdem("nomeFantasia");
		this.setCrudPesquisaModel(new Fornecedor());
		this.getCrudPesquisaModel().setCidade(new Cidade());
		this.getCrudPesquisaModel().setFlagAtivo(Boolean.TRUE);
		this.setGrid(new ArrayList<Fornecedor>());
		return SUCESSO;
	}

	public void listenerLogoMarca(FileUploadEvent event) {

		if (!TSUtil.isEmpty(event) && !TSUtil.isEmpty(event.getFile())) {

			this.setArquivo(event.getFile());
		}
	}

	@Override
	protected boolean validaCampos() {

		boolean validado = true;

		if (!Pattern.matches(Constantes.REGEX_LATITUDE_LONGITUDE, this.getCrudModel().getLatitude().toString())) {

			validado = false;

			super.addErrorMessage("Latiude: Formato inválido.");
		}

		if (!Pattern.matches(Constantes.REGEX_LATITUDE_LONGITUDE, this.getCrudModel().getLongitude().toString())) {

			validado = false;

			super.addErrorMessage("Longitude: Formato inválido.");
		}

		if (TSUtil.isEmpty(this.categorias.getTarget())) {

			validado = false;

			super.addErrorMessage("Longitude: Formato inválido.");
		}

		return validado;
	}

	@Override
	protected void prePersist() {

		int count = 1;

		this.getCrudModel().setFornecedorCategorias(new ArrayList<FornecedorCategoria>());
		this.getCrudModel().setImagensAmbiente(new ArrayList<ImagemFornecedor>());

		if (this.isFlagAlterar()) {

			try {

				new FornecedorCategoria().delete("delete from FornecedorCategoria fc where fc.fornecedor.id = ?", this.getCrudModel().getId());

			} catch (TSApplicationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		for (Categoria item : this.categorias.getTarget()) {

			FornecedorCategoria model = new FornecedorCategoria();

			model.setFornecedor(this.getCrudModel());

			model.setCategoria(item);

			model.setPrioridade(count);

			count++;

			this.getCrudModel().getFornecedorCategorias().add(model);

		}

		for (UploadedFile item : this.imagens) {

			ImagemFornecedor imagemFornecedor = new ImagemFornecedor();

			imagemFornecedor.setFornecedor(this.getCrudModel());

			imagemFornecedor.setImagem(TSUtil.gerarId() + TSFile.obterExtensaoArquivo(item.getFileName()));

			imagemFornecedor.setFile(item);

			this.getCrudModel().getImagensAmbiente().add(imagemFornecedor);
		}

		if (!TSUtil.isEmpty(this.getArquivo())) {

			this.getCrudModel().setLogoMarca(TSUtil.gerarId() + "_logo" + TSFile.obterExtensaoArquivo(this.getArquivo().getFileName()));

		}

		if (!TSUtil.isEmpty(this.getCrudModel().getSite()) && !this.getCrudModel().getSite().contains("http://")) {

			this.getCrudModel().setSite("http://" + this.getCrudModel().getSite());
		}

	}

	@Override
	protected void posDetail() {

		List<Categoria> categoriaTarget = new ArrayList<Categoria>();

		FornecedorCategoria fc = new FornecedorCategoria();

		fc.setFornecedor(this.getCrudModel());

		List<FornecedorCategoria> fornecedorCategorias = fc.findByModel("prioridade");

		for (FornecedorCategoria item : fornecedorCategorias) {

			categoriaTarget.add(item.getCategoria());

		}

		this.categorias = new DualListModel<Categoria>(this.categoriasSources, categoriaTarget);

	}

	@Override
	protected void posPersist() throws TSSystemException, TSApplicationException {

		if (!TSUtil.isEmpty(this.imagens)) {

			for (ImagemFornecedor item : this.getCrudModel().getImagensAmbiente()) {

				try {

					ZapeatUtil.gravarLogoFornecedor(item.getFile().getInputstream(), TSFile.obterExtensaoArquivo(item.getImagem()), item.getImagem(), Constantes.PASTA_UPLOAD);

				} catch (IOException e) {

					e.printStackTrace();
				}
			}
		}

		if (!TSUtil.isEmpty(this.getArquivo())) {

			try {

				ZapeatUtil.gravarLogoFornecedor(this.getArquivo().getInputstream(), TSFile.obterExtensaoArquivo(this.getArquivo().getFileName()), this.getCrudModel().getLogoMarca(), Constantes.PASTA_UPLOAD);

			} catch (IOException e) {

				super.addErrorMessage("Não foi possível gravar a LogoMarca.");

				e.printStackTrace();
			}

		}

	}

	public void handleFileUpload(FileUploadEvent event) {

		this.imagens.add(event.getFile());

	}

	public List<SelectItem> getCidades() {
		return cidades;
	}

	public void setCidades(List<SelectItem> cidades) {
		this.cidades = cidades;
	}

	public UploadedFile getArquivo() {
		return arquivo;
	}

	public void setArquivo(UploadedFile arquivo) {
		this.arquivo = arquivo;
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

	public List<UploadedFile> getImagens() {
		return imagens;
	}

	public void setImagens(List<UploadedFile> imagens) {
		this.imagens = imagens;
	}

}
