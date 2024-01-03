package facade;

import sun.misc.BASE64Encoder;

public class CipherMachine {
	public String Encrypt(String plainStr){
		return new BASE64Encoder().encode(plainStr.getBytes());
	}
	public static void main(String[] args) {
		System.out.println(new CipherMachine().Encrypt("hellojason"));
	}
}
