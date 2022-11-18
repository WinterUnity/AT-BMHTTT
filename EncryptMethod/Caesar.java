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
	byte[] encryptedBytes, decryptedBytes;

	public String getEncryptedString() {
		return encryptedString;
	}

	public String getDecryptedString() {
		return decryptedString;
	}

	/**
	 * Tạo khóa Caesar
	 * 
	 * @return key
	 */
	public int createKey() {
		Random rd = new Random();
		while (key == 0) {
			key = rd.nextInt(10);
		}
		return key;
	}

	/**
	 * Đọc khóa Caesar
	 * 
	 * @param srcFile
	 * @return key
	 * @throws IOException
	 */
	public int readKey(File srcFile) throws IOException {
		FileReader fr = new FileReader(srcFile);
		BufferedReader br = new BufferedReader(fr);
		key = Integer.parseInt(br.readLine());
		br.close();

		return key;
	}

	/**
	 * Mã hóa Caesar - Text
	 * 
	 * @param source
	 * @param input
	 * @param destDir
	 * @throws Exception
	 */
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
		int position, count, length = alphabet.length(), half = alphabet.length() / 2;
		encryptedString = "";
		key = input;
		for (int i = 0; i < plainText.length(); i++) {
			count = 0;
			for (int j = 0; j < alphabet.length(); j++) {
				if (plainText.charAt(i) == alphabet.charAt(j)) {
					if (j < half) {
						position = (j + key) % half;
						encryptedString += alphabet.charAt(position);
					} else {
						position = ((j + key) % half) + half;
						encryptedString += alphabet.charAt(position);
					}
				} else {
					count++;
					if (count == length) {
						encryptedString += plainText.charAt(i);
					}
				}
			}
		}

		// Create encrypted file
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

	/**
	 * Mã hóa Caesar - File
	 * 
	 * @param srcFile
	 * @param input
	 * @param destDir
	 * @throws Exception
	 */
	public void encryptFile(File srcFile, int input, File destDir) throws Exception {
		byte[] fileContent = Files.readAllBytes(srcFile.toPath());

		// Check if source is file path or not
		if (srcFile.isFile()) {
			// Encrypting
			encryptedBytes = new byte[fileContent.length];
			key = input;

			for (int i = 0; i < fileContent.length; i++) {
				fileContent[i] = (byte) (fileContent[i] + key);
			}

			// Create encrypted file
			try {
				File destFile = new File(
						destDir.getAbsolutePath() + "\\CaesarEncryptedFile" + getFileExtension(srcFile));
				FileOutputStream fos = new FileOutputStream(destFile);
				fos.write(fileContent);
				fos.close();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		} else {
			System.out.println("This is not a file");
		}
	}

	/**
	 * Giải mã Caesar - Text
	 * 
	 * @param source
	 * @param input
	 * @param destDir
	 * @throws Exception
	 */
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

		// Decrypting
		int position, count;
		int length = alphabet.length(), half = alphabet.length() / 2;
		decryptedString = "";
		key = input;
		for (int i = 0; i < cipherText.length(); i++) {
			count = 0;
			for (int j = 0; j < alphabet.length(); j++) {
				if (cipherText.charAt(i) == alphabet.charAt(j)) {
					if (j < half) {
						if (j - key < 0) {
							position = (j - key + half) % half;
							decryptedString += alphabet.charAt(position);
						} else {
							position = (j - key) % half;
							decryptedString += alphabet.charAt(position);
						}
					} else {
						position = ((j - key) % 26) + half;
						decryptedString += alphabet.charAt(position);
					}
				} else {
					count++;
					if (count == length) {
						decryptedString += cipherText.charAt(i);
					}
				}
			}
		}

		// Create decrypted file
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

	/**
	 * Giải mã Caesar - File
	 * 
	 * @param srcFile
	 * @param input
	 * @param destDir
	 * @throws Exception
	 */
	public void decryptFile(File srcFile, int input, File destDir) throws Exception {
		byte[] fileContent = Files.readAllBytes(srcFile.toPath());

		// Check if source is file path or not
		if (srcFile.isFile()) {
			// Decrypting
			decryptedBytes = new byte[fileContent.length];
			key = input;

			for (int i = 0; i < fileContent.length; i++) {
				fileContent[i] = (byte) (fileContent[i] - key);
			}

			// Create decrypted file
			try {
				File destFile = new File(
						destDir.getAbsolutePath() + "\\CaesarDecryptedFile" + getFileExtension(srcFile));
				FileOutputStream fos = new FileOutputStream(destFile);
				fos.write(fileContent);
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