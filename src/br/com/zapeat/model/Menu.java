package br.com.zapeat.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import br.com.topsys.database.hibernate.TSActiveRecordAb;
import br.com.topsys.util.TSUtil;

@Entity
@Table(name = "menus")
public final class Menu extends TSActiveRecordAb<Menu> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5780740765382371694L;

	@Id
	@GeneratedValue
	private Long id;

	private String descricao;

	private String url;

	@Column(name = "managed_bean_reset")
	private String managedBeanReset;

	@Column(name = "flag_ativo")
	private Boolean flagAtivo;

	private Integer ordem;

	@ManyToOne
	@JoinColumn(name = "menu_id")
	private Menu menuPai;

	@OneToMany(mappedBy = "menuPai")
	private List<Menu> menus;

	public Menu() {
	}
	
	public Menu(Long id) {
		this.id = id;
	}

	public Menu(Boolean flagAtivo) {
		this.flagAtivo = flagAtivo;
	}

	public Long getId() {
		return TSUtil.tratarLong(id);
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Boolean getFlagAtivo() {
		return flagAtivo;
	}

	public void setFlagAtivo(Boolean flagAtivo) {
		this.flagAtivo = flagAtivo;
	}

	public Integer getOrdem() {
		return ordem;
	}

	public void setOrdem(Integer ordem) {
		this.ordem = ordem;
	}

	public List<Menu> getMenus() {
		return menus;
	}

	public void setMenus(List<Menu> menus) {
		this.menus = menus;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + ((flagAtivo == null) ? 0 : flagAtivo.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((managedBeanReset == null) ? 0 : managedBeanReset.hashCode());
		result = prime * result + ((menuPai == null) ? 0 : menuPai.hashCode());
		result = prime * result + ((menus == null) ? 0 : menus.hashCode());
		result = prime * result + ((ordem == null) ? 0 : ordem.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		return result;
	}

	public String getManagedBeanReset() {
		return managedBeanReset;
	}

	public void setManagedBeanReset(String managedBeanReset) {
		this.managedBeanReset = managedBeanReset;
	}

	public Menu getMenuPai() {
		return menuPai;
	}

	public void setMenuPai(Menu menuPai) {
		this.menuPai = menuPai;
	}

	public List<Menu> pesquisarExecutaveis() {

		StringBuilder query = new StringBuilder();

		query.append(" from Menu m where menuPai is not null and flagAtivo = true");

		return super.find(query.toString());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Menu other = (Menu) obj;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		if (flagAtivo == null) {
			if (other.flagAtivo != null)
				return false;
		} else if (!flagAtivo.equals(other.flagAtivo))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (managedBeanReset == null) {
			if (other.managedBeanReset != null)
				return false;
		} else if (!managedBeanReset.equals(other.managedBeanReset))
			return false;
		if (menuPai == null) {
			if (other.menuPai != null)
				return false;
		} else if (!menuPai.equals(other.menuPai))
			return false;
		if (menus == null) {
			if (other.menus != null)
				return false;
		} else if (!menus.equals(other.menus))
			return false;
		if (ordem == null) {
			if (other.ordem != null)
				return false;
		} else if (!ordem.equals(other.ordem))
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}

}
