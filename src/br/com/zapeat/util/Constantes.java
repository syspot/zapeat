package br.com.zapeat.util;

import java.io.File;

public class Constantes {

	public static final String USUARIO_CONECTADO = "usuarioConectado";
	public static final String USUARIOS_CONECTADOS = "usuariosConectados";

	public static final String PASTA_UPLOAD = "e:" + File.separator + "img_zapeat" + File.separator;

	public static final String PASTA_DOWNLOAD = "http://localhost:8080/img_zapeat/";

	public static final Integer LARGURA_ALTURA_80 = 80;

	public static final Integer LARGURA_THUMB = 80;
	public static final Integer ALTURA_THUMB = 80;

	public static final Integer LARGURA_IMAGEM_PROMOCAO_FULL = 590;
	public static final Integer ALTURA_IMAGEM_PROMOCAO_FULL = 260;

	public static final Integer LARGURA_BANNER_SUPERIOR_GRANDE = 728;
	public static final Integer ALTURA_BANNER_SUPERIOR_GRANDE = 90;

	public static final Integer LARGURA_BANNER_SUPERIOR_PEQUENO = 210;
	public static final Integer ALTURA_BANNER_SUPERIOR_PEQUENO = 110;

	public static final Integer LARGURA_BANNER_LATERAL = 170;
	public static final Integer ALTURA_BANNER_LATERAL = 260;

	public static final Integer LARGURA_IMAGEM_PROMOCAO_THUMB = 180;
	public static final Integer ALTURA_IMAGEM_PROMOCAO_THUMB = 79;

	public static final Integer LARGURA_PROMOCAO_THUMB = 80;
	public static final Integer ALTURA_PROMOCAO_THUMB = 80;

	public static final Integer LARGURA_IMAGEM_FORNECEDOR_FULL = 590;
	public static final Integer ALTURA_IMAGEM_FORNECEDOR_FULL = 260;

	public static final Integer LARGURA_IMAGEM_CARRO_CHEFE_FULL = 590;
	public static final Integer ALTURA_IMAGEM_CARRO_CHEFE_FULL = 260;

	public static final Integer LARGURA_IMAGEM_FORNECEDOR_THUMB = 180;
	public static final Integer ALTURA_IMAGEM_FORNECEDOR_THUMB = 79;

	public static final Integer LARGURA_IMAGEM_CARRO_CHEFE_THUMB = 180;
	public static final Integer ALTURA_IMAGEM_CARRO_CHEFE_THUMB = 79;

	public static final Integer LARGURA_FORNECEDOR_LOGOMARCA = 80;
	public static final Integer ALTURA_FORNECEDOR_LOGOMARCA = 80;

	public static final Integer LARGURA_CATEGORIA = 80;
	public static final Integer ALTURA_CATEGORIA = 80;

	public static final Integer LARGURA_180 = 180;
	public static final Integer ALTURA_79 = 79;

	public static final String FOTO_80x80 = "80x80_";
	public static final String FOTO_180x79 = "180x79_";

	public static final String PREFIXO_IMAGEM_PROMOCAO_FULL = "590x260_";
	public static final String PREFIXO_IMAGEM_PROMOCAO_THUMB = "180x79_";
	public static final String PREFIXO_PROMOCAO_THUMB = "80x80_";
	public static final String PREFIXO_IMAGEM_FORNECEDOR_FULL = "590x260_";
	public static final String PREFIXO_IMAGEM_FORNECEDOR_THUMB = "180x79_";
	public static final String PREFIXO_IMAGEM_CARRO_CHEFE_FULL = "590x260_";
	public static final String PREFIXO_IMAGEM_CARRO_CHEFE_THUMB = "180x79_";
	public static final String PREFIXO_IMAGEM_FORNECEDOR_LOGOMARCA = "80x80_";
	public static final String PREFIXO_IMAGEM_BANNER_LATERAL = "170x260_";
	public static final String PREFIXO_IMAGEM_BANNER_SUPERIOR_GRANDE = "728x90_";
	public static final String PREFIXO_IMAGEM_BANNER_SUPERIOR_PEQUENO = "210x110_";

	public static final String REGEX_LATITUDE_LONGITUDE = "[-+]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)?";

	public static final Long TIPO_BANNER_SUPERIOR_GRANDE = 1L;
	public static final Long TIPO_BANNER_SUPERIOR_PEQUENO = 2L;
	public static final Long TIPO_BANNER_SUPERIOR_LATERAL = 3L;

	public static Long TIPO_PROMOCAO_HORA = 1L;
	public static Long TIPO_PROMOCAO_DIA = 2L;
	public static Long TIPO_PROMOCAO_SEMANA = 3L;
	
	public static Long MENU_PROMOCAO = 5L;
	public static Long MENU_CADASTRO_BASE = 1L;

}
