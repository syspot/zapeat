package br.com.zapeat.faces;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.topsys.web.faces.TSMainFaces;
import br.com.topsys.web.util.TSFacesUtil;
import br.com.zapeat.model.UsuarioSite;

@ViewScoped
@ManagedBean(name = "relatorioUsuarioFaces")
public class RelatorioUsuarioFaces extends TSMainFaces {

	private List<UsuarioSite> grid;
	private Integer situacao;
	
	public String gerarRelatorio() {

		this.grid = new UsuarioSite(situacao.equals(1) ? null : situacao.equals(2)).findByModel("nome");
		
		TSFacesUtil.gerarResultadoLista(this.grid);
		
		/*
        try {

        	Map<String, Object> parametros = new HashMap<String, Object>();
        	
    		parametros.put("REPORT_LOCALE", new Locale("pt","BR"));
			parametros.put("SITUACAO", this.situacao);
    			
            new JasperUtil().gerarRelatorio("relatUsuarios.jasper", "relatorio_usuarios".toString(), parametros);

        } catch (Exception ex) {

            super.addErrorMessage("Não foi possível gerar relatório.");

            ex.printStackTrace();

        } */

        return null;

    }

	public Integer getSituacao() {
		return situacao;
	}

	public void setSituacao(Integer situacao) {
		this.situacao = situacao;
	}

	public List<UsuarioSite> getGrid() {
		return grid;
	}

	public void setGrid(List<UsuarioSite> grid) {
		this.grid = grid;
	}
}
