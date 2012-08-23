package br.com.zapeat.faces;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import org.primefaces.event.FileUploadEvent;

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
		this.getCrudModel().setArquivo(null);
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

			this.getCrudModel().setArquivo(event.getFile());
		}
	}

	@Override
	protected void prePersist() {

		if (!TSUtil.isEmpty(this.getCrudModel().getArquivo())) {

			this.getCrudModel().setLogoMarca(TSUtil.gerarId() + TSFile.obterExtensaoArquivo(this.getCrudModel().getArquivo().getFileName()));

		}
	}

	@Override
	protected void posPersist() throws TSSystemException, TSApplicationException {

		if (!TSUtil.isEmpty(this.getCrudModel().getArquivo())) {

			try {

				ZapeatUtil.gravarLogoFornecedor(this.getCrudModel().getArquivo().getInputstream(), TSFile.obterExtensaoArquivo(this.getCrudModel().getArquivo().getFileName()), this.getCrudModel().getLogoMarca(), Constantes.PASTA_UPLOAD_TEMP);

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

}
