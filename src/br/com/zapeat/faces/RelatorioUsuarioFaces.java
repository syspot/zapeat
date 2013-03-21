package br.com.zapeat.faces;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.topsys.web.faces.TSMainFaces;
import br.com.zapeat.relat.JasperUtil;

@RequestScoped
@ManagedBean(name = "relatorioUsuarioFaces")
public class RelatorioUsuarioFaces extends TSMainFaces {

	private Integer situacao;
	
	public String gerarRelatorio() {

        try {

        	Map<String, Object> parametros = new HashMap<String, Object>();
        	
    		parametros.put("REPORT_LOCALE", new Locale("pt","BR"));
			parametros.put("SITUACAO", this.situacao);
    			
            new JasperUtil().gerarRelatorio("relatUsuarios.jasper", "relatorio_usuarios".toString(), parametros);

        } catch (Exception ex) {

            super.addErrorMessage("Não foi possível gerar relatório.");

            ex.printStackTrace();

        }

        return "sucesso";

    }

	public Integer getSituacao() {
		return situacao;
	}

	public void setSituacao(Integer situacao) {
		this.situacao = situacao;
	}
}
