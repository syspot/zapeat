package br.com.zapeat.faces;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import org.primefaces.event.FileUploadEvent;

import br.com.topsys.file.TSFile;
import br.com.topsys.util.TSUtil;
import br.com.zapeat.model.Fornecedor;
import br.com.zapeat.model.FornecedorCategoria;
import br.com.zapeat.model.ImagemPromocao;
import br.com.zapeat.model.Promocao;
import br.com.zapeat.model.TipoPromocao;
import br.com.zapeat.model.UsuarioAdm;
import br.com.zapeat.util.Constantes;
import br.com.zapeat.util.UsuarioUtil;
import br.com.zapeat.util.ZapeatUtil;

@ViewScoped
@ManagedBean(name = "promocaoFaces")
public class PromocaoFaces extends CrudFaces<Promocao> {

	private List<SelectItem> tiposPromocoes;
	private List<SelectItem> fornecedores;
	private List<SelectItem> fornecedoresCategorias;

	private ImagemPromocao imagemPromocaoSelecionada;
	private Integer mover;

	private boolean usuarioFornecedor;
	
	private Date dataAtual;
	private int horaAtual;
	private int minutoAtual;

	@PostConstruct
	protected void init() {

		this.clearFields();
		this.initCombos();
		setFieldOrdem("descricao");

		UsuarioAdm usuario = UsuarioUtil.obterUsuarioConectado();

		if (!TSUtil.isEmpty(usuario.getFornecedor())) {

			getCrudModel().setFornecedor(usuario.getFornecedor());
			getCrudPesquisaModel().setFornecedor(usuario.getFornecedor());
			this.atualizarComboFornecedorCategoria();
			this.usuarioFornecedor = true;

		} else {

			this.usuarioFornecedor = false;

		}

		Calendar c = Calendar.getInstance();
		
		dataAtual = c.getTime();
		horaAtual = c.get(Calendar.HOUR_OF_DAY);
		minutoAtual = c.get(Calendar.MINUTE);
		

	}

	private void initCombos() {
		this.tiposPromocoes = super.initCombo(new TipoPromocao().findAll(), "id", "descricao");
		this.fornecedores = super.initCombo(new Fornecedor().findAll(), "id", "nomeFantasia");
	}

	public void atualizarComboFornecedorCategoria() {
		this.fornecedoresCategorias = super.initCombo(new FornecedorCategoria(getCrudModel().getFornecedor()).findByModel(), "id", "categoria.descricao");
	}

	@Override
	public String limpar() {
		setCrudModel(new Promocao());
		getCrudModel().setFornecedor(new Fornecedor());
		getCrudModel().setFornecedorCategoria(new FornecedorCategoria());
		getCrudModel().setTipoPromocao(new TipoPromocao());
		getCrudModel().setImagensPromocoes(new ArrayList<ImagemPromocao>());
		setFlagAlterar(Boolean.FALSE);
		return null;
	}

	@Override
	public String limparPesquisa() {
		setCrudPesquisaModel(new Promocao());
		getCrudPesquisaModel().setFornecedor(new Fornecedor());
		getCrudPesquisaModel().setTipoPromocao(new TipoPromocao());
		setGrid(new ArrayList<Promocao>());
		return null;
	}

	@Override
	protected boolean validaCampos() {

		boolean valida = true;

		if (getCrudModel().isPromocaoDoDia()) {
			getCrudModel().setFim(ZapeatUtil.getProximoDia(getCrudModel().getInicio()));
		}

		if (getCrudModel().isPromocaoDaSemana()) {
			getCrudModel().setFim(ZapeatUtil.getProximaSemana(getCrudModel().getInicio()));
		}

		if (getCrudModel().isPromocaoDaHora()) {
			getCrudModel().setFim(ZapeatUtil.getProximaHora(getCrudModel().getInicio()));
		}

		List<Promocao> promocoes = getCrudModel().pesquisarPromocoesAtivas();

		if (TSUtil.isEmpty(getCrudModel().getImagemThumb())) {
			valida = false;
			ZapeatUtil.addErrorMessage("Imagem Thumb: Campo obrigatório");
		}
		
		if (!TSUtil.isEmpty(promocoes)) {
			
			boolean igual = false;
			
			for(Promocao promocao : promocoes){
				
				if(isFlagAlterar()){
					
					if(!promocao.getId().equals(getCrudModel().getId())){
						
						igual = true;
						break;
						
					}
					
				} else{
					
					igual = true;
					
				}
				
			}
			
			if(igual){
				valida = false;
				ZapeatUtil.addErrorMessage("Já existe uma promoção ativa para o período cadastrado");
			}
			
		}

		return valida;
	}
	
	@Override
	protected void preInsert() {
		getCrudModel().setDataCadastro(new Date());
	}

	@Override
	protected void posDetail() {

		getCrudModel().setFornecedor(getCrudModel().getFornecedorCategoria().getFornecedor());

		this.atualizarComboFornecedorCategoria();

	}

	public void enviarImagem(FileUploadEvent event) {

		ImagemPromocao imagemPromocao = new ImagemPromocao();

		imagemPromocao.setPromocao(getCrudModel());
		imagemPromocao.setImagem(TSUtil.gerarId() + TSFile.obterExtensaoArquivo(event.getFile().getFileName()));

		ZapeatUtil.gravarImagemComRedimensionamento(event.getFile(), Constantes.PREFIXO_IMAGEM_PROMOCAO_FULL + imagemPromocao.getImagem(), Constantes.PASTA_UPLOAD, Constantes.LARGURA_IMAGEM_PROMOCAO_FULL, Constantes.ALTURA_IMAGEM_PROMOCAO_FULL);
		ZapeatUtil.gravarImagemComRedimensionamento(event.getFile(), Constantes.PREFIXO_IMAGEM_PROMOCAO_THUMB + imagemPromocao.getImagem(), Constantes.PASTA_UPLOAD, Constantes.LARGURA_IMAGEM_PROMOCAO_THUMB, Constantes.ALTURA_IMAGEM_PROMOCAO_THUMB);

		getCrudModel().getImagensPromocoes().add(imagemPromocao);

	}

	public void enviarThumb(FileUploadEvent event) {

		getCrudModel().setImagemThumb(TSUtil.gerarId() + TSFile.obterExtensaoArquivo(event.getFile().getFileName()));
		ZapeatUtil.gravarImagemComRedimensionamento(event.getFile(), Constantes.PREFIXO_PROMOCAO_THUMB + getCrudModel().getImagemThumb(), Constantes.PASTA_UPLOAD, Constantes.LARGURA_PROMOCAO_THUMB, Constantes.ALTURA_PROMOCAO_THUMB);

	}

	public String removerImagem() {
		getCrudModel().getImagensPromocoes().remove(this.imagemPromocaoSelecionada);
		return null;
	}

	@Override
	public boolean isUsuarioFornecedor(){
		return usuarioFornecedor;
	}

	public List<SelectItem> getTiposPromocoes() {
		return tiposPromocoes;
	}

	public void setTiposPromocoes(List<SelectItem> tiposPromocoes) {
		this.tiposPromocoes = tiposPromocoes;
	}

	public List<SelectItem> getFornecedores() {
		return fornecedores;
	}

	public void setFornecedores(List<SelectItem> fornecedores) {
		this.fornecedores = fornecedores;
	}

	public ImagemPromocao getImagemPromocaoSelecionada() {
		return imagemPromocaoSelecionada;
	}

	public void setImagemPromocaoSelecionada(ImagemPromocao imagemPromocaoSelecionada) {
		this.imagemPromocaoSelecionada = imagemPromocaoSelecionada;
	}

	public Integer getMover() {
		return mover;
	}

	public void setMover(Integer mover) {
		this.mover = mover;
	}

	public List<SelectItem> getFornecedoresCategorias() {
		return fornecedoresCategorias;
	}

	public void setFornecedoresCategorias(List<SelectItem> fornecedoresCategorias) {
		this.fornecedoresCategorias = fornecedoresCategorias;
	}

	public Date getDataAtual() {
		return dataAtual;
	}

	public void setDataAtual(Date dataAtual) {
		this.dataAtual = dataAtual;
	}

	public int getHoraAtual() {
		return horaAtual;
	}

	public void setHoraAtual(int horaAtual) {
		this.horaAtual = horaAtual;
	}

	public int getMinutoAtual() {
		return minutoAtual;
	}

	public void setMinutoAtual(int minutoAtual) {
		this.minutoAtual = minutoAtual;
	}

}
