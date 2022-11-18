package EncryptMethod;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class RSA {
	PrivateKey privateKey;
	PublicKey publicKey;
	KeyPair keyPair;
	byte[] encodedPublicKey, encodedPrivateKey;
	byte[] encryptedBytes, decryptedBytes;
	String decryptedString;
	
	public PublicKey getPublicKey() {
		return publicKey;
	}
	
	public PrivateKey getPrivateKey() {
		return privateKey;
	}
	
	public byte[] getEncodedPublicKey() {
		return encodedPublicKey;
	}
	
	public byte[] getEncodedPrivateKey() {
		return encodedPrivateKey;
	}
	
	public String getDecryptedString() {
		return decryptedString;
	}

	/**
	 * Tạo khóa
	 */
	public void createKey() {
		try {
			KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
			generator.initialize(2048);
			keyPair = generator.generateKeyPair();
			publicKey = keyPair.getPublic();
			privateKey = keyPair.getPrivate();
			
			encodedPublicKey = publicKey.getEncoded();
			encodedPrivateKey = privateKey.getEncoded();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Đọc khóa
	 * @param path
	 * @return
	 * @throws ClassNotFoundException
	 * @throws IOException 
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidKeySpecException 
	 */
	public PublicKey readPublicKey(File file) throws ClassNotFoundException, IOException, NoSuchAlgorithmException, InvalidKeySpecException {
		encodedPublicKey = Files.readAllBytes(file.toPath());
		KeyFactory kf = KeyFactory.getInstance("RSA");
		publicKey = kf.generatePublic(new X509EncodedKeySpec(encodedPublicKey));
		
		return publicKey;
	}
	
	public PrivateKey readPrivateKey(File file) throws ClassNotFoundException, IOException, NoSuchAlgorithmException, InvalidKeySpecException {
		encodedPrivateKey = Files.readAllBytes(file.toPath());
		KeyFactory kf = KeyFactory.getInstance("RSA");
		privateKey = kf.generatePrivate(new PKCS8EncodedKeySpec(encodedPrivateKey));
		
		return privateKey;
	}

	/**
	 * Mã hóa RSA - Text
	 * @param source
	 * @param inputKey
	 * @param destDir
	 * @throws Exception
	 */
	public void encrypt(String source, PublicKey inputKey, File destDir) throws Exception {
		String plainText;
		File file = new File(source);
		if (file.isFile()) {
			Path path = Path.of(source);
			plainText = Files.readString(path);
		} else {
			plainText = source;
		}

		publicKey = inputKey;
		if (plainText.length() > 256) {
			System.out.println("Text length exceed encryptable length");
		} else {
			byte[] data = plainText.getBytes();
			try {
				Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
				cipher.init(Cipher.ENCRYPT_MODE, publicKey);
				byte[] bytes = cipher.doFinal(data);
				encryptedBytes = bytes;
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		// Create encrypted file
		try {
			File destFile = new File(destDir.getAbsolutePath() + "\\RSAEncrypted.txt");
			FileOutputStream fos = new FileOutputStream(destFile);
			fos.write(encryptedBytes);
			fos.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Mã hóa RSA - File
	 * @param srcFile
	 * @param inputKey
	 * @param destDir
	 * @throws Exception
	 */
	public void encryptFile(File srcFile, PublicKey inputKey, File destDir) throws Exception {
		byte[] fileContent = Files.readAllBytes(srcFile.toPath());
		publicKey = inputKey;

		if (srcFile.isFile()) {
			Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			
			int number = fileContent.length / 245;
			int modulo = fileContent.length % 245;
			List<byte[]> list = new ArrayList<byte[]>();
			
			byte[] subData, lastData;
			for (int i = 0; i < number; i++) {
				subData = Arrays.copyOfRange(fileContent, 245 * i, 245 * (i + 1));
				byte[] bytes = cipher.doFinal(subData);
				list.add(bytes);
			}
			lastData = new byte[modulo];
			lastData = Arrays.copyOfRange(fileContent, 245*number, fileContent.length);
			byte[] bytes = cipher.doFinal(lastData);
			list.add(bytes);
			
			int length = 256*list.size();
			encryptedBytes = new byte[length];
			for (int l = 0; l < list.size(); l++) {
				for (int b = 0; b < list.get(l).length; b++) {
					encryptedBytes[256*l+b] = list.get(l)[b];
				}
			}

			// Create encrypted file
			try {
				File destFile = new File(destDir.getAbsolutePath() + "\\RSAEncryptedFile" + getFileExtension(srcFile));
				FileOutputStream fos = new FileOutputStream(destFile);
				fos.write(encryptedBytes);
				fos.close();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		} else {
			System.out.println("This is not a file");
		}
	}

	/**
	 * Giải mã RSA - Text
	 * @param sourceFile
	 * @param inputKey
	 * @param destDir
	 * @throws IOException
	 */
	public void decrypt(String sourceFile, PrivateKey inputKey, File destDir) throws IOException {
		File file = new File(sourceFile);
		byte[] fileContent = Files.readAllBytes(file.toPath());
		
		if (file.isFile()) {
			try {
				Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
				cipher.init(Cipher.DECRYPT_MODE, privateKey);
				byte[] bytes = cipher.doFinal(fileContent);
				decryptedString =  new String(bytes);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		} else {
			System.out.println("This is not a file");
		}
		
		//Create decrypt file
		try {
			File destFile = new File(destDir.getAbsolutePath() + "\\RSADecrypted.txt");
			FileWriter fw = new FileWriter(destFile);
			PrintWriter pw = new PrintWriter(fw);
			pw.println(decryptedString);
			pw.close();
			fw.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Giải mã RSA - File
	 * @param srcFile
	 * @param inputKey
	 * @param destDir
	 * @throws Exception
	 */
	public void decryptFile(File srcFile, PrivateKey inputKey, File destDir) throws Exception {
		byte[] fileContent = Files.readAllBytes(srcFile.toPath());
		privateKey = inputKey;
		
		if (srcFile.isFile()) {
			Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			
			int number = fileContent.length / 256;
			List<byte[]> list = new ArrayList<byte[]>();
			
			byte[] subData;
			for (int i = 0; i < number; i++) {
				subData = Arrays.copyOfRange(fileContent, 256 * i, 256 * (i + 1));
				byte[] bytes = cipher.doFinal(subData);
				list.add(bytes);
			}
			
			int length = 0;
			for (int i = 0; i < list.size(); i++) {
				length += list.get(i).length;
			}
			
			int currentPosition = 0;
			decryptedBytes = new byte[length];
			for (int l = 0; l < list.size(); l++) {
				for (int b = 0; b < list.get(l).length; b++) {
					decryptedBytes[currentPosition] = list.get(l)[b];
					currentPosition++;
				}
			}

			// Create decrypted file
			try {
				File destFile = new File(destDir.getAbsolutePath() + "\\RSADecryptedFile" + getFileExtension(srcFile));
				FileOutputStream fos = new FileOutputStream(destFile);
				fos.write(decryptedBytes);
				fos.close();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		} else {
			System.out.println("This is not a file");
		}
	}
	
	// Lấy extension của file
	public String getFileExtension(File file) {
		String fileName = file.getName();

		int i = fileName.lastIndexOf(".");
		String fileExtension = fileName.substring(i);

		return fileExtension;
	}
}