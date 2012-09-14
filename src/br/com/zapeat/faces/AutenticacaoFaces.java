package br.com.zapeat.faces;

import java.util.Collections;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.topsys.util.TSUtil;
import br.com.topsys.web.faces.TSMainFaces;
import br.com.topsys.web.util.TSFacesUtil;
import br.com.zapeat.model.Menu;
import br.com.zapeat.model.Usuario;
import br.com.zapeat.util.Constantes;
import br.com.zapeat.util.ZapeatUtil;
import br.com.zapeat.util.UsuarioUtil;

@SessionScoped
@ManagedBean(name = "autenticacaoFaces")
public class AutenticacaoFaces extends TSMainFaces {

	
	private String nomeTela;
	private String tela;
	private Usuario usuario;
	private List<Menu> menus;
	private Menu menuSelecionado;
	private Integer tabAtiva;

	public AutenticacaoFaces() {

		clearFields();

		setTabAtiva(new Integer(0));

		setNomeTela("Área de Trabalho");
		
	}
	
	protected void clearFields() {
    	
        this.usuario = new Usuario();

        this.menus = Collections.emptyList();               
        
        this.menuSelecionado = new Menu();

    }

	public String redirecionar() {

		if (!TSUtil.isEmpty(this.menuSelecionado.getManagedBeanReset())) {
			TSFacesUtil.removeManagedBeanInSession(this.menuSelecionado.getManagedBeanReset());
		}

		setTela(this.menuSelecionado.getUrl());
		setNomeTela("Área de Trabalho > " + menuSelecionado.getMenuPai().getDescricao() + " > " + menuSelecionado.getDescricao());
		setTabAtiva(Integer.valueOf(this.menus.indexOf(this.menuSelecionado.getMenuPai())));
		
		return SUCESSO;
	}
	
	private void carregarMenu() {
		
		menus = new Menu().findAll("ordem");
		
	}
	
	public String login() {			
		
		usuario = UsuarioUtil.usuarioAutenticado(usuario);
				
		if (TSUtil.isEmpty(usuario)) {
			clearFields();
			ZapeatUtil.addWarnMessage("Login/Senha sem credencial para acesso.");			
			return null;
		}
		
		TSFacesUtil.addObjectInSession(Constantes.USUARIO_CONECTADO, usuario);
		carregarMenu();
				
		return SUCESSO;
	}

	public String logout() {
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

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
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
	
}
