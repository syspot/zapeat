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
import br.com.zapeat.model.Categoria;
import br.com.zapeat.model.Cidade;
import br.com.zapeat.model.Fornecedor;
import br.com.zapeat.model.FornecedorCategoria;
import br.com.zapeat.model.ImagemFornecedor;
import br.com.zapeat.util.Constantes;
import br.com.zapeat.util.ZapeatUtil;

@ViewScoped
@ManagedBean(name = "fornecedorFaces")
public class FornecedorFaces extends CrudFaces<Fornecedor> {

	private List<SelectItem> cidades;
	private List<Categoria> categoriasSources;
	private DualListModel<Categoria> categorias;
	private DualListModel<Categoria> targetCategorias;

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
		this.setFlagAlterar(Boolean.FALSE);
		this.getCrudModel().setFornecedorCategorias(new ArrayList<FornecedorCategoria>());
		this.getCrudModel().setImagensFornecedores(new ArrayList<ImagemFornecedor>());

		this.categoriasSources = new Categoria().findAll("descricao");

		this.targetCategorias = new DualListModel<Categoria>();

		List<Categoria> categoriaTarget = new ArrayList<Categoria>();

		this.categorias = new DualListModel<Categoria>(this.categoriasSources, categoriaTarget);

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

	@Override
	protected boolean validaCampos() {

		boolean validado = true;

		if (!Pattern.matches(Constantes.REGEX_LATITUDE_LONGITUDE, this.getCrudModel().getLatitude().toString())) {
			validado = false;
			ZapeatUtil.addErrorMessage("Latitude: Formato inválido.");
		}

		if (!Pattern.matches(Constantes.REGEX_LATITUDE_LONGITUDE, this.getCrudModel().getLongitude().toString())) {
			validado = false;
			ZapeatUtil.addErrorMessage("Longitude: Formato inválido.");
		}

		return validado;
	}
	
	@Override
	protected void prePersist() {
		
		FornecedorCategoria fornecedorCategoria;
		
		getCrudModel().getFornecedorCategorias().clear();
		
		int cont = 1;
		
		for(Categoria categoria : categorias.getTarget()){
			
			fornecedorCategoria = new FornecedorCategoria();
			
			fornecedorCategoria.setCategoria(categoria);
			fornecedorCategoria.setFornecedor(getCrudModel());
			fornecedorCategoria.setPrioridade(cont++);
			
			getCrudModel().getFornecedorCategorias().add(fornecedorCategoria);
			
		}
		
	}

	@Override
	protected void posDetail() {

		List<Categoria> categoriaTarget = new ArrayList<Categoria>();

		for (FornecedorCategoria item : getCrudModel().getFornecedorCategorias()) {

			categoriaTarget.add(item.getCategoria());

		}
		
		this.categoriasSources.removeAll(categoriaTarget);

		this.categorias = new DualListModel<Categoria>(this.categoriasSources, categoriaTarget);

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
		
		ZapeatUtil.gravarImagemComRedimensionamento(event.getFile(), Constantes.PREFIXO_IMAGEM_FORNECEDOR_FULL + imagemFornecedor.getImagem(), Constantes.PASTA_UPLOAD, Constantes.LARGURA_FORNECEDOR_FULL, Constantes.ALTURA_FORNECEDOR_FULL);
		ZapeatUtil.gravarImagemComRedimensionamento(event.getFile(), Constantes.PREFIXO_IMAGEM_FORNECEDOR_THUMB + imagemFornecedor.getImagem(), Constantes.PASTA_UPLOAD, Constantes.LARGURA_FORNECEDOR_THUMB, Constantes.ALTURA_FORNECEDOR_THUMB);
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

}
