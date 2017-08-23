package main.java.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Security {

	private final static String HASH = "eb834bc6843d818ea0fd29038673a50f";
	
	public static String gerarMD5(String mensagem) {
		mensagem = mensagem + HASH;
		MessageDigest mensagemMd5 = null;
		try {
			mensagemMd5 = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		mensagemMd5.update(mensagem.getBytes(), 0, mensagem.length());
		BigInteger hash = new BigInteger(1, mensagemMd5.digest());
		return hash.toString(16);
	}
	
}
