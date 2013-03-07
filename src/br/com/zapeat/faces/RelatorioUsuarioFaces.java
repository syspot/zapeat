package br.com.zapeat.faces;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.cenajur.relat.JasperUtil;
import br.com.topsys.web.faces.TSMainFaces;

@RequestScoped
@ManagedBean(name = "relatorioUsuarioFaces")
public class RelatorioUsuarioFaces extends TSMainFaces {

	public String gerarRelatorio() {

        try {

        	Map<String, Object> parametros = new HashMap<String, Object>();
        	
    		parametros.put("REPORT_LOCALE", new Locale("pt","BR"));

            new JasperUtil().gerarRelatorio("relatUsuarios.jasper", "relatorio_usuarios".toString(), parametros);

        } catch (Exception ex) {

            super.addErrorMessage("Não foi possível gerar relatório.");

            ex.printStackTrace();

        }

        return "sucesso";

    }
}
