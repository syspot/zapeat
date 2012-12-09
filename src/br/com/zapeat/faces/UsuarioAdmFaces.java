package br.com.zapeat.faces;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.topsys.constant.TSConstant;
import br.com.topsys.util.TSCryptoUtil;
import br.com.topsys.util.TSUtil;
import br.com.zapeat.model.Cidade;
import br.com.zapeat.model.Estado;
import br.com.zapeat.model.Fornecedor;
import br.com.zapeat.model.UsuarioAdm;

@ViewScoped
@ManagedBean(name = "usuarioAdmFaces")
public class UsuarioAdmFaces extends ComboCidadeEstadoFaces<UsuarioAdm> {

	private String senha;

	@PostConstruct
	protected void init() {
		this.clearFields();
		this.setFieldOrdem("nome");
	}

	public String limpar() {
		super.limpar();
		getCrudModel().setFornecedor(new Fornecedor());
		getCrudModel().getFornecedor().setCidade(new Cidade());
		getCrudModel().getFornecedor().getCidade().setEstado(new Estado());
		this.senha = null;
		return null;
	}

	public String limparPesquisa() {
		super.limparPesquisa();
		getCrudPesquisaModel().setFornecedor(new Fornecedor());
		getCrudPesquisaModel().getFornecedor().setCidade(new Cidade());
		getCrudPesquisaModel().getFornecedor().getCidade().setEstado(new Estado());
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
		
		super.atualizarComboCidade();
		
		super.atualizarComboFornecedor();

	}
	
	@Override
	protected boolean validaCampos() {
		
		UsuarioAdm usuario = getCrudModel().obterPorLogin();
		
		boolean valida = true;
		
		if(!TSUtil.isEmpty(usuario)){
			
			if(!usuario.getId().equals(getCrudModel().getId())){

				valida = false;
				super.addErrorMessage("Esse login ja existe.");
				
			}
				
		} 
		
		return valida;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
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
		return getCrudModel().getFornecedor();
	}

}
