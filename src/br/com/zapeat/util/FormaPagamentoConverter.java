package br.com.zapeat.util;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import br.com.zapeat.model.FormaPagamento;

public class FormaPagamentoConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {

		FormaPagamento formaPagamento = new FormaPagamento();

		formaPagamento.setId(new Long(arg2));

		return formaPagamento.getById();
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		
		return arg2.toString();
	}

}
