package br.com.zapeat.util;

import java.util.regex.Pattern;

public class GerarSenha {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//System.out.println(TSCryptoUtil.gerarHash("1234", "MD5"));
		
		String regExp = "[-+]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)?";
		System.out.println( Pattern.matches( regExp, ".-1" ) ); // true
		System.out.println( Pattern.matches( regExp, "1." ) ); // false
		System.out.println( Pattern.matches( regExp, ".7" ) ); // true
		System.out.println(Pattern.matches(regExp, "-38.496414" )); //true
		
		
	}

}
