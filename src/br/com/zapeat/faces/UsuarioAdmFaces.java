package br.com.zapeat.faces;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import br.com.topsys.constant.TSConstant;
import br.com.topsys.util.TSCryptoUtil;
import br.com.topsys.util.TSUtil;
import br.com.zapeat.model.Fornecedor;
import br.com.zapeat.model.UsuarioAdm;

@ManagedBean(name = "usuarioAdmFaces")
@ViewScoped
public class UsuarioAdmFaces extends CrudFaces<UsuarioAdm> {

	private List<SelectItem> fornecedores;
	private String senha;

	@PostConstruct
	protected void init() {
		this.clearFields();
		this.initCombo();
		this.setFieldOrdem("login");
	}

	private void initCombo() {
		this.fornecedores = super.initCombo(new Fornecedor().findAll(), "id", "nomeFantasia");
	}

	public String limpar() {
		super.limpar();
		this.getCrudModel().setFornecedor(new Fornecedor());
		this.senha = null;
		return null;
	}

	public String limparPesquisa() {
		super.limparPesquisa();
		this.getCrudPesquisaModel().setFornecedor(new Fornecedor());
		return null;
	}

	@Override
	protected void prePersist() {

		if (!super.isFlagAlterar()) {

			this.getCrudModel().setSenha(TSCryptoUtil.gerarHash(this.getCrudModel().getSenha(), TSConstant.CRIPTOGRAFIA_MD5));

		} else if (!this.getCrudModel().getSenha().equals(this.getSenha())) {

			this.getCrudModel().setSenha(TSCryptoUtil.gerarHash(this.getCrudModel().getSenha(), TSConstant.CRIPTOGRAFIA_MD5));
		}

		if (TSUtil.isEmpty(this.getCrudModel().getFornecedor().getId())) {

			this.getCrudModel().setFornecedor(null);
		}

	}

	@Override
	protected void posDetail() {

		if (TSUtil.isEmpty(this.getCrudModel().getFornecedor())) {

			this.getCrudModel().setFornecedor(new Fornecedor());
		}

		this.setSenha(this.getCrudModel().getSenha());

	}

	public List<SelectItem> getFornecedores() {
		return fornecedores;
	}

	public void setFornecedores(List<SelectItem> fornecedores) {
		this.fornecedores = fornecedores;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

}
