package br.com.zapeat.faces;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.topsys.constant.TSConstant;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.util.TSCryptoUtil;
import br.com.topsys.util.TSUtil;
import br.com.topsys.web.faces.TSMainFaces;
import br.com.topsys.web.util.TSFacesUtil;
import br.com.zapeat.model.Menu;
import br.com.zapeat.model.UsuarioAdm;
import br.com.zapeat.util.Constantes;
import br.com.zapeat.util.UsuarioUtil;

@SessionScoped
@ManagedBean(name = "autenticacaoFaces")
public class AutenticacaoFaces extends TSMainFaces {

	private String nomeTela;
	private String tela;
	private UsuarioAdm usuario;
	private List<Menu> menus;
	private Menu menuSelecionado;
	private Integer tabAtiva;
	private String novaSenha;
	private String confirmaNovaSenha;
	private Long opcao;

	public AutenticacaoFaces() {

		clearFields();

		setTabAtiva(new Integer(0));

		setNomeTela("Área de Trabalho");

	}

	protected void clearFields() {

		this.usuario = new UsuarioAdm();

		this.menus = Collections.emptyList();

		this.menuSelecionado = new Menu();
		
		this.opcao = 1L;

	}

	public String redirecionar() {

		if (!TSUtil.isEmpty(this.menuSelecionado.getManagedBeanReset())) {
			TSFacesUtil.removeManagedBeanInSession(this.menuSelecionado.getManagedBeanReset());
		}

		setTela(this.menuSelecionado.getUrl());
		setNomeTela("Área de Trabalho > " + menuSelecionado.getMenuPai().getDescricao() + " > " + menuSelecionado.getDescricao());
		//setTabAtiva(Integer.valueOf(this.menus.indexOf(this.menuSelecionado.getMenuPai())));

		return SUCESSO;
	}

	private void carregarMenu() {

		if(TSUtil.isEmpty(usuario.getFornecedor())){
			
			menus = new Menu().findAll("ordem");
			
		} else{
			
			this.menus = new ArrayList<Menu>();
			
			Menu menu = new Menu(Constantes.MENU_CADASTRO_BASE).getById();
			
			menu.setMenus(new ArrayList<Menu>());
			
			if(usuario.getFornecedor().getFlagPermissaoPromocao()){
				
				menu.getMenus().add(new Menu(Constantes.MENU_PROMOCAO).getById());
				
				Menu m = new Menu(Constantes.MENU_FORNECEDOR).getById();
				
				m.setDescricao("Cadastro");
				
				menu.getMenus().add(m);
				menu.getMenus().add(new Menu(Constantes.MENU_CARRO_CHEFE).getById());
				
			}
			
			this.menus.add(menu);
			
		}

	}

	public String login() {

		usuario = UsuarioUtil.usuarioAutenticado(usuario);

		if (TSUtil.isEmpty(usuario)) {
			clearFields();
			super.addErrorMessage("Login/Senha sem credencial para acesso.");
			return null;
		}

		carregarMenu();
		
		TSFacesUtil.addObjectInSession(Constantes.USUARIO_CONECTADO, usuario);

		return SUCESSO;
	}
	
	public String trocarSenha() throws TSApplicationException{
		
		usuario = UsuarioUtil.usuarioAutenticado(usuario);
		
		if(TSUtil.isEmpty(usuario)){
			super.addErrorMessage("Usuário ou Senha inválidos");
			return null;
		}
		
		if(novaSenha.equals(confirmaNovaSenha)){
			
			usuario.setSenha(TSCryptoUtil.gerarHash(novaSenha, TSConstant.CRIPTOGRAFIA_MD5));
			usuario.update();
			super.addInfoMessage("Senha alterada com sucesso");
			
		} else{
			
			super.addErrorMessage("Senhas não conferem");
			
		}
		
		clearFields();
		return null;
		
	}

	public String logout() {

		TSFacesUtil.removeObjectInSession(Constantes.USUARIO_CONECTADO);

		TSFacesUtil.getRequest().getSession().invalidate();

		return "sair";
	}

	public String getNomeTela() {
		return nomeTela;
	}

	public void setNomeTela(String nomeTela) {
		this.nomeTela = nomeTela;
	}

	public String getTela() {
		return tela;
	}

	public void setTela(String tela) {
		this.tela = tela;
	}

	public List<Menu> getMenus() {
		return menus;
	}

	public void setMenus(List<Menu> menus) {
		this.menus = menus;
	}

	public Menu getMenuSelecionado() {
		return menuSelecionado;
	}

	public void setMenuSelecionado(Menu menuSelecionado) {
		this.menuSelecionado = menuSelecionado;
	}

	public Integer getTabAtiva() {
		return tabAtiva;
	}

	public void setTabAtiva(Integer tabAtiva) {
		this.tabAtiva = tabAtiva;
	}

	public UsuarioAdm getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioAdm usuario) {
		this.usuario = usuario;
	}

	public String getNovaSenha() {
		return novaSenha;
	}

	public void setNovaSenha(String novaSenha) {
		this.novaSenha = novaSenha;
	}

	public String getConfirmaNovaSenha() {
		return confirmaNovaSenha;
	}

	public void setConfirmaNovaSenha(String confirmaNovaSenha) {
		this.confirmaNovaSenha = confirmaNovaSenha;
	}

	public Long getOpcao() {
		return opcao;
	}

	public void setOpcao(Long opcao) {
		this.opcao = opcao;
	}

}
