package EncryptMethod;

import java.io.*;
import java.net.URI;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import javax.crypto.SecretKey;

public class Caesar {
	final String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	int key;
	String encryptedString, decryptedString;
	
	public String getEncryptedString() {
		return encryptedString;
	}

	public String getDecryptedString() {
		return decryptedString;
	}

	// Tạo khóa Caesar
	public int createKey() {
		Random rd = new Random();
		while (key == 0) {
			key = rd.nextInt(10);
		}
		return key;
	}

	// Đọc khóa Caesar
	public int readKey(File srcFile) throws IOException {
		FileReader fr = new FileReader(srcFile);
		BufferedReader br = new BufferedReader(fr);
		key = Integer.parseInt(br.readLine());
		return key;
	}

	// Mã hóa Caesar
	public void encrypt(String source, int input, File destDir) throws Exception {
		String plainText;
		File file = new File(source);

		// Check if source is file path or not
		if (file.isFile()) {
			Path path = Path.of(source);
			plainText = Files.readString(path);
		} else {
			plainText = source;
		}

		// Encrypting
		int position, count;
		int half = 26;
		encryptedString = "";
		key = input;
		for (int i = 0; i < plainText.length(); i++) {
			count = 0;
			for (int j = 0; j < alphabet.length(); j++) {
				if (plainText.charAt(i) == alphabet.charAt(j)) {
					if (j < half) {
						position = (j + key) % 26;
						encryptedString += alphabet.charAt(position);
					} else {
						position = ((j + key) % 26) + half;
						encryptedString += alphabet.charAt(position);
					}
				} else {
					count++;
					if (count == 52) {
						encryptedString += plainText.charAt(i);
					}
				}
			}
		}
		
		//Create encrypted file
		try {
			File destFile = new File(destDir.getAbsolutePath() + "\\CaesarEncrypted.txt");
			FileWriter fw = new FileWriter(destFile);
			PrintWriter pw = new PrintWriter(fw);
			pw.println(encryptedString);
			pw.close();
			fw.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	// Giải mã Caesar
	public void decrypt(String source, int input, File destDir) throws Exception {
		String cipherText;
		File file = new File(source);

		// Check if source is file path or not
		if (file.isFile()) {
			Path path = Path.of(source);
			cipherText = Files.readString(path);
		} else {
			cipherText = source;
		}

		//Decrypting
		int position, count;
		int half = 26;
		decryptedString = "";
		key = input;
		for (int i = 0; i < cipherText.length(); i++) {
			count = 0;
			for (int j = 0; j < alphabet.length(); j++) {
				if (cipherText.charAt(i) == alphabet.charAt(j)) {
					if (j < half) {
						if (j - key < 0) {
							position = (j - key + half) % 26;
							decryptedString += alphabet.charAt(position);
						} else {
							position = (j - key) % 26;
							decryptedString += alphabet.charAt(position);
						}
					} else {
						position = ((j - key) % 26) + half;
						decryptedString += alphabet.charAt(position);
					}
				} else {
					count++;
					if (count == 52) {
						decryptedString += cipherText.charAt(i);
					}
				}
			}
		}

		//Create decrypted file
		try {
			File destFile = new File(destDir.getAbsolutePath() + "\\CaesarDecrypted.txt");
			FileWriter fw = new FileWriter(destFile);
			PrintWriter pw = new PrintWriter(fw);
			pw.println(decryptedString);
			pw.close();
			fw.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void main(String[] args) throws Exception {
		Caesar c = new Caesar();
		File keyFile = new File("E:\\key.txt");
		
//		c.createKey();
		c.readKey(keyFile);
//		c.encrypt("D:\\BMHTTT\\Caesar\\SourceText.txt");
//		c.decrypt("CipherText.txt");
	}
}