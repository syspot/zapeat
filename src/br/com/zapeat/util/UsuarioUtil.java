package br.com.zapeat.util;

import java.util.ArrayList;
import java.util.List;

import br.com.topsys.util.TSCryptoUtil;
import br.com.topsys.util.TSUtil;
import br.com.topsys.web.util.TSFacesUtil;
import br.com.zapeat.model.Usuario;
import br.com.zapeat.model.UsuarioAdm;

public class UsuarioUtil {		    
    
    private UsuarioUtil(){
    	
    }
    
    public static Usuario obterUsuarioConectado() {
        return (Usuario) TSFacesUtil.getObjectInSession(Constantes.USUARIO_CONECTADO);
    }

    @SuppressWarnings("unchecked")
	public static List<Usuario> obterUsuarioesConectados() {
        return (List<Usuario>) TSFacesUtil.getRequest().getSession().getServletContext().getAttribute(Constantes.USUARIOS_CONECTADOS);
    }

    @SuppressWarnings("unchecked")
	public static void adicionar(Usuario Usuario) {
    	
        List<Usuario> UsuarioesConectados = (List<Usuario>) TSFacesUtil.getRequest().getSession().getServletContext().getAttribute("UsuarioesConectados");

        if (TSUtil.isEmpty(UsuarioesConectados)) {
        	UsuarioesConectados = new ArrayList<Usuario>();
        }

        TSFacesUtil.addObjectInSession(Constantes.USUARIO_CONECTADO, Usuario);

        if (!UsuarioesConectados.contains(Usuario)) {
        	UsuarioesConectados.add(Usuario);
        }

        TSFacesUtil.getRequest().getSession().getServletContext().setAttribute(Constantes.USUARIOS_CONECTADOS, UsuarioesConectados);
    }

    @SuppressWarnings("unchecked")
	public static void remover() {
    	
        List<Usuario> UsuarioesConectados = (List<Usuario>) TSFacesUtil.getRequest().getSession().getServletContext().getAttribute("operadoresConectados");

        if (!TSUtil.isEmpty(UsuarioesConectados)) {
        	UsuarioesConectados.remove(obterUsuarioesConectados());
        }

        TSFacesUtil.getRequest().getSession().getServletContext().setAttribute(Constantes.USUARIOS_CONECTADOS, UsuarioesConectados);
    
    }
    
    public static String getSenhaCriptografada(String senha) {
    	return TSCryptoUtil.gerarHash(senha, "MD5");
    }

    public static UsuarioAdm usuarioAutenticado(UsuarioAdm model) {
    	
    	model.setSenha(getSenhaCriptografada(model.getSenha()));    	
		
        return model.getByModel();
            
    } 

    
}
