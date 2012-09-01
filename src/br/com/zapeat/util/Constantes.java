package br.com.zapeat.util;

import java.io.File;

public class Constantes {

	public static final String USUARIO_CONECTADO = "usuarioConectado";
	public static final String USUARIOS_CONECTADOS = "usuariosConectados";

	public static final String PASTA_UPLOAD_TEMP = "e:" + File.separator + "img_zapeat" + File.separator;
	public static final String PASTA_UPLOAD = "e:" + File.separator + "img_zapeat" + File.separator;
	public static final String PASTA_UPLOAD_PROMOCAO = "e:" + File.separator + "img_zapeat" + File.separator + "promocao" + File.separator;
	public static final String PASTA_UPLOAD_BANNER = "e:" + File.separator + "img_zapeat" + File.separator + "banner" + File.separator;

	public static final String PASTA_DOWNLOAD_TEMP = "http://localhost:8080/img_zapeat/";
	public static final String PASTA_DOWNLOAD = "http://localhost:8080/img_zapeat/";
	public static final String PASTA_DOWNLOAD_PROMOCAO = "http://localhost:8080/img_zapeat/promocao/";
	public static final String PASTA_DOWNLOAD_BANNER = "http://localhost:8080/img_zapeat/banner/";

	public static final Integer LARGURA_ALTURA_80 = 80;
	
	public static final Integer LARGURA_180 = 180;
	public static final Integer ALTURA_79 = 79;
	
	public static final String FOTO_80x80 = "80x80_";
	public static final String FOTO_180x79 = "180x79_";
	
	public static final String REGEX_LATITUDE_LONGITUDE = "[-+]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)?";

}
