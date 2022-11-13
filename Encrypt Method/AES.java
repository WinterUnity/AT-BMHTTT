package EncryptMethod;

import java.io.*;
import java.security.NoSuchAlgorithmException;
import javax.crypto.*;

public class AES {
	SecretKey key;
	Cipher cipher;

	public SecretKey createKey() throws NoSuchAlgorithmException {
		KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
		keyGenerator.init(128);
		key = keyGenerator.generateKey();
		
		return key;
	}

	public SecretKey readKey(String path) throws ClassNotFoundException {
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(new FileInputStream(path));
			key = (SecretKey) ois.readObject();
			return key;
		} catch (IOException e) {
			System.out.println("Key cannot read from file");
			throw new RuntimeException(e);
		} catch (ClassNotFoundException e) {
			System.out.println("Key not found");
			throw new RuntimeException(e);
		}
	}

	public void encryptFile(String sourceFile, SecretKey inputKey, String destFile) throws Exception {
		if (inputKey == null) {
			throw new FileNotFoundException("Key not found");
		}
		File file = new File(sourceFile);
		if (file.isFile()) {
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.ENCRYPT_MODE, inputKey);

			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
			CipherOutputStream cos = new CipherOutputStream(new FileOutputStream(destFile), cipher);
			byte[] input = new byte[1024];
			int byteRead;
			while ((byteRead = bis.read(input)) != -1) {
				cos.write(input, 0, byteRead);
			}
			cos.flush();
			cos.close();
			bis.close();
		} else {
			System.out.println("This is not a file");
		}
	}
	
	public void decryptFile(String sourceFile, SecretKey inputKey, String destFile) throws Exception{
		if(inputKey == null) {
			throw new FileNotFoundException("Key not found");
		}
		File file = new File(sourceFile);
		if(file.isFile()) {
			cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.DECRYPT_MODE, inputKey);
			CipherInputStream cis = new CipherInputStream(new FileInputStream(file), cipher);
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(destFile));
			byte[] input = new byte[1024];
			int byteRead;
			while((byteRead = cis.read(input)) != -1) {
				bos.write(input, 0, byteRead);
			}
			bos.flush();
			bos.close();
			cis.close();
		} else {
			System.out.println("This is not a file");
		}
	}
	
	public String getFileExtension(File file) {
		String fileName = file.getName();
		
		int i = fileName.lastIndexOf(".");
		String fileExtension = fileName.substring(i);
		
		return fileExtension;
	}
}