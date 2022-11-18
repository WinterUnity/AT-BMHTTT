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

	public void encryptFile(File srcFile, SecretKey inputKey, File destDir) throws Exception {
		if (inputKey == null) {
			throw new FileNotFoundException("Key not found");
		}
		if (srcFile.isFile()) {
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.ENCRYPT_MODE, inputKey);

			File destFile = new File(destDir.getAbsolutePath() + 
									"\\AESEncryptedFile" + getFileExtension(srcFile));

			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(srcFile));
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

	public void decryptFile(File srcFile, SecretKey inputKey, File destDir) throws Exception {
		if (inputKey == null) {
			throw new FileNotFoundException("Key not found");
		}
		if (srcFile.isFile()) {
			cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.DECRYPT_MODE, inputKey);
			File destFile = new File(destDir.getAbsolutePath() + 
									"\\AESDecryptedFile" + getFileExtension(srcFile));
			
			
			CipherInputStream cis = new CipherInputStream(new FileInputStream(srcFile), cipher);
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(destFile));
			byte[] input = new byte[1024];
			int byteRead;
			while ((byteRead = cis.read(input)) != -1) {
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