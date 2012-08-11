package br.com.zapeat.util;

import br.com.topsys.util.TSCryptoUtil;

public class GerarSenha {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(TSCryptoUtil.gerarHash("1234", "MD5"));
	}

}
