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
import org.primefaces.model.UploadedFile;

import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.exception.TSSystemException;
import br.com.topsys.file.TSFile;
import br.com.topsys.util.TSUtil;
import br.com.zapeat.model.Cidade;
import br.com.zapeat.model.Fornecedor;
import br.com.zapeat.util.Constantes;
import br.com.zapeat.util.ZapeatUtil;

@ManagedBean(name = "fornecedorFaces")
@ViewScoped
public class FornecedorFaces extends CrudFaces<Fornecedor> {

	private List<SelectItem> cidades;
	private UploadedFile arquivo;
	
	private UploadedFile imagemThumb;

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
	protected void posDetail() {
		getCrudModel().setImagemCadastrada(Boolean.TRUE);
	}

	@Override
	protected void prePersist() {

		if(!TSUtil.isEmpty(imagemThumb)){
			
			String nomeArquivo = TSUtil.gerarId() + TSFile.obterExtensaoArquivo(getCrudModel().getImagemThumb());
			
			getCrudModel().setImagemThumb(nomeArquivo);
			
			ZapeatUtil.gravarImagemComRedimensionamento(imagemThumb, Constantes.PREFIXO_IMAGEM_FORNECEDOR_THUMB + nomeArquivo, Constantes.PASTA_UPLOAD_FORNECEDOR, Constantes.LARGURA_FORNECEDOR_THUMB, Constantes.ALTURA_FORNECEDOR_THUMB);
			
		}
		
		if (!TSUtil.isEmpty(this.getArquivo())) {

			this.getCrudModel().setLogoMarca(TSUtil.gerarId() + TSFile.obterExtensaoArquivo(this.getArquivo().getFileName()));

		}
	}

	@Override
	protected void posPersist() throws TSSystemException, TSApplicationException {

		if (!TSUtil.isEmpty(this.getArquivo())) {

			try {

				ZapeatUtil.gravarLogoFornecedor(this.getArquivo().getInputstream(), TSFile.obterExtensaoArquivo(this.getArquivo().getFileName()), this.getCrudModel().getLogoMarca(), Constantes.PASTA_UPLOAD_TEMP);

			} catch (IOException e) {

				super.addErrorMessage("Não foi possível gravar a LogoMarca.");

				e.printStackTrace();
			}

		}

	}
	
	public void enviarImagem(FileUploadEvent event) {
		this.imagemThumb = event.getFile();
		getCrudModel().setImagemThumb(ZapeatUtil.criarImagemTemp(event));
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

	public UploadedFile getImagemThumb() {
		return imagemThumb;
	}

	public void setImagemThumb(UploadedFile imagemThumb) {
		this.imagemThumb = imagemThumb;
	}

}
