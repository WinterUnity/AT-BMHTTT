package EncryptMethod;

import java.io.*;
import java.math.BigInteger;
import java.security.DigestInputStream;
import java.security.MessageDigest;

public class Hash {
	String name;
	MessageDigest md;
	
	public Hash(String name) throws Exception {
		this.name = name;
		this.md = MessageDigest.getInstance(name);
	}
	
	/**
	 * Mã hóa Text
	 * @param data
	 * @return
	 */
	public String hashText(String data) {
		if(this.md == null) {
			return "";
		}
		byte[] output = this.md.digest(data.getBytes());
		BigInteger bi = new BigInteger(1, output);
		return bi.toString(16);
	}
	
	/**
	 * Mã hóa File
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public String hashFile(String path) throws Exception {
		if(this.md == null) {
			return "";
		}
		File file = new File(path);
		if(file.exists()){
			DigestInputStream dis = new DigestInputStream(new BufferedInputStream(new FileInputStream(file)), md);
			int i;
			byte[] buff = new byte[1024];
			do {
				i = dis.read(buff);
			} while (i!=-1);
			BigInteger bi = new BigInteger(1, dis.getMessageDigest().digest());
			dis.close();
			return bi.toString(16);
		}
		return "";
	}
}