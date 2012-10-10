package br.com.zapeat.faces;

import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.FileUploadEvent;

import br.com.topsys.file.TSFile;
import br.com.topsys.util.TSUtil;
import br.com.zapeat.model.FormaPagamento;
import br.com.zapeat.util.Constantes;
import br.com.zapeat.util.ZapeatUtil;

@ViewScoped
@ManagedBean(name = "formaPagamentoFaces")
public class FormaPagamentoFaces extends CrudFaces<FormaPagamento> {


	@PostConstruct
	protected void init() {
		this.clearFields();
		setFieldOrdem("descricao");
	}
	
	@Override
	public String limpar() {
		setCrudModel(new FormaPagamento());
		getCrudModel().setFlagAtivo(Boolean.TRUE);
		setFlagAlterar(Boolean.FALSE);
		return null;
	}
	
	@Override
	public String limparPesquisa() {
		setCrudPesquisaModel(new FormaPagamento());
		getCrudPesquisaModel().setFlagAtivo(Boolean.TRUE);
		setGrid(new ArrayList<FormaPagamento>());
		return null;
	}
	
	@Override
	protected boolean validaCampos() {
		
		boolean valida = true;
		
		if(TSUtil.isEmpty(getCrudModel().getImagem())){
			valida = false;
			ZapeatUtil.addErrorMessage("Imagem: Campo obrigatório");
		}
		
		return valida;
	}
	
	public void enviarImagem(FileUploadEvent event) {
		
		getCrudModel().setImagem(TSUtil.gerarId() + TSFile.obterExtensaoArquivo(event.getFile().getFileName()));
		ZapeatUtil.gravarImagemComRedimensionamento(event.getFile(), Constantes.PREFIXO_IMAGEM_FORMA_PAGAMENTO + getCrudModel().getImagem(), Constantes.PASTA_UPLOAD, Constantes.LARGURA_IMAGEM_FORMA_PAGAMENTO, Constantes.ALTURA_IMAGEM_FORMA_PAGAMENTO);
		
	}
	
	
}
