package br.com.zapeat.util;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.topsys.util.TSUtil;

/**
 * Servlet Filter implementation class Piloto
 */
@WebFilter("/")
public class ZapeatFilter implements Filter {

	@Override
	public void destroy() {
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		HttpServletRequest r = (HttpServletRequest) request;

		HttpServletResponse resp = (HttpServletResponse) response;

		String uri = r.getRequestURI();

		if (uri != null) {
			
			uri = uri.substring(uri.lastIndexOf("/"), uri.length());
			
		}
		
		if ((!TSUtil.isEmpty(r.getSession().getAttribute(Constantes.USUARIO_CONECTADO)) && (uri.equals("/dashboard.xhtml"))) || uri.contains("/login.xhtml")) {
			
			chain.doFilter(request, response);
			
		} else {

			resp.sendRedirect(r.getContextPath() + "/pages/login.xhtml");

		}

	}

}
