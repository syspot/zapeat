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

		this.categoriasSources = new Categoria().findAll("descricao");

		this.targetCategorias = new DualListModel<Categoria>();

		List<Categoria> categoriaTarget = new ArrayList<Categoria>();

		this.categorias = new DualListModel<Categoria>(this.categoriasSources, categoriaTarget);

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

		return validado;
	}

	@Override
	protected void prePersist() {

		int count = 1;

		this.getCrudModel().setFornecedorCategorias(new ArrayList<FornecedorCategoria>());

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

		if (!TSUtil.isEmpty(this.getArquivo())) {

			this.getCrudModel().setLogoMarca(TSUtil.gerarId() + TSFile.obterExtensaoArquivo(this.getArquivo().getFileName()));

		}
	}

	@Override
	protected void posDetail() {

		List<Categoria> categoriaTarget = new ArrayList<Categoria>();

		List<FornecedorCategoria> fornecedorCategorias = new FornecedorCategoria().findByModel("prioridade");

		for (FornecedorCategoria item : fornecedorCategorias) {

			categoriaTarget.add(item.getCategoria());

		}

		this.categorias = new DualListModel<Categoria>(this.categoriasSources, categoriaTarget);

	}

	@Override
	protected void posPersist() throws TSSystemException, TSApplicationException {

		if (!TSUtil.isEmpty(this.getArquivo())) {

			try {

				ZapeatUtil.gravarLogoFornecedor(this.getArquivo().getInputstream(), TSFile.obterExtensaoArquivo(this.getArquivo().getFileName()), this.getCrudModel().getLogoMarca(), Constantes.PASTA_UPLOAD);

			} catch (IOException e) {

				super.addErrorMessage("Não foi possível gravar a LogoMarca.");

				e.printStackTrace();
			}

		}

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

}
